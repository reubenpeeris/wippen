package com.reubenpeeris.wippen.robotloader;

import com.reubenpeeris.wippen.robot.Robot;

public class ConstructorRobotLoader extends ConstructorLoader<Robot> {
	static {
		RobotLoaderManager.registerLoader(new ConstructorRobotLoader());
	}

	public ConstructorRobotLoader() {
		super(Robot.class);
	}
}
