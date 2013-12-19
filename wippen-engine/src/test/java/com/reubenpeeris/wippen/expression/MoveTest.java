package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.TestData.*;
import static com.reubenpeeris.wippen.expression.Move.Type.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.*;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.expression.Move.Type;

public class MoveTest extends BaseExpressionTest {
	private Player player;

	@Override
	protected Move validInstance() {
		return newValidMove(CAPTURE, h1, s1);
	}

	@Override
	protected List<MethodSignature> methodsExemptFromImmutabilityCheck() {
		List<MethodSignature> exemptMethods = super.methodsExemptFromImmutabilityCheck();
		exemptMethods.add(new MethodSignature(Boolean.TYPE, "isSound", Set.class, Set.class, Player.class));

		return exemptMethods;
	}

	@Before
	public void setUp() {
		player = players[0];
	}

	@Test
	public void create_returns_move_for_valid_arguments() {
		Type type = DISCARD;
		Expression expression = null;
		Card handCard = s1;
		Move move = Move.create(type, expression, handCard, Collections.<Pile> emptySet(), Collections.singleton(handCard), players[0]);
		assertThat(move, is(equalTo(new Move(type, expression, handCard, bigTable, exampleHand, players[0]))));
	}

	@Test
	public void create_returns_null_when_expression_is_not_valid() {
		Move move = Move.create(DISCARD, c2, c1, Collections.<Pile> emptySet(), Collections.singleton(c1), players[0]);
		assertThat(move, is(nullValue()));
	}

	@Test
	public void create_returns_null_when_move_is_not_valid_for_specified_table_hand_and_player() {
		Move move = Move.create(DISCARD, null, c1, Collections.<Pile> emptySet(), Collections.singleton(c2), players[0]);
		assertThat(move, is(nullValue()));
	}

	@Test
	public void create_returns_null_when_type_is_invalid() {
		Move move = Move.create(INVALID, null, c1, Collections.<Pile> emptySet(), Collections.singleton(c1), players[0]);
		assertThat(move, is(nullValue()));
	}

	@Test
	public void construct_with_null_type_throws() {
		expect(NullPointerException.class, "type");
		newValidMove(null, c1, c1);
	}

	@Test
	public void construct_with_null_handCard_throws() {
		expect(NullPointerException.class, "handCard");
		newValidMove(DISCARD, c1, null);
	}

	@Test
	public void verify_properties_of_discard_move() {
		Card card = s1;
		Move move = newValidMove(DISCARD, null, card);
		assertThat(move.getCards(), contains(card));
		assertThat(move.getHandCard(), is(equalTo(card)));
		assertThat(move.getPiles(), contains((Pile) card));
		assertThat(move.getType(), is(equalTo(DISCARD)));
		assertThat(move.getValue(), is(equalTo(card.getValue())));
		assertThat(move.toString(), is(equalTo("DISCARD 1S")));
	}

	@Test
	public void construct_discard_with_non_null_expression_throws() {
		expect(IllegalArgumentException.class, "For DISCARD type, expression must be null");
		newValidMove(DISCARD, d1, c1);
	}

	@Test
	public void verify_properties_of_capture_move() {
		Card handCard = s12;
		Card[] tableCards = { h3, h4 };
		Expression expression = new Multiply(tableCards[0], tableCards[1]);
		Move move = newValidMove(CAPTURE, expression, handCard);
		assertThat(move.getCards(), contains(handCard, tableCards[0], tableCards[1]));
		assertThat(move.getHandCard(), is(equalTo(handCard)));
		assertThat(move.getPiles(), contains((Pile) handCard, tableCards[0], tableCards[1]));
		assertThat(move.getType(), is(equalTo(CAPTURE)));
		assertThat(move.getValue(), is(equalTo(handCard.getValue())));
		assertThat(move.toString(), is(equalTo("CAPTURE (3H*4H) USING 12S")));
	}

	@Test
	public void construct_capture_with_null_expression_throws() {
		expect(IllegalArgumentException.class, "For CAPTURE type, hand card must have same value as expression");
		newValidMove(CAPTURE, null, c1);
	}

	@Test
	public void construct_capture_with_hand_card_not_equal_to_expression_throws() {
		expect(IllegalArgumentException.class, "For CAPTURE type, hand card must have same value as expression");
		newValidMove(CAPTURE, c2, c1);
	}

	@Test
	public void verify_properties_of_build_move() {
		Card handCard = s1;
		Card tableCard = h4;
		Expression expression = new Multiply(handCard, tableCard);
		Move move = newValidMove(BUILD, expression, handCard);
		assertThat(move.getCards(), contains(handCard, tableCard));
		assertThat(move.getHandCard(), is(equalTo(handCard)));
		assertThat(move.getPiles(), contains((Pile) handCard, tableCard));
		assertThat(move.getType(), is(equalTo(BUILD)));
		assertThat(move.getValue(), is(equalTo(expression.getValue())));
		assertThat(move.toString(), is(equalTo("BUILD (1S*4H) USING 1S")));
	}

	@Test
	public void construct_build_with_null_expression_throws() {
		expect(IllegalArgumentException.class, "For BUILD type, expression must contain handCard");
		newValidMove(BUILD, null, c1);
	}

	@Test
	public void construct_build_with_null_table_throws() {
		expect(NullPointerException.class, "table");
		new Move(DISCARD, c1, c1, null, exampleHand, players[0]);
	}

	@Test
	public void construct_build_with_null_player_throws() {
		expect(NullPointerException.class, "player");
		new Move(DISCARD, c1, c1, bigTable, exampleHand, null);
	}

