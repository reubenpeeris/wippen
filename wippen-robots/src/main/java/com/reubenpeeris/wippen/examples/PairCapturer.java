package com.reubenpeeris.wippen.examples;

import static com.reubenpeeris.wippen.expression.Move.Type.*;

import java.util.Set;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.NodeBuilder;
import com.reubenpeeris.wippen.expression.Pile;

public class PairCapturer extends SingleValueMatcher {
	@Override
	public Move takeTurn(Set<Pile> table, Set<Card> hand) {
		for (Pile pile1 : table) {
			for (Pile pile2 : table) {
				for (NodeBuilder nodeBuilder : factory().newBuilders()) {
					Expression expression = nodeBuilder.left(pile1).right(pile2).build();
					for (Card card : hand) {
						Move move = factory().tryMove(CAPTURE, expression, card);
						if (move != null) {
							return move;
						}
					}
				}
			}
		}

		return super.takeTurn(table, hand);
	}
}