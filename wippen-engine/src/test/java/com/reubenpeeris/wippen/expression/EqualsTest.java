package com.reubenpeeris.wippen.expression;

import org.junit.Test;

import static com.reubenpeeris.wippen.Cards.*;

import static org.junit.Assert.*;

public class EqualsTest {
	@Test
	public void testBuilderBuildsCorrectClass() {
		PairNode pairNode = new Equals.Builder().build(s1, c1);
		assertSame(Equals.class, pairNode.getClass());
	}
	
	@Test
	public void testGetValue() {
		PairNode pairNode = new Equals.Builder().build(s1, c1);
		assertEquals(1, pairNode.getValue());
	}

	@Test
	public void testGetOperatorSymbol() {
		PairNode pairNode = new Equals.Builder().build(s1, c1);
		assertEquals("=", pairNode.getOperatorSymbol());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorWithNonEqualArgs() {
		new Equals(s1, s2);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testBuilderWithNonEqualArgs() {
		new Equals.Builder().build(s1, s2);
	}
}
