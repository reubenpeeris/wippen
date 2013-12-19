package com.reubenpeeris.wippen.examples;

import static org.hamcrest.collection.IsIterableContainingInOrder.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.reubenpeeris.wippen.engine.Score;
import com.reubenpeeris.wippen.engine.Wippen;
import com.reubenpeeris.wippen.robot.Robot;

public class TestScenarios {
	@Test
	public void discarder_vs_single_value_matcher() {
		assertThat(runWippen(new Discarder(), new SingleValueMatcher()), contains(new Score(0, 0, 0), new Score(2200, 22, 11)));
	}

	@Test
	public void single_value_matcher_vs_pair_adder() {
		assertThat(runWippen(new SingleValueMatcher(), new PairAdder()), contains(new Score(505, 4, 0), new Score(1796, 18, 11)));
	}

	@Test
	public void pair_adder_vs_pair_capturer() {
		assertThat(runWippen(new PairAdder(), new PairCapturer()), contains(new Score(689, 10, 5), new Score(2323, 18, 8)));
	}

	@Test
	public void discarder_vs_single_value_matcher_pair_adder_vs_pair_capturer() {
		assertThat(runWippen(new Discarder(), new SingleValueMatcher(), new PairAdder(), new PairCapturer()),
				contains(new Score(0, 0, 0), new Score(512, 1, 0), new Score(1350, 9, 2), new Score(3009, 37, 9)));
	}

	public List<Score> runWippen(Robot... robots) {
		Wippen wippen = Wippen.create();
		wippen.setRobots(Arrays.asList(robots));
		return wippen.runMatch();
	}
}
