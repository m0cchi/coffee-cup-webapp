package net.m0cchi.util;

import java.io.IOException;

import net.m0cchi.parser.semantic.SemanticAnalyzer;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.SList;
import net.m0cchi.value.Value;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ElementContext implements HttpHandler {
	SemanticAnalyzer semanticAnalyzer;
	Value<?> proc;

	public ElementContext(Environment environment, Value<?> proc) {
		semanticAnalyzer = new SemanticAnalyzer(environment);
		this.proc = proc;
	}

	public static byte[] toBytes(Value<?> value) {
		Object object = value.getNativeValue();
		return (byte[]) (object instanceof byte[] ? object : object.toString().getBytes());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void handle(HttpExchange exchange) throws IOException {
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

}
