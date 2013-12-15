package com.reubenpeeris.wippen.engine;

import static com.reubenpeeris.wippen.TestData.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.reubenpeeris.wippen.BaseImmutableTest;
import com.reubenpeeris.wippen.expression.Card;

public class ScorerTest extends BaseImmutableTest<Scorer> {
	private Scorer scorer;

	@Override
	protected Scorer validInstance() {
		return scorer;
	}

	@Before
	public void createScorer() {
		scorer = new Scorer(Arrays.asList(players[0], players[1], players[2], players[3]));
	}

	@Test
	public void construct_with_null_players_throws() {
		expect(NullPointerException.class, "players");
		new Scorer(null);
	}

	@Test
	public void players_with_null_member_throws() {
		expect(NullPointerException.class, "player");
		new Scorer(Arrays.asList((Player) null));
	}

	@Test
	public void no_cards_gives_no_points() {
		assertGamePoints();
	}

	@Test
	public void player_with_largest_number_of_cards_gets_two_points() {
		players[0].addToCapturedCards(c3.getCards());
		assertGamePoints(2);
	}

	@Test
	public void two_players_with_equal_largest_number_of_cards_gets_one_point_each() {
		players[0].addToCapturedCards(c3.getCards());
		players[1].addToCapturedCards(h3.getCards());
		assertGamePoints(1, 1);
	}

	@Test
	public void three_players_with_equal_largest_number_of_cards_gets_no_points_each() {
		players[0].addToCapturedCards(c3.getCards());
		players[1].addToCapturedCards(h3.getCards());
		players[2].addToCapturedCards(d3.getCards());
		assertGamePoints();
	}

	@Test
	public void player_with_largest_number_of_spades_gets_two_points() {
		players[0].addToCapturedCards(s3.getCards());
		players[1].addToCapturedCards(h3.getCards());
		players[2].addToCapturedCards(h4.getCards());
		players[3].addToCapturedCards(h5.getCards());
		assertGamePoints(2);
	}

	@Test
	public void two_players_with_equal_largest_number_of_spades_gets_one_point_each() {
		players[0].addToCapturedCards(s3.getCards());
		players[1].addToCapturedCards(s4.getCards());
		players[2].addToCapturedCards(h4.getCards());
		players[3].addToCapturedCards(h5.getCards());
		assertGamePoints(1, 1);
	}

	@Test
	public void three_players_with_equal_largest_number_of_spades_gets_no_points_each() {
		players[0].addToCapturedCards(s3.getCards());
		players[1].addToCapturedCards(s4.getCards());
		players[2].addToCapturedCards(s5.getCards());
		players[3].addToCapturedCards(h5.getCards());
		assertGamePoints();
	}

	@Test
	public void player_with_zero_cards_gets_no_points_even_if_that_is_the_highest_card_and_spade_count() {
		scorer = new Scorer(Arrays.asList(players[0]));
		assertGamePoints();
	}

	@Test
	public void player_gets_one_point_for_ace_of_clubs() {
		addCardsForPlayers(c1.getCards());
		assertGamePoints(1);
	}

	@Test
	public void player_gets_one_point_for_ace_of_hearts() {
		addCardsForPlayers(h1.getCards());
		assertGamePoints(1);
	}

	@Test
	public void player_gets_one_point_for_ace_of_spades() {
		addCardsForPlayersWithDafault(s3.getCards(), s1.getCards());
		assertGamePoints(1);
	}

	@Test
	public void player_gets_one_point_for_ace_of_diamonds() {
		addCardsForPlayers(d1.getCards());
		assertGamePoints(1);
	}

	@Test
	public void player_gets_one_point_for_the_good_two() {
		addCardsForPlayersWithDafault(s3.getCards(), s2.getCards());
		assertGamePoints(1);
	}

	@Test
	public void player_gets_two_point_for_the_good_ten() {
		addCardsForPlayers(d10.getCards());
		assertGamePoints(2);
	}

	@Test
	public void player_gets_a_point_for_a_sweep() {
		players[0].addSweep();
		assertGamePoints(1);
	}

	@Test
	public void getScores_returns_player_scores_in_order() {
		assertThat(scorer.getScores(),
				is(equalTo(Arrays.asList(players[0].getScore(), players[1].getScore(), players[2].getScore(), players[3].getScore()))));
	}

	@Test
	public void toString_outputs_human_readable_summary() {
		addCardsForPlayers(s1.getCards(), Arrays.asList(s2, s3), d10.getCards(), Arrays.<Card> asList());
		scorer.calculateGameScores();
		String humanReadableSummary = "Player 1 [NullRobot]                         1  12.5%\n"
				+ "Player 2 [NullRobot]                         5  62.5%\n" + "Player 3 [NullRobot]                         2  25.0%\n"
				+ "Player 4 [NullRobot]                         0   0.0%\n";
		assertThat(scorer.toString(), is(equalTo(humanReadableSummary)));
	}

	@SafeVarargs
	private final void addCardsForPlayersWithDafault(Collection<Card> defaultCards, Collection<Card>... cardsForPlayers) {
		for (int i = 0; i < players.length; i++) {
			players[i].addToCapturedCards(cardsForPlayers.length > i ? cardsForPlayers[i] : defaultCards);
		}
	}

	@SafeVarargs
	private final void addCardsForPlayers(Collection<Card>... cardsForPlayers) {
		addCardsForPlayersWithDafault(h3.getCards(), cardsForPlayers);
	}

	private void assertGamePoints(int... playersGamePoints) {
		scorer.calculateGameScores();
		for (int i = 0; i < players.length; i++) {
			assertThat(String.format("players[%s]", i), players[i].getScore().getGamePoints(), is(equalTo(pointsForPlayer(i, playersGamePoints))));
		}
	}

	private int pointsForPlayer(int position, int[] playersGamePoints) {
		return playersGamePoints.length > position ? playersGamePoints[position] : 0;
	}
}
