package com.reubenpeeris.wippen.robot;

import java.util.Collection;
import java.util.List;

import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.engine.Score;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;

public interface Robot {
	String getName();

	void startMatch(List<Player> allPlayers, Player you, int numberOfSets);

	void startSet();

	void startGame(Player first, Collection<Pile> table);

	void cardsDealt(Collection<Card> hand);

	Move takeTurn(Collection<Pile> table, Collection<Card> hand);

	void turnPlayed(Player player, Collection<Pile> table, Move move);

	void gameComplete(List<Score> scores);

	void setComplete(List<Score> scores);

	void matchComplete(List<Score> scores);
}
