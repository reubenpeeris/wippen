package com.reubenpeeris.wippen.robotloader;

import org.junit.Test;

import com.reubenpeeris.wippen.engine.MockRobot;
import com.reubenpeeris.wippen.robot.Robot;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

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
