package com.reubenpeeris.wippen.expression;

import java.util.Collection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.reubenpeeris.wippen.util.CollectionPair;

@EqualsAndHashCode(callSuper = false, of = { "left", "right", "clazz" })
abstract class PairNode extends Expression {
	@RequiredArgsConstructor
	abstract static class Validator {
		private final Class<? extends Expression> clazz;

		abstract boolean isValid(int left, int right);

		final boolean isValid(Expression left, Expression right) {
			if (left == null || right == null) {
				return false;
			}

			if (!left.canHaveParent(clazz) || !right.canHaveParent(clazz)) {
				return false;
			}

			return isValid(left.getValue(), right.getValue());
		}
	}

	static final Validator ALWAYS_VALID = new Validator(Expression.class) {
		@Override
		public boolean isValid(int left, int right) {
			return true;
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
