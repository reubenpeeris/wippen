package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.expression.Suit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;

public class SuitTest extends BaseTest {
	@Test
	public void fromLetter_throws_for_null_letter() {
		expect(NullPointerException.class, "letter");
		Suit.fromLetter(null);
	}

	@Test
	public void fromLetter_throws_for_invalid_letter() {
		expect(IllegalArgumentException.class, "Invalid suit letter");
		Suit.fromLetter("invalid");
	}

	@Test
	public void fromLetter_returns_club_for_input_of_C() {
		assertThat(Suit.fromLetter("C"), is(equalTo(CLUBS)));
	}

	@Test
	public void fromLetter_returns_heart_for_input_of_H() {
		assertThat(Suit.fromLetter("H"), is(equalTo(HEARTS)));
	}

	@Test
	public void fromLetter_returns_spade_for_input_of_S() {
		assertThat(Suit.fromLetter("S"), is(equalTo(SPADES)));
	}

	@Test
	public void fromLetter_returns_diamond_for_input_of_D() {
		assertThat(Suit.fromLetter("D"), is(equalTo(DIAMONDS)));
	}

	@Test
	public void club_toString_is_C() {
		assertThat(CLUBS.toString(), is(equalTo("C")));
	}

	@Test
	public void heart_toString_is_H() {
		assertThat(HEARTS.toString(), is(equalTo("H")));
	}

	@Test
	public void spade_toString_is_S() {
		assertThat(SPADES.toString(), is(equalTo("S")));
	}

	@Test
	public void diamond_toString_is_D() {
		assertThat(DIAMONDS.toString(), is(equalTo("D")));
	}
}
