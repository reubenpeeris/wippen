package com.reubenpeeris.wippen.examples;

import java.util.Collection;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Equals;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.robot.BaseRobot;

public class FirstValueMatcher extends BaseRobot {
    @Override
    public Expression takeTurn(Collection<Pile> table, Collection<Card> hand) {
        for (Pile pile : table) {
            for (Card card : hand) {
                if (pile.getValue() == card.getValue()) {
                    return new Equals(card, pile);
                }
            }
        }

        return hand.iterator().next();
    }
}