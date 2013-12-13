package com.reubenpeeris.wippen.expression;

import java.util.Collection;

/**
 * This hierarchy of classes are immutable.
 */
public abstract class Expression {
	/**
	 * Limit visibility of constructor to package. This way robot implementers
	 * cannot create their own Expressions.
	 */
	Expression() {
	}

	public abstract int getValue();

	public abstract Collection<Pile> getPiles();

	public abstract Collection<Card> getCards();
}
