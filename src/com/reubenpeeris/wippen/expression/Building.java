package com.reubenpeeris.wippen.expression;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * A Building contains multiple cards that have been built together to form a
 * specific value by a specific player.
 */
@EqualsAndHashCode(callSuper=false)
public final class Building extends Pile {
	@Getter
	private final Collection<Card> cards;
	@Getter
	private final Rank rank;
	private final int playerPosition;

	public Building(Collection<Card> cards, Rank rank, int playerPosition) {
		this.rank = rank;
		this.cards = Collections.unmodifiableCollection(new HashSet<Card>(cards));
		this.playerPosition = playerPosition;
	}

	@Override
	public boolean wasBuiltByPlayerInPosion(int position) {
		return playerPosition == position;
	}

	@Override
	public String toString() {
		return rank.toString() + (playerPosition == -1 ? "" : "B" + playerPosition);
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
