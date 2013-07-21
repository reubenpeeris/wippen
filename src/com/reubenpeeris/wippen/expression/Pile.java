package com.reubenpeeris.wippen.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Pile extends Expression {
	/**
	 * Limit scope so that robot implementors cannot create their own
	 * implementations of Pile.
	 */
	Pile(){}
	public abstract Rank getRank();
	public abstract Collection<Card> getCards();
	
	//not really happy with this as int is not type safe.
	public abstract boolean wasBuiltByPlayerInPosion(int position);
	
	public static Collection<Card> getCards(Collection<Pile> piles) {
		if (piles == null) {
			throw new IllegalArgumentException();
		}
		
		List<Card> cards = new ArrayList<Card>();
		
		for (Pile pile : piles) {
			cards.addAll(pile.getCards());
		}
		
		return cards;
	}
}
