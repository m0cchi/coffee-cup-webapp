package net.m0cchi.util;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public class HttpUtil {
	public static void send500(HttpExchange exchange) throws IOException {
		send(exchange, 500, "Internal Server Error");
	}
	
	public static void send404(HttpExchange exchange) throws IOException {
		send(exchange, 404, "Not Found");
	}

	public static void send(HttpExchange exchange, int status, String str) throws IOException {
		byte[] bytes = (status + " " + str).getBytes();
		exchange.sendResponseHeaders(status, bytes.length);
		exchange.getResponseBody().write(bytes);
		exchange.close();
	}
}
