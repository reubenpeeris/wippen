package com.reubenpeeris.wippen.engine;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.reubenpeeris.wippen.BaseImmutableTest;

public class ScoreTest extends BaseImmutableTest<Score> {
	private static final int SOME_POINTS = 4;

	private Score score;

	@Before
	public void createNewScore() {
		score = new Score();
	}

	@Test
	public void initially_all_scores_are_zZero() {
		assertValues(0, 0, 0);
	}

	@Test
	public void increment_affects_all_values() {
		score.incrementPoints(SOME_POINTS);
		assertValues(SOME_POINTS, SOME_POINTS, SOME_POINTS);
	}

	@Test
	public void startGame_resets_game_score_only() {
		score.incrementPoints(SOME_POINTS);
		score.startGame();
		assertValues(SOME_POINTS, SOME_POINTS, 0);
	}

	@Test
	public void startSet_resets_game_and_set_scores() {
		score.incrementPoints(SOME_POINTS);
		score.startSet();
		assertValues(SOME_POINTS, 0, 0);
	}

	@Test
	public void startMatch_resets_all_scores() {
		score.incrementPoints(SOME_POINTS);
		score.startMatch();
		assertValues(0, 0, 0);
	}

	private void assertValues(int match, int set, int game) {
		assertThat(score.getMatchPoints(), is(equalTo(match)));
		assertThat(score.getSetPoints(), is(equalTo(set)));
		assertThat(score.getGamePoints(), is(equalTo(game)));
	}

	@Override
	protected Score validInstance() {
		return new Score();
	}
}
