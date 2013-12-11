package com.reubenpeeris.wippen.robot;

import static com.reubenpeeris.wippen.ObjectMother.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;
import com.reubenpeeris.wippen.ObjectMother.NullRobot;

public class BaseRobotTest extends BaseTest {
	@Test
	public void verifyNameIsSimpleNameOfClass() {
		assertThat(new NullRobot().getName(), is(equalTo(NullRobot.class.getSimpleName())));
	}

	@Test
	public void assertGetMeThrowsExceptionBeforeGameStarted() {
		thrown.expect(IllegalStateException.class);
		new NullRobot().getMe();
	}

	@Test
	public void assertGetMeRetrunsSameInstancePassedToStartMatch() {
		BaseRobot robot = new NullRobot();
		robot.startMatch(null, player1, 0);

		assertThat(robot.getMe(), is(player1));
	}
}
