package com.reubenpeeris.wippen.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Rank;
import com.reubenpeeris.wippen.expression.Suit;

import static com.reubenpeeris.wippen.expression.AnonymousExpressionFactory.*;

class Deck {
	private static final int CARDS_PER_DECK = 52;

	public static final List<Card> FULL_DECK;

	static {
		List<Card> deck = new ArrayList<>(CARDS_PER_DECK);
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				deck.add(factory().newCard(rank, suit));
			}
		}

		FULL_DECK = Collections.unmodifiableList(deck);
	}

	public static Deck newDeck(Random shuffler) {
		LinkedList<Card> cards = new LinkedList<>(FULL_DECK);
		Collections.shuffle(cards, shuffler);

		return new Deck(cards);
	}

	private final LinkedList<Card> cards;
	private final ListIterator<Card> iterator;

	private Deck(LinkedList<Card> cards) {
		this.cards = cards;
		iterator = this.cards.listIterator();
	}

	public boolean isEmpty() {
		return cards.isEmpty();
	}

	public Card nextCard() {
		Card c = iterator.next();
		iterator.remove();

		return c;
	}

	public Collection<Card> nextCards(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("Must request at least one card");
		}
		if (n > cards.size()) {
			throw new IllegalArgumentException("Requested more cards that available in deck");
		}

		Collection<Card> c = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			c.add(nextCard());
		}

		return c;
	}
}
