package com.reubenpeeris.wippen.robotloader;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;
import com.reubenpeeris.wippen.robot.Robot;

public class SpringRobotLoaderTest extends BaseTest {
	private static final String MOCK_ROBOT_URL = "spring:SpringRobotLoaderTest.xml";

	@Test
	public void loader_produces_robots() {
		assertThat(new SpringRobotLoader().createInstance(MOCK_ROBOT_URL), isA(Robot.class));
	}

	@Test
	public void loader_is_added_to_manager() throws Exception {
		Class.forName(SpringRobotLoader.class.getName());
		assertThat(RobotLoaderManager.createInstance(MOCK_ROBOT_URL), isA(Robot.class));
	}
}
