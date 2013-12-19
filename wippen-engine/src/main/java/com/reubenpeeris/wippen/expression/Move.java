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

@EqualsAndHashCode(of = { "type", "coreExpression", "handCard", "player" }, callSuper = false)
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
		},

		INVALID(false) {
			@Override
			Expression createExpresion(Card handCard, Expression expression) {
				return null;
			}

			@Override
			String getMessage() {
				return "no parameters are valid";
			}
		};

		private final boolean pileGenerated;

		abstract Expression createExpresion(Card handCard, Expression expression);

		abstract String getMessage();
	}

	@Getter
	private final Type type;
	@Getter
	private final Card handCard;
	@Getter
	private final Set<Pile> tablePilesUsed;
	private final Player player;

	@Getter(AccessLevel.NONE)
	private final Expression expression;
	@Getter(AccessLevel.NONE)
	private final Expression coreExpression;

	Move(Type type, Expression expression, Card handCard, @NonNull Set<Pile> table, Set<Card> hand, @NonNull Player player) {
		String error = checkArgs(type, expression, handCard, table, hand, player);
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
		this.player = player;
	}

	static String checkArgs(@NonNull Type type, Expression expression, @NonNull Card handCard, Set<Pile> table, Set<Card> hand, Player player) {
		expression = type.createExpresion(handCard, expression);

		if (expression == null) {
			return String.format("For %s type, %s", type, type.getMessage());
		}

		if (new HashSet<>(expression.getCards()).size() < expression.getCards().size()) {
			return "Trying to use card multiple times: " + expression;
		}

		if (hand != null && !hand.contains(handCard)) {
			return "Hand does not contain handCard: " + handCard;
		}

		for (Pile pile : expression.getPiles()) {
			if (!pile.equals(handCard)) {
				if (!table.contains(pile)) {
					return "Non-hand pile not present on table: " + pile;
				}
			}
		}

		if (type == Type.BUILD) {
			for (Pile pile : expression.getPiles()) {
				if (pile.getPlayer().equals(player) && pile.getValue() != expression.getValue()) {
					return "A building cannot be made using your own building of a different value";
				}
			}

			if (hand != null) {
				boolean hasCardOfCorrectValue = false;
				for (Card card : hand) {
					if (!card.equals(handCard) && card.getValue() == expression.getValue()) {
						hasCardOfCorrectValue = true;
						break;
					}
				}

				if (!hasCardOfCorrectValue) {
					return "Player must have hand card of the value of the building being created: " + expression.getValue();
				}
			}
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

	public Pile getPileCreated() {
		if (type == Type.BUILD) {
			return new Building(this, player);
		} else if (type == Type.DISCARD) {
			return getHandCard();
		} else {
			return null;
		}
	}

	public boolean isSound(@NonNull Set<Pile> table, @NonNull Set<Card> hand, @NonNull Player player) {
		return checkArgs(type, coreExpression, handCard, table, hand, player) == null;
	}

	@Override
	public String toString() {
		if (coreExpression != null) {
			return type + " " + coreExpression.toString() + " USING " + handCard;
		} else {
			return type + " " + handCard;
		}
	}

	static Move create(@NonNull Type type, Expression expression, @NonNull Card handCard, @NonNull Set<Pile> table, @NonNull Set<Card> hand,
			@NonNull Player player) {
		if (Move.checkArgs(type, expression, handCard, table, hand, player) == null) {
			return new Move(type, expression, handCard, table, hand, player);
		}
		return null;
	}
}
