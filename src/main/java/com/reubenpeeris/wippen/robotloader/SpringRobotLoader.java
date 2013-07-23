package com.reubenpeeris.wippen.robotloader;

import com.reubenpeeris.wippen.robot.Robot;

public class SpringRobotLoader extends SpringLoader<Robot> {
	static {
		RobotLoaderManager.registerLoader(new SpringRobotLoader());
	}
	
	public SpringRobotLoader() {
		super(Robot.class, "robot");
	}
}
