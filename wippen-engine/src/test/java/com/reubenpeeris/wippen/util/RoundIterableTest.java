package com.reubenpeeris.wippen.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;

public class RoundIterableTest extends BaseTest {
	private static final List<String> LIST = Collections.unmodifiableList(Arrays.asList("one", "two", "three", "four"));

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullList() {
		new RoundIterable<>(null, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullStart() {
		new RoundIterable<>(Collections.emptyList(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorItemNotPresent() {
		new RoundIterable<>(LIST, "does not contain this item");
	}

	@Test
	public void testStartAtStart() {
		RoundIterable<String> iterable = new RoundIterable<>(LIST, "one");

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
		RoundIterable<String> iterable = new RoundIterable<>(LIST, "three");

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

	@Test
	public void iteratorRemoveIsUnsupported() {
		thrown.expect(UnsupportedOperationException.class);
		RoundIterable<String> iterable = new RoundIterable<>(LIST, "three");
		iterable.iterator().remove();
	}
}
