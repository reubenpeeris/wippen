package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.TestData.*;
import static com.reubenpeeris.wippen.expression.Move.Type.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;

public class ExpressionFactoryTest extends BaseTest {
	private static final Expression LEFT = c3;
	private static final Expression RIGHT = h3;
	private static final Expression ADD = new Add(LEFT, RIGHT);
	private static final Expression SUBTRACT = new Subtract(LEFT, RIGHT);
	private static final Expression MULTIPLY = new Multiply(LEFT, RIGHT);
	private static final Expression DIVIDE = new Divide(LEFT, RIGHT);
	private static final Expression EQUALS = new Equals(LEFT, RIGHT);

	@Test
	public void addBuilder_builds_Add() {
		assertValueBuilt(create.addBuilder(), ADD);
	}

	@Test
	public void subtractBuilder_builds_Subtract() {
		assertValueBuilt(create.subtractBuilder(), SUBTRACT);
	}

	@Test
	public void multiplyBuilder_builds_Multiply() {
		assertValueBuilt(create.multiplyBuilder(), MULTIPLY);
	}

	@Test
	public void divideBuilder_builds_Divide() {
		assertValueBuilt(create.divideBuilder(), DIVIDE);
	}

	@Test
	public void equalsBuilder_builds_Equals() {
		assertValueBuilt(create.equalsBuilder(), EQUALS);
	}

	@Test
	public void nodeBuilders_returns_a_builder_for_each_type() {
		List<Expression> builtExpressions = new ArrayList<>();
		for (NodeBuilder builder : create.newBuilders()) {
			builtExpressions.add(builder.left(LEFT).right(RIGHT).build());
		}
		assertThat(builtExpressions, containsInAnyOrder(ADD, SUBTRACT, MULTIPLY, DIVIDE, EQUALS));
	}

	@Test
	public void newAdd_returns_Add() {
		assertThat(create.newAdd(LEFT, RIGHT), is(equalTo(ADD)));
	}

	@Test
	public void newSubtract_returns_Subtract() {
		assertThat(create.newSubtract(LEFT, RIGHT), is(equalTo(SUBTRACT)));
	}

	@Test
	public void newMultiply_returns_Multiply() {
		assertThat(create.newMultiply(LEFT, RIGHT), is(equalTo(MULTIPLY)));
	}

	@Test
	public void newDivide_returns_Divide() {
		assertThat(create.newDivide(LEFT, RIGHT), is(equalTo(DIVIDE)));
	}

	@Test
	public void newEquals_returns_Equals() {
		assertThat(create.newEquals(LEFT, RIGHT), is(equalTo(EQUALS)));
	}

	@Test
	public void newMove_retuns_a_valid_Move() {
		assertThat(create.newMove(DISCARD, null, s1), is(not(nullValue())));
	}

	@Test
	public void newMove_parser_retuns_a_valid_Move() {
		assertThat(create.newMove("DISCARD 1S"), is(not(nullValue())));
	}

	@Test
	public void tryAdd_returns_Add() {
		assertThat(create.tryAdd(LEFT, RIGHT), is(equalTo(ADD)));
	}

	@Test
	public void trySubtract_returns_Subtract() {
		assertThat(create.trySubtract(LEFT, RIGHT), is(equalTo(SUBTRACT)));
	}

	@Test
	public void tryMultiply_returns_Multiply() {
		assertThat(create.tryMultiply(LEFT, RIGHT), is(equalTo(MULTIPLY)));
	}

	@Test
	public void tryDivide_returns_Divide() {
		assertThat(create.tryDivide(LEFT, RIGHT), is(equalTo(DIVIDE)));
	}

	@Test
	public void tryEquals_returns_Equals() {
		assertThat(create.tryEquals(LEFT, RIGHT), is(equalTo(EQUALS)));
	}

	@Test
	public void tryMove_retuns_a_valid_Move() {
		assertThat(create.tryMove(DISCARD, null, s1), is(not(nullValue())));
	}

	@Test
	public void tryMove_parser_retuns_a_valid_Move() {
		assertThat(create.tryMove("DISCARD 1S"), is(not(nullValue())));
	}

	@Test
	public void invalid_newAdd_throws() {
		expect(IllegalStateException.class, "Expression is invalid");
		create.newAdd(s1, equals);
	}

	@Test
	public void invalid_newSubtract_throws() {
		expect(IllegalStateException.class, "Expression is invalid");
		create.newSubtract(s1, equals);
	}

	@Test
	public void invalid_newMultiply_throws() {
		expect(IllegalStateException.class, "Expression is invalid");
		create.newMultiply(s1, equals);
	}

	@Test
	public void invalid_newDivide_throws() {
		expect(IllegalStateException.class, "Expression is invalid");
		create.newDivide(s1, equals);
	}

	@Test
	public void invalid_newEquals_throws() {
		expect(IllegalStateException.class, "Expression is invalid");
		create.newEquals(s1, s2);
	}

	@Test
	public void invalid_newMove_throws() {
		expect(IllegalStateException.class, "Expression is invalid");
		create.newMove(CAPTURE, null, s1);
	}

	@Test
	public void invalid_newMove_string_throws() {
		expect(IllegalStateException.class, "Expression is invalid");
		create.newMove("DISCARD BOB");
	}

	@Test
	public void invalid_tryAdd_returns_null() {
		assertThat(create.tryAdd(s1, equals), is(nullValue()));
	}

	@Test
	public void invalid_trySubtract_returns_null() {
		assertThat(create.trySubtract(s1, equals), is(nullValue()));
	}

	@Test
	public void invalid_tryMultiply_returns_null() {
		assertThat(create.tryMultiply(s1, equals), is(nullValue()));
	}

	@Test
	public void invalid_tryDivide_returns_null() {
		assertThat(create.tryDivide(s1, equals), is(nullValue()));
	}

	@Test
	public void invalid_tryEquals_returns_null() {
		assertThat(create.tryEquals(s1, s2), is(nullValue()));
	}

	@Test
	public void invalid_tryMove_returns_null() {
		assertThat(create.tryMove(CAPTURE, null, s1), is(nullValue()));
	}

	@Test
	public void invalid_tryMove_string_returns_null() {
		assertThat(create.tryMove("DISCARD BOB"), is(nullValue()));
	}
	
	@Test
	public void static_returns_same_instance_each_time() {
		assertThat(AnonymousExpressionFactory.factory(), is(sameInstance(AnonymousExpressionFactory.factory())));
	}

	private void assertValueBuilt(NodeBuilder builder, Expression expected) {
		assertThat(builder.left(LEFT).right(RIGHT).build(), is(equalTo(expected)));
	}
}
