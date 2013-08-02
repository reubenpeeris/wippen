package com.reubenpeeris.wippen.robotloader;

import org.junit.Test;

import com.reubenpeeris.wippen.robot.Robot;

import static org.junit.Assert.assertTrue;

public class SpringRobotLoaderTest {
	private static final String MOCK_ROBOT_URL = "spring:SpringRobotLoaderTest.xml";

	@Test
	public void testLoaderProducesRobots() {
		assertTrue(new SpringRobotLoader().createInstance(MOCK_ROBOT_URL) instanceof Robot);
	}
	
	@Test
	public void testLoaderIsAddedToManager() throws Exception {
		Class.forName(SpringRobotLoader.class.getName());
		assertTrue(RobotLoaderManager.createInstance(MOCK_ROBOT_URL) instanceof Robot);
	}
}
