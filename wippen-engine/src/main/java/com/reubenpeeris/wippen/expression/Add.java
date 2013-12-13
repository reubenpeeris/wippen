package com.reubenpeeris.wippen.expression;

public final class Add extends PairNode {
	public static NodeBuilder builder() {
		return new NodeBuilder() {
			@Override
			protected PairNode build(Expression left, Expression right) {
				return new Add(left, right);
			}
		};
	}

	public Add(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	protected int getValue(int left, int right) {
		return left + right;
	}

	@Override
	protected String getOperatorSymbol() {
		return "+";
	}
}
