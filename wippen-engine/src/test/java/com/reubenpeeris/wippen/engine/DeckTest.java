package com.reubenpeeris.wippen.engine;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;
import com.reubenpeeris.wippen.expression.Card;

public class DeckTest extends BaseTest {
	private Deck deck;

	@Before
	public void createNewDeck() {
		deck = Deck.newDeck(new Random(1));
	}

	@Test
	public void full_deck_has_52_different_cards() {
		Set<Card> uniqueCards = new HashSet<>(Deck.FULL_DECK);

		assertThat(Deck.FULL_DECK.size(), is(equalTo(52)));
		assertThat(uniqueCards.size(), is(equalTo(52)));
	}

	@Test
	public void newDeck_returns_same_cards_as_the_full_deck_but_in_a_different_order() {
		List<Card> newDeckOrder = getCards(deck);
		Set<Card> newDeckCards = new HashSet<>(newDeckOrder);
		Set<Card> fullDeckCards = new HashSet<>(Deck.FULL_DECK);

		assertThat(newDeckCards, is(equalTo(fullDeckCards)));
		assertThat("Should not be same order as new deck", newDeckOrder, is(not(equalTo(Deck.FULL_DECK))));
	}

	@Test
	public void nextCards_throws_for_value_less_than_one() {
		expect(IllegalArgumentException.class, "Must request at least one card");
		deck.nextCards(0);
	}

	@Test
	public void nextCards_throws_for_value_greater_that_remaining_cards() {
		deck.nextCards(10);
		expect(IllegalArgumentException.class, "Requested more cards that available in deck");
		deck.nextCards(43);
	}

	@Test
	public void nextCards_returns_correct_cards_for_valid_count() {
		Deck deck = Deck.newDeck(new Random(1));
		List<Card> expectedCards = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			expectedCards.add(deck.nextCard());
		}
		deck = Deck.newDeck(new Random(1));

		assertThat(new ArrayList<>(deck.nextCards(5)), is(equalTo(expectedCards)));
	}

	private List<Card> getCards(Deck deck) {
		List<Card> cards = new ArrayList<>(52);
		while (!deck.isEmpty()) {
			Card card = deck.nextCard();
			cards.add(card);
		}
		return cards;
	}
}
