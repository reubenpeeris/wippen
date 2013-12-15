package com.reubenpeeris.wippen.engine;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.reubenpeeris.wippen.robot.Robot;
import com.reubenpeeris.wippen.robotloader.RobotLoaderManager;
import com.reubenpeeris.wippen.robotloader.WippenLoaderException;

public class Wippen {
	@Parameter(names = { "-s", "--sets" }, description = "The number of sets to play")
	private Integer sets = 100;

	@Parameter(names = { "-g", "--game" }, description = "The first game to play")
	private Integer firstGame = 0;

	@Parameter(description = "robot1 robot2 [robot3 [robot4]]", required = true, converter = RobotConverter.class)
	private List<Robot> robots;

	@Parameter(names = { "-h", "--help" }, help = true, description = "Prints the help message")
	private boolean help = false;

	private final JCommander commander;

	public static class RobotConverter implements IStringConverter<Robot> {
		@Override
		public Robot convert(String value) {
			try {
				return RobotLoaderManager.createInstance(value);
			} catch (WippenLoaderException e) {
				throw new ParameterException("Unable to create robot for parameter, problem was: " + e.getMessage());
			}
		}
	}

	public static void main(String... args) {
		try {
			new Wippen(args);
		} catch (ExitRequest exitRequest) {
			System.exit(exitRequest.getCode());
		}
	}

	@Getter
	@RequiredArgsConstructor
	static class ExitRequest extends RuntimeException {
		private static final long serialVersionUID = 1L;
		private final int code;
	}

	@SneakyThrows(ClassNotFoundException.class)
	Wippen(String... args) {
		Class.forName(com.reubenpeeris.wippen.robotloader.SpringRobotLoader.class.getName());
		Class.forName(com.reubenpeeris.wippen.robotloader.ConstructorRobotLoader.class.getName());
		String programName = "java -jar Wippen.jar";
		commander = new JCommander(this);
		commander.setProgramName(programName);

		try {
			commander.parse(args);
			if (help) {
				commander.usage();
				System.out.println("  Main arguments");
				System.out.println("    robot1, robot2, robot3, robot4");
				System.out.println("      RobotLoader strigs representing the robots to load");

				System.out.println();
				System.out.println("  Example");
				System.out.println("    " + programName
						+ " --sets 1000 -g 29 class:com.reubenpeeris.wippen.examples.PairAdder class:com.reubenpeeris.wippen.examples.PairCapturer");
				System.out.println("      Execute a run of 1000 sets starting with game 29 for PairAdder vs PairCapturer");
			} else {
				validateParameters();
			}
		} catch (ParameterException e) {
			System.out.println(e.getMessage());
			throw new ExitRequest(1);
		}

		if (!help) {
			new WippenRunner().playMatch(robots, firstGame, sets);
		}
	}

	private void validateParameters() throws ParameterException {
		if (robots.size() < 2 || robots.size() > 4) {
			throw new ParameterException("Main parameters are required to have between 2 and 4 robots (\"robot1 robot2 [robot3 [robot4]]\")");
		}
	}
}
