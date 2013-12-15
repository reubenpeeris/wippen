package com.reubenpeeris.wippen.expression;

import java.util.Collection;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.reubenpeeris.wippen.engine.WippenIllegalFormatException;
import com.reubenpeeris.wippen.expression.Move.Type;

final class Parser {
	private Parser() {
	}

	// Order is significant, increasing precedence left to right.
	private static final String OPERATORS = "=+-*/";
	private static final Pattern BUILDING_PATTERN = Pattern.compile("^([1-9]|1[0-3])B(\\d+)$");
	private static final Pattern MOVE_PATTERN = Pattern.compile("(BUILD|CAPTURE|DISCARD) (?:(.*) USING )?([^ ]*)$");

	public static Move parseMove(String moveExpression, Collection<Pile> table) throws WippenIllegalFormatException {
		Matcher matcher = MOVE_PATTERN.matcher(moveExpression);
		if (!matcher.matches()) {
			throw new WippenIllegalFormatException("Unable to parse move expression: " + moveExpression);
		}

		Type type = Type.valueOf(matcher.group(1));
		Expression expression = parseMath(matcher.group(2), table);
		Card handCard = Card.parseCard(matcher.group(3));

		return new Move(type, expression, handCard);
	}

	private static int precedence(char c) {
		return OPERATORS.indexOf(c);
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

	private static Expression parseMath(String expression, Collection<Pile> table) throws WippenIllegalFormatException {
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
					throw new WippenIllegalFormatException(expression);
				}

				if (stack.size() < 2) {
					throw new WippenIllegalFormatException(expression);
				}

				nodeBuilder.right(stack.pop());
				nodeBuilder.left(stack.pop());

				Expression e = nodeBuilder.build();
				if (e == null) {
					throw new WippenIllegalFormatException(expression);
				}

				stack.push(e);
			} else {
				try {
					stack.push(parsePile(token, table));
				} catch (WippenIllegalFormatException e) {
					throw new WippenIllegalFormatException(expression, e);
				}
			}
		}

		if (stack.size() != 1) {
			throw new WippenIllegalFormatException(expression);
		}

		return stack.pop();
	}

	private static Pile parsePile(String string, Collection<Pile> table) throws WippenIllegalFormatException {
		Matcher matcher = BUILDING_PATTERN.matcher(string);

		if (matcher.matches()) {
			Rank rank = new Rank(Integer.parseInt(matcher.group(1)));
			int position = Integer.parseInt(matcher.group(2));

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
