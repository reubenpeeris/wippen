package com.reubenpeeris.wippen;

import static com.reubenpeeris.wippen.expression.Move.Type.*;
import static com.reubenpeeris.wippen.expression.Rank.*;
import static com.reubenpeeris.wippen.expression.Suit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.expression.AnonymousExpressionFactory;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.ExpressionFactory;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.robot.BaseRobot;
import com.reubenpeeris.wippen.robot.Robot;

public final class TestData {
	private static AnonymousExpressionFactory createAnon = new AnonymousExpressionFactory();
	public static ExpressionFactory create;

	public static final Card c1 = createAnon.newCard(ACE, CLUB);
	public static final Card d1 = createAnon.newCard(ACE, DIAMOND);
	public static final Card h1 = createAnon.newCard(ACE, HEART);
	public static final Card s1 = createAnon.newCard(ACE, SPADE);
	public static final Card c2 = createAnon.newCard(TWO, CLUB);
	public static final Card d2 = createAnon.newCard(TWO, DIAMOND);
	public static final Card h2 = createAnon.newCard(TWO, HEART);
	public static final Card s2 = createAnon.newCard(TWO, SPADE);
	public static final Card c3 = createAnon.newCard(THREE, CLUB);
	public static final Card d3 = createAnon.newCard(THREE, DIAMOND);
	public static final Card h3 = createAnon.newCard(THREE, HEART);
	public static final Card s3 = createAnon.newCard(THREE, SPADE);
	public static final Card c4 = createAnon.newCard(FOUR, CLUB);
	public static final Card d4 = createAnon.newCard(FOUR, DIAMOND);
	public static final Card h4 = createAnon.newCard(FOUR, HEART);
	public static final Card s4 = createAnon.newCard(FOUR, SPADE);
	public static final Card c5 = createAnon.newCard(FIVE, CLUB);
	public static final Card d5 = createAnon.newCard(FIVE, DIAMOND);
	public static final Card h5 = createAnon.newCard(FIVE, HEART);
	public static final Card s5 = createAnon.newCard(FIVE, SPADE);
	public static final Card c6 = createAnon.newCard(SIX, CLUB);
	public static final Card d6 = createAnon.newCard(SIX, DIAMOND);
	public static final Card h6 = createAnon.newCard(SIX, HEART);
	public static final Card s6 = createAnon.newCard(SIX, SPADE);
	public static final Card c7 = createAnon.newCard(SEVEN, CLUB);
	public static final Card d7 = createAnon.newCard(SEVEN, DIAMOND);
	public static final Card h7 = createAnon.newCard(SEVEN, HEART);
	public static final Card s7 = createAnon.newCard(SEVEN, SPADE);
	public static final Card c8 = createAnon.newCard(EIGHT, CLUB);
	public static final Card d8 = createAnon.newCard(EIGHT, DIAMOND);
	public static final Card h8 = createAnon.newCard(EIGHT, HEART);
	public static final Card s8 = createAnon.newCard(EIGHT, SPADE);
	public static final Card c9 = createAnon.newCard(NINE, CLUB);
	public static final Card d9 = createAnon.newCard(NINE, DIAMOND);
	public static final Card h9 = createAnon.newCard(NINE, HEART);
	public static final Card s9 = createAnon.newCard(NINE, SPADE);
	public static final Card c10 = createAnon.newCard(TEN, CLUB);
	public static final Card d10 = createAnon.newCard(TEN, DIAMOND);
	public static final Card h10 = createAnon.newCard(TEN, HEART);
	public static final Card s10 = createAnon.newCard(TEN, SPADE);
	public static final Card c11 = createAnon.newCard(JACK, CLUB);
	public static final Card d11 = createAnon.newCard(JACK, DIAMOND);
	public static final Card h11 = createAnon.newCard(JACK, HEART);
	public static final Card s11 = createAnon.newCard(JACK, SPADE);
	public static final Card c12 = createAnon.newCard(QUEEN, CLUB);
	public static final Card d12 = createAnon.newCard(QUEEN, DIAMOND);
	public static final Card h12 = createAnon.newCard(QUEEN, HEART);
	public static final Card s12 = createAnon.newCard(QUEEN, SPADE);
	public static final Card c13 = createAnon.newCard(KING, CLUB);
	public static final Card d13 = createAnon.newCard(KING, DIAMOND);
	public static final Card h13 = createAnon.newCard(KING, HEART);
	public static final Card s13 = createAnon.newCard(KING, SPADE);

	public static final Set<Pile> emptyTable = Collections.emptySet();
	public static final Set<Card> exampleHand = Collections.unmodifiableSet(new LinkedHashSet<>(Arrays.<Card> asList(s1, s3, s4, s12)));
	public static Move discard;
	public static Move build;

	public static final Robot robot = new NullRobot();

	public static final Player[] players = new Player[4];
	public static List<Player> playerList = new ArrayList<>();

	public static Pile building_12B1_s6PlusD6;
	public static Pile building;
	public static Set<Pile> bigTable;

	public static Expression equals;

	public static void buildTestData() {
		players[0] = new Player(1, robot);
		players[1] = new Player(2, robot);
		players[2] = new Player(3, robot);
		players[3] = new Player(4, robot);

		playerList.clear();
		playerList.addAll(Arrays.asList(players));

		create = new ExpressionFactory(Collections.<Pile> singleton(d6), new HashSet<>(Arrays.asList(s6, s12)), players[0]);
		building_12B1_s6PlusD6 = create.newMove(BUILD, createAnon.newAdd(s6, d6), s6).getPileCreated();
		bigTable = new LinkedHashSet<>(Arrays.<Pile> asList(h1, h2, h3, h4, building_12B1_s6PlusD6));
		create = new ExpressionFactory(bigTable, exampleHand, players[0]);

		discard = create.newMove(DISCARD, null, s1);
		build = create.newMove(BUILD, createAnon.newMultiply(h3, s4), s4);
		building = build.getPileCreated();

		equals = createAnon.newEquals(h1, c1);
	}

	public static final class NullRobot extends BaseRobot {
		@Override
		public Move takeTurn(Set<Pile> table, Set<Card> hand) {
			return null;
		}
	}
}
