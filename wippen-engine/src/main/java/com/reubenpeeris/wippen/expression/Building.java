package com.reubenpeeris.wippen.expression;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import com.reubenpeeris.wippen.engine.Player;

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
	private final Player player;

	//TODO should take an expression to verify maths is right?
	public Building(Collection<Card> cards, Rank rank, Player player) {
		if (cards == null || rank == null || player == null || player == Player.NOBODY) {
			throw new IllegalArgumentException();
		}
		this.rank = rank;
		this.cards = Collections.unmodifiableCollection(new HashSet<Card>(cards));
		this.player = player;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public String toString() {
		return rank.toString() + "B" + player.getPosition();
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
