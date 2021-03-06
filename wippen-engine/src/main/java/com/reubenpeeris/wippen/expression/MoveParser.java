package com.reubenpeeris.wippen.expression;

import java.util.Collection;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.engine.WippenIllegalFormatException;
import com.reubenpeeris.wippen.expression.Move.Type;

final class MoveParser {
	private static final Pattern MOVE_PATTERN = Pattern.compile("(BUILD|CAPTURE|DISCARD) (?:(.*) USING )?([^ ]*)$");
	private static final int TYPE_GROUP = 1;
	private static final int EXPRESSION_GROUP = 2;
	private static final int CARD_GROUP = 3;
	private static final String OPERATOR_PRESEDENCE = "=+-*/";
	private static final Pattern BUILDING_PATTERN = Pattern.compile("^([1-9]|1[0-3])B(\\d+)$");
	private static final int RANK_GROUP = 1;
	private static final int PLAYER_POSITION_GROUP = 2;
	private static final int CARDS_NEEDED_FOR_PAIR = 2;

	private MoveParser() {
	}

	public static Move parseMove(String moveExpression, Set<Pile> table, Set<Card> hand, Player player) {
		try {
			Matcher matcher = MOVE_PATTERN.matcher(moveExpression);
			if (!matcher.matches()) {
				throw new WippenIllegalFormatException();
			}

			Type type = Type.valueOf(matcher.group(TYPE_GROUP));
			Expression expression = parseMath(matcher.group(EXPRESSION_GROUP), table);
			Card handCard = Card.parseCard(matcher.group(CARD_GROUP));
			if (Move.checkArgs(type, expression, handCard, table, hand, player) == null) {
				return new Move(type, expression, handCard, table, hand, player);
			} else {
				return null;
			}
		} catch (WippenIllegalFormatException e) {
			return null;
		}
	}

	private static int precedence(char c) {
		return OPERATOR_PRESEDENCE.indexOf(c);
	}

	// Using the shunting-yard algorithm
	// http://en.wikipedia.org/wiki/Shunting_yard_algorithm
	// http://www.chris-j.co.uk/parsing.php
	private static String infixToPostfix(String expression) {
		StringBuilder output = new StringBuilder();
		Stack<Character> stack = new Stack<>();

		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);

			switch (c) {
			case '(':
				stack.push(c);
				break;
			case ')':
				char popped;
				while ((popped = stack.pop()) != '(') {
					output.append(' ');
					output.append(popped);
				}
				break;
			default:
				int precidence = precedence(c);
				if (precidence == -1) {
					// Operand
					output.append(c);
				} else {
					while (!stack.isEmpty() && precedence(stack.peek()) >= precidence) {
						output.append(' ');
						output.append(stack.pop());
					}

					output.append(' ');
					stack.push(c);
				}
			}
		}

		while (!stack.isEmpty()) {
			output.append(' ');
			output.append(stack.pop());
		}

		return output.toString().trim();
	}

	private static Expression parseMath(String expression, Collection<Pile> table) {
		if (expression == null) {
			return null;
		}

		String[] tokens = infixToPostfix(expression).split("\\s");

		Stack<Expression> stack = new Stack<>();

		for (String token : tokens) {
			if (token.length() == 1) {
				// Operator
				NodeBuilder nodeBuilder = null;
				switch (token.charAt(0)) {
				case '/':
					nodeBuilder = Divide.builder();
					break;
				case '*':
					nodeBuilder = Multiply.builder();
					break;
				case '-':
					nodeBuilder = Subtract.builder();
					break;
				case '+':
					nodeBuilder = Add.builder();
					break;
				case '=':
					nodeBuilder = Equals.builder();
					break;
				default:
					return null;
				}

				if (stack.size() < CARDS_NEEDED_FOR_PAIR) {
					return null;
				}

				nodeBuilder.right(stack.pop());
				nodeBuilder.left(stack.pop());

				stack.push(nodeBuilder.build());
			} else {
				try {
					stack.push(parsePile(token, table));
				} catch (WippenIllegalFormatException e) {
					return null;
				}
			}
		}

		if (stack.size() != 1) {
			return null;
		}

		return stack.pop();
	}

	private static Pile parsePile(String string, Collection<Pile> table) {
		Matcher matcher = BUILDING_PATTERN.matcher(string);

		if (matcher.matches()) {
			Rank rank = Rank.fromInt(Integer.parseInt(matcher.group(RANK_GROUP)));
			int position = Integer.parseInt(matcher.group(PLAYER_POSITION_GROUP));

			for (Pile pile : table) {
				if (pile.getValue() == rank.getValue() && pile.getPlayer().getPosition() == position) {
					return pile;
				}
			}

			throw new WippenIllegalFormatException("Pile not found on table: " + string);
		} else {
			return Card.parseCard(string);
		}
	}
}
