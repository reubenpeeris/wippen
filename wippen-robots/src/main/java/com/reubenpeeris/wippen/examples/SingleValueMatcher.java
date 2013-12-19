package com.reubenpeeris.wippen.examples;

import static com.reubenpeeris.wippen.expression.Move.Type.*;

import java.util.Set;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;

public class SingleValueMatcher extends Discarder {
	@Override
	public Move takeTurn(Set<Pile> table, Set<Card> hand) {
		for (Pile pile : table) {
			for (Card card : hand) {
				if (pile.getValue() == card.getValue()) {
					return factory().newMove(CAPTURE, pile, card);
				}
			}
		}

		return super.takeTurn(table, hand);
	}
}