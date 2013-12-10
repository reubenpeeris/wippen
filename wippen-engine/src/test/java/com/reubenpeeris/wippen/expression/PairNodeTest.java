package com.reubenpeeris.wippen.expression;

import org.junit.Test;

import static com.reubenpeeris.wippen.Cards.*;
import static org.junit.Assert.*;

public class PairNodeTest {
	private static final class MockPairNode extends PairNode {
		public MockPairNode(Expression left, Expression right) {
			super(left, right);
		}

		@Override
		String getOperatorSymbol() {
			return null;
		}

		@Override
		int getValue(int left, int right) {
			return 0;
		}
	}

	@Test
	public void testAlwayValidValidator() {
		assertTrue(PairNode.ALWAYS_VALID.isValid(1, 1));
		assertFalse(PairNode.ALWAYS_VALID.canParentEquals());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPairNodeLeftNull() {
		new MockPairNode(null, s2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPairNodeRightNull() {
		new MockPairNode(s2, null);
	}
}
