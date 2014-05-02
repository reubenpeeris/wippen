package com.reubenpeeris.wippen.robot;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Set;

import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.engine.Score;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;

public class SocketRobot implements Robot {
    private final Process process;
    private final String command;
    private final ServerSocket serverSocket;
    private final Socket socket;

    public SocketRobot(String command) {
        this.command = command;

        try {
            serverSocket = new ServerSocket(0);
            int port = serverSocket.getLocalPort();

            ProcessBuilder pb = new ProcessBuilder(command.replaceAll("$port", Integer.toString(port)));
            process = pb.start();

            serverSocket.setSoTimeout(500);
            socket = serverSocket.accept();

            socket.getInputStream();
            socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void startMatch(Set<Pile> table, Set<Card> hand, List<Player> allPlayers, Player you, int numberOfSets) {
        // TODO Auto-generated method stub

    }

    @Override
    public void startSet() {
        // TODO Auto-generated method stub

    }

    @Override
    public void startGame(Player first, Set<Pile> table) {
        // TODO Auto-generated method stub

    }

    @Override
    public Move takeTurn(Set<Pile> table, Set<Card> hand) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void turnPlayed(Player player, Set<Pile> table, Move move) {
        // TODO Auto-generated method stub

    }

    @Override
    public void gameComplete(List<Score> scores) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setComplete(List<Score> scores) {
        // TODO Auto-generated method stub

    }

    @Override
    public void matchComplete(List<Score> scores) {
        try {
            process.destroy();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
