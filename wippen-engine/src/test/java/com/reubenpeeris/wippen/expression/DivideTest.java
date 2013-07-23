package com.reubenpeeris.wippen.expression;

import org.junit.Test;
import static com.reubenpeeris.wippen.Cards.*;

public class DivideTest extends OperatorTest {
	public DivideTest() {
		super(new Divide.Builder(), Divide.class);
	}

	@Override
	protected int getResult4And2() {
		return 2;
	}

	@Override
	protected String getSymbol() {
		return "/";
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNonDivisibleNumbers() {
		new Divide(c2, c3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testBuilderNonDivisibleNumbers() {
		new Divide.Builder().build(c2, c3);
	}
}
