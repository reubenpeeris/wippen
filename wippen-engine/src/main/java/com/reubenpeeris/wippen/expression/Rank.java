package com.reubenpeeris.wippen.expression;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Rank {
	private final int value;

	public Rank(int value) {
		if (value < 1 || value > 13) {
			throw new IllegalArgumentException("Rank must be in the range 1 to 13");
		}
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}
}
