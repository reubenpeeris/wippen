package com.reubenpeeris.wippen.expression;


/*
 * This should be fine as a top level option only. Take for example 3 equal value piles with an equal value card in the hand,
 * they could as a triple equals: H=A=B=C, but it is always also possible to do H=A+B-C or H=A*B/C
 * Suppose there are 4:
 * H=A=B=C=D or H=A+B-C=D etc
 * 5 is the smallest that doesn't work though:
 * H=A=B=C=D=E or H=A+B-C=D=E
 * 
 * This should not occur with even 1 moderately good player.
 */
public final class Equals extends PairNode {
	private static final Validator EQUALS_VALIDAOTR = new Validator() {
		@Override
		public boolean isValid(int left, int right) {
			return left == right;
		}

		@Override
		public boolean canParentEquals() {
			return true;
		}
	};
	
	public static final class Builder extends NodeBuilder {
		public Builder() {
			super(EQUALS_VALIDAOTR);
		}
		
		@Override
		protected PairNode build(Expression left, Expression right) {
			return new Equals(left, right);
		}
	}
	
	public Equals(Expression left, Expression right) {
		super(left, right, EQUALS_VALIDAOTR);
	}
	
	@Override
	protected int getValue(int left, int right) {
		return left;
	}
	
	@Override
	protected String getOperatorSymbol() {
		return "=";
	}
}
