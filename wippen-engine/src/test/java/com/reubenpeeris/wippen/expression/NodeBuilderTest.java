package com.reubenpeeris.wippen.expression;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;
import com.reubenpeeris.wippen.expression.PairNode.Validator;

public class NodeBuilderTest extends BaseTest {
	@Test
	public void constructor_throws_for_null_validator() {
		expect(NullPointerException.class, "validator");
		new MockNodeBuilder(false, null);
	}

	private static class MockNodeBuilder extends NodeBuilder {
		public MockNodeBuilder(boolean failEarly, Validator validator) {
			super(validator);
		}

		@Override
		PairNode build(Expression left, Expression right) {
			return null;
		}
	}
}
