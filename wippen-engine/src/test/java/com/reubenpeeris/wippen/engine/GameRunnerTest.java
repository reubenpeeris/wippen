package com.reubenpeeris.wippen.engine;

import static com.reubenpeeris.wippen.TestData.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Pile;

public class GameRunnerTest extends BaseTest {
	private GameRunner gameRunner;
	private DealRunner dealRunner;
	private ScoreKeeper scoreKeeper;
	private Deck deck;
	
	@Before
	public void setUp() {
		dealRunner = mock(DealRunner.class);
		gameRunner = new GameRunner(dealRunner);
		scoreKeeper = mock(ScoreKeeper.class);
		deck = mock(Deck.class);
		when(deck.isEmpty()).thenReturn(true);
	}
	
	@Test
	public void constructor_throws_for_null_dealRunner() {
		expect(NullPointerException.class, "dealRunner");
		new GameRunner(null);
	}
	
	@Test
	public void throws_if_table_is_null() {
		expect(NullPointerException.class, "table");
		gameRunner.runGame(null, playerList, scoreKeeper, deck);
	}
	
	@Test
	public void throws_if_players_is_null() {
		expect(NullPointerException.class, "players");
		gameRunner.runGame(emptyTable, null, scoreKeeper, deck);
	}
	
	@Test
	public void throws_if_scoreKeeper_is_null() {
		expect(NullPointerException.class, "scoreKeeper");
		gameRunner.runGame(emptyTable, playerList, null, deck);
	}
	
	@Test
	public void throws_if_deck_is_null() {
		expect(NullPointerException.class, "deck");
		gameRunner.runGame(emptyTable, playerList, scoreKeeper, null);
	}
	
	@Test
	public void throws_if_table_is_not_empty() {
		expect(IllegalArgumentException.class, "Table is not empty");
		gameRunner.runGame(bigTable, playerList, scoreKeeper, deck);
	}
	
	@Test
	public void throws_if_players_is_empty() {
		expect(IllegalArgumentException.class, "No players supplied");
		gameRunner.runGame(emptyTable, new HashSet<Player>(), scoreKeeper, deck);
	}
	
	@Test
	public void four_cards_dealt_to_table_then_deals_run_until_deck_empty() {
		Collection<Card> newCards = Arrays.asList(s1, s5, s8, s13);
		when(deck.nextCards(4)).thenReturn(newCards);
		when(deck.isEmpty()).thenReturn(false).thenReturn(false).thenReturn(true);
		Set<Pile> table = new HashSet<>();
		gameRunner.runGame(table, playerList, scoreKeeper, deck);
		Set<Pile> expectedTable = new HashSet<Pile>(newCards);
		assertThat(table, is(equalTo(expectedTable)));
		verify(dealRunner, times(2)).runDeal(expectedTable, playerList, scoreKeeper, deck);
	}
	
	@Test
	public void each_player_gets_informed_of_the_start_and_end_of_game() {
		List<Player> players = Arrays.asList(mock(Player.class), mock(Player.class));
		List<Score> scores = new ArrayList<>();
		when(scoreKeeper.getScores()).thenReturn(scores);
		gameRunner.runGame(emptyTable, players, scoreKeeper, deck);
		
		Player first = players.get(0);
		for (Player player : players) {
			verify(player).startGame(first);
			verify(player).gameComplete(scores);
		}
	}
	
	@Test
	public void score_keeper_calculates_game_scores() {
		gameRunner.runGame(emptyTable, playerList, scoreKeeper, deck);
		verify(scoreKeeper).calculateGameScores(emptyTable);
	}
}
