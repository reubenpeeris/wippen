package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.expression.Suit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;

public class SuitTest extends BaseTest {
	@Test
	public void verifyNullLetterThrowsException() {
		expect(NullPointerException.class, "letter");
		Suit.fromLetter(null);
	}

	@Test
	public void invalidLetterThrowsException() {
		expect(IllegalArgumentException.class, "Invalid suit letter");
		Suit.fromLetter("invalid");
	}

	@Test
	public void cGeneratesClub() {
		assertThat(Suit.fromLetter("C"), is(equalTo(CLUB)));
	}

	@Test
	public void hGeneratesHeart() {
		assertThat(Suit.fromLetter("H"), is(equalTo(HEART)));
	}

	@Test
	public void sGeneratesSpade() {
		assertThat(Suit.fromLetter("S"), is(equalTo(SPADE)));
	}

	@Test
	public void dGeneratesDiamond() {
		assertThat(Suit.fromLetter("D"), is(equalTo(DIAMOND)));
	}

	@Test
	public void clubToStringIsC() {
		assertThat(CLUB.toString(), is(equalTo("C")));
	}

	@Test
	public void heartToStringIsH() {
		assertThat(HEART.toString(), is(equalTo("H")));
	}

	@Test
	public void spadeToStringIsS() {
		assertThat(SPADE.toString(), is(equalTo("S")));
	}

	@Test
	public void diamondToStringIsD() {
		assertThat(DIAMOND.toString(), is(equalTo("D")));
	}
}
