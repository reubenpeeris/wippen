package com.reubenpeeris.wippen.expression;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseImmutableTest;

public abstract class BaseExpressionTest extends BaseImmutableTest<Expression> {
	@Test
	public void getPiles_returns_same_instance_each_time() {
		Expression e = validInstance();
		Collection<Pile> piles1 = e.getPiles();
		Collection<Pile> piles2 = e.getPiles();

		assertThat(piles2, is(sameInstance(piles1)));
	}

	@Test
	public void getCards_returns_same_instance_each_time() {
		Expression e = validInstance();
		Collection<Card> cards1 = e.getCards();
		Collection<Card> cards2 = e.getCards();

		assertThat(cards2, is(sameInstance(cards1)));
	}
}
