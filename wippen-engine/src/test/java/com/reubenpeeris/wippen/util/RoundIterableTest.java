package com.reubenpeeris.wippen.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;

public class RoundIterableTest extends BaseTest {
	private static final List<String> LIST = Collections.unmodifiableList(Arrays.asList("zero", "one", "two", "three"));

	@Test
	public void constructor_throws_for_null_list() {
		expect(NullPointerException.class, "list");
		new RoundIterable<>(null, 0);
	}

	@Test
	public void constructor_throws_for_negative_startPosition() {
		expect(IndexOutOfBoundsException.class, "list size: 4 index: -1");
		new RoundIterable<>(LIST, -1);
	}

	@Test
	public void constructor_throws_for_startPosition_larger_than_list() {
		expect(IndexOutOfBoundsException.class, "list size: 4 index: 5");
		new RoundIterable<>(LIST, 5);
	}

	@Test
	public void can_iterate_over_all_elements_starting_with_specified_element() {
		RoundIterable<String> iterable = new RoundIterable<>(LIST, 2);

		Iterator<String> it = iterable.iterator();

		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(equalTo("two")));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(equalTo("three")));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(equalTo("zero")));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(equalTo("one")));
		assertThat(it.hasNext(), is(false));
	}

	@Test
	public void remove_is_unsupported() {
		thrown.expect(UnsupportedOperationException.class);
		RoundIterable<String> iterable = new RoundIterable<>(LIST, 2);
		iterable.iterator().remove();
	}

	@Test
	public void next_throws_when_no_elements_left() {
		String only = "only element";
		Iterator<String> iterator = new RoundIterable<>(Arrays.asList(only), 0).iterator();
		iterator.next();
		thrown.expect(NoSuchElementException.class);
		iterator.next();
	}
}
