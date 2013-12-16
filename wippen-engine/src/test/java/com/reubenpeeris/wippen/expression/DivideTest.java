package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.TestData.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class DivideTest extends BaseOperatorTest {
	public DivideTest() {
		super(Divide.builder(), Divide.class);
	}

	@Override
	protected int getExpectedValue() {
		return 2;
	}

	@Override
	protected String getSymbol() {
		return "/";
	}

	@Test
	public void constructor_throws_for_non_divisible_numbers() {
		thrown.expect(IllegalArgumentException.class);
		new Divide(c2, c3);
	}

	@Test
	public void builder_returns_null_for_non_divisible_numbers() {
		assertThat(Divide.builder().left(c2).right(c3).build(), is(nullValue()));
	}
}
