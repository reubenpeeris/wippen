package com.reubenpeeris.wippen.examples;

import static com.reubenpeeris.wippen.expression.Move.Type.*;

import java.util.Set;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;

public class PairAdder extends SingleValueMatcher {
	@Override
	public Move takeTurn(Set<Pile> table, Set<Card> hand) {
		for (Pile pile1 : table) {
			for (Pile pile2 : table) {
				if (pile1 != pile2) {
					Expression expression = factory().newAdd(pile1, pile2);
					for (Card card : hand) {
						if (expression.getValue() == card.getValue()) {
							return factory().newMove(CAPTURE, expression, card);
						}
					}
				}
			}
		}

		return super.takeTurn(table, hand);
	}
}