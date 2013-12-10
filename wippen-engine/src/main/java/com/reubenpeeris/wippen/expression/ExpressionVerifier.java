package com.reubenpeeris.wippen.expression;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.engine.WippenRuleException;
import com.reubenpeeris.wippen.expression.Move.Type;

import static com.reubenpeeris.wippen.expression.Move.Type.*;

public class ExpressionVerifier {
    private ExpressionVerifier() {}

    public static Move verifyExpression(Expression expression, Player player, Collection<Pile> table, Collection<Card> hand) {
        Set<Pile> tablePilesUsed = new HashSet<>();
        Card handCardUsed = null;
        Set<Pile> ownBuildingsUsed = new HashSet<>();

        final int value = expression.getValue();

        pile:
            for (Pile pile : expression.getPiles()) {
                if (table.contains(pile)) {
                    if (!tablePilesUsed.add(pile)) {
                        throw new WippenRuleException("Trying to use Pile multiple times in expression: " + expression);
                    }
                    if (pile.getPlayer().equals(player)) {
                        ownBuildingsUsed.add(pile);
                    }
                    continue pile;
                }

                if (hand.contains(pile)) {
                    if (handCardUsed != null) {
                        throw new WippenRuleException("Trying to use multiple cards from hand in expression: " + expression);
                    }
                    handCardUsed = (Card)pile;

                    continue pile;
                }

                throw new WippenRuleException("Pile not found on table or in hand in expression: " + expression);
                //			}
            }

        if (handCardUsed == null) {
            throw new WippenRuleException("No card from hand used in expression: " + expression);
        }

        final Type type = determineType(expression, handCardUsed);

        //If building check that player has card they claim to
        if (type == BUILD) {
            boolean validBuild = false;

            card:
                for (Card card : hand) {
                    if (!card.equals(handCardUsed) && card.getRank().getValue() == value) {
                        validBuild = true;
                        break card;
                    }
                }

            if (!validBuild) {
                throw new WippenRuleException("Trying to build for a value not contained in hand in expression: " + expression);
            }
        }

        //Check for rebuilding to new value which is illegal
        if (type == BUILD) {
            for (Pile ownBuildingValue : ownBuildingsUsed) {
                if (ownBuildingValue.getValue() != value) {
                    throw new WippenRuleException("Trying to build to new value using own building in expression: " + expression);
                }
            }
        }
        
        return new Move(type, handCardUsed, expression, false);
    }

    private static Type determineType(Expression expression, Card handCardUsed) {
        if (expression.getPiles().size() == 1) {
            return DISCARD;
        }

        // The first card returned by getPiles() will be the first card in the expression
        if (expression.getValue() == handCardUsed.getRank().getValue()
                && expression.getPiles().iterator().next().equals(handCardUsed)
                && (expression.getClass().equals(Equals.class))) {
            return CAPTURE;
        } else {
            return BUILD;
        }
    }
}
