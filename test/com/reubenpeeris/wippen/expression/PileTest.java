package com.reubenpeeris.wippen.expression;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

import static com.reubenpeeris.wippen.Cards.c1;
import static com.reubenpeeris.wippen.Cards.c2;
import static com.reubenpeeris.wippen.Cards.h1;
import static com.reubenpeeris.wippen.Cards.h2;

import static org.junit.Assert.assertEquals;

public class PileTest {
	static class MockPile extends Pile {
		private final Collection<Card> cards;

		public MockPile(Collection<Card> cards) {
			this.cards = cards;
		}

		@Override
		public Rank getRank() {
			return null;
		}

		@Override
		public Collection<Card> getCards() {
			return cards;
		}

		@Override
		public boolean wasBuiltByPlayerInPosion(int position) {
			return false;
		}

		@Override
		public int getValue() {
			return 0;
		}

		@Override
		public Collection<Pile> getPiles() {
			return null;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCardsNull() {
		Pile.getCards(null);
	}

	@Test
	public void testGetCardsEmpty() {
		assertEquals(Collections.<Card> emptyList(),
				Pile.getCards(Collections.<Pile> emptySet()));
	}

	@Test
	public void testGetCardsCoupleOfPiles() {
		Pile pile1 = new MockPile(Arrays.asList(c1, c2));
		Pile pile2 = new MockPile(Arrays.asList(h1, h2));

		assertEquals(Arrays.asList(c1, c2, h1, h2),
				Pile.getCards(Arrays.asList(pile1, pile2)));
	}
}
