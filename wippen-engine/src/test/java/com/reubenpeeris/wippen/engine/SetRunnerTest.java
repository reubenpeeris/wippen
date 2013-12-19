package com.reubenpeeris.wippen.engine;

import static com.reubenpeeris.wippen.TestData.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import com.reubenpeeris.wippen.BaseTest;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.util.RoundIterable;

public class SetRunnerTest extends BaseTest {
	private SetRunner setRunner;
	private GameRunner gameRunner;
	private ScoreKeeper scoreKeeper;
	private List<Player> players;
	
	@Before
	public void setUp() {
		gameRunner = mock(GameRunner.class);
		setRunner = new SetRunner(gameRunner);
		scoreKeeper = mock(ScoreKeeper.class);
		players = Arrays.asList(mock(Player.class), mock(Player.class), mock(Player.class));
	}
	
	@Test
	public void constructor_throws_for_null_gameRunner() {
		expect(NullPointerException.class, "gameRunner");
		new SetRunner(null);
	}
	
	@Test
	public void throws_for_null_table() {
		expect(NullPointerException.class, "table");
		setRunner.runSet(null, players, scoreKeeper, new Random());
	}
	
	@Test
	public void throws_for_null_players() {
		expect(NullPointerException.class, "players");
		setRunner.runSet(bigTable, null, scoreKeeper, new Random());
	}
	
	@Test
	public void throws_for_null_scoreKeeper() {
		expect(NullPointerException.class, "scoreKeeper");
		setRunner.runSet(bigTable, players, null, new Random());
	}
	
	@Test
	public void throws_for_null_shuffler() {
		expect(NullPointerException.class, "shuffler");
		setRunner.runSet(bigTable, players, scoreKeeper, null);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void players_are_informed_of_start_and_end_of_set() {
		List<Score> scores = Arrays.asList((Score)null);
		when(scoreKeeper.getScores()).thenReturn(scores);
		setRunner.runSet(bigTable, players, scoreKeeper, new Random());
		InOrder inOrder = inOrder(players.get(0), players.get(1), players.get(2), gameRunner);
		
		for (Player player : players) {
			inOrder.verify(player).startSet();
		}
		
		inOrder.verify(gameRunner, times(players.size())).runGame((Set<Pile>)notNull(), (Iterable<Player>)notNull(), (ScoreKeeper)notNull(), (Deck)notNull());
		
		for (Player player : players) {
			inOrder.verify(player).setComplete(scores);
		}
	}
	
	@Test
	public void a_game_is_started_for_each_player() {
		setRunner.runSet(bigTable, players, scoreKeeper, new Random());
		InOrder inOrder = inOrder(gameRunner);
		for (int i = 0; i < 3; i++) {
			inOrder.verify(gameRunner).runGame(eq(bigTable), eq(new RoundIterable<>(players, i)), eq(scoreKeeper), (Deck)isNotNull());
		}
	}
}
