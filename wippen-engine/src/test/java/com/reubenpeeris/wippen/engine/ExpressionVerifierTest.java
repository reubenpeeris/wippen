package com.reubenpeeris.wippen.engine;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import com.reubenpeeris.wippen.engine.ParseException;
import com.reubenpeeris.wippen.engine.Parser;
import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.engine.WippenRuleException;
import com.reubenpeeris.wippen.expression.Add;
import com.reubenpeeris.wippen.expression.Building;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.ExpressionVerifier;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;

import static com.reubenpeeris.wippen.Cards.*;
import static com.reubenpeeris.wippen.expression.Move.Type.*;
import static java.util.Arrays.*;

import static org.junit.Assert.*;

/**
 * Discard - place a card onto the table from the hand as a new pile.
 * Capture - remove 1 or more piles from the table using a card from the hand.
 * Build - create a building by combining  1 or more piles from the table with a card from the hand to represent a new value that is already held in the hand.
 * Rebuild - build using a building created by the same player.
 * Overbuild - build using a building created by a different player.
 * Doublebuild - build to the same value as a existing building.
 * Dualbuild - build a new building while another exists.
 */
public class ExpressionVerifierTest {
	private final Player player1 = new Player(1, new MockRobot());
	private final Player player2 = new Player(2, new MockRobot());
	private final Set<Pile> emptyTable = Collections.emptySet();
	
	private void assertValid(Collection<Pile> table, Collection<Card> hand,
			Player player, String expression, Move.Type type,
			Set<Card> cardsUsed, Card handCardUsed, Set<Pile> tablePilesUsed) throws ParseException {
		Move move = ExpressionVerifier.verifyExpression(Parser.parseMath(expression, table), player, table, hand);

		assertEquals(type, move.getType());
		assertEquals(cardsUsed, asSet(move.getCards()));
		assertEquals(handCardUsed, move.getHandCard());
		assertEquals(tablePilesUsed, asSet(move.getTablePilesUsed()));
	}
	
	private void assertInvalid(Collection<Pile> table, Collection<Card> hand,
			Player player, String expression, String message) throws ParseException {
		try {
			Move move = ExpressionVerifier.verifyExpression(Parser.parseMath(expression, table), player, table, hand);
			fail(move.toString());
		} catch (WippenRuleException e) {
			assertEquals(message + expression, e.getMessage());
		}
	}
	
	//Discard
	@Test
	public void testSimpleDiscard() throws Exception {
		assertValid(emptyTable, asSet(s1, s2),
				player1, "1S", DISCARD,
				asSet(s1), s1, emptyTable);
	}

	@Test
	public void testIllegalDiscardCardNotAvailable() throws Exception {
		assertInvalid(Arrays.<Pile>asList(c1, c3), asList(s1, s4),
				player1, "2S",
				"Pile not found on table or in hand in expression: ");
	}

	@Test
	public void testIllegalDiscardCardAlreadyOnTable() throws Exception {
		assertInvalid(Arrays.<Pile>asList(c1, c3), asList(s1, s4),
				player1, "1C",
				"No card from hand used in expression: ");
	}
	
	//Capture
	@Test
	public void testSimpleCapture() throws Exception {
		assertValid(this.<Pile>asSet(c1, c3), asSet(s1, s2),
				player1, "1S=1C", CAPTURE,
				asSet(s1, c1), s1, asSet(new Pile[]{c1}));
	}

	@Test
	public void testDoubleCapture() throws Exception {
		assertValid(this.<Pile>asSet(h12, h6, h2), asSet(c12),
				player1, "12C=12H=6H*2H", CAPTURE,
				asSet(h12, h6, h2, c12), c12, asSet(new Pile[]{h12, h6, h2}));
	}

	@Test
	public void testTripleCapture() throws Exception {
		assertValid(asSet(new Pile[]{h12, h6, h2, d12}), asSet(c12),
				player1, "12C=12H=6H*2H=12D", CAPTURE,
				asSet(h12, h6, h2, c12, d12), c12, asSet(new Pile[]{h12, h6, h2, d12}));
	}

	@Test
	public void testDoubleCaptureIncludingBuilding() throws Exception {
		assertValid(asSet(new Pile[]{b12_s6PlusD6, h6, h2}), asSet(c12),
				player1, "12C=12B1=6H*2H", CAPTURE,
				asSet(s6, d6, h6, h2, c12), c12, asSet(new Pile[]{b12_s6PlusD6, h6, h2}));
	}
	
	//Build
	@Test
	public void testSimpleBuild() throws Exception {
		assertValid(asSet(new Pile[]{c1, c3}), asSet(s1, s2),
				player1, "1S+1C", BUILD,
				asSet(s1, c1), s1, asSet(new Pile[]{c1}));
	}

	@Test
	public void testSimpleCaptureOwnBuilding() throws Exception {
		assertValid(asSet(new Pile[]{b12_s6PlusD6, h6, h2}), asSet(c12),
				player1, "12C=12B1", CAPTURE,
				asSet(s6, d6, c12), c12, asSet(new Pile[]{b12_s6PlusD6}));
	}

