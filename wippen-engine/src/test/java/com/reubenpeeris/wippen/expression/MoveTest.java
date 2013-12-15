package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.TestData.*;
import static com.reubenpeeris.wippen.expression.Move.Type.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.*;
import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.expression.Move.Type;

public class MoveTest extends BaseExpressionTest {
	private Player player;

	@Override
	protected Expression validInstance() {
		return new Move(CAPTURE, h1, s1);
	}

	@Override
	protected List<MethodSignature> methodsExemptFromImmutabilityCheck() {
		List<MethodSignature> exemptMethods = super.methodsExemptFromImmutabilityCheck();
		exemptMethods.add(new MethodSignature(Boolean.TYPE, "isValidFor", Collection.class, Collection.class, Player.class));

		return exemptMethods;
	}

	@Before
	public void setUp() {
		player = players[0];
	}

	@Test
	public void construct_with_null_type_throws() {
		expect(NullPointerException.class, "type");
		new Move(null, c1, c1);
	}

	@Test
	public void construct_with_null_handCard_throws() {
		expect(NullPointerException.class, "handCard");
		new Move(DISCARD, c1, null);
	}

	@Test
	public void verify_properties_of_discard_move() {
		Card card = c1;
		Move move = new Move(DISCARD, null, card);
		assertThat(move.getCards(), contains(card));
		assertThat(move.getHandCard(), is(equalTo(card)));
		assertThat(move.getPiles(), contains((Pile) card));
		assertThat(move.getType(), is(equalTo(DISCARD)));
		assertThat(move.getValue(), is(equalTo(card.getValue())));
		assertThat(move.isPileGenerated(), is(true));
		assertThat(move.toString(), is(equalTo("DISCARD 1C")));
	}

	@Test
	public void construct_discard_with_non_null_expression_throws() {
		expect(IllegalArgumentException.class, "For DISCARD type, expression must be null");
		new Move(DISCARD, d1, c1);
	}

	@Test
	public void verify_properties_of_capture_move() {
		Card handCard = c8;
		Card[] tableCards = { c1, c7 };
		Expression expression = new Add(tableCards[0], tableCards[1]);
		Move move = new Move(CAPTURE, expression, handCard);
		assertThat(move.getCards(), contains(handCard, tableCards[0], tableCards[1]));
		assertThat(move.getHandCard(), is(equalTo(handCard)));
		assertThat(move.getPiles(), contains((Pile) handCard, tableCards[0], tableCards[1]));
		assertThat(move.getType(), is(equalTo(CAPTURE)));
		assertThat(move.getValue(), is(equalTo(handCard.getValue())));
		assertThat(move.isPileGenerated(), is(false));
		assertThat(move.toString(), is(equalTo("CAPTURE (1C+7C) USING 8C")));
	}

	@Test
	public void construct_capture_with_null_expression_throws() {
		expect(IllegalArgumentException.class, "For CAPTURE type, hand card must have same value as expression");
		new Move(CAPTURE, null, c1);
	}

	@Test
	public void construct_capture_with_hand_card_not_equal_to_expression_throws() {
		expect(IllegalArgumentException.class, "For CAPTURE type, hand card must have same value as expression");
		new Move(CAPTURE, c2, c1);
	}

	@Test
	public void verify_properties_of_build_move() {
		Card handCard = c8;
		Card tableCard = c7;
		Expression expression = new Subtract(handCard, tableCard);
		Move move = new Move(BUILD, expression, handCard);
		assertThat(move.getCards(), contains(handCard, tableCard));
		assertThat(move.getHandCard(), is(equalTo(handCard)));
		assertThat(move.getPiles(), contains((Pile) handCard, tableCard));
		assertThat(move.getType(), is(equalTo(BUILD)));
		assertThat(move.getValue(), is(equalTo(expression.getValue())));
		assertThat(move.isPileGenerated(), is(true));
		assertThat(move.toString(), is(equalTo("BUILD (8C-7C) USING 8C")));
	}

	@Test
	public void construct_build_with_null_expression_throws() {
		expect(IllegalArgumentException.class, "For BUILD type, expression must contain handCard");
		new Move(BUILD, null, c1);
	}

	@Test
	public void construct_build_with_expression_that_does_not_contain_hand_card_throws() {
		expect(IllegalArgumentException.class, "For BUILD type, expression must contain handCard");
		new Move(BUILD, c2, c1);
	}

