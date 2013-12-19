package com.reubenpeeris.wippen.examples;

import static com.reubenpeeris.wippen.expression.Move.Type.*;

import java.util.Set;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.robot.BaseRobot;

/**
 * Simplest working robot. Each go, simply discards the first card in its hand.
 */
public class Discarder extends BaseRobot {
	@Override
	public Move takeTurn(Set<Pile> table, Set<Card> hand) {
		return factory().newMove(DISCARD, null, hand.iterator().next());
	}
}
