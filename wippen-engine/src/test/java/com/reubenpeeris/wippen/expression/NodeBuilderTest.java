package com.reubenpeeris.wippen.expression;

import org.junit.Test;

public class NodeBuilderTest {
	@Test(expected = IllegalArgumentException.class)
	public void testNodeBuilderWithNullValidator() {
		new NodeBuilder(null) {
			@Override
			PairNode build(Expression left, Expression right) {
				return null;
			}
		};
	}
}
