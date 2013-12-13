package com.reubenpeeris.wippen.engine;

public class WippenIllegalFormatException extends IllegalArgumentException {
	private static final long serialVersionUID = 0L;

	public WippenIllegalFormatException(String message) {
		super(message);
	}

	public WippenIllegalFormatException(String message, Throwable cause) {
		super(message, cause);
	}
}