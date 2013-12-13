package com.reubenpeeris.wippen.robotloader;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.engine.MockRobot;
import com.reubenpeeris.wippen.robot.Robot;

public class ConstructorRobotLoaderTest {
	private static final String MOCK_ROBOT_URL = "class:" + MockRobot.class.getName();

	@Test
	public void testLoaderProducesRobots() {
		assertThat(new ConstructorRobotLoader().createInstance(MOCK_ROBOT_URL), isA(Robot.class));
	}

	@Test
	public void testLoaderIsAddedToManager() throws Exception {
		Class.forName(ConstructorRobotLoader.class.getName());
		assertThat(RobotLoaderManager.createInstance(MOCK_ROBOT_URL), isA(Robot.class));
	}
}
