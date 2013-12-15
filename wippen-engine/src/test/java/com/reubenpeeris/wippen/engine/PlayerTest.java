package com.reubenpeeris.wippen.engine;

import com.reubenpeeris.wippen.BaseImmutableTest;
import com.reubenpeeris.wippen.TestData;

public class PlayerTest extends BaseImmutableTest<Player> {
	@Override
	protected Player validInstance() {
		return TestData.players[0];
	}
}