	@Test
	public void construct_using_card_multiple_times_throws() {
		expect(IllegalArgumentException.class, "Trying to use card multiple times");
		new Move(CAPTURE, new Divide(c10, c10), c1);
	}

	@Test
	public void create_returns_move_for_valid_arguments() {
		Type type = DISCARD;
		Expression expression = null;
		Card handCard = c1;
		Move move = Move.create(type, expression, handCard, Collections.<Pile> emptySet(), Collections.singleton(c1), players[0]);
		assertThat(move, is(equalTo(new Move(type, expression, handCard))));
	}

	@Test
	public void create_throws_for_null_type() {
		assertExceptionForCreate(null, c1, null, Collections.<Pile> emptySet(), Collections.singleton(c1), players[0], "type");
	}

	@Test
	public void create_throws_for_null_hand_card() {
		assertExceptionForCreate(DISCARD, null, null, Collections.<Pile> emptySet(), Collections.singleton(c1), players[0], "handCard");
	}

	@Test
	public void create_throws_for_null_table() {
		assertExceptionForCreate(DISCARD, c1, null, null, Collections.singleton(c1), players[0], "table");
	}

	@Test
	public void create_throws_for_null_hand() {
		assertExceptionForCreate(DISCARD, c1, null, Collections.<Pile> emptySet(), null, players[0], "hand");
	}

	@Test
	public void create_throws_for_null_player() {
		assertExceptionForCreate(DISCARD, c1, null, Collections.<Pile> emptySet(), Collections.singleton(c1), null, "player");
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
	public void parseMove_returns_same_as_Parser_parseMove() {
		String moveExpression = "DISCARD 1S";
		assertThat(Move.parseMove(moveExpression, bigTable), is(equalTo(Parser.parseMove(moveExpression, bigTable))));
	}

	// Discard
	@Test
	public void simple_discard_is_valid() {
		assertValid(DISCARD, null, s1);
	}

	@Test
	public void discard_card_promised_for_building_is_foolish_but_is_valid() {
		assertValid(DISCARD, null, s12);
	}

	// Capture
	@Test
	public void single_capture_is_valid() {
		assertValid(CAPTURE, h1, s1);
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
		assertValid(BUILD, new Add(s1, h2), s1);
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
	public void build_to_new_value_using_own_building_is_invalid() {
		assertInvalid(BUILD, new Divide(building_12B1_s6PlusD6, s3), s3);
	}

	@Test
	public void build_to_new_value_using_someoneelses_building_is_valid() {
		player = players[1];
		assertValid(BUILD, new Divide(building_12B1_s6PlusD6, s3), s3);
	}

	@Test
	public void build_to_same_value_as_someoneelses_building_is_foolish_but_valid() {
		player = players[1];
		assertValid(BUILD, new Multiply(s3, h4), s3);
	}

	@Test
	public void build_to_value_not_held_in_hand_is_invalid() {
		assertInvalid(BUILD, new Add(h4, s3), s3);
	}

	@Test
	public void build_to_value_not_held_in_hand_one_card_played_is_invalid() {
		assertInvalid(BUILD, new Multiply(h1, s1), s1);
	}

	// General
	@Test
	public void move_with_hand_card_not_in_hand_is_invalid() {
		assertInvalid(DISCARD, null, c1);
	}

	@Test
	public void move_using_multiple_hand_cards_is_invalid() {
		assertInvalid(CAPTURE, new Subtract(s4, s3), s1);
	}

	@Test
	public void move_with_table_card_not_on_table_is_invalid() {
		assertInvalid(CAPTURE, c1, s1);
	}

	private void assertValid(Type type, Expression expression, Card handCard) {
		assertMove(type, expression, handCard, true);
	}

	private void assertInvalid(Type type, Expression expression, Card handCard) {
		assertMove(type, expression, handCard, false);
	}

	private void assertMove(Type type, Expression expression, Card handCard, boolean valid) {
		Move move = new Move(type, expression, handCard);
		assertThat(move.isValidFor(bigTable, exampleHand, player), is(valid));
	}

	private void assertExceptionForCreate(Type type, Card handCard, Expression expression, Collection<Pile> table, Collection<Card> hand,
			Player player, String message) {
		expect(NullPointerException.class, message);

		Move.create(type, expression, handCard, table, hand, player);
	}
}
