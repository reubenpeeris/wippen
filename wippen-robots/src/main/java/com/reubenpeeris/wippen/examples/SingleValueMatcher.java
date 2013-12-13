package com.reubenpeeris.wippen.examples;

import static com.reubenpeeris.wippen.expression.Move.Type.*;

import java.util.Collection;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;

public class SingleValueMatcher extends Discarder {
	@Override
	public Move takeTurn(Collection<Pile> table, Collection<Card> hand) {
		for (Pile pile : table) {
			for (Card card : hand) {
				if (pile.getValue() == card.getValue()) {
					return new Move(CAPTURE, pile, card);
				}
			}
		}

		return super.takeTurn(table, hand);
	}
}