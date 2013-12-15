package com.reubenpeeris.wippen.expression;

public class SubtractTest extends OperatorTest {
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
