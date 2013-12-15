package com.reubenpeeris.wippen.expression;

import java.util.Collection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import com.reubenpeeris.wippen.util.CollectionPair;

@EqualsAndHashCode(callSuper = false, of = { "left", "right", "clazz" })
abstract class PairNode extends Expression {
	abstract static class Validator {
		abstract boolean isValid(int left, int right);

		abstract boolean canHaveLeftChildOfTypeEqual();

		final boolean isValid(Expression left, Expression right) {
			if (left == null || right == null) {
				return false;
			}

			if (!isValid(left.getValue(), right.getValue())) {
				return false;
			}

			return (canHaveLeftChildOfTypeEqual() || (!left.getClass().equals(Equals.class) && !right.getClass().equals(Equals.class)));
		}
	}

	static final Validator ALWAYS_VALID = new Validator() {
		@Override
		public boolean isValid(int left, int right) {
			return true;
		}

		@Override
		public boolean canHaveLeftChildOfTypeEqual() {
			return false;
		}
	};

	// Used for hashCode and equals
	private final Class<? extends PairNode> clazz = getClass();
	private final Expression left;
	private final Expression right;
	private Collection<Pile> piles;
	@Getter
	private Collection<Card> cards;
	@Getter
	private final int value;

	PairNode(@NonNull Expression left, @NonNull Expression right, @NonNull Validator validator) {
		if (!validator.isValid(left, right)) {
			throw new IllegalArgumentException();
		}

		this.left = left;
		this.right = right;
		this.value = getValue(left.getValue(), right.getValue());

		this.piles = new CollectionPair<>(left.getPiles(), right.getPiles());
		this.cards = Pile.getCards(this.piles);
	}

	PairNode(Expression left, Expression right) {
		this(left, right, ALWAYS_VALID);
	}

	@Override
	public Collection<Pile> getPiles() {
		return piles;
	}

	abstract String getOperatorSymbol();

	abstract int getValue(int left, int right);

	@Override
	public final String toString() {
		return "(" + left + getOperatorSymbol() + right + ")";
	}
}
