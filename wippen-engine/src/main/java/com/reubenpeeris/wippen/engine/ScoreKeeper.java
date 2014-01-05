package com.reubenpeeris.wippen.engine;

import static com.reubenpeeris.wippen.expression.AnonymousExpressionFactory.*;
import static com.reubenpeeris.wippen.expression.Rank.*;
import static com.reubenpeeris.wippen.expression.Suit.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.NonNull;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Pile;

public class ScoreKeeper {
	private static final CardFilter ALL_CARDS_FILTER = new CardFilter() {
		@Override
		public boolean matches(Card c) {
			return true;
		}
	};

	private static final CardFilter SPADES_FILTER = new CardFilter() {
		@Override
		public boolean matches(Card c) {
			return c.getSuit() == SPADES;
		}
	};

	private final List<Player> players = new ArrayList<>();
	private final List<Score> scores = new ArrayList<>();
	private final List<Score> unmodifiableScores = Collections.unmodifiableList(scores);
	private Player lastPlayerToCapture;

	ScoreKeeper(@NonNull Collection<Player> players) {
		this.players.addAll(players);
		for (Player player : players) {
			if (player == null) {
				throw new NullPointerException("player");
			}
			scores.add(player.getScore());
		}
	}

	void calculateGameScores(Collection<Pile> table) {
		giveCardsToLastPlayerThatCaptured(table);
		incrementScoreForLargestNumberOfCards(ALL_CARDS_FILTER);
		incrementScoreForLargestNumberOfCards(SPADES_FILTER);
		incrementScoreForValueCards();
		incrementScoreForSweepsAndClearUp();
	}

	void playerCaptured(Player player) {
		lastPlayerToCapture = player;
	}

	private void giveCardsToLastPlayerThatCaptured(Collection<Pile> table) {
		if (lastPlayerToCapture != null) {
			for (Pile pile : table) {
				lastPlayerToCapture.addToCapturedCards(pile.getCards());
			}
		}
		lastPlayerToCapture = null;
		table.clear();
	}

	private void incrementScoreForSweepsAndClearUp() {
		for (Player player : players) {
			player.getScore().incrementPoints(player.getSweeps());
			player.clearCapturedAndSweeps();
		}
	}

	private static final Card THE_GOOD_TWO = factory().newCard(TWO, SPADES);
	private static final Card THE_GOOD_TEN = factory().newCard(TEN, DIAMONDS);

	private void incrementScoreForValueCards() {
		for (Player player : players) {
			Score score = player.getScore();
			for (Card card : player.getCapturedCards()) {
				if (card.getRank() == ACE) {
					score.incrementPoints(1);
				} else if (card.equals(THE_GOOD_TWO)) {
					score.incrementPoints(1);
				} else if (card.equals(THE_GOOD_TEN)) {
					score.incrementPoints(2);
				}
			}
		}
	}

	private void incrementScoreForLargestNumberOfCards(CardFilter filter) {
		int highestNumberOfCards = 0;
		List<Player> playersWithHighestScore = new ArrayList<>();

		for (Player player : players) {
			int playerCards = filter(player.getCapturedCards(), filter).size();
			if (highestNumberOfCards < playerCards) {
				highestNumberOfCards = playerCards;
				playersWithHighestScore.clear();
			}
			if (highestNumberOfCards == playerCards) {
				playersWithHighestScore.add(player);
			}
		}

		if (playersWithHighestScore.size() < 3 && highestNumberOfCards > 0) {
			int points = playersWithHighestScore.size() == 1 ? 2 : 1;

			for (Player player : playersWithHighestScore) {
				player.getScore().incrementPoints(points);
			}
		}
	}

	private Collection<Card> filter(Collection<Card> cards, CardFilter filter) {
		List<Card> filtered = new ArrayList<>();
		for (Card card : cards) {
			if (filter.matches(card)) {
				filtered.add(card);
			}
		}

		return filtered;
	}

	private interface CardFilter {
		boolean matches(Card c);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		int total = 0;
		for (Player player : players) {
			total += player.getScore().getMatchPoints();
		}

		for (Player player : players) {
			int score = player.getScore().getMatchPoints();
			double percentage = score == 0 ? 0 : 100D * score / total;
			sb.append(String.format("%-50s %8s %5.1f%%%n", player, score, percentage));
		}

		return sb.toString();
	}

	public List<Score> getScores() {
		return unmodifiableScores;
	}
}
