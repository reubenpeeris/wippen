package com.reubenpeeris.wippen.expression;

final class Subtract extends PairNode {
	public static NodeBuilder builder() {
		return new NodeBuilder() {
			@Override
			public PairNode build(Expression left, Expression right) {
				return new Subtract(left, right);
			}
		};
	}

	Subtract(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	protected int getValue(int left, int right) {
		return left - right;
	}

	@Override
	protected String getOperatorSymbol() {
		return "-";
	}
}
