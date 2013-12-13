package com.reubenpeeris.wippen.expression;

import lombok.NonNull;

import com.reubenpeeris.wippen.engine.WippenIllegalFormatException;

public enum Suit {
	CLUB("C"), DIAMOND("D"), HEART("H"), SPADE("S");

	private final String letter;

	private Suit(String letter) {
		this.letter = letter;
	}

	@Override
	public String toString() {
		return letter;
	}

	public static Suit fromLetter(@NonNull String letter) {
		for (Suit suit : Suit.values()) {
			if (suit.letter.equals(letter)) {
				return suit;
			}
		}

		throw new WippenIllegalFormatException("Invalid suit letter: " + letter);
	}
}
