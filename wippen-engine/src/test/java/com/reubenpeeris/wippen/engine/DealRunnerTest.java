package com.reubenpeeris.wippen.engine;

import static com.reubenpeeris.wippen.TestData.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import com.reubenpeeris.wippen.BaseTest;

public class DealRunnerTest extends BaseTest {
	private MoveRunner moveRunner;
	private DealRunner dealRunner;
	private ScoreKeeper scoreKeeper;
	private Deck deck;
	
	@Before
	public void setUp() {
		buildTestData();
		moveRunner = mock(MoveRunner.class);
		dealRunner = new DealRunner(moveRunner);
		scoreKeeper = new ScoreKeeper(playerList);
		deck = mock(Deck.class);
	}
	
	@Test
	public void constructor_throws_for_null_moveRunner() {
		expect(NullPointerException.class, "moveRunner");
		new DealRunner(null);
	}
	
	@Test
	public void throws_for_null_table() {
		expect(NullPointerException.class, "table");
		dealRunner.runDeal(null, playerList, scoreKeeper, deck);
	}
	
	@Test
	public void throws_for_null_players() {
		expect(NullPointerException.class, "players");
		dealRunner.runDeal(bigTable, null, scoreKeeper, deck);
	}
	
	@Test
	public void throws_for_null_scoreKeeper() {
		expect(NullPointerException.class, "scoreKeeper");
		dealRunner.runDeal(bigTable, playerList, null, deck);
	}
	
	@Test
	public void throws_for_null_deck() {
		expect(NullPointerException.class, "deck");
		dealRunner.runDeal(bigTable, playerList, scoreKeeper, null);
	}

	@Test
	public void runMove_is_run_for_each_player_in_order_until_hand_empty() {
		when(deck.isEmpty()).thenReturn(false).thenReturn(true);
		List<Player> players = Arrays.asList(mock(Player.class), mock(Player.class));
		when(players.get(0).isHandEmpty()).thenReturn(false).thenReturn(false).thenReturn(true);
		dealRunner.runDeal(bigTable, players, scoreKeeper, deck);
		InOrder inOrder = inOrder(moveRunner);
		for (int i = 0; i < 2; i++) {
			for (Player player : players) {
				inOrder.verify(moveRunner).runMove(bigTable, players, scoreKeeper, player);
			}
		}
	}
}
