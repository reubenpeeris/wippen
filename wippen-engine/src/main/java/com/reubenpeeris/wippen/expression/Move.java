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
@EqualsAndHashCode(of = { "type", "expression", "handCard" }, callSuper = false)
public final class Move extends Expression {
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
	@Getter(AccessLevel.NONE)
	private final Expression coreExpression;

	public Move(Type type, Expression expression, Card handCard) {
		String error = checkArgs(type, expression, handCard);
		if (error != null) {
			throw new IllegalArgumentException(error);
		}
		this.coreExpression = expression;
		this.expression = type.createExpresion(handCard, expression);

		this.type = type;
		this.handCard = handCard;

		Set<Pile> tablePilesUsed = new HashSet<>(this.expression.getPiles());
		tablePilesUsed.remove(handCard);
		this.tablePilesUsed = Collections.unmodifiableSet(tablePilesUsed);
	}

	private static String checkArgs(@NonNull Type type, Expression expression, @NonNull Card handCard) {
		expression = type.createExpresion(handCard, expression);

		if (expression == null) {
			return String.format("For %s type, %s", type, type.getMessage());
		}

		if (new HashSet<>(expression.getCards()).size() < expression.getCards().size()) {
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

			boolean hasCardOfCorrectValue = false;
			for (Card card : hand) {
				if (!card.equals(handCard) && card.getValue() == getValue()) {
					hasCardOfCorrectValue = true;
					break;
				}
			}

			if (!hasCardOfCorrectValue) {
				return false;
			}
		}

		return true;
	}

	public static Move create(@NonNull Type type, Expression expression, @NonNull Card handCard, @NonNull Collection<Pile> table,
			@NonNull Collection<Card> hand, @NonNull Player player) {
		if (checkArgs(type, expression, handCard) == null) {
			Move move = new Move(type, expression, handCard);
			if (move.isValidFor(table, hand, player)) {
				return move;
			}
		}
		return null;
	}

	public static Move parseMove(String moveExpression, Collection<Pile> table) {
		return Parser.parseMove(moveExpression, table);
	}

	@Override
	public String toString() {
		if (coreExpression != null) {
			return type + " " + coreExpression.toString() + " USING " + handCard;
		} else {
			return type + " " + handCard;
		}
	}
}
