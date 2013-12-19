package com.reubenpeeris.wippen.examples;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Set;

import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.engine.Score;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.robot.BaseRobot;

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
	public Move takeTurn(Set<Pile> table, Set<Card> hand) {
		sendMessage(String.format("TAKE_TURN TABLE=%s HAND=%s", table.toString(), hand.toString()));

		while (true) {
			String expression = receiveMessage();
			try {
				Move move = factory().newMove(expression);
				return move;
			} catch (Exception e) {
				sendMessage("TRY AGAIN (" + e.getMessage() + ")");
			}
		}
	}

	@Override
	public void turnPlayed(Player player, Set<Pile> table, Move move) {
		sendMessage(String.format("TURN_PLAYED PLAYER=%d TABLE=%s ACTION=%s", player.getPosition(), table, move));
	}

	@Override
	public void gameComplete(List<Score> scores) {
		sendMessage(scores.toString());
	}
}
