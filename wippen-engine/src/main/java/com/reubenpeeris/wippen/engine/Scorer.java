package com.reubenpeeris.wippen.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.NonNull;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Rank;
import com.reubenpeeris.wippen.expression.Suit;

final class Scorer {
	private static final CardFilter ALL_CARDS_FILTER = new CardFilter() {
		@Override
		public boolean matches(Card c) {
			return true;
		}
	};
	
	private static final CardFilter SPADES_FILTER = new CardFilter() {
		@Override
		public boolean matches(Card c) {
			return c.getSuit() == Suit.SPADE;
		}
	};
	
	private final List<Player> players = new ArrayList<>();
	private final List<Score> scores = new ArrayList<>();
	private final List<Score> unmodifiableScores = Collections.unmodifiableList(scores);

	public Scorer(@NonNull Collection<Player> players) {
		this.players.addAll(players);
		for (Player player : players) {
			if (player == null) {
				throw new NullPointerException("player");
			}
			scores.add(player.getScore());
		}
	}

	public void calculateGameScores() {
		incrementScoreForLargestNumberOfCards(ALL_CARDS_FILTER);
		incrementScoreForLargestNumberOfCards(SPADES_FILTER);
		incrementScoreForValueCards();
		incrementScoreForSweepsAndClearUp();
	}

	private void incrementScoreForSweepsAndClearUp() {
		for (Player player : players) {
			player.getScore().incrementPoints(player.getSweeps());
			player.clearCapturedAndSweeps();
		}
	}

	private static final Rank ACE = new Rank(1);
	private static final Card THE_GOOD_TWO = new Card(Suit.SPADE, new Rank(2));
	private static final Card THE_GOOD_TEN = new Card(Suit.DIAMOND, new Rank(10));

	private void incrementScoreForValueCards() {
		for (Player player : players) {
			Score score = player.getScore();
			for (Card card : player.getCapturedCards()) {
				if (card.getRank().equals(ACE)) {
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
			sb.append(String.format("%-40s %5s %5.1f%%%n", player, score, percentage));
		}

		return sb.toString();
	}

	public List<Score> getScores() {
		return unmodifiableScores;
	}
}
