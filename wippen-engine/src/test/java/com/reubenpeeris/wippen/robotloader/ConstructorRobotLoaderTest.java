package com.reubenpeeris.wippen.robotloader;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;
import com.reubenpeeris.wippen.engine.MockRobot;
import com.reubenpeeris.wippen.robot.Robot;

public class ConstructorRobotLoaderTest extends BaseTest {
	private static final String MOCK_ROBOT_URL = "class:" + MockRobot.class.getName();

	@Test
	public void loader_produces_robots() {
		assertThat(new ConstructorRobotLoader().createInstance(MOCK_ROBOT_URL), isA(Robot.class));
	}

	@Test
	public void loader_is_dded_to_manager() throws Exception {
		Class.forName(ConstructorRobotLoader.class.getName());
		assertThat(RobotLoaderManager.createInstance(MOCK_ROBOT_URL), isA(Robot.class));
	}
}
