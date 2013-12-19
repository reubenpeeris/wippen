package com.reubenpeeris.wippen.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;

public class CollectionPairTest extends BaseTest {
	private final Collection<String> left = Arrays.asList("inLeft1", "inLeft2");
	private final Collection<String> right = Arrays.asList("inRight1", "inRight2");
	private final Collection<String> empty = Collections.emptyList();

	private final CollectionPair<String> emptyPair = new CollectionPair<>(empty, empty);
	private final CollectionPair<String> leftOnly = new CollectionPair<>(left, empty);
	private final CollectionPair<String> rightOnly = new CollectionPair<>(empty, right);
	private final CollectionPair<String> leftAndRight = new CollectionPair<>(left, right);

	@Test
	public void constructor_throws_for_null_left() {
		expect(NullPointerException.class, "left");
		new CollectionPair<>(null, Collections.emptyList());
	}

	@Test
	public void constructor_throws_for_null_right() {
		expect(NullPointerException.class, "right");
		new CollectionPair<>(Collections.emptyList(), null);
	}

	@Test
	public void contains_returns_false_for_null_input() {
		assertThat(leftAndRight.contains(null), is(false));
	}

	@Test
	public void contains_returns_false_if_not_in_left_nor_right() {
		assertThat(leftAndRight.contains("inNeither"), is(false));
	}

	@Test
	public void contains_returns_true_present_if_in_left() {
		assertThat(leftAndRight.contains(left.iterator().next()), is(true));
	}

	@Test
	public void contains_returns_true_present_if_in_right() {
		assertThat(leftAndRight.contains(right.iterator().next()), is(true));
	}

	@Test
	public void isEmpty_returns_true_if_left_and_right_are_both_empty() {
		assertThat(emptyPair.isEmpty(), is(true));
	}

	@Test
	public void isEmpty_returns_false_if_elements_are_in_left() {
		assertThat(leftOnly.isEmpty(), is(false));
	}

	@Test
	public void isEmpty_returns_false_if_elements_are_in_right() {
		assertThat(rightOnly.isEmpty(), is(false));
	}

	@Test
	public void size_is_zero_when_emty() {
		assertThat(emptyPair.size(), is(equalTo(0)));
	}

	@Test
	public void size_is_sum_of_left_and_right() {
		assertThat(leftAndRight.size(), is(equalTo(left.size() + right.size())));
	}

	@Test
	public void containsAll_throws_for_null() {
		expect(NullPointerException.class, "collection");
		emptyPair.containsAll(null);
	}

	@Test
	public void containsAll_returns_false_if_an_element_is_not_present() {
		assertThat(leftAndRight.containsAll(Arrays.asList(left.iterator().next(), "not present")), is(false));
	}

	@Test
	public void containsAll_returns_true_when_all_items_present() {
		assertThat(leftAndRight.containsAll(Arrays.asList(left.iterator().next(), right.iterator().next())), is(true));
	}

	@Test
	public void toString_for_empty_collection() {
		assertThat(emptyPair.toString(), is(equalTo("[]")));
	}

	@Test
	public void toString_for_left_only() {
		assertThat(leftOnly.toString(), is(equalTo("[inLeft1, inLeft2]")));
	}

	@Test
	public void toString_for_right_only() {
		assertThat(rightOnly.toString(), is(equalTo("[inRight1, inRight2]")));
	}

	@Test
	public void toString_for_left_and_right() {
		assertThat(leftAndRight.toString(), is(equalTo("[inLeft1, inLeft2, inRight1, inRight2]")));
	}

	@Test
	public void iterator_remove_is_unsupported() {
		thrown.expect(UnsupportedOperationException.class);
		leftAndRight.iterator().remove();
	}

	@Test
	public void can_iterate_over_all_elements() {
		Iterator<String> it = leftAndRight.iterator();
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(equalTo("inLeft1")));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(equalTo("inLeft2")));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(equalTo("inRight1")));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next(), is(equalTo("inRight2")));
		assertThat(it.hasNext(), is(false));
	}

	@Test
	public void two_instances_are_equal_if_they_have_the_same_left_and_right() {
		CollectionPair<String> leftAndRight2 = new CollectionPair<>(left, right);
		assertThat(leftAndRight.equals(leftAndRight2), is(true));
	}

	@Test
	public void two_instances_are_equal_if_they_have_the_different_left() {
		assertThat(leftAndRight.equals(rightOnly), is(false));
	}

	@Test
	public void two_instances_are_equal_if_they_have_the_different_right() {
		assertThat(leftAndRight.equals(leftOnly), is(false));
	}

	@Test
	public void toArray_is_unsupported() {
		thrown.expect(UnsupportedOperationException.class);
		emptyPair.toArray();
	}

	@Test
	public void toArrayT_is_unsupported() {
		thrown.expect(UnsupportedOperationException.class);
		emptyPair.toArray(null);
	}

	@Test
	public void add_is_unsupported() {
		thrown.expect(UnsupportedOperationException.class);
		emptyPair.add(null);
	}

	@Test
	public void addAll_is_unsupported() {
		thrown.expect(UnsupportedOperationException.class);
		emptyPair.addAll(null);
	}

	@Test
	public void clear_is_unsupported() {
		thrown.expect(UnsupportedOperationException.class);
		emptyPair.clear();
	}

	@Test
	public void remove_is_unsupported() {
		thrown.expect(UnsupportedOperationException.class);
		emptyPair.remove(null);
	}

	@Test
	public void removeAll_is_unsupported() {
		thrown.expect(UnsupportedOperationException.class);
		emptyPair.removeAll(null);
	}

	@Test
	public void retainAll_is_unsupported() {
		thrown.expect(UnsupportedOperationException.class);
		emptyPair.retainAll(null);
	}
}
