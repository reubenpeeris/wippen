package com.reubenpeeris.wippen.engine;

public class WippenRuleException extends RuntimeException {
	private static final long serialVersionUID = -3206573797456650542L;

	public WippenRuleException() {
		super();
	}

	public WippenRuleException(String message, Throwable cause) {
		super(message, cause);
	}

	public WippenRuleException(String message) {
		super(message);
	}

	public WippenRuleException(Throwable cause) {
		super(cause);
	}
}
