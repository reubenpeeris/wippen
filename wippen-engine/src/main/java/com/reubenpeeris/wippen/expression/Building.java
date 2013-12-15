package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.expression.Move.Type.*;

import java.util.Collection;
import java.util.Collections;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import com.reubenpeeris.wippen.engine.Player;

/**
 * A Building contains multiple cards that have been built together to form a
 * specific value by a specific player.
 */
@Getter
@EqualsAndHashCode(of = { "move", "player" }, callSuper = false)
public final class Building extends Pile {
	@Getter(AccessLevel.NONE)
	private final Move move;

	private final Collection<Pile> piles;
	private final Player player;

	public Building(@NonNull Move move, @NonNull Player player) {
		if (move.getType() != BUILD) {
			throw new IllegalArgumentException("Move must have type BUILD");
		}
		if (player == Player.NOBODY) {
			throw new IllegalArgumentException("Player cannot be NOBODY");
		}

		this.move = move;
		this.player = player;
		this.piles = Collections.<Pile> singleton(this);
	}

	@Override
	public String toString() {
		return move.getValue() + "B" + player.getPosition();
	}

	@Override
	public int getValue() {
		return move.getValue();
	}

	@Override
	public Rank getRank() {
		return new Rank(move.getValue());
	}

	@Override
	public Collection<Card> getCards() {
		return move.getCards();
	}
}
