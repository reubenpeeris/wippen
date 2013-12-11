package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.expression.Move.Type.*;

import java.util.Collection;
import java.util.Collections;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import com.reubenpeeris.wippen.engine.Player;

/**
 * A Building contains multiple cards that have been built together to form a
 * specific value by a specific player.
 */
@EqualsAndHashCode(callSuper = false)
public final class Building extends Pile {
	private final Expression expression;
	@Getter
	private final Player player;

	public Building(@NonNull Move move, @NonNull Player player) {
		if (move.getType() != BUILD) {
			throw new IllegalArgumentException("Move must have type BUILD");
		}
		if (player == Player.NOBODY) {
			throw new IllegalArgumentException("Player cannot be NOBODY");
		}

		this.expression = move;
		this.player = player;
	}

	@Override
	public String toString() {
		return expression.getValue() + "B" + player.getPosition();
	}

	@Override
	public Collection<Pile> getPiles() {
		return Collections.<Pile> singleton(this);
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
