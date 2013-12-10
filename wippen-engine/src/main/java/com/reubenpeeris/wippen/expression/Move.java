package com.reubenpeeris.wippen.expression;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import com.reubenpeeris.wippen.engine.Player;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Move extends Expression {
    @AllArgsConstructor
    @Getter
    public enum Type {
        BUILD(true) {
            @Override
            Expression createExpresion(Card handCard, Expression expression) {
                if (expression == null || !expression.getCards().contains(handCard)) {
                    return null;
                }

                return expression;
            }

            @Override
            String getMessage() {
                return "expression must contain handCard";
            }
        },

        CAPTURE(false) {
            @Override
            Expression createExpresion(Card handCard, Expression expression) {
                if (expression == null || expression.getValue() != handCard.getValue()) {
                    return null;
                }

                return new Equals(handCard, expression);
            }

            @Override
            String getMessage() {
                return "hand card must have same value as expression";
            }
        },

        DISCARD(true) {
            @Override
            Expression createExpresion(Card handCard, Expression expression) {
                if (expression != null) {
                    return null;
                }

                return handCard;
            }

            @Override
            String getMessage() {
                return "expression must be null";
            }
        };

        private final boolean pileGenerated;

        abstract Expression createExpresion(Card handCard, Expression expression);

        abstract String getMessage();
    }

    private final Type type;
    private final Card handCard;

    private final Set<Pile> tablePilesUsed;

    @Getter(AccessLevel.NONE)
    private final Expression expression;

    public Move(Type type, Card handCard, Expression expression) {
        this(type, handCard, expression, true);
    }

    // The boolean generateExpression is not good, but is used by the parser for
    // now.
    Move(Type type, Card handCard, Expression expression, boolean generateExpression) {
        String error = checkArgs(type, handCard, expression, generateExpression);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        if (generateExpression) {
            this.expression = type.createExpresion(handCard, expression);
        } else {
            this.expression = expression;
        }
        this.type = type;
        this.handCard = handCard;

        Set<Pile> tablePilesUsed = new HashSet<Pile>(this.expression.getPiles());
        tablePilesUsed.remove(handCard);
        this.tablePilesUsed = Collections.unmodifiableSet(tablePilesUsed);
    }

    private static String checkArgs(@NonNull Type type, @NonNull Card handCard, Expression expression, boolean generateExpression) {
        if (generateExpression) {
            expression = type.createExpresion(handCard, expression);
        }

        if (expression == null) {
            return String.format("For %s type %s", type, type.getMessage());
        }

        if (new HashSet<Card>(expression.getCards()).size() < expression.getCards().size()) {
            return "Trying to use card multiple times: " + expression;
        }

        return null;
    }

    @Override
    public int getValue() {
        return expression.getValue();
    }

    @Override
    public Collection<Pile> getPiles() {
        return expression.getPiles();
    }

    @Override
    public Collection<Card> getCards() {
        return expression.getCards();
    }

    public boolean isPileGenerated() {
        return type.isPileGenerated();
    }

    public boolean isValidFor(Collection<Pile> table, Collection<Card> hand, Player player) {
        if (!hand.contains(handCard)) {
            return false;
        }

        for (Pile pile : getPiles()) {
            if (!pile.equals(handCard)) {
                if (!table.contains(pile)) {
                    return false;
                }
            }
        }

        if (type == Type.BUILD) {
            for (Pile pile : getPiles()) {
                if (pile.getPlayer().equals(player) && pile.getValue() != getValue()) {
                    return false;
                }
            }
        }

        return true;
    }

    public static Move create(@NonNull Type type, @NonNull Card handCard, Expression expression, @NonNull Collection<Pile> table,
            @NonNull Collection<Card> hand, @NonNull Player player) {
        if (checkArgs(type, handCard, expression, true) == null) {
            Move move = new Move(type, handCard, expression);
            if (move.isValidFor(table, hand, player)) {
                return move;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return type + ":" + expression.toString();
    }
}
