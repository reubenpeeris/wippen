package com.reubenpeeris.wippen.expression;


public class AddTest extends OperatorTest {
	public AddTest() {
		super(new Add.Builder(), Add.class);
	}

	@Override
	protected int getResult4And2() {
		return 6;
	}

	@Override
	protected String getSymbol() {
		return "+";
	}
}
