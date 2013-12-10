package com.reubenpeeris.wippen.robotloader;

import org.junit.Test;

import com.reubenpeeris.wippen.robot.Robot;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

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
