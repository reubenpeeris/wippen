package com.reubenpeeris.wippen.expression;

import com.reubenpeeris.wippen.expression.PairNode.Validator;


public abstract class NodeBuilder {
	private final Validator validator;
	private Expression left;
	private Expression right;
	
	abstract PairNode build(Expression left, Expression right);

	public PairNode build() {
		Expression left = this.left;
		Expression right = this.right;
		
		if (validator.isValid(left, right)) {
			return build(left, right);
		} else {
			return null;
		}
	}
	
	NodeBuilder(Validator validator) {
		if (validator == null) {
			throw new IllegalArgumentException();
		}
		
		this.validator = validator;
	}
	
	NodeBuilder() {
		this(PairNode.ALWAYS_VALID);
	}

	public NodeBuilder left(Expression left) {
		this.left = left;
		return this;
	}

	public NodeBuilder right(Expression right) {
		this.right = right;
		return this;
	}
}
