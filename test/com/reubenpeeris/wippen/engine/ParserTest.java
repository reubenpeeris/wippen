package com.reubenpeeris.wippen.engine;
import static org.junit.Assert.assertEquals;

import com.reubenpeeris.wippen.expression.Expression;
import org.junit.Test;


public class ParserTest {

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
		Expression output = Parser.parseMath("3H*4D-2S");
		assertEquals("((3H*4D)-2S)", output.toString());
		assertEquals(10, output.getValue());
	}
	
	@Test
	public void testParseMathPresidenceMinusVsMultiply() throws Exception {
		Expression output = Parser.parseMath("12H-3D*2S");
		assertEquals("(12H-(3D*2S))", output.toString());
		assertEquals(6, output.getValue());
	}

	@Test
	public void testParseMathPresidenceDivideVsMinus() throws Exception {
		Expression output = Parser.parseMath("12H/4D-2S");
		assertEquals("((12H/4D)-2S)", output.toString());
		assertEquals(1, output.getValue());
	}

	@Test
	public void testParseMathPresidenceMinusVsDivide() throws Exception {
		Expression output = Parser.parseMath("4S-12H/4D");
		assertEquals("(4S-(12H/4D))", output.toString());
		assertEquals(1, output.getValue());
	}

	@Test
	public void testParseMathPresidenceMultiplyVsPlus() throws Exception {
		Expression output = Parser.parseMath("2H*3C+1S");
		assertEquals("((2H*3C)+1S)", output.toString());
		assertEquals(7, output.getValue());
	}

	@Test
	public void testParseMathPresidencePlusVsMultiply() throws Exception {
		Expression output = Parser.parseMath("2H+3C*1S");
		assertEquals("(2H+(3C*1S))", output.toString());
		assertEquals(5, output.getValue());
	}

	@Test
	public void testParseMathPresidenceDivideVsPlus() throws Exception {
		Expression output = Parser.parseMath("12H/4D+2S");
		assertEquals("((12H/4D)+2S)", output.toString());
		assertEquals(5, output.getValue());
	}

	@Test
	public void testParseMathPresidencePlusVsDivide() throws Exception {
		Expression output = Parser.parseMath("4S+12H/4D");
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
		Expression output = Parser.parseMath("3C-2D-1H");
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
		Expression output = Parser.parseMath("4S/2C+6H/2D");
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
	public void testParseD() throws Exception {
		Expression output = Parser.parseMath("3");
		assertEquals("3", output.toString());
		assertEquals(3, output.getValue());
		assertEquals("[3]", output.getPiles().toString());
	}

	@Test
	public void testParseActionBuild() throws Exception {
		Expression expression = Parser.parseExpression("4S/2C+6H/2D");
		assertEquals("[4S, 2C, 6H, 2D]", expression.getPiles().toString());
		assertEquals("((4S/2C)+(6H/2D))", expression.toString());
	}

	@Test
	public void testParseActionTake() throws Exception {
		Expression expression = Parser.parseExpression("5C=4S/2C+6H/2D");
		assertEquals("[5C, 4S, 2C, 6H, 2D]", expression.getPiles().toString());
		assertEquals("5C=((4S/2C)+(6H/2D))", expression.toString());
	}

	@Test
	public void testParseActionTakeA() throws Exception {
		Expression expression = Parser.parseExpression("9H=(12D-11H)*9S");
		assertEquals("[9H, 12D, 11H, 9S]", expression.getPiles().toString());
		assertEquals("9H=((12D-11H)*9S)", expression.toString());
	}

	@Test
	public void testParseMissingLeadingNumber() {
		try {
			Parser.parseMath("+3");
		} catch (ParseException e) {
			assertEquals("Invalid format parsing: '+3'", e.getMessage());
		}
	}

	@Test
	public void testParseMissingTrailingNumber() {
		try {
			Parser.parseMath("3+");
		} catch (ParseException e) {
			assertEquals("Invalid format parsing: '3+'", e.getMessage());
		}
	}
}
