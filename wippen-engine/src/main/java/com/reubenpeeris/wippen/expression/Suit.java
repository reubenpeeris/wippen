package com.reubenpeeris.wippen.expression;

public enum Suit {
	CLUB("C"),
	DIAMOND("D"),
	HEART("H"),
	SPADE("S");
	
	private final String letter;
	
	private Suit(String letter) {
		this.letter = letter;
	}

	@Override
	public String toString() {
		return letter;
	}
	
	public static Suit fromLetter(String shortName) {
		for (Suit suit : Suit.values()) {
			if (suit.letter.equals(shortName)) {
				return suit;
			}
		}
		
		throw new IllegalArgumentException("Invalid suit letter: " + shortName);
	}
}
