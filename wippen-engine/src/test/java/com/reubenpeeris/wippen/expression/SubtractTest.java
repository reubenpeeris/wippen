package com.reubenpeeris.wippen.expression;

public class SubtractTest extends BaseOperatorTest {
	public SubtractTest() {
		super(Subtract.builder(), Subtract.class);
	}

	@Override
	protected int getExpectedValue() {
		return 2;
	}

	@Override
	protected String getSymbol() {
		return "-";
	}
}
