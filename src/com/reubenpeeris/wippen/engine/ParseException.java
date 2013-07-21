package com.reubenpeeris.wippen.engine;

public class ParseException extends Exception {
	private static final long serialVersionUID = -7872542684911345395L;

	public ParseException(String message) {
		super(message);
	}

	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}
}