package com.reubenpeeris.wippen.expression;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;

public class NodeBuilderTest extends BaseTest {
	@Test
	public void constructor_throws_for_null_validator() {
		expect(NullPointerException.class, "validator");
		new ValidatorlessNodeBuilder();
	}

	private static class ValidatorlessNodeBuilder extends NodeBuilder {
		public ValidatorlessNodeBuilder() {
			super(null);
		}
		
		@Override
		PairNode build(Expression left, Expression right) {
			return null;
		}
	}
}
