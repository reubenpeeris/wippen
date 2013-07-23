package com.reubenpeeris.wippen.expression;


public final class Subtract extends PairNode {
	public static final class Builder extends NodeBuilder {
		@Override
		public PairNode build(Expression left, Expression right) {
			return new Subtract(left, right);
		}
	}
	
	public Subtract(Expression left, Expression right) {
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
