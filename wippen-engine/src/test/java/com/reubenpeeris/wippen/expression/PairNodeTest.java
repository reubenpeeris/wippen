package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.TestData.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;

public class PairNodeTest extends BaseTest {
	private static final class MockPairNode extends PairNode {
		public MockPairNode(Expression left, Expression right) {
			super(left, right);
		}

		public MockPairNode(Expression left, Expression right, Validator validator) {
			super(left, right, validator);
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
	public void alway_valid_validator_is_valid_for_non_null_inputs() {
		assertThat(PairNode.ALWAYS_VALID.isValid(s1, s1), is(true));
	}

	@Test
	public void alway_valid_validator_is_not_valid_for_null_left() {
		assertThat(PairNode.ALWAYS_VALID.isValid(null, s1), is(false));
	}

	@Test
	public void alway_valid_validator_is_not_valid_for_null_right() {
		assertThat(PairNode.ALWAYS_VALID.isValid(s1, null), is(false));
	}

	@Test
	public void constructor_throws_for_null_left() {
		expect(NullPointerException.class, "left");
		new MockPairNode(null, s2);
	}

	@Test
	public void constructor_throws_for_null_right() {
		expect(NullPointerException.class, "right");
		new MockPairNode(s2, null);
	}

	@Test
	public void constructor_throws_for_null_validator() {
		expect(NullPointerException.class, "validator");
		new MockPairNode(s2, s2, null);
	}

	@Test
	public void two_PairNodes_of_same_type_with_equivalent_expressions_are_equal() {
		PairNode node1 = new MockNode1(c1, new Add(c2, c3));
		PairNode node2 = new MockNode1(c1, new Add(c2, c3));

		assertThat(node1.equals(node2), is(true));
	}

	@Test
	public void two_PairNodes_of_different_type_with_equivalent_expressions_are_not_equal() {
		PairNode node1 = new MockNode1(c1, new Add(c2, c3));
		PairNode node2 = new MockNode2(c1, new Add(c2, c3));

		assertThat(node1.equals(node2), is(false));
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
