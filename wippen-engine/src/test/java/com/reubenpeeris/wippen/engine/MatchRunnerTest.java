package com.reubenpeeris.wippen.engine;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import com.reubenpeeris.wippen.BaseTest;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.robot.Robot;

public class MatchRunnerTest extends BaseTest {
	private MatchRunner matchRunner;
	private SetRunner setRunner;
	private List<Robot> robots;
	
	@Before
	public void setUp() {
		setRunner = mock(SetRunner.class);
		matchRunner = new MatchRunner(setRunner);
		robots = Arrays.asList(mock(Robot.class), mock(Robot.class), mock(Robot.class));
	}
	
	@Test
	public void constructor_throws_for_null_setRunner() {
		expect(NullPointerException.class, "setRunner");
		new MatchRunner(null);
	}
	
	@Test
	public void throws_for_null_robots() {
		expect(NullPointerException.class, "robots");
		matchRunner.runMatch(null, 0, 0);
	}
	
	@Test
	public void throws_for_negative_number_of_sets() {
		expect(IllegalArgumentException.class, "Negative number of sets specified:");
		matchRunner.runMatch(robots, 0, -1);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void each_robot_gets_informed_of_start_and_end_of_match() {
		matchRunner.runMatch(robots, 0, 0);
		InOrder inOrder = inOrder(robots.toArray());
		for (Robot robot : robots) {
			inOrder.verify(robot).startMatch((Set<Pile>)notNull(), (Set<Card>)notNull(), (List<Player>)notNull(), (Player)notNull(), anyInt());
		}
		for (Robot robot : robots) {
			inOrder.verify(robot).matchComplete((List<Score>)notNull());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void correct_number_of_sets_are_run() {
		int sets = 3;
		matchRunner.runMatch(robots, 0, sets);
		verify(setRunner, times(sets)).runSet((Set<Pile>)notNull(), (List<Player>)notNull(), (ScoreKeeper)notNull(), (Random)notNull());
	}
}
