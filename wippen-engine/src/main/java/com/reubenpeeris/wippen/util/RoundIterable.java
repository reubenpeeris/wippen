package com.reubenpeeris.wippen.util;

import java.util.Iterator;
import java.util.List;

public class RoundIterable<T> implements Iterable<T> {
	private final List<T> list;
	private final int startPosition;
	
	public RoundIterable(List<T> list, T start) {
		if (list == null || start == null) {
			throw new IllegalArgumentException();
		}
		
		this.list = list;
		
		startPosition = list.indexOf(start);
		if (startPosition == -1) {
			throw new IllegalArgumentException();
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
			return list.get((position+++startPosition) % list.size());
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
