package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.TestData.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.*;
import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;
import com.reubenpeeris.wippen.engine.WippenIllegalFormatException;

public class ParserTest extends BaseTest {
	@Test
	public void can_parse_equals() {
		assertParses("(4C=4H)", 4);
	}

	@Test
	public void can_parse_add() {
		assertParses("(4C+4H)", 8);
	}

	@Test
	public void can_parse_subtract() {
		assertParses("(4C-3H)", 1);
	}

	@Test
	public void can_parse_multiply() {
		assertParses("(4C*2H)", 8);
	}

	@Test
	public void can_parse_divide() {
		assertParses("(4C/4H)", 1);
	}

	@Test
	public void parse_gives_correct_presidence_between_equal_and_add() {
		assertParsesWithoutParens("(7C=(3H+4D))", 7);
	}

	@Test
	public void parse_gives_correct_presidence_between_add_and_subtract() {
		assertParsesWithoutParens("(9C+(4H-3D))", 10);
	}

	@Test
	public void parse_gives_correct_presidence_between_subtract_and_multiply() {
		assertParsesWithoutParens("(13C-(3H*4D))", 1);
	}

	@Test
	public void parse_gives_correct_presidence_between_multiply_and_divide() {
		assertParsesWithoutParens("(2C*(6H/3D))", 4);
	}

	@Test
	public void can_parse_valid_building() {
		assertParses("12B1", 12);
	}

	@Test
	public void parses_correctly_without_parenthesis() {
		assertParsesWithoutParens("((4S/2C)+((6H*2D)-(2S*1C)))", 12);
	}

	@Test
	public void parses_correctly_with_explicit_parenthesis_that_change_presidence() {
		assertParses("(8S/(2C+6H))", 1);
	}

	@Test
	public void can_parse_build_move() {
		assertParsesMove("BUILD ((4S/2C)+(6H/2D)) USING 6H", s4, c2, h6, d2);
	}

	@Test
	public void can_parse_capture_move() {
		assertParsesMove("CAPTURE ((4S/2C)+(6H/2D)) USING 5C", c5, s4, c2, h6, d2);
	}

	@Test
	public void can_parse_discard_move() {
		assertParsesMove("DISCARD 9H", h9);
	}

	@Test
	public void parse_throws_for_invalid_divide() {
		parseThrowsFormatException("4S/3S");
	}

	@Test
	public void parse_throws_for_invalid_maths_syntax() {
		parseThrowsFormatException("4S 4S");
	}

	@Test
	public void parse_throws_for_invalid_leading_character() {
		parseThrowsFormatException("(4S");
	}

	@Test
	public void parse_throws_for_invalid_character_in_middile_of_expression() {
		parseThrowsFormatException("4S~3C");
	}

	@Test
	public void parse_throws_for_operator_missing_leading_card() {
		parseThrowsFormatException("+3S");
	}

	@Test
	public void parse_throws_for_operator_missing_trailing_card() {
		parseThrowsFormatException("3S+");
	}

	@Test
	public void parse_throws_for_invalid_expression() {
		parseThrowsFormatException("invalid input expression");
	}

	@Test
	public void parse_throws_for_invalid_base_format() {
		parseThrowsFormatException("this is not valid");
	}

	@Test
	public void parse_throws_for_pile_with_wrong_value() {
		parseThrowsFormatException("11B1");
	}

	@Test
	public void parse_throws_for_pile_with_wrong_player() {
		parseThrowsFormatException("12B2");
	}

	@Test
	public void parse_move_throws_for_invalid_card() {
		parseThrowsFormatException("1K");
	}

	@Test
	public void parse_throws_for_invalid_move_type() {
		String expression = "GRAB 1C";
		expect(WippenIllegalFormatException.class, expression);
		Parser.parseMove(expression, Collections.<Pile> emptyList());
	}

	private void assertParsesMove(String moveExpression, Pile... piles) {
		Move move = Parser.parseMove(moveExpression, bigTable);
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
		Move output = Parser.parseMove(moveExpression, bigTable);
		assertThat(output.toString(), is(equalTo(toCaptureMoveExpression(resultPattern, expectedValue))));
		assertThat(output.getValue(), is(equalTo(expectedValue)));
	}

	private void parseThrowsFormatException(String expression) {
		expect(WippenIllegalFormatException.class, expression);
		Parser.parseMove(toCaptureMoveExpression(expression, 1), bigTable);
	}

	private String toCaptureMoveExpression(String expression, int value) {
		return String.format("CAPTURE %s USING %s", expression, new Card(Suit.SPADE, new Rank(value)));
	}
}
