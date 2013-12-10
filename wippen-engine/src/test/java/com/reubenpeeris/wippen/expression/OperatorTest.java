package com.reubenpeeris.wippen.expression;

import org.junit.Test;

import static com.reubenpeeris.wippen.Cards.*;
import static org.junit.Assert.*;

public abstract class OperatorTest {
	private final NodeBuilder nodeBuilder;
	private final Class<? extends PairNode> clazz;
	
	public OperatorTest(NodeBuilder nodeBuilder, Class<? extends PairNode> clazz) {
		this.nodeBuilder = nodeBuilder;
		this.clazz = clazz;
	}
	
	@Test
	public void testBuilderBuildsCorrectClass() {
		PairNode pairNode = nodeBuilder.build(s2, s1);
		assertSame(clazz, pairNode.getClass());
	}
	
	@Test
	public void testGetValue() {
		PairNode pairNode = nodeBuilder.build(s4, s2);
		assertEquals(getResult4And2(), pairNode.getValue());
	}

	@Test
	public void testGetOperatorSymbol() {
		PairNode pairNode = nodeBuilder.build(s2, s1);
		assertEquals(getSymbol(), pairNode.getOperatorSymbol());
	}
	
	protected abstract int getResult4And2();
	protected abstract String getSymbol();
}
