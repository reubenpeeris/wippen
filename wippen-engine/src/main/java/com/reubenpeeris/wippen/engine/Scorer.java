package com.reubenpeeris.wippen.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Rank;
import com.reubenpeeris.wippen.expression.Suit;

class Scorer {
	private final List<Player> players = new ArrayList<Player>();
	private final List<Score> scores = new ArrayList<Score>();
	
	public Scorer(Collection<Player> players) {
		this.players.addAll(players);
		for (Player player : players) {
			scores.add(player.getScore());
		}
	}
	
	public void calculateGameScores() {
		incrementScoreForLargestNumberOfCards();
		incrementScoreForLargestNumberOfSpades();
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
				} else if(card.equals(THE_GOOD_TEN)) {
					score.incrementPoints(2);
				} 
			}
		}
	}

	private void incrementScoreForLargestNumberOfCards() {
		int highestNumberOfCards = -1;
		int playerCount = 0;
		
		for (Player player : players) {
			int playerCards = player.getCapturedCards().size();
			if (highestNumberOfCards < playerCards) {
				highestNumberOfCards = playerCards;
				playerCount = 0;
			} else if (highestNumberOfCards == playerCards) {
				playerCount++;
			}
		}
		
		if (playerCount < 3 && highestNumberOfCards > 0) {
			int points = playerCount == 1 ? 2 : 1;	
			
			for (Player player : players) {
				int playerCards = player.getCapturedCards().size();
				
				if (highestNumberOfCards == playerCards) {
					player.getScore().incrementPoints(points);
				}
			}
		}
	}
	
	private void incrementScoreForLargestNumberOfSpades() {
		int highestNumberOfCards = -1;
		int playerCount = 0;
		
		for (Player player : players) {
			int playerCards = countSpades(player.getCapturedCards());
			if (highestNumberOfCards < playerCards) {
				highestNumberOfCards = playerCards;
				playerCount = 0;
			} else if (highestNumberOfCards == playerCards) {
				playerCount++;
			}
		}
		
		if (playerCount < 3 && highestNumberOfCards > 0) {
			int points = playerCount == 1 ? 2 : 1;	
			
			for (Player player : players) {
				int playerCards = countSpades(player.getCapturedCards());
				
				if (highestNumberOfCards == playerCards) {
					player.getScore().incrementPoints(points);
				}
			}
		}
	}

	private int countSpades(Collection<Card> cards) {
		int spades = 0;
		for (Card card : cards) {
			if (card.getSuit() == Suit.SPADE) spades++;
		}
		
		return spades;
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
		return scores;
	}
}

