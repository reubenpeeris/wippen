package com.reubenpeeris.wippen.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.reubenpeeris.wippen.engine.Player;

public abstract class Pile extends Expression {
	/**
	 * Limit scope so that robot implementors cannot create their own
	 * implementations of Pile.
	 */
	Pile(){}
	public abstract Rank getRank();
	
	public Player getPlayer() {
		return Player.NOBODY;
	}
	
	public static Collection<Card> getCards(Collection<Pile> piles) {
		if (piles == null) {
			throw new IllegalArgumentException();
		}
		
		List<Card> cards = new ArrayList<>();
		
		for (Pile pile : piles) {
			cards.addAll(pile.getCards());
		}
		
		return cards;
	}
}
