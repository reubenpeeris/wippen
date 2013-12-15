package com.reubenpeeris.wippen.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.NonNull;

import com.reubenpeeris.wippen.engine.Player;

public abstract class Pile extends Expression {
	/**
	 * Limit scope so that robot implementors cannot create their own
	 * implementations of Pile.
	 */
	Pile() {
	}

	public abstract Rank getRank();

	public Player getPlayer() {
		return Player.NOBODY;
	}

	public static Collection<Card> getCards(@NonNull Collection<Pile> piles) {
		List<Card> cards = new ArrayList<>();

		for (Pile pile : piles) {
			cards.addAll(pile.getCards());
		}

		return Collections.unmodifiableCollection(cards);
	}
}
