package com.reubenpeeris.wippen.engine;

import static com.reubenpeeris.wippen.TestData.*;
import static com.reubenpeeris.wippen.expression.Move.Type.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;
import com.reubenpeeris.wippen.TestData;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;

public class MoveRunnerTest extends BaseTest {
	private final MoveRunner moveRunner = new MoveRunner();

	@Before
	public void setUpTestData() {
		buildTestData();
	}

	@Test
	public void move_with_null_players_throws() {
		moveThrowsNullPointer("players", null, new ScoreKeeper(playerList), players[0], bigTable);
	}

	@Test
	public void move_with_null_scoreKeeper_throws() {
		moveThrowsNullPointer("scoreKeeper", playerList, null, players[0], bigTable);
	}

	@Test
	public void move_with_null_player_throws() {
		moveThrowsNullPointer("player", playerList, new ScoreKeeper(playerList), null, bigTable);
	}

	@Test
	public void move_with_null_table_throws() {
		moveThrowsNullPointer("table", playerList, new ScoreKeeper(playerList), players[0], null);
	}

	@Test
	public void players_are_correctly_informed_of_move() {
		Player p1 = mock(Player.class);
		Player p2 = mock(Player.class);
		Move move = create.newMove(DISCARD, null, s1);
		when(p1.takeTurn()).thenReturn(move);
		when(p1.getHand()).thenReturn(new HashSet<>(Arrays.asList(s1)));

		run(p1, p2);
		verify(p1, never()).turnPlayed((Player) any(), (Move) any());
		verify(p2).turnPlayed(p1, move);
	}

	@Test
	public void card_are_correctly_moved_for_discard() {
		Card card = s1;
		Player p1 = mock(Player.class);
		Move move = create.newMove(DISCARD, null, card);
		when(p1.takeTurn()).thenReturn(move);
		when(p1.getHand()).thenReturn(new HashSet<>(Arrays.asList(card)));

		assertThat(bigTable.contains(card), is(false));
		run(p1);
		verify(p1).removeFromHand(card);
		assertThat(bigTable.contains(card), is(true));
	}

	@Test
	public void card_are_correctly_moved_for_build() {
		Card card = s4;
		Player p1 = mock(Player.class);
		Move move = create.newMove(BUILD, create.newMultiply(h3, card), card);
		Pile building = move.getPileCreated();
		when(p1.takeTurn()).thenReturn(move);
		when(p1.getHand()).thenReturn(new HashSet<>(Arrays.asList(card, h12)));

		assertThat(bigTable.contains(building), is(false));
		run(p1);
		verify(p1).removeFromHand(card);
		assertThat(bigTable.contains(building), is(true));
	}

	@Test
	public void card_are_correctly_moved_for_capture() {
		verifyCapture(bigTable, false);
	}

	@Test
	public void sweep_added_for_capture_that_clears_table() {
		verifyCapture(new HashSet<Pile>(Arrays.asList(h1)), true);
	}

	@Test
	public void null_move_throws_exception() {
		Player p1 = mock(Player.class);
		when(p1.takeTurn()).thenReturn(null);

		expect(WippenRuleException.class, "Null Move returned by player: " + p1.toString());
		run(p1);
	}

	@Test
	public void unsound_move_throws_exception() {
		Player p1 = mock(Player.class);
		when(p1.takeTurn()).thenReturn(TestData.build);

		expect(WippenRuleException.class, "Invalid Move returned by player: " + p1.toString());
		run(p1);
	}

	private void verifyCapture(Set<Pile> table, boolean sweepExpected) {
		Card handCard = s1;
		Card tableCard = h1;
		Player p1 = mock(Player.class);
		Move move = create.newMove(CAPTURE, tableCard, handCard);
		when(p1.takeTurn()).thenReturn(move);
		when(p1.getHand()).thenReturn(new HashSet<>(Arrays.asList(handCard)));

		assertThat(table.contains(tableCard), is(true));
		List<Player> playerList = Arrays.asList(p1);
		ScoreKeeper scoreKeeper = mock(ScoreKeeper.class);
		moveRunner.runMove(table, playerList, scoreKeeper, p1);
		verify(p1).removeFromHand(handCard);
		if (sweepExpected) {
			verify(p1).addSweep();
		} else {
			verify(p1, never()).addSweep();
		}
		assertThat(table.contains(tableCard), is(false));
		verify(scoreKeeper).playerCaptured(p1);
	}

	private void run(Player... players) {
		List<Player> playerList = Arrays.asList(players);
		moveRunner.runMove(bigTable, playerList, new ScoreKeeper(playerList), players[0]);
	}

	private void moveThrowsNullPointer(String field, List<Player> players, ScoreKeeper scoreKeeper, Player player, Set<Pile> table) {
		expect(NullPointerException.class, field);
		moveRunner.runMove(table, players, scoreKeeper, player);
	}
}
