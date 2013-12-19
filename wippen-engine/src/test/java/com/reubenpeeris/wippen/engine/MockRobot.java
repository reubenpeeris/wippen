package com.reubenpeeris.wippen.engine;

import java.util.Set;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Move.Type;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.robot.BaseRobot;

public class MockRobot extends BaseRobot {
	@Override
	public Move takeTurn(Set<Pile> table, Set<Card> hand) {
		return factory().newMove(Type.DISCARD, null, hand.iterator().next());
	}
}
