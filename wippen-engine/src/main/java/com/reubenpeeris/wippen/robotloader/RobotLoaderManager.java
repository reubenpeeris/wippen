package com.reubenpeeris.wippen.robotloader;

import com.reubenpeeris.wippen.robot.Robot;

public final class RobotLoaderManager {
	private static final LoaderManager<Robot> INSTANCE = new LoaderManager<Robot>();
	
	private RobotLoaderManager(){}
	
	public static void registerLoader(Loader<Robot> loader) {
		INSTANCE.registerLoader(loader);
	}
	
	public static Robot createInstance(String url) throws LoaderException {
		return INSTANCE.createInstance(url);
	}
}
