package com.reubenpeeris.wippen.expression;

import java.util.Set;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.expression.Move.Type;

@RequiredArgsConstructor
public class ExpressionFactory extends AnonymousExpressionFactory {
	@NonNull
	private final Set<Pile> table;
	private final Set<Card> hand;
	@NonNull
	private final Player player;

	public Move tryMove(Type type, Expression expression, Card handCard) {
		return Move.create(type, expression, handCard, table, hand, player);
	}

	public Move tryMove(String moveExpression) {
		return MoveParser.parseMove(moveExpression, table, hand, player);
	}

	public Move newMove(Type type, Expression expression, Card handCard) {
		return throwIfNull(Move.create(type, expression, handCard, table, hand, player));
	}

	public Move newMove(String moveExpression) {
		return throwIfNull(MoveParser.parseMove(moveExpression, table, hand, player));
	}
}
