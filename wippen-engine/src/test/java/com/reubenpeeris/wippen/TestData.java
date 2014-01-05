package com.reubenpeeris.wippen;

import static com.reubenpeeris.wippen.expression.AnonymousExpressionFactory.*;
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
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.ExpressionFactory;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.robot.BaseRobot;
import com.reubenpeeris.wippen.robot.Robot;

public final class TestData {
	public static ExpressionFactory create;

	public static final Card c1 = factory().newCard(ACE, CLUBS);
	public static final Card d1 = factory().newCard(ACE, DIAMONDS);
	public static final Card h1 = factory().newCard(ACE, HEARTS);
	public static final Card s1 = factory().newCard(ACE, SPADES);
	public static final Card c2 = factory().newCard(TWO, CLUBS);
	public static final Card d2 = factory().newCard(TWO, DIAMONDS);
	public static final Card h2 = factory().newCard(TWO, HEARTS);
	public static final Card s2 = factory().newCard(TWO, SPADES);
	public static final Card c3 = factory().newCard(THREE, CLUBS);
	public static final Card d3 = factory().newCard(THREE, DIAMONDS);
	public static final Card h3 = factory().newCard(THREE, HEARTS);
	public static final Card s3 = factory().newCard(THREE, SPADES);
	public static final Card c4 = factory().newCard(FOUR, CLUBS);
	public static final Card d4 = factory().newCard(FOUR, DIAMONDS);
	public static final Card h4 = factory().newCard(FOUR, HEARTS);
	public static final Card s4 = factory().newCard(FOUR, SPADES);
	public static final Card c5 = factory().newCard(FIVE, CLUBS);
	public static final Card d5 = factory().newCard(FIVE, DIAMONDS);
	public static final Card h5 = factory().newCard(FIVE, HEARTS);
	public static final Card s5 = factory().newCard(FIVE, SPADES);
	public static final Card c6 = factory().newCard(SIX, CLUBS);
	public static final Card d6 = factory().newCard(SIX, DIAMONDS);
	public static final Card h6 = factory().newCard(SIX, HEARTS);
	public static final Card s6 = factory().newCard(SIX, SPADES);
	public static final Card c7 = factory().newCard(SEVEN, CLUBS);
	public static final Card d7 = factory().newCard(SEVEN, DIAMONDS);
	public static final Card h7 = factory().newCard(SEVEN, HEARTS);
	public static final Card s7 = factory().newCard(SEVEN, SPADES);
	public static final Card c8 = factory().newCard(EIGHT, CLUBS);
	public static final Card d8 = factory().newCard(EIGHT, DIAMONDS);
	public static final Card h8 = factory().newCard(EIGHT, HEARTS);
	public static final Card s8 = factory().newCard(EIGHT, SPADES);
	public static final Card c9 = factory().newCard(NINE, CLUBS);
	public static final Card d9 = factory().newCard(NINE, DIAMONDS);
	public static final Card h9 = factory().newCard(NINE, HEARTS);
	public static final Card s9 = factory().newCard(NINE, SPADES);
	public static final Card c10 = factory().newCard(TEN, CLUBS);
	public static final Card d10 = factory().newCard(TEN, DIAMONDS);
	public static final Card h10 = factory().newCard(TEN, HEARTS);
	public static final Card s10 = factory().newCard(TEN, SPADES);
	public static final Card c11 = factory().newCard(JACK, CLUBS);
	public static final Card d11 = factory().newCard(JACK, DIAMONDS);
	public static final Card h11 = factory().newCard(JACK, HEARTS);
	public static final Card s11 = factory().newCard(JACK, SPADES);
	public static final Card c12 = factory().newCard(QUEEN, CLUBS);
	public static final Card d12 = factory().newCard(QUEEN, DIAMONDS);
	public static final Card h12 = factory().newCard(QUEEN, HEARTS);
	public static final Card s12 = factory().newCard(QUEEN, SPADES);
	public static final Card c13 = factory().newCard(KING, CLUBS);
	public static final Card d13 = factory().newCard(KING, DIAMONDS);
	public static final Card h13 = factory().newCard(KING, HEARTS);
	public static final Card s13 = factory().newCard(KING, SPADES);

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
		building_12B1_s6PlusD6 = create.newMove(BUILD, factory().newAdd(s6, d6), s6).getPileCreated();
		bigTable = new LinkedHashSet<>(Arrays.<Pile> asList(h1, h2, h3, h4, building_12B1_s6PlusD6));
		create = new ExpressionFactory(bigTable, exampleHand, players[0]);

		discard = create.newMove(DISCARD, null, s1);
		build = create.newMove(BUILD, factory().newMultiply(h3, s4), s4);
		building = build.getPileCreated();

		equals = factory().newEquals(h1, c1);
	}

	public static final class NullRobot extends BaseRobot {
		@Override
		public Move takeTurn(Set<Pile> table, Set<Card> hand) {
			return null;
		}
	}
}
