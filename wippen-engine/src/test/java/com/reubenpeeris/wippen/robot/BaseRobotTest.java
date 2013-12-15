package com.reubenpeeris.wippen.robot;

import static com.reubenpeeris.wippen.TestData.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;
import com.reubenpeeris.wippen.TestData.NullRobot;

public class BaseRobotTest extends BaseTest {
	@Test
	public void n_ame_is_simple_ame_of_lass() {
		assertThat(new NullRobot().getName(), is(equalTo(NullRobot.class.getSimpleName())));
	}

	@Test
	public void getMe_throws_if_called_before_the_game_has_started() {
		thrown.expect(IllegalStateException.class);
		new NullRobot().getMe();
	}

	@Test
	public void getMe_retruns_the_same_instance_passed_to_start_match() {
		BaseRobot robot = new NullRobot();
		robot.startMatch(null, players[0], 0);

		assertThat(robot.getMe(), is(sameInstance(players[0])));
	}
}
