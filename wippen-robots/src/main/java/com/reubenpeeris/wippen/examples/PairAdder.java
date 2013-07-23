package com.reubenpeeris.wippen.examples;

import java.util.Collection;

import com.reubenpeeris.wippen.expression.Add;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Equals;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.Pile;

public class PairAdder extends FirstValueMatcher {
    @Override
    public Expression takeTurn(Collection<Pile> table, Collection<Card> hand) {
        for (Pile pile1 : table) {
            for (Pile pile2 : table) {
                if (pile1 != pile2) {
                    Expression expression = new Add(pile1, pile2);
                    for (Card card : hand) {
                        if (expression.getValue() == card.getValue()) {
                            return new Equals(card, expression);
                        }
                    }
                }
            }
        }

        return super.takeTurn(table, hand);
    }
}