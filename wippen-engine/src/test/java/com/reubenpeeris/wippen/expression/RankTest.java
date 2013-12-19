package com.reubenpeeris.wippen.expression;

import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;

public class RankTest extends BaseTest {
	private static final String OUT_OF_RANGE_MESSAGE = "Rank must be in the range 1 to 13";

	@Test
	public void has_value_and_toString_equivalent_to_constructor_value() {
		for (int i = 1; i <= 13; i++) {
			Rank rank = Rank.fromInt(i);
			assertEquals(i, rank.getValue());
			assertEquals(Integer.toString(i), rank.toString());
		}
	}

	@Test
	public void constructor_throws_for_small_value() {
		expect(IllegalArgumentException.class, OUT_OF_RANGE_MESSAGE);
		Rank.fromInt(0);
	}

	@Test
	public void constructor_throws_for_large_value() {
		expect(IllegalArgumentException.class, OUT_OF_RANGE_MESSAGE);
		Rank.fromInt(14);
	}
}
