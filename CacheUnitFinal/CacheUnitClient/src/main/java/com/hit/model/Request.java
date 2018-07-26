package main.java.com.hit.model;

import java.io.Serializable;
import java.util.Map;

public class Request<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, String> headers = null;

	private T body;

	public Request(Map<String, String> headers, T body) {
		this.headers = headers;
		this.body = body;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		for (Map.Entry<String, String> currentItem : headers.entrySet()) {
			this.headers.put(currentItem.getKey(), currentItem.getValue());
		}
	}

	public T getBody() {
		return this.body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	@Override
	public String toString() {
		String stringToReturn = "";
		for (Map.Entry<String, String> currentItem : headers.entrySet()) {
			stringToReturn.concat(currentItem.getKey() + " " + currentItem.getValue() + "\n");
		}
		stringToReturn.concat("\n" + body.toString());
		return stringToReturn;
	}
}