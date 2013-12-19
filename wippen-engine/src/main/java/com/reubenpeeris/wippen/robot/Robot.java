package com.reubenpeeris.wippen.robot;

import java.util.List;
import java.util.Set;

import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.engine.Score;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;

public interface Robot {
	String getName();

	void startMatch(Set<Pile> table, Set<Card> hand, List<Player> allPlayers, Player you, int numberOfSets);

	void startSet();

	void startGame(Player first, Set<Pile> table);

	Move takeTurn(Set<Pile> table, Set<Card> hand);

	void turnPlayed(Player player, Set<Pile> table, Move move);

	void gameComplete(List<Score> scores);

	void setComplete(List<Score> scores);

	void matchComplete(List<Score> scores);
}
