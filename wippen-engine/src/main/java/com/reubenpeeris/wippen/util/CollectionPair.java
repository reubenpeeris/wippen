package com.reubenpeeris.wippen.util;

import java.util.Collection;
import java.util.Iterator;

import lombok.EqualsAndHashCode;

/**
 * A collection backed by 2 other collections. This is not thread safe.
 * None of the underlying Collections should be mutated when iterating over
 * this. If the underlying collections are immutable then so is this.
 */
@EqualsAndHashCode
public final class CollectionPair<E> implements Collection<E> {
	private final Collection<E> left;
	private final Collection<E> right;

	public CollectionPair(Collection<E> left, Collection<E> right) {
		if (left == null || right == null) {
			throw new IllegalArgumentException();
		}

		this.left = left;
		this.right = right;
	}

	@Override
	public boolean contains(Object o) {
		return left.contains(o) || right.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}

		for (Object o : c) {
			if (!contains(o)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean isEmpty() {
		return left.isEmpty() && right.isEmpty();
	}

	@Override
	public int size() {
		return left.size() + right.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");

		boolean first = true;
		for (E e : left) {
			if (first) {
				first = false;
			} else {
				sb.append(", ");
			}

			sb.append(e);
		}

		for (E e : right) {
			if (first) {
				first = false;
			} else {
				sb.append(", ");
			}

			sb.append(e);
		}

		sb.append("]");

		return sb.toString();
	}

	@Override
	public Iterator<E> iterator() {
		return new CPIterator();
	}

	private class CPIterator implements Iterator<E> {
		private final Iterator<E> leftIt = left.iterator();
		private final Iterator<E> rightIt = right.iterator();

		@Override
		public boolean hasNext() {
			return leftIt.hasNext() || rightIt.hasNext();
		}

		@Override
		public E next() {
			if (leftIt.hasNext()) {
				return leftIt.next();
			} else {
				return rightIt.next();
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	// Immutable so the following methods just throw an
	// UnsupportedOperationException.
	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
}
