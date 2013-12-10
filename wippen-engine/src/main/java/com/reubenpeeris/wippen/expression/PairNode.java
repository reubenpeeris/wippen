package com.reubenpeeris.wippen.expression;

import java.util.Collection;

import lombok.Getter;

import com.reubenpeeris.wippen.util.CollectionPair;


/**
 * Represents a mathematical expression build using cards.
 */
@Getter
abstract class PairNode extends Expression {
	abstract static class Validator {
		abstract boolean isValid(int left, int right);
		abstract boolean canParentEquals();
		
		final boolean isValid(Expression left, Expression right) {
			if (left == null || right == null) {
				return false;
			}
			
			if (!isValid(left.getValue(), right.getValue())){
				return false;
			}
			
			return (canParentEquals() ||
					(!left.getClass().equals(Equals.class) && !right.getClass()
							.equals(Equals.class)));
		}
	}
	
	static final Validator ALWAYS_VALID = new Validator() {
		@Override
		public boolean isValid(int left, int right) {
			return true;
		}

		@Override
		public boolean canParentEquals() {
			return false;
		}
	};
	
	private final Expression left;
	private final Expression right;

	private int value;
	
	PairNode(Expression left, Expression right, Validator validator) {
		if (!validator.isValid(left, right)) {
			throw new IllegalArgumentException();
		}
		
		this.left = left;
		this.right = right;
		this.value = getValue(left.getValue(), right.getValue());
		
		this.piles = new CollectionPair<>(left.getPiles(), right.getPiles());
	}
	
	PairNode(Expression left, Expression right) {
		this(left, right, ALWAYS_VALID);
	}
	
	private Collection<Pile> piles;
	
	@Override
	public Collection<Pile> getPiles() {
		return piles;
	}
	
	@Override
	public Collection<Card> getCards() {
		return Pile.getCards(getPiles());
	}
	
	abstract String getOperatorSymbol();
		
	abstract int getValue(int left, int right); 
	
	@Override
	public final String toString() {
		String expression = left + getOperatorSymbol() + right;
		
		return !Equals.class.equals(getClass()) ? "(" + expression + ")" : expression;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	//NOT GENERATED
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PairNode other = (PairNode) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}
}
