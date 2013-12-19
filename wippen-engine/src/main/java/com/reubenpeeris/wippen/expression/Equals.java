package com.reubenpeeris.wippen.expression;

final class Equals extends PairNode {
	private static final Validator EQUALS_VALIDAOTR = new Validator(Equals.class) {
		@Override
		public boolean isValid(int left, int right) {
			return left == right;
		}
	};

	public static NodeBuilder builder() {
		return new NodeBuilder(EQUALS_VALIDAOTR) {
			@Override
			protected PairNode build(Expression left, Expression right) {
				return new Equals(left, right);
			}
		};
	}

	Equals(Expression left, Expression right) {
		super(left, right, EQUALS_VALIDAOTR);
	}

	@Override
	boolean canHaveParent(Class<? extends Expression> clazz) {
		return clazz == Equals.class;
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
