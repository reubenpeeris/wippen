package com.reubenpeeris.wippen.engine;

import java.util.Collection;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Move.Type;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.robot.BaseRobot;

public class MockRobot extends BaseRobot {

	@Override
	public Move takeTurn(Collection<Pile> table, Collection<Card> hand) {
		return new Move(Type.DISCARD, null, hand.iterator().next());
	}
}
