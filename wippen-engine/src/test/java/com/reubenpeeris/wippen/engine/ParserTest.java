package com.reubenpeeris.wippen.engine;

import static com.reubenpeeris.wippen.ObjectMother.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.*;
import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;

public class ParserTest extends BaseTest {
	@Test
	public void testInfixToPostFixSimple() {
		String output = Parser.infixToPostfix("3H*4D-2S");
		assertEquals("3H 4D * 2S -", output);
	}

	@Test
	public void testInfixToPostFixWithParenthesis() {
		String output = Parser.infixToPostfix("3H*(4D-2S)");
		assertEquals("3H 4D 2S - *", output);
	}

	@Test
	public void testParseMathPresidenceMultiplyVsMinus() throws Exception {
		Expression output = Parser.parseMath("3H*4D-2S", bigTable);
		assertEquals("((3H*4D)-2S)", output.toString());
		assertEquals(10, output.getValue());
	}

	@Test
	public void testParseMathPresidenceMinusVsMultiply() throws Exception {
		Expression output = Parser.parseMath("12H-3D*2S", bigTable);
		assertEquals("(12H-(3D*2S))", output.toString());
		assertEquals(6, output.getValue());
	}

	@Test
	public void testParseMathPresidenceDivideVsMinus() throws Exception {
		Expression output = Parser.parseMath("12H/4D-2S", bigTable);
		assertEquals("((12H/4D)-2S)", output.toString());
		assertEquals(1, output.getValue());
	}

	@Test
	public void testParseMathPresidenceMinusVsDivide() throws Exception {
		Expression output = Parser.parseMath("4S-12H/4D", bigTable);
		assertEquals("(4S-(12H/4D))", output.toString());
		assertEquals(1, output.getValue());
	}

	@Test
	public void testParseMathPresidenceMultiplyVsPlus() throws Exception {
		Expression output = Parser.parseMath("2H*3C+1S", bigTable);
		assertEquals("((2H*3C)+1S)", output.toString());
		assertEquals(7, output.getValue());
	}

	@Test
	public void testParseMathPresidencePlusVsMultiply() throws Exception {
		Expression output = Parser.parseMath("2H+3C*1S", bigTable);
		assertEquals("(2H+(3C*1S))", output.toString());
		assertEquals(5, output.getValue());
	}

	@Test
	public void testParseMathPresidenceDivideVsPlus() throws Exception {
		Expression output = Parser.parseMath("12H/4D+2S", bigTable);
		assertEquals("((12H/4D)+2S)", output.toString());
		assertEquals(5, output.getValue());
	}

	@Test
	public void testParseMathPresidencePlusVsDivide() throws Exception {
		Expression output = Parser.parseMath("4S+12H/4D", bigTable);
		assertEquals("(4S+(12H/4D))", output.toString());
		assertEquals(7, output.getValue());
	}

	@Test
	public void testInfixToPostfixMinusMinus() {
		String output = Parser.infixToPostfix("3C-2D-1H");
		assertEquals("3C 2D - 1H -", output);
	}

	@Test
	public void testParseMinusMinus() throws Exception {
		Expression output = Parser.parseMath("3C-2D-1H", bigTable);
		assertEquals("((3C-2D)-1H)", output.toString());
		assertEquals(0, output.getValue());
	}

	@Test
	public void testInfixToPostfixDivideMinusDivide() {
		String output = Parser.infixToPostfix("4S/2C+6H/2D");
		assertEquals("4S 2C / 6H 2D / +", output);
	}

	@Test
	public void testParseDivideMinusDivide() throws Exception {
		Expression output = Parser.parseMath("4S/2C+6H/2D", bigTable);
		assertEquals("((4S/2C)+(6H/2D))", output.toString());
		assertEquals(5, output.getValue());
		assertEquals("[4S, 2C, 6H, 2D]", output.getPiles().toString());
	}

	@Test
	public void testInfixToPostfixSingle() {
		String output = Parser.infixToPostfix("3");
		assertEquals("3", output);
	}

	@Test
	public void testParseBuilding() throws Exception {
		Expression output = Parser.parseMath("12B1", bigTable);
		assertEquals("12B1", output.toString());
		assertEquals(12, output.getValue());
		assertEquals("[12B1]", output.getPiles().toString());
	}

	@Test
	public void testParseBuildMove() {
		assertParsesMove("BUILD ((4S/2C)+(6H/2D)) 6H", s4, c2, h6, d2);
	}

	@Test
	public void testParseCaptureMove() {
		assertParsesMove("CAPTURE ((4S/2C)+(6H/2D)) 5C", c5, s4, c2, h6, d2);
	}

	@Test
	public void testParseDiscardMove() {
		assertParsesMove("DISCARD 9H", h9);
	}

	@Test
	public void parseMoveThrowsForInvalidExpression() {
		expect(WippenIllegalFormatException.class, "Unable to parse move expression");
		Parser.parseMove("invalid", Collections.<Pile> emptyList());
	}

	@Test
	public void parseMoveThrowsForInvalidType() {
		expect(WippenIllegalFormatException.class, "Invalid card format");
		Parser.parseMove("DISCARD 1K", Collections.<Pile> emptyList());
	}

	@Test
	public void testParseMissingLeadingNumber() {
		try {
			Parser.parseMath("+3S", bigTable);
		} catch (WippenIllegalFormatException e) {
			assertEquals("Invalid format parsing: '+3S'", e.getMessage());
		}
	}

	@Test
	public void testParseMissingTrailingNumber() {
		try {
			Parser.parseMath("3S+", bigTable);
		} catch (WippenIllegalFormatException e) {
			assertEquals("Invalid format parsing: '3S+'", e.getMessage());
		}
	}

	private void assertParsesMove(String moveExpression, Pile... piles) {
		Move move = Parser.parseMove(moveExpression, bigTable);
		assertThat(move.getPiles(), containsInAnyOrder(piles));
		assertThat(move.toString(), is(equalTo(moveExpression)));
	}
}
