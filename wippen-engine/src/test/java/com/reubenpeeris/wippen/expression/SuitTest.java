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
		assertThat(Suit.fromLetter("C"), is(equalTo(CLUB)));
	}

	@Test
	public void fromLetter_returns_heart_for_input_of_H() {
		assertThat(Suit.fromLetter("H"), is(equalTo(HEART)));
	}

	@Test
	public void fromLetter_returns_spade_for_input_of_S() {
		assertThat(Suit.fromLetter("S"), is(equalTo(SPADE)));
	}

	@Test
	public void fromLetter_returns_diamond_for_input_of_D() {
		assertThat(Suit.fromLetter("D"), is(equalTo(DIAMOND)));
	}

	@Test
	public void club_toString_is_C() {
		assertThat(CLUB.toString(), is(equalTo("C")));
	}

	@Test
	public void heart_toString_is_H() {
		assertThat(HEART.toString(), is(equalTo("H")));
	}

	@Test
	public void spade_toString_is_S() {
		assertThat(SPADE.toString(), is(equalTo("S")));
	}

	@Test
	public void diamond_toString_is_D() {
		assertThat(DIAMOND.toString(), is(equalTo("D")));
	}
}
