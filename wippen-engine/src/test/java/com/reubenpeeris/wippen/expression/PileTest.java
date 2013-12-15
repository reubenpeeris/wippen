package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.TestData.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

public class PileTest extends BaseExpressionTest {
	static final class MockPile extends Pile {
		private final Collection<Card> cards;

		public MockPile(Card... cards) {
			this.cards = Collections.unmodifiableCollection(Arrays.asList(cards));
		}

		@Override
		public Rank getRank() {
			return null;
		}

		@Override
		public Collection<Card> getCards() {
			return cards;
		}

		@Override
		public int getValue() {
			return 0;
		}

		@Override
		public Collection<Pile> getPiles() {
			return Collections.emptySet();
		}
	}

	@Override
	protected Expression validInstance() {
		return new MockPile();
	}

	@Test
	public void getCards_throws_for_null_piles() {
		expect(NullPointerException.class, "piles");
		Pile.getCards(null);
	}

	@Test
	public void getCards_returns_empty_result_for_empty_input() {
		assertThat(Pile.getCards(Collections.<Pile> emptySet()).size(), is(equalTo(0)));
	}

	@Test
	public void getCards_returns_all_cards_from_multiple_piles() {
		Pile pile1 = new MockPile(c1, c2);
		Pile pile2 = new MockPile(h1, h2);

		assertThat(Pile.getCards(Arrays.asList(pile1, pile2)), contains(c1, c2, h1, h2));
	}
}
