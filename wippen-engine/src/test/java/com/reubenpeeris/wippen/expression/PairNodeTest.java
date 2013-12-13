package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.ObjectMother.*;
import static org.junit.Assert.*;

import org.junit.Test;

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
		assertFalse(PairNode.ALWAYS_VALID.canHaveLeftChildOfTypeEqual());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPairNodeLeftNull() {
		new MockPairNode(null, s2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPairNodeRightNull() {
		new MockPairNode(s2, null);
	}

	@Test
	public void assertThatTwoPairNodesOfSameTypeWithEquivalentExpressionsAreEqual() {
		PairNode node1 = new MockNode1(c1, new Add(c2, c3));
		PairNode node2 = new MockNode1(c1, new Add(c2, c3));

		assertTrue(node1.equals(node2));
	}

	@Test
	public void assertThatTwoPairNodesOfDifferentTypeWithEquivalentExpressionsAreNotEqual() {
		PairNode node1 = new MockNode1(c1, new Add(c2, c3));
		PairNode node2 = new MockNode2(c1, new Add(c2, c3));

		assertFalse(node1.equals(node2));
	}

	private class MockNode1 extends PairNode {
		MockNode1(Expression left, Expression right) {
			super(left, right);
		}

		@Override
		String getOperatorSymbol() {
			return "M";
		}

		@Override
		int getValue(int left, int right) {
			return 0;
		}
	}

	private class MockNode2 extends PairNode {
		MockNode2(Expression left, Expression right) {
			super(left, right);
		}

		@Override
		String getOperatorSymbol() {
			return "M";
		}

		@Override
		int getValue(int left, int right) {
			return 0;
		}
	}
}
