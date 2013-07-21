package com.reubenpeeris.wippen.engine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Rank;
import com.reubenpeeris.wippen.expression.Suit;

/**
 * Players with largest number of cards get 1 point each. If there is only 1 they get a bonus point (i.e. 2 in total).
 * 
 * Players with largest number of spades get 1 point each. If there is only 1 they get a bonus point (i.e. 2 in total).
 */
class ScoreKeeper {
	private final Map<Player, Score> scores = new HashMap<Player, Score>();
	
	public ScoreKeeper(Collection<Player> players) {
		for (Player player : players) {
			scores.put(player, new Score());
		}
	}
	
	private static class Score {
		private int thisGame;
		private int total;
	}
	
	public void addClearUp(Player player) {
		scores.get(player).thisGame++;
	}
	
	public void calculateGameScores() {
		incrementScoreForLargestNumberOfCards();
		incrementScoreForLargestNumberOfSpades();
		incrementScoreForValueCards();
		applyGameScoreToTotal();
	}
	
	private void applyGameScoreToTotal() {
		for (Score score : scores.values()) {
			score.total += score.thisGame;
		}
	}

	private static final Rank ACE = new Rank(1);
	private static final Card TWO_OF_SPADES = new Card(Suit.SPADE, new Rank(2));
	private static final Card TEN_OF_DIAMONDS = new Card(Suit.DIAMOND, new Rank(10));
	
	private void incrementScoreForValueCards() {
		for (Player player : scores.keySet()) {
			Score score = scores.get(player);
			for (Card card : player.getWinnings()) {
				if (card.getRank().equals(ACE)) {
					score.thisGame++;
				} else if (card.equals(TWO_OF_SPADES)) {
					score.thisGame++;
				} else if(card.equals(TEN_OF_DIAMONDS)) {
					score.thisGame += 2;
				} 
			}
		}
	}

	private void incrementScoreForLargestNumberOfCards() {
		int highestNumberOfCards = -1;
		boolean singlePlayer = true;
		
		for (Player player : scores.keySet()) {
			int playerCards = player.getWinnings().size();
			if (highestNumberOfCards < playerCards) {
				highestNumberOfCards = playerCards;
				singlePlayer = true;
			} else if (highestNumberOfCards == playerCards) {
				singlePlayer = false;
			}
		}
		
		for (Player player : scores.keySet()) {
			int playerCards = player.getWinnings().size();
			if (highestNumberOfCards == playerCards) {
				scores.get(player).thisGame++;
				if (singlePlayer) {
					scores.get(player).thisGame++;
				}
			}
		}
	}
	
	private void incrementScoreForLargestNumberOfSpades() {
		int highestNumberOfCards = -1;
		boolean singlePlayer = true;
		
		for (Player player : scores.keySet()) {
			int playerCards = countSpades(player.getWinnings());
			if (highestNumberOfCards < playerCards) {
				highestNumberOfCards = playerCards;
				singlePlayer = true;
			} else if (highestNumberOfCards == playerCards) {
				singlePlayer = false;
			}
		}
		
		for (Player player : scores.keySet()) {
			int playerCards = countSpades(player.getWinnings());
			if (highestNumberOfCards == playerCards) {
				scores.get(player).thisGame++;
				if (singlePlayer) {
					scores.get(player).thisGame++;
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
	
	public void startNewGame() {
		for (Score score : scores.values()) {
			score.thisGame = 0;
		}
		
		for (Player player : scores.keySet()) {
			player.clearWinnings();
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		int total = 0;
		for (Score score : scores.values()) {
			total += score.total;
		}
		
		for (Entry<Player, Score> entry : scores.entrySet()) {
			Player player = entry.getKey();
			Score score = entry.getValue();
			
			double percentage = 100D * (double)score.total / (double)total;
			sb.append(String.format("%-20s %2s %5s %5.1f%%\n", player, score.thisGame, score.total, percentage));
		}
		
		return sb.toString();
	}
}

