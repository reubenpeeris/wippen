package com.reubenpeeris.wippen.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class RoundIterableTest {
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullList() {
		new RoundIterable<>(null, "");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullStart() {
		new RoundIterable<>(Collections.emptyList(), null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorItemNotPresent() {
		new RoundIterable<>(Arrays.asList("some", "items"), "but not this one");
	}
	
	@Test
	public void testStartAtStart() {
		List<String> list = Arrays.asList("one", "two", "three", "four");
		
		RoundIterable<String> iterable = new RoundIterable<String>(list, "one");
		
		Iterator<String> it = iterable.iterator();

		assertTrue(it.hasNext());
		assertEquals("one", it.next());
		assertTrue(it.hasNext());
		assertEquals("two", it.next());
		assertTrue(it.hasNext());
		assertEquals("three", it.next());
		assertTrue(it.hasNext());
		assertEquals("four", it.next());
		assertFalse(it.hasNext());
	}
	
	@Test
	public void testStartNotAtStart() {
		List<String> list = Arrays.asList("one", "two", "three", "four");
		
		RoundIterable<String> iterable = new RoundIterable<String>(list, "three");
		
		Iterator<String> it = iterable.iterator();

		assertTrue(it.hasNext());
		assertEquals("three", it.next());
		assertTrue(it.hasNext());
		assertEquals("four", it.next());
		assertTrue(it.hasNext());
		assertEquals("one", it.next());
		assertTrue(it.hasNext());
		assertEquals("two", it.next());
		assertFalse(it.hasNext());
	}
}
