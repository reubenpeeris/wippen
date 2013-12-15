package com.reubenpeeris.wippen.expression;

import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseImmutableTest;

public class RankTest extends BaseImmutableTest<Rank> {
	private static final String OUT_OF_RANGE_MESSAGE = "Rank must be in the range 1 to 13";

	@Override
	protected Rank validInstance() {
		return new Rank(5);
	}

	@Test
	public void has_value_and_toString_equivalent_to_constructor_value() {
		for (int i = 1; i <= 13; i++) {
			Rank rank = new Rank(i);
			assertEquals(i, rank.getValue());
			assertEquals(Integer.toString(i), rank.toString());
		}
	}

	@Test
	public void constructor_throws_for_small_value() {
		expect(IllegalArgumentException.class, OUT_OF_RANGE_MESSAGE);
		new Rank(0);
	}

	@Test
	public void constructor_throws_for_large_value() {
		expect(IllegalArgumentException.class, OUT_OF_RANGE_MESSAGE);
		new Rank(14);
	}
}
