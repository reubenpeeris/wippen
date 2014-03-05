package com.reubenpeeris.wippen.expression;

final class Divide extends PairNode {
	private static final Validator DIVIDE_VALIDATOR = new Validator(Divide.class) {
		@Override
		public boolean isValid(int left, int right) {
			return right != 0 && left % right == 0;
		}
	};

	public static NodeBuilder builder() {
		return new NodeBuilder(DIVIDE_VALIDATOR) {
			@Override
			protected PairNode build(Expression left, Expression right) {
				return new Divide(left, right);
			}
		};
	}

	Divide(Expression left, Expression right) {
		super(left, right, DIVIDE_VALIDATOR);
	}

	@Override
	protected int getValue(int left, int right) {
		return left / right;
	}

	@Override
	protected String getOperatorSymbol() {
		return "/";
	}
}
