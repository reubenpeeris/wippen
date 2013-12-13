package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.ObjectMother.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;
import com.reubenpeeris.wippen.engine.Player;

public class BuildingTest extends BaseTest {
	@Test
	public void constructWithNullMoveThrows() {
		expect(NullPointerException.class, "move");

		new Building(null, player1);
	}

	@Test
	public void constructWithNullPlayerThrows() {
		expect(NullPointerException.class, "player");

		new Building(build, null);
	}

	@Test
	public void constructWithNonBuildMoveThrows() {
		expect(IllegalArgumentException.class, "Move must have type BUILD");

		new Building(discard, player1);
	}

	@Test
	public void constructWithNobodyThrows() {
		expect(IllegalArgumentException.class, "Player cannot be NOBODY");

		new Building(build, Player.NOBODY);
	}

	@Test
	public void verifyGettersReturnsExpressionInstances() {
		assertThat(building.getCards(), is(build.getCards()));
		assertThat(building.getValue(), is(build.getValue()));
	}

	@Test
	public void verifyRankEqualInValueExpressionValue() {
		assertThat(building.getRank().getValue(), is(equalTo(build.getValue())));
	}

	@Test
	public void verifyGetterReturnsPlayer() {
		assertThat(building.getPlayer(), is(player1));
	}

	@Test
	public void verifyGetPileReturnsItself() {
		assertThat(building.getPiles(), contains((Pile) building));
	}

	@Test
	public void verifyToStringFormat() {
		assertThat(building.toString(), is(equalTo(build.getValue() + "B" + player1.getPosition())));
	}
}
