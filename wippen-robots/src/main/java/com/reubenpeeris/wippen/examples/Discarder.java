package com.reubenpeeris.wippen.examples;

import java.util.Collection;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.robot.BaseRobot;

import static com.reubenpeeris.wippen.expression.Move.Type.*;


/**
 * Simplest working robot. Each go, simply discards the first card in its hand.
 */
public class Discarder extends BaseRobot {
	@Override
	public Move takeTurn(Collection<Pile> table, Collection<Card> hand) {
		return new Move(DISCARD, null, hand.iterator().next());
	}
}
