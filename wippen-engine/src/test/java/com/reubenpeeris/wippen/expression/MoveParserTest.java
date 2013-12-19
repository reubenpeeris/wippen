package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.TestData.*;
import static com.reubenpeeris.wippen.expression.Suit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;

public class MoveParserTest extends BaseTest {
	@Test
	public void can_parse_equals() {
		assertParses("(12B1=(3H*4H))", 12);
	}

	@Test
	public void can_parse_add() {
		assertParses("(1H+2H)", 3);
	}

	@Test
	public void can_parse_subtract() {
		assertParses("(4H-3H)", 1);
	}

	@Test
	public void can_parse_multiply() {
		assertParses("(3H*4H)", 12);
	}

	@Test
	public void can_parse_divide() {
		assertParses("(12B1/4H)", 3);
	}

	@Test
	public void parse_gives_correct_presidence_between_equal_and_add() {
		assertParsesWithoutParens("(3H=(1H+2H))", 3);
	}

	@Test
	public void parse_gives_correct_presidence_between_add_and_subtract() {
		assertParsesWithoutParens("(2H+(4H-3H))", 3);
	}

	@Test
	public void parse_gives_correct_presidence_between_subtract_and_multiply() {
		assertParsesWithoutParens("((1H*3H)-2H)", 1);
	}

	@Test
	public void parse_gives_correct_presidence_between_multiply_and_divide() {
		assertParsesWithoutParens("(4H*(3H/1H))", 12);
	}

	@Test
	public void can_parse_valid_building() {
		assertParses("12B1", 12);
	}

	@Test
	public void parses_correctly_with_explicit_parenthesis_that_change_presidence() {
		assertParses("((4H-3H)*1H)", 1);
	}

	@Test
	public void can_parse_build_move() {
		assertParsesMove("BUILD (3H*4S) USING 4S", s4, h3);
	}

	@Test
	public void can_parse_capture_move() {
		assertParsesMove("CAPTURE (3H*4H) USING 12S", h3, h4, s12);
	}

	@Test
	public void can_parse_discard_move() {
		assertParsesMove("DISCARD 4S", s4);
	}

	@Test
	public void parse_returns_null_for_invalid_divide() {
		parseReturnsNullMove("4S/3S");
	}

	@Test
	public void parse_returns_null_for_invalid_maths_syntax() {
		parseReturnsNullMove("4S 4S");
	}

	@Test
	public void parse_returns_null_for_invalid_leading_character() {
		parseReturnsNullMove("(4S");
	}

	@Test
	public void parse_returns_null_for_invalid_character_in_middile_of_expression() {
		parseReturnsNullMove("4S~3C");
	}

	@Test
	public void parse_returns_null_for_operator_missing_leading_card() {
		parseReturnsNullMove("+3S");
	}

	@Test
	public void parse_returns_null_for_operator_missing_trailing_card() {
		parseReturnsNullMove("3S+");
	}

	@Test
	public void parse_returns_null_for_invalid_expression() {
		parseReturnsNullMove("invalid input expression");
	}

	@Test
	public void parse_returns_null_for_invalid_base_format() {
		parseReturnsNullMove("this is not valid");
	}

	@Test
	public void parse_returns_null_for_pile_with_wrong_value() {
		parseReturnsNullMove("11B1");
	}

	@Test
	public void parse_returns_null_for_pile_with_wrong_player() {
		parseReturnsNullMove("12B2");
	}

	@Test
	public void parse_returns_null_for_invalid_card() {
		parseReturnsNullMove("1K");
	}

	@Test
	public void parse_returns_null_for_invalid_move_type() {
		String expression = "GRAB 1C";
		assertThat(parseMove(expression), is(nullValue()));
	}

	private void assertParsesMove(String moveExpression, Pile... piles) {
		Move move = parseMove(moveExpression);
		assertThat(move.getPiles(), containsInAnyOrder(piles));
		assertThat(move.toString(), is(equalTo(moveExpression)));
	}

	private void assertParsesWithoutParens(String pattern, int expectedValue) {
		assertParses(pattern.replaceAll("[()]", ""), pattern, expectedValue);
	}

	private void assertParses(String pattern, int expectedValue) {
		assertParses(pattern, pattern, expectedValue);
	}

	private void assertParses(String inputPattern, String resultPattern, int expectedValue) {
		String moveExpression = toCaptureMoveExpression(inputPattern, expectedValue);
		Move output = parseMove(moveExpression);
		assertThat(output.toString(), is(equalTo(toCaptureMoveExpression(resultPattern, expectedValue))));
		assertThat(output.getValue(), is(equalTo(expectedValue)));
	}

	private void parseReturnsNullMove(String expression) {
		assertThat(parseMove(toCaptureMoveExpression(expression, 1)), is(nullValue()));
	}

	private String toCaptureMoveExpression(String expression, int value) {
		return String.format("CAPTURE %s USING %s", expression, new Card(Rank.fromInt(value), SPADE));
	}

	private Move parseMove(String moveExpression) {
		return MoveParser.parseMove(moveExpression, bigTable, exampleHand, players[0]);
	}
}
