package com.reubenpeeris.wippen.robot;

import static com.reubenpeeris.wippen.TestData.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;
import com.reubenpeeris.wippen.TestData.NullRobot;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Pile;

public class BaseRobotTest extends BaseTest {
	@Test
	public void name_is_simple_name_of_class() {
		assertThat(new NullRobot().getName(), is(equalTo(NullRobot.class.getSimpleName())));
	}

	@Test
	public void getMe_throws_if_called_before_the_game_has_started() {
		thrown.expect(IllegalStateException.class);
		new NullRobot().getMe();
	}

	@Test
	public void getMe_retuns_the_same_instance_passed_to_start_match() {
		BaseRobot robot = new NullRobot();
		robot.startMatch(Collections.<Pile> emptySet(), Collections.<Card> emptySet(), playerList, players[0], 0);

		assertThat(robot.getMe(), is(sameInstance(players[0])));
	}
}
