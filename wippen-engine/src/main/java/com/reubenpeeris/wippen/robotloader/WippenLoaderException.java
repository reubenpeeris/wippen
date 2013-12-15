package com.reubenpeeris.wippen.robotloader;

public class WippenLoaderException extends RuntimeException {
	private static final long serialVersionUID = 8231925290841379519L;

	public WippenLoaderException(String message) {
		super(message);
	}

	public WippenLoaderException(String message, Throwable cause) {
		super(message, cause);
	}
}
