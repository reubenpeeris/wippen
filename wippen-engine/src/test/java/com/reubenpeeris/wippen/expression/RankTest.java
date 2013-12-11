package com.reubenpeeris.wippen.expression;

import org.junit.Test;

import static org.junit.Assert.*;

public class RankTest {
	@Test
	public void testConstructValid() {
		for (int i = 1; i <=13; i++) {
			Rank rank = new Rank(i);
			assertEquals(i, rank.getValue());
			assertEquals(Integer.toString(i), rank.toString());
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructTooSmall() {
		new Rank(0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructTooLarge() {
		new Rank(14);
	}
	
	@Test
	public void testDisplayValueAce() {
		assertEquals("A", new Rank(1).getDisplayValue());
	}
	
	@Test
	public void testDisplayValueNumber() {
		assertEquals("2", new Rank(2).getDisplayValue());
	}
	
	@Test
	public void testDisplayValueJack() {
		assertEquals("J", new Rank(11).getDisplayValue());
	}
	
	@Test
	public void testDisplayValueQueen() {
		assertEquals("Q", new Rank(12).getDisplayValue());
	}
	
	@Test
	public void testDisplayValueKing() {
		assertEquals("K", new Rank(13).getDisplayValue());
	}
}
