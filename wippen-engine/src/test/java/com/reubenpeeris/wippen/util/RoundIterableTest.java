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
	private static final List<String> LIST = Collections.unmodifiableList(Arrays.asList("one", "two", "three", "four"));

	@Test
	public void constructor_throws_for_null_list() {
		expect(NullPointerException.class, "list");
		new RoundIterable<>(null, "");
	}

	@Test
	public void constructor_throws_for_null_start() {
		expect(NullPointerException.class, "start");
		new RoundIterable<>(Collections.emptyList(), null);
	}

	@Test
	public void testConstructorItemNotPresent() {
		String item = "does not contain this item";
		expect(IllegalArgumentException.class, item);
		new RoundIterable<>(LIST, item);
	}

	@Test
	public void can_iterate_over_all_elements_starting_with_specified_element() {
		RoundIterable<String> iterable = new RoundIterable<>(LIST, "three");

		Iterator<String> it = iterable.iterator();

		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(equalTo("three")));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(equalTo("four")));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(equalTo("one")));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(equalTo("two")));
		assertThat(it.hasNext(), is(false));
	}

	@Test
	public void remove_is_unsupported() {
		thrown.expect(UnsupportedOperationException.class);
		RoundIterable<String> iterable = new RoundIterable<>(LIST, "three");
		iterable.iterator().remove();
	}

	@Test
	public void next_throws_when_no_elements_left() {
		String only = "only element";
		Iterator<String> iterator = new RoundIterable<>(Arrays.asList(only), only).iterator();
		iterator.next();
		thrown.expect(NoSuchElementException.class);
		iterator.next();
	}
}
