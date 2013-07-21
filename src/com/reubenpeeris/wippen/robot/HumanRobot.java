package com.reubenpeeris.wippen.robot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collection;

import com.reubenpeeris.wippen.engine.Parser;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.Pile;

public class HumanRobot extends BaseRobot {
	private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out)); 
	
	private void sendMessage(String message) {
		try {
			out.append(message).append('\n').flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	private String receiveMessage() {
		try {
			return in.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String matchStart(int players, int rounds, int position) {
		sendMessage(String.format("MATCH_START PLAYERS=%d ROUNDS=%d POSITION=%d", players, rounds, position));
		return receiveMessage();
	}

	@Override
	public void gameStart(int first) {
		sendMessage(String.format("GAME_START FIRST_PLAYER=%d", first));
	}

	@Override
	public Expression takeTurn(Collection<Pile> table, Collection<Card> hand) {
		sendMessage(String.format("TAKE_TURN TABLE=%s HAND=%s", table.toString(), hand.toString()));
		
		Expression parsedExpression = null;
		boolean done = false;
		do {
			String expression = receiveMessage();
			try {
				//TODO fix!
				//This does not check as much as the MoveValidator
				//E.g. if pile does not exist or belongs to wrong owner
				parsedExpression = Parser.parseExpression(expression);
				done = true;
			} catch (Exception e) {
				sendMessage("TRY AGAIN (" + e.getMessage() + ")");
			}
		} while (!done);
		
		return parsedExpression;
	}

	@Override
	public void turnPlayed(int player, Collection<Pile> table, Expression expression) {
		sendMessage(String.format("TURN_PLAYED PLAYER=%d TABLE=%s ACTION=%s", player, table.toString(), expression));
	}

	@Override
	public void gameComplete(String scores, String totals) {
		sendMessage(String.format("GAME_COMPLETE SCORES=%s TOTALS=%s", scores, totals));
	}
}
