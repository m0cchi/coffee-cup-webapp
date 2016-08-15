package net.m0cchi.util;

import java.io.IOException;
import java.io.InputStream;

import net.m0cchi.parser.semantic.SemanticAnalyzer;
import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.SList;
import net.m0cchi.value.Value;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ElementContext implements HttpHandler {
	private enum HTTP_METHOD {
		GET, POST
	}

	Environment environment;
	Value<?> proc;

	public ElementContext(Environment environment, Value<?> proc) {
		this.environment = environment;
		this.proc = proc;
	}

	public static byte[] toBytes(Value<?> value) {
		Object object = value.getNativeValue();
		return (byte[]) (object instanceof byte[] ? object : object.toString().getBytes());
	}

	public static void setQueryString(Environment environment, String querys) {
		if(querys == null) {
			return;
		}
		for (String query : querys.split("&")) {
			String[] pair = query.split("=");
			if (pair.length == 2) {
				environment.defineVariable(pair[0], new Value<String>(AtomicType.LETTER, pair[1]));
			} else {
				environment.defineVariable(pair[0], new Value<Boolean>(AtomicType.BOOL, true));
			}
		}
	}

	private void handlePost(HttpExchange exchange, Environment environment) throws Throwable {
		String type = exchange.getRequestHeaders().getFirst("Content-Type");
		String lengthStr = exchange.getRequestHeaders().getFirst("Content-Type");
		int length = Integer.parseInt(lengthStr);
		if (type == "application/x-www-form-urlencoded") {
			InputStream is = exchange.getRequestBody();
			byte[] buf = new byte[length];
			int count = 0;
			while ((count += is.read(buf, count, length - count)) >= 0)
				;
			setQueryString(environment, new String(buf));
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void handle(HttpExchange exchange) throws IOException {
		Environment environment = getEnvironment();
		SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(environment);
		try {

			setQueryString(environment, exchange.getRequestURI().getQuery());
		} catch (Exception e) {
			e.printStackTrace();
			HttpUtil.send500(exchange);
			return;
		}

		String method = exchange.getRequestMethod();
		try {
			if (method.equalsIgnoreCase(HTTP_METHOD.POST.name())) {
				handlePost(exchange, environment);
			}
		} catch (Throwable e) {
			e.printStackTrace();
			HttpUtil.send500(exchange);
			return;
		}

		Element element = semanticAnalyzer.evaluate(proc);
		Headers responseHeaders = exchange.getResponseHeaders();
		int status = 500;
		byte[] body = new byte[0];
		if (element instanceof SList) {
			// 0: status
			// 1: header
			// last: body
			SList ret = (SList) element;
			Element[] values = ret.toArray();
			status = ((Value<Integer>) values[0]).getNativeValue();
			if (values.length == 2) {
				body = toBytes((Value<?>) values[1]);
			} else {
				body = toBytes((Value<?>) values[2]);
				SList headers = (SList) values[1];
				for (Element tmp : headers.toArray()) {
					SList header = (SList) tmp;
					// length == 2
					String name = ((Value<String>) header.get(0)).getNativeValue();
					String value = ((Value<String>) header.get(1)).getNativeValue();
					responseHeaders.add(name, value);
				}
			}
		} else {
			// body
			status = 200;
			body = toBytes((Value<?>) element);
		}
		exchange.sendResponseHeaders(status, body.length);
		exchange.getResponseBody().write(body);
		exchange.close();
	}

	private Environment getEnvironment() {
		return new Environment(this.environment);
	}

}
