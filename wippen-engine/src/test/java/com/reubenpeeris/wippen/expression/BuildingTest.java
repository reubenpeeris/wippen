package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.TestData.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.engine.Player;

public class BuildingTest extends BaseExpressionTest {
	@Test
	public void construct_with_null_move_throws() {
		expect(NullPointerException.class, "move");
		new Building(null, players[0]);
	}

	@Test
	public void construct_with_null_player_throws() {
		expect(NullPointerException.class, "player");
		new Building(build, null);
	}

	@Test
	public void construct_with_non_build_move_throws() {
		expect(IllegalArgumentException.class, "Move must have type BUILD");
		new Building(discard, players[0]);
	}

	@Test
	public void construct_with_nobody_throws() {
		expect(IllegalArgumentException.class, "Player cannot be NOBODY");
		new Building(build, Player.NOBODY);
	}

	@Test
	public void getters_returns_same_values_as_expression() {
		assertThat(building.getCards(), is(sameInstance(build.getCards())));
		assertThat(building.getValue(), is(build.getValue()));
	}

	@Test
	public void rank_equal_in_value_to_expression_value() {
		assertThat(building.getRank().getValue(), is(equalTo(build.getValue())));
	}

	@Test
	public void getter_returns_player() {
		assertThat(building.getPlayer(), is(sameInstance(players[0])));
	}

	@Test
	public void getPile_returns_collection_containing_itself() {
		assertThat(building.getPiles(), contains((Pile) building));
	}

	@Test
	public void verify_toString_format() {
		assertThat(building.toString(), is(equalTo(build.getValue() + "B" + players[0].getPosition())));
	}

	@Override
	protected Expression validInstance() {
		return building;
	}
}