	@Test
	public void construct_build_with_expression_that_does_not_contain_hand_card_throws() {
		expect(IllegalArgumentException.class, "For BUILD type, expression must contain handCard");
		newValidMove(BUILD, c2, c1);
	}

	@Test
	public void construct_with_invalid_type_throws() {
		expect(IllegalArgumentException.class, "For INVALID type, no parameters are valid");
		newValidMove(INVALID, d1, c1);
	}

	@Test
	public void construct_using_card_multiple_times_throws() {
		expect(IllegalArgumentException.class, "Trying to use card multiple times");
		newValidMove(CAPTURE, new Divide(c2, c2), c1);
	}

	// Discard
	@Test
	public void simple_discard_is_valid() {
		Move move = assertValid(DISCARD, null, s1);
		Pile pile = move.getPileCreated();
		assertEquals(Card.class, pile.getClass());
		assertThat(pile.getCards(), containsInAnyOrder(s1));
	}

	@Test
	public void discard_is_no_longer_valid_if_card_has_been_used() {
		Card card = s1;
		Move move = assertValid(DISCARD, null, card);
		Set<Card> hand = new HashSet<>(exampleHand);
		assertThat(move.isSound(bigTable, hand, player), is(true));
		hand.remove(card);
		assertThat(move.isSound(bigTable, hand, player), is(false));
	}

	@Test
	public void discard_card_promised_for_building_is_foolish_but_is_valid() {
		assertValid(DISCARD, null, s12);
	}

	// Capture
	@Test
	public void single_capture_is_valid() {
		Move move = assertValid(CAPTURE, h1, s1);
		assertThat(move.getPileCreated(), is(nullValue()));
	}

	@Test
	public void multiple_capture_is_valid() {
		assertValid(CAPTURE, new Equals(h1, new Subtract(h4, h3)), s1);
	}

	@Test
	public void capture_building_is_valid() {
		assertValid(CAPTURE, building_12B1_s6PlusD6, s12);
	}

	// Build
	@Test
	public void simple_build_is_valid() {
		Move move = assertValid(BUILD, new Add(s1, h2), s1);
		Pile pile = move.getPileCreated();
		assertEquals(Building.class, pile.getClass());
		assertThat(pile.getCards(), containsInAnyOrder(s1, h2));
	}

	@Test
	public void build_to_same_value_of_own_building_is_valid() {
		assertValid(BUILD, new Multiply(s3, h4), s3);
	}

	@Test
	public void build_to_same_value_of_own_building_using_own_building_is_valid() {
		assertValid(BUILD, new Multiply(building_12B1_s6PlusD6, s1), s1);
	}

	@Test
	public void build_second_building_of_new_value_is_valid() {
		assertValid(BUILD, new Add(s1, h2), s1);
	}

	@Test
	public void build_to_new_value_using_own_building_throws() {
		expect(IllegalArgumentException.class, "A building cannot be made using your own building of a different value");
		newValidMove(BUILD, new Divide(building_12B1_s6PlusD6, s3), s3);
	}

	@Test
	public void build_to_new_value_using_someone_elses_building_is_valid() {
		new Move(BUILD, new Divide(building_12B1_s6PlusD6, s3), s3, bigTable, exampleHand, players[1]);
	}

	@Test
	public void build_to_same_value_as_someoneelses_building_is_foolish_but_valid() {
		player = players[1];
		assertValid(BUILD, new Multiply(s3, h4), s3);
	}

	@Test
	public void build_to_value_not_held_in_hand_throws() {
		expect(IllegalArgumentException.class, "Player must have hand card of the value of the building being created: ");
		newValidMove(BUILD, new Add(h4, s3), s3);
	}

	@Test
	public void build_to_value_not_held_in_hand_after_move_throws() {
		expect(IllegalArgumentException.class, "Player must have hand card of the value of the building being created: ");
		newValidMove(BUILD, new Multiply(h1, s1), s1);
	}

	// General
	@Test
	public void move_with_hand_card_not_in_hand_throws() {
		expect(IllegalArgumentException.class, "Hand does not contain handCard: ");
		newValidMove(DISCARD, null, c1);
	}

	@Test
	public void move_with_no_hand_is_fine_ie_does_not_throw() {
		new Move(DISCARD, null, c1, bigTable, null, player);
	}

	@Test
	public void move_using_multiple_hand_cards_is_invalid() {
		expect(IllegalArgumentException.class, "Non-hand pile not present on table: ");
		newValidMove(CAPTURE, new Subtract(s4, s3), s1);
	}

	@Test
	public void move_with_table_card_not_on_table_throws() {
		expect(IllegalArgumentException.class, "Non-hand pile not present on table: ");
		newValidMove(CAPTURE, c1, s1);
	}

	@Test
	public void isValidFor_throws_for_null_table() {
		assertIsValidForThrows("table", null, exampleHand, player);
	}

	@Test
	public void isValidFor_throws_for_null_hand() {
		assertIsValidForThrows("hand", bigTable, null, player);
	}

	@Test
	public void isValidFor_throws_for_null_player() {
		assertIsValidForThrows("player", bigTable, exampleHand, null);
	}

	private void assertIsValidForThrows(String field, Set<Pile> table, Set<Card> hand, Player player) {
		expect(NullPointerException.class, field);
		validInstance().isSound(table, hand, player);
	}

	private Move assertValid(Type type, Expression expression, Card handCard) {
		Move move = newValidMove(type, expression, handCard);
		assertThat(move.isSound(bigTable, exampleHand, player), is(true));
		return move;
	}

	private Move newValidMove(Type type, Expression expression, Card handCard) {
		return new Move(type, expression, handCard, bigTable, exampleHand, players[0]);
	}
}
