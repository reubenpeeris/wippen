package com.reubenpeeris.wippen.engine;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.reubenpeeris.wippen.robot.Robot;
import com.reubenpeeris.wippen.robotloader.RobotLoaderManager;
import com.reubenpeeris.wippen.robotloader.WippenLoaderException;

@Component
@Scope("prototype")
@Getter
@Setter
public class Wippen {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	private static final int FAIL_EXIT_CODE = 1;

	@Parameter(names = { "-s", "--sets" }, description = "The number of sets to play", validateWith = PositiveInteger.class)
	private int sets = 100;

	@Parameter(names = { "-f", "--first-set" }, description = "The first set to play")
	private int firstSet = 0;

	@NonNull
	@Parameter(description = "robot1 robot2 [robot3 [robot4]]", required = true, converter = RobotConverter.class)
	private List<Robot> robots;

	@Parameter(names = { "-h", "--help" }, help = true, description = "Prints the help message")
	private boolean help = false;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private final JCommander commander;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private final MatchRunner matchRunner;

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

	public static void main(@NonNull String... args) {
		Wippen wippen = create();
		wippen.run(args);
	}

	public static Wippen create() {
		return context.getBean(Wippen.class);
	}

	@Autowired
	Wippen(@NonNull MatchRunner matchRunner) {
		this.matchRunner = matchRunner;
		commander = new JCommander(this);
	}

	void run(@NonNull String... args) {
		try {
			Class.forName(com.reubenpeeris.wippen.robotloader.SpringRobotLoader.class.getName());
			Class.forName(com.reubenpeeris.wippen.robotloader.ConstructorRobotLoader.class.getName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		String programName = "java -jar Wippen.jar";
		commander.setProgramName(programName);

		try {
			commander.parse(args);
			if (help) {
				commander.usage();
				System.out.println("  Main arguments");
				System.out.println("    robot1 robot2 [robot3 [robot4]]");
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
			System.err.println(e.getMessage());
			fail();
		}

		if (!help) {
			try {
				runMatch();
			} catch (Exception e) {
				if (e instanceof WippenRuleException) {
					System.err.println(e.getMessage());
				} else {
					e.printStackTrace();
				}
				fail();
			}
		}
	}

	public List<Score> runMatch() {
		validateParameters();
		System.out.println("Running");
		System.out.println(" Sets:      " + sets);
		System.out.println(" First set: " + firstSet);
		System.out.println(" Players:   " + robots.size());

		ScoreKeeper scoreKeeper = matchRunner.runMatch(robots, firstSet, sets);
		System.out.print(scoreKeeper);

		return scoreKeeper.getScores();
	}

	private void validateParameters() throws ParameterException {
		if (robots.size() < 2 || robots.size() > 4) {
			throw new ParameterException("Main parameters are required to have between 2 and 4 robots (\"robot1 robot2 [robot3 [robot4]]\")");
		}
	}

	public static class PositiveInteger implements IParameterValidator {
		@Override
		public void validate(String name, String value) throws ParameterException {
			try {
				int parsedValue = Integer.parseInt(value);
				if (parsedValue < 0) {
					throw new ParameterException(String.format("\"%s\": couldn't convert \"%s\" to a positive integer", name, value));
				}
			} catch (NumberFormatException e) {
				throw new ParameterException(String.format("\"%s\": couldn't convert \"%s\" to an integer", name, value));
			}
		}
	}

	/*
	 * EMMA code coverage will consider this method any any branch that executes
	 * this method to have not been touched!
	 */
	private void fail() {
		System.exit(FAIL_EXIT_CODE);
	}
}
