package com.reubenpeeris.wippen.expression;


public class SubtractTest extends OperatorTest {
	public SubtractTest() {
		super(new Subtract.Builder(), Subtract.class);
	}

	@Override
	protected int getResult4And2() {
		return 2;
	}

	@Override
	protected String getSymbol() {
		return "-";
	}
}
