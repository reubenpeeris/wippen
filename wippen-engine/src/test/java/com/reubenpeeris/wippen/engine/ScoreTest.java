package com.reubenpeeris.wippen.engine;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ScoreTest {
	private static final int SOME_POINTS = 4;

	private Score score;

	@Before
	public void createNewScore() {
		score = new Score();
	}

	@Test
	public void verifyInitiallyAllScoresAreZero() {
		assertValues(0, 0, 0);
	}

	@Test
	public void verifyIncrementAffectsAllValues() {
		score.incrementPoints(SOME_POINTS);
		assertValues(SOME_POINTS, SOME_POINTS, SOME_POINTS);
	}

	@Test
	public void verifyStartGameResetsGameOnly() {
		score.incrementPoints(SOME_POINTS);
		score.startGame();
		assertValues(SOME_POINTS, SOME_POINTS, 0);
	}

	@Test
	public void verifyStartSetResetsGameAndSet() {
		score.incrementPoints(SOME_POINTS);
		score.startSet();
		assertValues(SOME_POINTS, 0, 0);
	}

	@Test
	public void verifyStartMatchResetsAllValues() {
		score.incrementPoints(SOME_POINTS);
		score.startMatch();
		assertValues(0, 0, 0);
	}

	private void assertValues(int match, int set, int game) {
		assertThat(score.getMatchPoints(), is(equalTo(match)));
	}
}
