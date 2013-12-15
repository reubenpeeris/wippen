package com.reubenpeeris.wippen.util;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import lombok.NonNull;

public class RoundIterable<T> implements Iterable<T> {
	private final List<T> list;
	private final int startPosition;

	public RoundIterable(@NonNull List<T> list, @NonNull T start) {
		this.list = list;

		startPosition = list.indexOf(start);
		if (startPosition == -1) {
			throw new IllegalArgumentException("element not found in list: " + start);
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new RoundIterator();
	}

	private class RoundIterator implements Iterator<T> {
		private int position;

		@Override
		public boolean hasNext() {
			return position < list.size();
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return list.get((position++ + startPosition) % list.size());
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
