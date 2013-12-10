package com.reubenpeeris.wippen.examples;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.List;

import com.reubenpeeris.wippen.engine.Parser;
import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.engine.Score;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.ExpressionVerifier;
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
    public Move takeTurn(Collection<Pile> table, Collection<Card> hand) {
        sendMessage(String.format("TAKE_TURN TABLE=%s HAND=%s", table.toString(), hand.toString()));

        Move move = null;
        boolean done = false;
        do {
            String expression = receiveMessage();
            try {
                Expression parsedExpression = Parser.parseExpression(expression, table);
                move = ExpressionVerifier.verifyExpression(parsedExpression, getMe(), table, hand);
                done = true;
            } catch (Exception e) {
                sendMessage("TRY AGAIN (" + e.getMessage() + ")");
            }
        } while (!done);

        return move;
    }

    @Override
    public void turnPlayed(Player player, Collection<Pile> table, Move move) {
        sendMessage(String.format("TURN_PLAYED PLAYER=%d TABLE=%s ACTION=%s", player.getPosition(), table, move));
    }

    @Override
    public void gameComplete(List<Score> scores) {
        sendMessage(scores.toString());
    }
}
