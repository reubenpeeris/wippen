package com.reubenpeeris.wippen.robot;

import java.util.List;
import java.util.Set;

import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.engine.Score;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.ExpressionFactory;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;

public abstract class BaseRobot implements Robot {
	private boolean matchStarted = false;
	private Player me;
	private ExpressionFactory factory;

	protected Player getMe() {
		assertMatchStarted();
		return me;
	}

	protected ExpressionFactory factory() {
		assertMatchStarted();
		return factory;
	}

	private void assertMatchStarted() {
		if (!matchStarted) {
			throw new IllegalStateException("Match has not yet started");
		}
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public void startMatch(Set<Pile> table, Set<Card> hand, List<Player> allPlayers, Player me, int numberOfSets) {
		this.matchStarted = true;
		this.me = me;
		this.factory = new ExpressionFactory(table, hand, me);
	}

	@Override
	public void startSet() {
	}

	@Override
	public void startGame(Player first, Set<Pile> table) {
	}

	@Override
	public void turnPlayed(Player player, Set<Pile> table, Move move) {
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
