package com.reubenpeeris.wippen.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import com.reubenpeeris.wippen.expression.Card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DeckTest {
	@Test
	public void testFullDeckSize() {
		Set<Card> uniqueCards = new HashSet<Card>(Deck.FULL_DECK);
		
		assertEquals(52, Deck.FULL_DECK.size());
		assertEquals(52, uniqueCards.size());
	}
	@Test
	public void testNewDeck() {
		Deck deck = Deck.newDeck(new Random(1));
		Set<Card> uniqueCards = new HashSet<>();
		List<Card> orderedcards = new ArrayList<>();
		
		while (!deck.isEmpty()) {
			Card card = deck.nextCard();
			uniqueCards.add(card);
			orderedcards.add(card);
		}
		
		assertEquals(52, Deck.FULL_DECK.size());
		assertEquals(52, uniqueCards.size());
		assertFalse("Should not be same order as new deck", Deck.FULL_DECK.equals(orderedcards));
	}
	
	@Test
	public void testNextZeroCards() {
		Deck deck = Deck.newDeck(new Random(1));
		assertEquals(Collections.emptyList(), deck.nextCards(0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNextCardsTooMany() {
		Deck deck = Deck.newDeck(new Random(1));
		deck.nextCards(53);
	}
	
	@Test
	public void testNextCardsWithValidNumber() {
		Deck deck = Deck.newDeck(new Random(1));
		List<Card> expectedCards = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			expectedCards.add(deck.nextCard());
		}
		deck = Deck.newDeck(new Random(1));
		
		assertEquals(expectedCards, deck.nextCards(5));
	}
}
