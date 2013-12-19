package com.reubenpeeris.wippen;

import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class WippenMatchers {
	private WippenMatchers() {
	}

	private static class Unmodifiable<T extends Collection<?>> extends BaseMatcher<T> {
		private final String[] UNMODIFIABLE_COLLECTION_CLASS_PREFIXES = { com.reubenpeeris.wippen.util.CollectionPair.class.getCanonicalName(),
				"java.util.Collections.Unmodifiable", "java.util.Collections.SingletonSet", "java.util.Collections.EmptySet" };

		@Override
		public boolean matches(Object item) {
			if (item != null) {
				String className = item.getClass().getCanonicalName();
				for (String unmodifiableCollectionPrefix : UNMODIFIABLE_COLLECTION_CLASS_PREFIXES) {
					if (className.startsWith(unmodifiableCollectionPrefix)) {
						return true;
					}
				}
			}

			return false;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("class should start with one of the following prefixes: "
					+ Arrays.toString(UNMODIFIABLE_COLLECTION_CLASS_PREFIXES));
		}
	}

	public static <C extends Collection<?>> Matcher<C> unmodifiable() {
		return new Unmodifiable<>();
	}
}
