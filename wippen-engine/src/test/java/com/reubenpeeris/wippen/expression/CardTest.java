package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.ObjectMother.*;
import static com.reubenpeeris.wippen.engine.Player.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;

public class CardTest extends BaseTest {
	@Test
	public void construct_with_null_suit_throws() {
		expect(NullPointerException.class, "suit");
		new Card(null, new Rank(1));
	}

	@Test
	public void construct_with_null_rank_throws() {
		expect(NullPointerException.class, "rank");
		new Card(Suit.CLUB, null);
	}

	@Test
	public void getCards_returns_collection_containing_itself() {
		assertThat(s1.getCards(), contains(s1));
	}

	@Test
	public void getPiles_returns_collection_containing_itself() {
		assertThat(s1.getPiles(), contains((Pile)s1));
	}

	@Test
	public void getPlayer_returns_NOBODY() {
		assertThat(s1.getPlayer(), is(NOBODY));
	}

	@Test
	public void getValue_returns_rank_value() {
		for (int i = 1; i <= 13; i++) {
			Rank rank = new Rank(i);
			Card card = new Card(Suit.CLUB, rank);

			assertEquals(rank.getValue(), card.getValue());
		}
	}

	@Test
	public void parseCard_throws_NullPointerException_for_null_input() {
		expect(NullPointerException.class, "string");

		Card.parseCard(null);
	}

	@Test
	public void parseCard_throws_IllegalArgumentException_for_invalid_input() {
		expect(IllegalArgumentException.class, "Invalid card format");

		Card.parseCard("invalid");
	}

	@Test
	public void parseCard_returns_correct_card_for_valid_single_digit_input() {
		assertCardParses(s1);
	}

	@Test
	public void parseCard_returns_correct_card_for_valid_two_digit_input() {
		assertCardParses(s10);
	}

	@Test
	public void parseCard_returns_correct_card_for_each_suit() {
		for (Suit suit : Suit.values()) {
			assertCardParses(new Card(suit, new Rank(1)));
		}
	}

	private void assertCardParses(Card card) {
		Card parsed = Card.parseCard(card.toString());
		assertThat(parsed, is(equalTo(card)));
	}
}
