package com.reubenpeeris.wippen.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lombok.NonNull;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.robot.Robot;

public class Player {
	public static final Player NOBODY = new Player(-1, new Robot() {
		@Override
		public void startMatch(Set<Pile> table, Set<Card> hand, List<Player> allPlayers, Player you, int numberOfSets) {
		}

		@Override
		public void startSet() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void gameComplete(List<Score> scores) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setComplete(List<Score> scores) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void matchComplete(List<Score> scores) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getName() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void startGame(Player first, Set<Pile> table) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Move takeTurn(Set<Pile> table, Set<Card> hand) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void turnPlayed(Player player, Set<Pile> table, Move move) {
			throw new UnsupportedOperationException();
		}
	});

	private final Collection<Card> capturedCards = new ArrayList<>();
	private final int position;
	private final Robot robot;
	private final Score score = new Score();
	private final Set<Card> hand = new LinkedHashSet<>();
	private final Set<Card> unmodifiableHand = Collections.unmodifiableSet(hand);
	private Set<Pile> unmodifiableTable;
	private int sweeps;

	public Player(int position, @NonNull Robot robot) {
		this.robot = robot;
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	Set<Card> getHand() {
		return hand;
	}

	void addToHand(Collection<Card> cards) {
		this.hand.addAll(cards);
	}

	boolean removeFromHand(Card card) {
		return hand.remove(card);
	}

	boolean isHandEmpty() {
		return hand.isEmpty();
	}

	// Scoring
	Score getScore() {
		return score;
	}

	void addSweep() {
		sweeps++;
	}

	int getSweeps() {
		return sweeps;
	}

	void addToCapturedCards(Collection<Card> cardsUsed) {
		capturedCards.addAll(cardsUsed);
	}

	Collection<Card> getCapturedCards() {
		return capturedCards;
	}

	void clearCapturedAndSweeps() {
		capturedCards.clear();
		sweeps = 0;
	}

	@Override
	public String toString() {
		return "Player " + position + " [" + robot.getName() + "]";
	}

	// Robot like methods
	void startMatch(Set<Pile> table, List<Player> allPlayers, int numberOfSets) {
		score.startMatch();
		unmodifiableTable = Collections.unmodifiableSet(table);
		robot.startMatch(unmodifiableTable, unmodifiableHand, Collections.unmodifiableList(allPlayers), this, numberOfSets);
	}

	void startSet() {
		score.startSet();
		robot.startSet();
	}

	void startGame(Player first) {
		score.startGame();
		robot.startGame(first, unmodifiableTable);
	}

	void gameComplete(List<Score> scores) {
		robot.gameComplete(scores);
	}

	void setComplete(List<Score> scores) {
		robot.setComplete(scores);
	}

	void matchComplete(List<Score> scores) {
		robot.matchComplete(scores);
	}

	Move takeTurn() {
		return robot.takeTurn(unmodifiableTable, unmodifiableHand);
	}

	void turnPlayed(Player player, Move move) {
		robot.turnPlayed(player, unmodifiableTable, move);
	}
}
