package com.reubenpeeris.wippen.expression;

import lombok.Getter;

@Getter
public enum Rank {
	ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);

	private final int value;

	private Rank(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}

	public static Rank fromInt(int value) {
		if (value < ACE.getValue() || value > KING.getValue()) {
			throw new IllegalArgumentException("Rank must be in the range 1 to 13");
		}
		return values()[value - 1];
	}
}
