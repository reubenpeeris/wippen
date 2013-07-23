package com.reubenpeeris.wippen.engine;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.reubenpeeris.wippen.expression.Building;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.expression.Rank;

import org.junit.Test;

import static com.reubenpeeris.wippen.Cards.*;

public class ParserTest {
	private final Player player1 = new Player(1, new MockRobot());
	
	private Collection<Pile> BIG_TABLE = Arrays.<Pile>asList(h3, d4, s2, new Building(Collections.<Card>emptySet(), new Rank(3), player1));
	
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
		Expression output = Parser.parseMath("3H*4D-2S", BIG_TABLE);
		assertEquals("((3H*4D)-2S)", output.toString());
		assertEquals(10, output.getValue());
	}
	
	@Test
	public void testParseMathPresidenceMinusVsMultiply() throws Exception {
		Expression output = Parser.parseMath("12H-3D*2S", BIG_TABLE);
		assertEquals("(12H-(3D*2S))", output.toString());
		assertEquals(6, output.getValue());
	}

	@Test
	public void testParseMathPresidenceDivideVsMinus() throws Exception {
		Expression output = Parser.parseMath("12H/4D-2S", BIG_TABLE);
		assertEquals("((12H/4D)-2S)", output.toString());
		assertEquals(1, output.getValue());
	}

	@Test
	public void testParseMathPresidenceMinusVsDivide() throws Exception {
		Expression output = Parser.parseMath("4S-12H/4D", BIG_TABLE);
		assertEquals("(4S-(12H/4D))", output.toString());
		assertEquals(1, output.getValue());
	}

	@Test
	public void testParseMathPresidenceMultiplyVsPlus() throws Exception {
		Expression output = Parser.parseMath("2H*3C+1S", BIG_TABLE);
		assertEquals("((2H*3C)+1S)", output.toString());
		assertEquals(7, output.getValue());
	}

	@Test
	public void testParseMathPresidencePlusVsMultiply() throws Exception {
		Expression output = Parser.parseMath("2H+3C*1S", BIG_TABLE);
		assertEquals("(2H+(3C*1S))", output.toString());
		assertEquals(5, output.getValue());
	}

	@Test
	public void testParseMathPresidenceDivideVsPlus() throws Exception {
		Expression output = Parser.parseMath("12H/4D+2S", BIG_TABLE);
		assertEquals("((12H/4D)+2S)", output.toString());
		assertEquals(5, output.getValue());
	}

	@Test
	public void testParseMathPresidencePlusVsDivide() throws Exception {
		Expression output = Parser.parseMath("4S+12H/4D", BIG_TABLE);
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
		Expression output = Parser.parseMath("3C-2D-1H", BIG_TABLE);
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
		Expression output = Parser.parseMath("4S/2C+6H/2D", BIG_TABLE);
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
		Expression output = Parser.parseMath("3B1", BIG_TABLE);
		assertEquals("3B1", output.toString());
		assertEquals(3, output.getValue());
		assertEquals("[3B1]", output.getPiles().toString());
	}

	@Test
	public void testParseActionBuild() throws Exception {
		Expression expression = Parser.parseExpression("4S/2C+6H/2D", BIG_TABLE);
		assertEquals("[4S, 2C, 6H, 2D]", expression.getPiles().toString());
		assertEquals("((4S/2C)+(6H/2D))", expression.toString());
	}

	@Test
	public void testParseActionTake() throws Exception {
		Expression expression = Parser.parseExpression("5C=4S/2C+6H/2D", BIG_TABLE);
		assertEquals("[5C, 4S, 2C, 6H, 2D]", expression.getPiles().toString());
		assertEquals("5C=((4S/2C)+(6H/2D))", expression.toString());
	}

	@Test
	public void testParseActionTakeA() throws Exception {
		Expression expression = Parser.parseExpression("9H=(12D-11H)*9S", BIG_TABLE);
		assertEquals("[9H, 12D, 11H, 9S]", expression.getPiles().toString());
		assertEquals("9H=((12D-11H)*9S)", expression.toString());
	}

	@Test
	public void testParseMissingLeadingNumber() {
		try {
			Parser.parseMath("+3S", BIG_TABLE);
		} catch (ParseException e) {
			assertEquals("Invalid format parsing: '+3S'", e.getMessage());
		}
	}

	@Test
	public void testParseMissingTrailingNumber() {
		try {
			Parser.parseMath("3S+", BIG_TABLE);
		} catch (ParseException e) {
			assertEquals("Invalid format parsing: '3S+'", e.getMessage());
		}
	}
}
