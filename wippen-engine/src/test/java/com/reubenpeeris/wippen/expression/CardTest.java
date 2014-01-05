package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.TestData.*;
import static com.reubenpeeris.wippen.engine.Player.*;
import static com.reubenpeeris.wippen.expression.Rank.*;
import static com.reubenpeeris.wippen.expression.Suit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class CardTest extends BaseExpressionTest {
	@Test
	public void construct_with_null_suit_throws() {
		expect(NullPointerException.class, "suit");
		new Card(ACE, null);
	}

	@Test
	public void construct_with_null_rank_throws() {
		expect(NullPointerException.class, "rank");
		new Card(null, CLUBS);
	}

	@Test
	public void getCards_returns_collection_containing_itself() {
		assertThat(s1.getCards(), contains(s1));
	}

	@Test
	public void getPiles_returns_collection_containing_itself() {
		assertThat(s1.getPiles(), contains((Pile) s1));
	}

	@Test
	public void getPlayer_returns_NOBODY() {
		assertThat(s1.getPlayer(), is(NOBODY));
	}

	@Test
	public void getValue_returns_rank_value() {
		for (Rank rank : Rank.values()) {
			Card card = new Card(rank, CLUBS);
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
			assertCardParses(new Card(ACE, suit));
		}
	}

	@Test
	public void cards_are_equal_if_rank_and_suit_match() {
		assertThat(new Card(ACE, CLUBS).equals(c1), is(true));
	}

	@Test
	public void cards_are_not_equal_if_rank_is_different() {
		assertThat(new Card(TWO, CLUBS).equals(c1), is(false));
	}

	@Test
	public void cards_are_not_equal_if_suit_is_different() {
		assertThat(new Card(ACE, SPADES).equals(c1), is(false));
	}

	private void assertCardParses(Card card) {
		Card parsed = Card.parseCard(card.toString());
		assertThat(parsed, is(equalTo(card)));
	}

	@Override
	protected Expression validInstance() {
		return s1;
	}
}
