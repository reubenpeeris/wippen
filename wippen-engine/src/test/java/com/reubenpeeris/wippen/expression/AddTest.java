package com.reubenpeeris.wippen.expression;

public class AddTest extends BaseOperatorTest {
	public AddTest() {
		super(Add.builder(), Add.class);
	}

	@Override
	protected int getExpectedValue() {
		return 6;
	}

	@Override
	protected String getSymbol() {
		return "+";
	}
}