	@Test
	public void testCaptureOthersBuildingAsPartOfComplexExpression() throws Exception {
		assertValid(asSet(new Pile[]{b12_s6PlusD6, h2}), asSet(h6),
				player2, "6H=(12B1/2H)", CAPTURE,
				asSet(s6, d6, h2, h6), h6, asSet(new Pile[]{b12_s6PlusD6, h2}));
	}

	@Test
	public void testSimpleCaptureOthersBuilding() throws Exception {
		assertValid(asSet(new Pile[]{b12_s6PlusD6, h6, h2}), asSet(c12),
				player2, "12C=12B1", CAPTURE,
				asSet(s6, d6, c12), c12, asSet(new Pile[]{b12_s6PlusD6}));
	}

	@Test
	public void testDaringBuild() throws Exception {
		assertValid(this.<Pile>asSet(c1, c2), asSet(s1, s2),
				player2, "1S+1C=2C", BUILD,
				asSet(c1, c2, s1), s1, asSet(new Pile[]{c1, c2}));
	}

	@Test
	public void testIllegalBuildWithoutEqualCard() throws Exception {
		assertInvalid(asList(new Pile[]{c1, c3}), asList(s1, s4),
				player1, "(1S+1C)",
				"Trying to build for a value not contained in hand in expression: ");
	}

	@Test
	public void testRebuildForSameValue() throws Exception {
		assertValid(asSet(new Pile[]{b12_s6PlusD6}), asSet(s1, c12),
				player1, "1S*12B1", BUILD,
				asSet(s1, s6, d6), s1, asSet(new Pile[]{b12_s6PlusD6}));
	}

	@Test
	public void testSimultaniousBuild() throws Exception {
		assertValid(asSet(new Pile[]{s5, s7, s6}), asSet(s2, s12),
				player1, "5S+7S=6S*2S", BUILD,
				asSet(s5, s7, s6, s2), s2, asSet(new Pile[]{s5, s6, s7}));
	}

	@Test
	public void testDoubleBuild() throws Exception {
		assertValid(asSet(new Pile[]{b12_s6PlusD6, h6}), asSet(c2, c12),
				player1, "6H*2C=12B1", BUILD,
				asSet(h6, c2, s6, d6), c2, asSet(new Pile[]{b12_s6PlusD6, h6}));
	}

	@Test
	public void testDualBuild() throws Exception {
		assertValid(asSet(new Pile[]{b12_s6PlusD6, h6}), asSet(c2, c12, c8),
				player1, "6H+2C", BUILD,
				asSet(h6, c2), c2, asSet(new Pile[]{h6}));
	}

	@Test
	public void testCaptureWithValueThatCouldBuild() throws Exception {
		assertValid(asSet(new Pile[]{h6, c2}), asSet(c12, s12), player1,
				"12C=(6H*2C)", CAPTURE,
				asSet(h6, c2, c12), c12, asSet(new Pile[]{h6, c2}));
	}

	@Test
	public void testBuildWithValueThatCouldCapture() throws Exception {
		assertValid(asSet(new Pile[]{h6, c2}), asSet(c12, s12), player1,
				"(6H*2C)=12C", BUILD,
				asSet(h6, c2, c12), c12, asSet(new Pile[]{h6, c2}));
	}

	@Test
	public void testBuildWhereFirstCardIsHandCard() throws Exception {
		assertValid(asSet(new Pile[]{h1}), asSet(c12, s12), player1,
				"12C*1H", BUILD,
				asSet(c12, h1), c12, asSet(new Pile[]{h1}));	
	}

	@Test
	public void testRebuildForNewValue() throws Exception {
		assertInvalid(asList(new Pile[]{b12_s6PlusD6}), asList(c12, c10, c2),
				player1, "(12B1-2C)",
				"Trying to build to new value using own building in expression: ");
	}

	@Test
	public void testOverbuildForNewValue() throws Exception {
		assertValid(asSet(new Pile[]{b12_s6PlusD6}), asSet(c12, c10, c2),
				player2, "12B1-2C", BUILD,
				asSet(c2, s6, d6), c2, asSet(new Pile[]{b12_s6PlusD6}));
	}

	@Test
	public void testUsingPromisedCardWithoutConsumingPile() throws Exception {
		assertValid(asList(new Pile[]{b12_s6PlusD6}), asList(c12, c10, c2),
				player1, "12C", DISCARD, 
				asSet(c12), c12, Collections.<Pile>emptySet());
	}

	@Test
	public void testUsingPromiedCardToConsumePile() throws Exception {
		assertValid(asSet(new Pile[]{b12_s6PlusD6}), asSet(c12, d12),
				player2, "12B1=12C", BUILD,
				asSet(c12, s6, d6), c12, asSet(new Pile[]{b12_s6PlusD6}));
	}

	private <T> Set<T> asSet(@SuppressWarnings("unchecked") T... array) {
		Set<T> set = new LinkedHashSet<T>();
		for (T t : array) {
			set.add(t);
		}
		
		return set;
	}
	
	private <T> Set<T> asSet(Collection<T> collection) {
		return new HashSet<T>(collection);
	}

	private final Building b12_s6PlusD6 = new Building(new Move(BUILD, s6, new Add(s6, d6)), player1);
}
