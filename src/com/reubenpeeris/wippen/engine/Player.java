package com.reubenpeeris.wippen.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.robot.Robot;

class Player {
	public static final int NOBODY = -1;
	
	private Collection<Card> hand;
	private final Collection<Card> winnings = new ArrayList<Card>();
	private final int position;
	private String name;
	
	private final Robot robot;
	
	public Player(int position, Robot robot) {
		if (robot == null) throw new NullPointerException();
		
		this.robot = robot;
		this.position = position;
	}

	public int getPosition() {
		return position;
	}
	
	public Collection<Card> getHand() {
		return hand;
	}
	
	public boolean removeFromHand(Card card) {
		return hand.remove(card);
	}
	
	public void setHand(Collection<Card> hand) {
		this.hand = hand;
	}
	
	public Collection<Card> getWinnings() {
		return winnings;
	}
	
	public boolean hasEmptyHand() {
		return hand.isEmpty();
	}

	public void addCardsToWinnings(Collection<Card> cardsUsed) {
		winnings.addAll(cardsUsed);
	}
		
	public String matchStart(int players, int rounds, int position) {
		name = robot.matchStart(players, rounds, position);
		return name;
	}
	
	public void gameStart(int first) {
		robot.gameStart(first);
	}

	public Expression takeTurn(Collection<Pile> table) {
		return robot.takeTurn(Collections.unmodifiableCollection(table), Collections.unmodifiableCollection(hand));
	}
	
	public void turnPlayed(int player, Collection<Pile> table, Expression expression) {
		robot.turnPlayed(player, table, expression);
	}
	
	public void gameComplete(String scores, String totals) {
		robot.gameComplete(scores, totals);
	}

	public void clearWinnings() {
		winnings.clear();
	}
	
	@Override
	public String toString() {
		return (name == null ? "[Unknown]" : name);
	}
}
