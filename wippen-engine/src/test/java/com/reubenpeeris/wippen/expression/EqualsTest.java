package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.TestData.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class EqualsTest extends OperatorTest {
	public EqualsTest() {
		super(Equals.builder(), Equals.class);
	}

	@Override
	protected int getExpectedValue() {
		return 2;
	}

	@Override
	protected Card getLeft() {
		return h2;
	}

	@Override
	protected String getSymbol() {
		return "=";
	}

	@Test
	public void builder_returns_null_for_non_equal_values() {
		assertThat(Equals.builder().left(s1).right(s2).build(), is(nullValue()));
	}
}
