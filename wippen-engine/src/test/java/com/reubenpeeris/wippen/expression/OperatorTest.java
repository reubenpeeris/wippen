package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.TestData.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.Assume.*;

import org.junit.Test;

public abstract class OperatorTest extends BaseExpressionTest {
	private final NodeBuilder nodeBuilder;
	private final Class<? extends PairNode> clazz;

	public OperatorTest(NodeBuilder nodeBuilder, Class<? extends PairNode> clazz) {
		this.nodeBuilder = nodeBuilder;
		this.clazz = clazz;
	}

	@Override
	protected Expression validInstance() {
		return nodeBuilder.left(getLeft()).right(getRight()).build();
	}

	protected abstract int getExpectedValue();

	protected abstract String getSymbol();

	protected Card getLeft() {
		return s4;
	}

	protected Card getRight() {
		return s2;
	}

	@Test
	public void builder_builds_expected_class() {
		assertSame(clazz, build().getClass());
	}

	@Test
	public void getValue_returns_expected_type() {
		assertEquals(getExpectedValue(), build().getValue());
	}

	@Test
	public void getOperatorSymbol_returns_expected_symbol() {
		assertEquals(getSymbol(), build().getOperatorSymbol());
	}

	@Test
	public void setting_left_to_Equal_node_type_throws() {
		assumeFalse("This test is applicable to operators except Equals", clazz == Equals.class);
		assertThat(nodeBuilder.left(new Equals(c1, h1)).right(getRight()).build(), is(nullValue()));
	}

	@Test
	public void setting_right_to_Equal_node_type_throws() {
		assumeFalse("This test is applicable to operators except Equals", clazz == Equals.class);
		assertThat(nodeBuilder.left(getLeft()).right(new Equals(c1, h1)).build(), is(nullValue()));
	}

	private PairNode build() {
		return nodeBuilder.build(getLeft(), getRight());
	}
}
