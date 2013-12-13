package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.ObjectMother.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class EqualsTest {
	@Test
	public void testBuilderBuildsCorrectClass() {
		PairNode pairNode = Equals.builder().build(s1, c1);
		assertSame(Equals.class, pairNode.getClass());
	}

	@Test
	public void testGetValue() {
		PairNode pairNode = Equals.builder().build(s1, c1);
		assertEquals(1, pairNode.getValue());
	}

	@Test
	public void testGetOperatorSymbol() {
		PairNode pairNode = Equals.builder().build(s1, c1);
		assertEquals("=", pairNode.getOperatorSymbol());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNonEqualArgs() {
		new Equals(s1, s2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuilderWithNonEqualArgs() {
		Equals.builder().build(s1, s2);
	}
}
