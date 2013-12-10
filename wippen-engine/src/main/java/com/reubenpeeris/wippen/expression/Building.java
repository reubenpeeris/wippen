package com.reubenpeeris.wippen.expression;

import java.util.Collection;
import java.util.Collections;

import lombok.EqualsAndHashCode;

import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.expression.Move.Type;

/**
 * A Building contains multiple cards that have been built together to form a
 * specific value by a specific player.
 */
@EqualsAndHashCode(callSuper=false)
public final class Building extends Pile {
	private final Expression expression;
	private final Player player;

	public Building(Move move, Player player) {
		if (move == null || player == null || player == Player.NOBODY || !move.getType().equals(Type.BUILD)) {
			throw new IllegalArgumentException();
		}
		this.expression = move;
		this.player = player;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public String toString() {
		return expression.getValue() + "B" + player.getPosition();
	}

	@Override
	public Collection<Pile> getPiles() {
		return Collections.<Pile>singleton(this);
	}

	@Override
	public int getValue() {
		return expression.getValue();
	}

	@Override
	public Rank getRank() {
		return new Rank(expression.getValue());
	}

	@Override
	public Collection<Card> getCards() {
		return expression.getCards();
	}
}
