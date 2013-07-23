package com.reubenpeeris.wippen.expression;


public final class Multiply extends PairNode {
	public static final class Builder extends NodeBuilder {
		@Override
		public PairNode build(Expression left, Expression right) {
			return new Multiply(left, right);
		}
	}
	
	public Multiply(Expression left, Expression right) {
		super(left, right);
	}
	
	@Override
	protected int getValue(int left, int right) {
		return left * right;
	}

	@Override
	protected String getOperatorSymbol() {
		return "*";
	}
}