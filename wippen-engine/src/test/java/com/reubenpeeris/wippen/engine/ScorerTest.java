package com.reubenpeeris.wippen.engine;

import static com.reubenpeeris.wippen.ObjectMother.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;

public class ScorerTest extends BaseTest {
	private Scorer scorer;

	@Before
	public void setUp() {
		scorer = new Scorer(Arrays.asList(player1, player2));
	}

	@Test
	public void assertConstructWithNullPlayersThrows() {
		expect(NullPointerException.class, "players");
		new Scorer(null);
	}

	@Test
	public void assertPlayersWithNullMemberThrows() {
		expect(NullPointerException.class, "player");
		new Scorer(Arrays.asList((Player) null));
	}

	@Test
	public void assertNoCardsGivesNoPoints() {
		scorer.calculateGameScores();
		for (Score score : scorer.getScores()) {
			assertThat(score.getGamePoints(), is(equalTo(0)));
		}
	}
}
