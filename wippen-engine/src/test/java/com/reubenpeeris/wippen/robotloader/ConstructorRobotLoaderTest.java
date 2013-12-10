package com.reubenpeeris.wippen.robotloader;

import org.junit.Test;

import com.reubenpeeris.wippen.engine.MockRobot;
import com.reubenpeeris.wippen.robot.Robot;

import static org.junit.Assert.*;

public class ConstructorRobotLoaderTest {
	private static final String MOCK_ROBOT_URL = "class:" + MockRobot.class.getName();

	@Test
	public void testLoaderProducesRobots() {
		assertTrue(new ConstructorRobotLoader().createInstance(MOCK_ROBOT_URL) instanceof Robot);
	}
	
	@Test
	public void testLoaderIsAddedToManager() throws Exception {
		Class.forName(ConstructorRobotLoader.class.getName());
		assertTrue(RobotLoaderManager.createInstance(MOCK_ROBOT_URL) instanceof Robot);
	}
}
