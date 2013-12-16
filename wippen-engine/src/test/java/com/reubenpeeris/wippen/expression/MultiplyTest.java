package com.reubenpeeris.wippen.expression;

public class MultiplyTest extends BaseOperatorTest {
	public MultiplyTest() {
		super(Multiply.builder(), Multiply.class);
	}

	@Override
	protected int getExpectedValue() {
		return 8;
	}

	@Override
	protected String getSymbol() {
		return "*";
	}
}
