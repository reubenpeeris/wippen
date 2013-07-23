package com.reubenpeeris.wippen.expression;


public class MultiplyTest extends OperatorTest {
	public MultiplyTest() {
		super(new Multiply.Builder(), Multiply.class);
	}

	@Override
	protected int getResult4And2() {
		return 8;
	}

	@Override
	protected String getSymbol() {
		return "*";
	}
}