package com.reubenpeeris.wippen.engine;

import java.util.Collection;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.robot.BaseRobot;

public class MockRobot extends BaseRobot {

	@Override
	public Expression takeTurn(Collection<Pile> table, Collection<Card> hand) {
		throw new UnsupportedOperationException();
	}
}
