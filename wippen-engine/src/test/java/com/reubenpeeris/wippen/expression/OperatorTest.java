package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.ObjectMother.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;

public abstract class OperatorTest extends BaseTest {
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

    @Test
    public void assertSettingLeftToEqualNodeTypeThrows() {
    	assertThat(nodeBuilder.left(new Equals(c1, s1)).right(c1).build(), is(nullValue()));
    }

    @Test
    public void assertSettingRightToEqualNodeTypeThrows() {
    	assertThat(nodeBuilder.left(c1).right(new Equals(c1, s1)).build(), is(nullValue()));
    }
	
	protected abstract int getResult4And2();
	protected abstract String getSymbol();
}
