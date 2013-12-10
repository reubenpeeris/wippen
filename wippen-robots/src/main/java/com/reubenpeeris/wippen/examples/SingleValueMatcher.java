package com.reubenpeeris.wippen.examples;

import java.util.Collection;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;

import static com.reubenpeeris.wippen.expression.Move.Type.*;

public class SingleValueMatcher extends Discarder {
    @Override
    public Move takeTurn(Collection<Pile> table, Collection<Card> hand) {
        for (Pile pile : table) {
            for (Card card : hand) {
                if (pile.getValue() == card.getValue()) {
                    return new Move(CAPTURE, card, pile);
                }
            }
        }

        return super.takeTurn(table, hand);
    }
}