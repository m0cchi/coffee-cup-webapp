package net.m0cchi.function;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.m0cchi.util.ElementContext;
import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.SList;
import net.m0cchi.value.Value;

import com.sun.net.httpserver.HttpServer;

public class HttpService extends Function {
	private static final long serialVersionUID = 5079977203818388645L;

	public HttpService() {
		setArgs(new String[] { "port number", " contexts" });
	}

	@Override
	public Element invoke(Environment environment) {
		Value<Integer> portValue = environment.getValue(getArgs()[0]);
		SList contexts = environment.getValue(getArgs()[1]);
		HttpServer server = null;
		try {
			server = HttpServer.create(new InetSocketAddress(portValue.getNativeValue()), 0);
			for (Element element : contexts.toArray()) {
				SList context = (SList) element;
				Value<String> path = context.get(0);
				Value<?> proc = context.get(1);
				server.createContext(path.getNativeValue(), new ElementContext(environment, proc));
			}
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Value<HttpServer>(AtomicType.JAVA, server);
	}

}
