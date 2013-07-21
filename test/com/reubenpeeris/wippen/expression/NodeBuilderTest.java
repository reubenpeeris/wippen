package com.reubenpeeris.wippen.expression;

import org.junit.Test;

public class NodeBuilderTest {
	@Test(expected=IllegalArgumentException.class)
	public void testNodeBuilderWithNullValidator() {
		new NodeBuilder(null) {
			@Override
			PairNode build(Expression left, Expression right) {
				return null;
			}
		};
	}
	
//	private static class MockPairNode extends PairNode {
//		private final Expression left;
//		private final Expression right;
//		
//		public MockPairNode(Expression left, Expression right) {
//			super(left, right);
//			
//			this.left = left;
//			this.right = right;
//		}
//
//		public Expression getLeft() {
//			return left;
//		}
//
//		public Expression getRight() {
//			return right;
//		}
//	}
	
//	@Test
//	public void testSet
}
