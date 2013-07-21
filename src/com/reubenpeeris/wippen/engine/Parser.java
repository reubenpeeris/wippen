package com.reubenpeeris.wippen.engine;

import java.util.Collections;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.reubenpeeris.wippen.expression.Add;
import com.reubenpeeris.wippen.expression.Building;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Divide;
import com.reubenpeeris.wippen.expression.Equals;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.Multiply;
import com.reubenpeeris.wippen.expression.NodeBuilder;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.expression.Rank;
import com.reubenpeeris.wippen.expression.Subtract;
import com.reubenpeeris.wippen.expression.Suit;

public final class Parser {
	//Order is significant, increasing precedence left to right.
	private static final String OPERATORS = "=+-*/";
	private static final Pattern PILE_PATTERN = Pattern.compile("^([1-9]|1[0-3])(?:([CDHS])|(?:B\\d+)?)$");
	
	public static Expression parseExpression(String expression) throws ParseException {
		return parseMath(expression);
	}
	
	private static int precedence(char c) {
		return OPERATORS.indexOf(c);
	}
	
	//Using the shunting-yard algorithm
	//http://en.wikipedia.org/wiki/Shunting_yard_algorithm
	//http://www.chris-j.co.uk/parsing.php
	static String infixToPostfix(String expression) {
		StringBuilder output = new StringBuilder();
		Stack<Character> stack = new Stack<Character>();
		
		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);
			
			switch (c) {
				case '(' :
					stack.push(c);
					break;
				case ')' :
					char popped;
					while ((popped = stack.pop()) != '(') {
						output.append(' ');
						output.append(popped);
					}
					break;
				default :
					int precidence = precedence(c);
					if (precidence == -1) {
						//Operand
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
		
		return output.toString();
	}
	
	static Expression parseMath(String expression) throws ParseException {
		String[] tokens = infixToPostfix(expression).split("\\s");
		
		Stack<Expression> stack = new Stack<Expression>();

		for (String token : tokens) {
			if (token.length() == 1 && precedence(token.charAt(0)) != -1) {
				//Operator
				NodeBuilder nodeBuilder = null;
				switch (token.charAt(0)) {
					case '/' : nodeBuilder = new Divide.Builder(); break;
					case '*' : nodeBuilder = new Multiply.Builder(); break;
					case '-' : nodeBuilder = new Subtract.Builder(); break;
					case '+' : nodeBuilder = new Add.Builder(); break;
					case '=' : nodeBuilder = new Equals.Builder(); break;
				}

				if (stack.size() < 2) {
					throw new IllegalStateException();
				}
				
				nodeBuilder.setRight(stack.pop());
				nodeBuilder.setLeft(stack.pop());
				
				Expression e =  nodeBuilder.build();
				if (e == null) {
					throw new ParseException("Invalid format parsing: '" + expression + "'");
				}
				
				stack.push(e);
			} else {
				try {
					stack.push(parsePile(token));
				} catch (ParseException e) {
					throw new ParseException("Invalid format parsing: '" + expression + "'", e);
				}
			}
		}
		
		if (stack.size() != 1) {
			throw new ParseException("Unable to parse input: " + expression);
		}
		
		return stack.pop();
	}
	
	static Pile parsePile(String string) throws ParseException {
		Matcher matcher = PILE_PATTERN.matcher(string);
		
		if (!matcher.matches()) {
			throw new ParseException("Invalid format: '" + string + "'");
		}
		
		final Rank rank = new Rank(Integer.valueOf(matcher.group(1)));
		final String suit = matcher.group(2);
		
		if (suit == null) {
			return new Building(Collections.<Card>emptyList(), rank, Player.NOBODY);
		}
		
		return new Card(Suit.fromLetter(suit), rank);
	}
}

