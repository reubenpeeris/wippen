package com.reubenpeeris.wippen;

import static com.reubenpeeris.wippen.expression.Move.Type.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.expression.Add;
import com.reubenpeeris.wippen.expression.Building;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Move.Type;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.expression.Rank;
import com.reubenpeeris.wippen.expression.Suit;
import com.reubenpeeris.wippen.robot.BaseRobot;
import com.reubenpeeris.wippen.robot.Robot;

public final class ObjectMother {
    public static final Card c1 = new Card(Suit.CLUB, new Rank(1));
    public static final Card d1 = new Card(Suit.DIAMOND, new Rank(1));
    public static final Card h1 = new Card(Suit.HEART, new Rank(1));
    public static final Card s1 = new Card(Suit.SPADE, new Rank(1));
    public static final Card c2 = new Card(Suit.CLUB, new Rank(2));
    public static final Card d2 = new Card(Suit.DIAMOND, new Rank(2));
    public static final Card h2 = new Card(Suit.HEART, new Rank(2));
    public static final Card s2 = new Card(Suit.SPADE, new Rank(2));
    public static final Card c3 = new Card(Suit.CLUB, new Rank(3));
    public static final Card d3 = new Card(Suit.DIAMOND, new Rank(3));
    public static final Card h3 = new Card(Suit.HEART, new Rank(3));
    public static final Card s3 = new Card(Suit.SPADE, new Rank(3));
    public static final Card c4 = new Card(Suit.CLUB, new Rank(4));
    public static final Card d4 = new Card(Suit.DIAMOND, new Rank(4));
    public static final Card h4 = new Card(Suit.HEART, new Rank(4));
    public static final Card s4 = new Card(Suit.SPADE, new Rank(4));
    public static final Card c5 = new Card(Suit.CLUB, new Rank(5));
    public static final Card d5 = new Card(Suit.DIAMOND, new Rank(5));
    public static final Card h5 = new Card(Suit.HEART, new Rank(5));
    public static final Card s5 = new Card(Suit.SPADE, new Rank(5));
    public static final Card c6 = new Card(Suit.CLUB, new Rank(6));
    public static final Card d6 = new Card(Suit.DIAMOND, new Rank(6));
    public static final Card h6 = new Card(Suit.HEART, new Rank(6));
    public static final Card s6 = new Card(Suit.SPADE, new Rank(6));
    public static final Card c7 = new Card(Suit.CLUB, new Rank(7));
    public static final Card d7 = new Card(Suit.DIAMOND, new Rank(7));
    public static final Card h7 = new Card(Suit.HEART, new Rank(7));
    public static final Card s7 = new Card(Suit.SPADE, new Rank(7));
    public static final Card c8 = new Card(Suit.CLUB, new Rank(8));
    public static final Card d8 = new Card(Suit.DIAMOND, new Rank(8));
    public static final Card h8 = new Card(Suit.HEART, new Rank(8));
    public static final Card s8 = new Card(Suit.SPADE, new Rank(8));
    public static final Card c9 = new Card(Suit.CLUB, new Rank(9));
    public static final Card d9 = new Card(Suit.DIAMOND, new Rank(9));
    public static final Card h9 = new Card(Suit.HEART, new Rank(9));
    public static final Card s9 = new Card(Suit.SPADE, new Rank(9));
    public static final Card c10 = new Card(Suit.CLUB, new Rank(10));
    public static final Card d10 = new Card(Suit.DIAMOND, new Rank(10));
    public static final Card h10 = new Card(Suit.HEART, new Rank(10));
    public static final Card s10 = new Card(Suit.SPADE, new Rank(10));
    public static final Card c11 = new Card(Suit.CLUB, new Rank(11));
    public static final Card d11 = new Card(Suit.DIAMOND, new Rank(11));
    public static final Card h11 = new Card(Suit.HEART, new Rank(11));
    public static final Card s11 = new Card(Suit.SPADE, new Rank(11));
    public static final Card c12 = new Card(Suit.CLUB, new Rank(12));
    public static final Card d12 = new Card(Suit.DIAMOND, new Rank(12));
    public static final Card h12 = new Card(Suit.HEART, new Rank(12));
    public static final Card s12 = new Card(Suit.SPADE, new Rank(12));
    public static final Card c13 = new Card(Suit.CLUB, new Rank(13));
    public static final Card d13 = new Card(Suit.DIAMOND, new Rank(13));
    public static final Card h13 = new Card(Suit.HEART, new Rank(13));
    public static final Card s13 = new Card(Suit.SPADE, new Rank(13));

    public static final Robot robot = new NullRobot();

    public static final class NullRobot extends BaseRobot {
        @Override
        public Move takeTurn(Collection<Pile> table, Collection<Card> hand) {
            return null;
        }
    }

    public static final Player player1 = new Player(1, robot);
    public static final Player player2 = new Player(2, robot);
    public static final Player player3 = new Player(3, robot);
    public static final Player player4 = new Player(4, robot);

    public static final Building building_12B1_s6PlusD6 = new Building(new Move(BUILD, new Add(s6, d6), s6), player1);

    public static final Move build = new Move(Type.BUILD, new Add(d7, s1), s1);
    public static final Building building = new Building(build, player1);

    public static final Move discard = new Move(Type.DISCARD, null, s7);

    public static final Set<Pile> emptyTable = Collections.emptySet();
	public static final Collection<Pile> bigTable = Collections.unmodifiableCollection(Arrays.<Pile> asList(h1, h2, h3, h4, building_12B1_s6PlusD6));
	public static final Collection<Card> exampleHand = Collections.unmodifiableCollection(Arrays.<Card> asList(s1, s3, s4, s12));
}
