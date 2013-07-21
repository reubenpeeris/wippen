package com.reubenpeeris.wippen.robot;

import java.util.Collection;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.Pile;

public interface Robot {
	/**
	 * Called to indicate the start of a match. The robot must respond with it's name.
	 * 
	 * @param players the number of players for this game (2, 3 or 4)
	 * @param rounds the number of rounds to be played
	 * @param position the position that this robot is situated in. The robot in position 1 deals first.
	 * 
	 * @return the name of the robot
	 */
	String matchStart(int numberOfPlayers, int numberOfRounds, int position);
	// Add (Player you)
	/*
	 Player contains
	 	NOT your hand - player is passed to all Brains 
	 	your table position - is this needed - it should be in Game.
	 	Round position
	 	
	 	
	 	Or use a Match object and Game object which BaseRobot will store so they are available at all times?
	 	Game.getMyPosition() relative to the starting player
	 	int Game.getGameInMatch() e.g. 0 for first game of match etc?
	 */
	/**
	 * Indicates the start of the game.
	 * 
	 * @param first the position of the first player
	 */
	void gameStart(int first);
	
	/*
	 Hand feels a bit wrong here, not sure where better to put it though. 
	 */
	/**
	 *  
	 * 
	 * @param table
	 * @param hand
	 * @return
	 */
	Expression takeTurn(Collection<Pile> table, Collection<Card> hand);
	/*
	 Move must contain player object?
	 */
//	void turnPlayed(Move move);
	void turnPlayed(int player, Collection<Pile> table, Expression expression);
	void gameComplete(String scores, String totals);
}
