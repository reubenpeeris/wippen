package com.reubenpeeris.wippen.robot;

import java.util.Collection;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.Pile;

/**
 * Simplest working robot. Each go, simply discards the first card in its hand.
 */
public class FirstCardDiscarder extends BaseRobot {

	@Override
	public Expression takeTurn(Collection<Pile> table, Collection<Card> hand) {
		return hand.iterator().next();
	}
}
