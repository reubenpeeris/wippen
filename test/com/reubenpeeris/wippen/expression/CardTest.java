package com.reubenpeeris.wippen.expression;

import java.util.Collections;

import org.junit.Test;

import static com.reubenpeeris.wippen.Cards.s1;

import static org.junit.Assert.*;

public class CardTest {
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullSuit() {
		new Card(null, new Rank(1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullRank() {
		new Card(Suit.CLUB, null);
	}

	@Test
	public void testGetCards() {
		assertEquals(Collections.singleton(s1), s1.getCards());
	}

	@Test
	public void testGetPiles() {
		assertEquals(Collections.<Pile>singleton(s1), s1.getPiles());
	}

	@Test
	public void testWasBuiltByPlayer() {
		for (int i = 1; i <= 4; i++) {
			assertFalse(s1.wasBuiltByPlayerInPosion(i));
		}
	}

	@Test
	public void testGetValue() {
		for (int i = 1; i <= 13; i++) {
			Rank rank = new Rank(i);
			Card card = new Card(Suit.CLUB, rank);
			
			assertEquals(rank.getValue(), card.getValue());
		}
	}
}
