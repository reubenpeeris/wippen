package com.reubenpeeris.wippen.robot;

import java.util.Collection;
import java.util.List;

import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.engine.Score;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;

public abstract class BaseRobot implements Robot {
	private Player me;
	
	public Player getMe() {
		if (me == null) {
			throw new IllegalStateException();
		}
		return me;
	}
	
	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public void startMatch(List<Player> allPlayers, Player me, int numberOfSets) {
		this.me = me;
	}

	@Override
	public void startSet() {
	}

	@Override
	public void startGame(Player first, Collection<Pile> table) {
	}

	@Override
	public void cardsDealt(Collection<Card> hand) {
	}

	@Override
	public void turnPlayed(Player player, Collection<Pile> table, Move move) {
	}

	@Override
	public void gameComplete(List<Score> scores) {
	}

	@Override
	public void setComplete(List<Score> scores) {
	}

	@Override
	public void matchComplete(List<Score> scores) {
	}
}
