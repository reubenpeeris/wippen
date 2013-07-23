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
	
	public String getDisplayValue() {
		switch (value) {
			case 1  : return "A";
			case 11 : return "J";
			case 12 : return "Q";
			case 13 : return "K";
			default : return Integer.toString(value);
		}
	}
	
	public String toString() {
		return Integer.toString(value);
	}
}
