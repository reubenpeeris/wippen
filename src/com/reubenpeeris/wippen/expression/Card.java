package com.reubenpeeris.wippen.expression;

import java.util.Collection;
import java.util.Collections;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper=false, doNotUseGetters=true)
@Getter
public final class Card extends Pile {
	private final Suit suit;
	private final Rank rank;
	
	public Card(Suit suit, Rank rank) {
		if (suit == null || rank == null) {
			throw new IllegalArgumentException("suit should not be null");
		}
		
		this.suit = suit;
		this.rank = rank;
	}
	
	@Override
	public Collection<Card> getCards() {
		return Collections.singleton(this);
	}
	
	@Override
	public String toString() {
		return rank.toString() + suit.toString();
	}

	@Override
	public boolean wasBuiltByPlayerInPosion(int position) {
		return false;
	}

	@Override
	public Collection<Pile> getPiles() {
		return Collections.<Pile>singleton(this);
	}

	@Override
	public int getValue() {
		return rank.getValue();
	}
}
