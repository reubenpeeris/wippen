package com.reubenpeeris.wippen.robotloader;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.robot.Robot;

public class SpringRobotLoaderTest {
	private static final String MOCK_ROBOT_URL = "spring:SpringRobotLoaderTest.xml";

	@Test
	public void testLoaderProducesRobots() {
		assertThat(new SpringRobotLoader().createInstance(MOCK_ROBOT_URL), isA(Robot.class));
	}

	@Test
	public void testLoaderIsAddedToManager() throws Exception {
		Class.forName(SpringRobotLoader.class.getName());
		assertThat(RobotLoaderManager.createInstance(MOCK_ROBOT_URL), isA(Robot.class));
	}
}
