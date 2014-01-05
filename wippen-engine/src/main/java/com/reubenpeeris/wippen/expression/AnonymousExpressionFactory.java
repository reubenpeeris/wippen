package com.reubenpeeris.wippen.expression;

import java.util.Arrays;
import java.util.Collection;

public class AnonymousExpressionFactory {
	private static final AnonymousExpressionFactory INSTANCE = new AnonymousExpressionFactory();
	
	//Can be accessed by the static factory() method
	AnonymousExpressionFactory() {
	}
	
	public NodeBuilder addBuilder() {
		return Add.builder();
	}

	public NodeBuilder subtractBuilder() {
		return Subtract.builder();
	}

	public NodeBuilder multiplyBuilder() {
		return Multiply.builder();
	}

	public NodeBuilder divideBuilder() {
		return Divide.builder();
	}

	public NodeBuilder equalsBuilder() {
		return Equals.builder();
	}

	public Collection<NodeBuilder> newBuilders() {
		return Arrays.asList(Add.builder(), Subtract.builder(), Multiply.builder(), Divide.builder(), Equals.builder());
	}

	public Expression tryAdd(Expression left, Expression right) {
		return addBuilder().left(left).right(right).build();
	}

	public Expression trySubtract(Expression left, Expression right) {
		return subtractBuilder().left(left).right(right).build();
	}

	public Expression tryMultiply(Expression left, Expression right) {
		return multiplyBuilder().left(left).right(right).build();
	}

	public Expression tryDivide(Expression left, Expression right) {
		return divideBuilder().left(left).right(right).build();
	}

	public Expression tryEquals(Expression left, Expression right) {
		return equalsBuilder().left(left).right(right).build();
	}

	public Expression newAdd(Expression left, Expression right) {
		return throwIfNull(addBuilder().left(left).right(right).build());
	}

	public Expression newSubtract(Expression left, Expression right) {
		return throwIfNull(subtractBuilder().left(left).right(right).build());
	}

	public Expression newMultiply(Expression left, Expression right) {
		return throwIfNull(multiplyBuilder().left(left).right(right).build());
	}

	public Expression newDivide(Expression left, Expression right) {
		return throwIfNull(divideBuilder().left(left).right(right).build());
	}

	public Expression newEquals(Expression left, Expression right) {
		return throwIfNull(equalsBuilder().left(left).right(right).build());
	}

	public Card newCard(Rank rank, Suit suit) {
		return new Card(rank, suit);
	}

	public static AnonymousExpressionFactory factory() {
		return INSTANCE;
	}
	
	protected <T extends Expression> T throwIfNull(T t) {
		if (t == null) {
			throw new IllegalStateException("Expression is invalid");
		}
		return t;
	}
}