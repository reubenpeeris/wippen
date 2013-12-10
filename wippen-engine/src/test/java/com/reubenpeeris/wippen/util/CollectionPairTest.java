package com.reubenpeeris.wippen.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.junit.Test;

import static org.junit.Assert.*;

public class CollectionPairTest {
	private final Collection<String> left = Arrays.asList("inLeft");
	private final Collection<String> right = Arrays.asList("inRight");
	private final Collection<String> empty = Collections.emptyList();
	
	private final CollectionPair<String> emptyPair = new CollectionPair<>(empty, empty);
	private final CollectionPair<String> leftOnly = new CollectionPair<>(left, empty);
	private final CollectionPair<String> rightOnly = new CollectionPair<>(empty, right);
	private final CollectionPair<String> leftAndRight = new CollectionPair<>(left, right);
	private final CollectionPair<String> multipleLeftAndRight = new CollectionPair<>(Arrays.asList("inLeft1", "inLeft2"), Arrays.asList("inRight1", "inRight2"));
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullLeft() {
		new CollectionPair<>(null, Collections.emptyList());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullRight() {
		new CollectionPair<>(Collections.emptyList(), null);
	}
	
	@Test
	public void testContainsNull() {
		assertFalse(leftAndRight.contains(null));
	}
	
	@Test
	public void testContainsNotInEithe() {
		assertFalse(leftAndRight.contains("inNeither"));
	}
	
	@Test
	public void testContainsInLeft() {
		assertTrue(leftAndRight.contains(left.iterator().next()));
	}
	
	@Test
	public void testContainsInRight() {
		assertTrue(leftAndRight.contains(right.iterator().next()));
	}
	
	@Test
	public void testIsEmptyWhenEmpty() {
		assertTrue(emptyPair.isEmpty());
	}
	
	@Test
	public void testIsEmptyOnlyLeft() {
		assertFalse(leftOnly.isEmpty());
	}
	
	@Test
	public void testIsEmptyOnlyRight() {
		assertFalse(rightOnly.isEmpty());
	}
	
	@Test
	public void testSizeEmpty() {
		assertEquals(0, emptyPair.size());
	}
	
	@Test
	public void testSizeLeftOnly() {
		assertEquals(1, leftOnly.size());
	}
	
	@Test
	public void testSizeRightOnly() {
		assertEquals(1, rightOnly.size());
	}
	
	@Test
	public void testSizeLeftAndRight() {
		assertEquals(2, leftAndRight.size());
	}
	
	@Test
	public void testSizeLeftAndRightMoreThan1() {
		CollectionPair<String> pair = new CollectionPair<>(Arrays.asList("1", "2"), Arrays.asList("3", "4"));
		assertEquals(4, pair.size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testContainsAllNull() {
		emptyPair.containsAll(null);
	}
	
	@Test
	public void testContainsAllNotAllPresent() {
		assertFalse(leftAndRight.containsAll(Arrays.asList("not present")));
	}
	
	@Test
	public void testContainsAllInLeftAndRight() {
		assertTrue(leftAndRight.containsAll(Arrays.asList(left.iterator().next(), right.iterator().next())));
	}
	
	@Test
	public void testToStringEmpty() {
		assertEquals("[]", empty.toString());
	}
	
	@Test
	public void testToStringLeftOnly() {
		assertEquals("[inLeft]", leftOnly.toString());
	}
	
	@Test
	public void testToStringRightOnly() {
		assertEquals("[inRight]", rightOnly.toString());
	}

	@Test
	public void testToStringMultiple() {
		assertEquals("[inLeft1, inLeft2, inRight1, inRight2]", multipleLeftAndRight.toString());
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testIteratorRemove() {
		leftAndRight.iterator().remove();
	}
	
	@Test
	public void testIterator() {
		Iterator<String> it = multipleLeftAndRight.iterator();
		assertTrue(it.hasNext());
		assertEquals("inLeft1", it.next());
		assertTrue(it.hasNext());
		assertEquals("inLeft2", it.next());
		assertTrue(it.hasNext());
		assertEquals("inRight1", it.next());
		assertTrue(it.hasNext());
		assertEquals("inRight2", it.next());
		assertFalse(it.hasNext());
	}
	
	//Unsupported operations
	@Test(expected=UnsupportedOperationException.class)
	public void testToArray() {
		emptyPair.toArray();
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testToArrayT() {
		emptyPair.toArray(null);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testAdd() {
		emptyPair.add(null);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testAddAll() {
		emptyPair.addAll(null);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testClear() {
		emptyPair.clear();
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testRemove() {
		emptyPair.remove(null);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testRemoveAll() {
		emptyPair.removeAll(null);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testRetainAll() {
		emptyPair.retainAll(null);
	}
}
