package com.reubenpeeris.wippen.robotloader;

public class LoaderException extends RuntimeException {
	private static final long serialVersionUID = 8231925290841379519L;

	public LoaderException(String message) {
		super(message);
	}

	public LoaderException(String message, Throwable cause) {
		super(message, cause);
	}
}
