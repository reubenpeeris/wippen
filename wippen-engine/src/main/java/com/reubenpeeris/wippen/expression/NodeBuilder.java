package com.reubenpeeris.wippen.expression;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.reubenpeeris.wippen.expression.PairNode.Validator;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class NodeBuilder {
	@NonNull
	private final Validator validator;
	private Expression left;
	private Expression right;

	abstract Expression build(Expression left, Expression right);

	public Expression build() {
		Expression left = this.left;
		Expression right = this.right;

		if (validator.isValid(left, right)) {
			return build(left, right);
		} else {
			return null;
		}
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
