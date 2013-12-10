package com.reubenpeeris.wippen.expression;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.expression.Move.Type;
import com.reubenpeeris.wippen.robot.BaseRobot;

import static com.reubenpeeris.wippen.Cards.*;
import static com.reubenpeeris.wippen.expression.Move.Type.*;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.*;

public class MoveTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Player player;
    private Building existingBuilding;

    @Before
    public void setUp() {
        player = new Player(1, new BaseRobot(){
            @Override
            public Move takeTurn(Collection<Pile> table, Collection<Card> hand) {
                return null;
            }
        });
        existingBuilding = new Building(new Move(BUILD, c2, new Multiply(c2, c4)), player);
    }

    @Test
    public void constructWithNullTypeThrows() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("type");

        new Move(null, c1, c1);
    }

    @Test
    public void constructWithNullHandCard() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("handCard");

        new Move(DISCARD, null, c1);
    }

    @Test
    public void constructDiscard() {
        Card card = c1;
        Move move = new Move(DISCARD, card, null);
        assertThat(move.getCards(), contains(card));
        assertThat(move.getHandCard(), is(equalTo(card)));
        assertThat(move.getPiles(), contains((Pile)card));
        assertThat(move.getType(), is(equalTo(DISCARD)));
        assertThat(move.getValue(), is(equalTo(card.getValue())));
        assertThat(move.isPileGenerated(), is(true));
        assertThat(move.toString(), is(equalTo("DISCARD:1C")));
    }

    @Test
    public void constructDiscardWithNonNullExpressionThrows() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("For DISCARD type expression must be null");

        new Move(DISCARD, c1, d1);
    }

    @Test
    public void constructCapture() {
        Card handCard = c8;
        Card[] tableCards = {c1, c7};
        Expression expression = new Add(tableCards[0], tableCards[1]);
        Move move = new Move(CAPTURE, handCard, expression);
        assertThat(move.getCards(), contains(handCard, tableCards[0], tableCards[1]));
        assertThat(move.getHandCard(), is(equalTo(handCard)));
        assertThat(move.getPiles(), contains((Pile)handCard, tableCards[0], tableCards[1]));
        assertThat(move.getType(), is(equalTo(CAPTURE)));
        assertThat(move.getValue(), is(equalTo(handCard.getValue())));
        assertThat(move.isPileGenerated(), is(false));
        assertThat(move.toString(), is(equalTo("CAPTURE:8C=(1C+7C)")));
    }

    @Test
    public void constructCaptureWithNullExpressionThrows() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("For CAPTURE type hand card must have same value as expression");

        new Move(CAPTURE, c1, null);
    }

    @Test
    public void constructCaptureWithHandCardNotEqualToExpressionThrows() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("For CAPTURE type hand card must have same value as expression");

        new Move(CAPTURE, c1, c2);
    }

    @Test
    public void constructBuild() {
        Card handCard = c8;
        Card tableCard = c7;
        Expression expression = new Subtract(handCard, tableCard);
        Move move = new Move(BUILD, handCard, expression);
        assertThat(move.getCards(), contains(handCard, tableCard));
        assertThat(move.getHandCard(), is(equalTo(handCard)));
        assertThat(move.getPiles(), contains((Pile)handCard, tableCard));
        assertThat(move.getType(), is(equalTo(BUILD)));
        assertThat(move.getValue(), is(equalTo(expression.getValue())));
        assertThat(move.isPileGenerated(), is(true));
        assertThat(move.toString(), is(equalTo("BUILD:(8C-7C)")));
    }

    @Test
    public void constructBuildWithNullExpressionThrows() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("For BUILD type expression must contain handCard");

        new Move(BUILD, c1, null);
    }

    @Test
    public void constructBuildWithExpressionThatDoesNotContainHandCardThrows() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("For BUILD type expression must contain handCard");

        new Move(BUILD, c1, c2);
    }

    @Test
    public void constructWithDuplicateCardThrows() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Trying to use card multiple times");

        new Move(CAPTURE, c1, new Divide(c10, c10));
    }

    @Test
    public void normalCaptureIsValid() {
        Move move = new Move(CAPTURE, c1, d1);
        assertThat(move.isValidFor(Arrays.<Pile>asList(d1), Arrays.asList(c1), player), is(true));
    }

    @Test
    public void normalBuildIsValid() {
        Move move = new Move(BUILD, c1, new Add(c1, d1));
        assertThat(move.isValidFor(Arrays.<Pile>asList(d1), Arrays.asList(c1), player), is(true));
    }

    @Test
    public void buildToSameValueAsOwnBuildingIsValid() {
        Move move = new Move(BUILD, c1, new Equals(existingBuilding, new Add(c1, d7)));
        assertThat(move.isValidFor(Arrays.<Pile>asList(d7, existingBuilding), Arrays.asList(c1), player), is(true));
    }

    @Test
    public void isNotValidWhenHandCardIsNotInHand() {
        Move move = new Move(CAPTURE, c1, d1);
        assertThat(move.isValidFor(Arrays.<Pile>asList(d1), Arrays.asList(c2), player), is(false));
    }

    @Test
    public void isNotValidWhenTableCardIsNotOnTable() {
        Move move = new Move(CAPTURE, c1, d1);
        assertThat(move.isValidFor(Arrays.<Pile>asList(d2), Arrays.asList(c1), player), is(false));
    }

    @Test
    public void isNotValidWhenBuildingForNewValueUsingOwnBuilding() {
        Move move = new Move(BUILD, d2, new Add(d2, existingBuilding));
        assertThat(move.isValidFor(Arrays.<Pile>asList(existingBuilding), Arrays.asList(d2), player), is(false));
    }

    @Test
    public void createReturnsCardForValidArguments() {
        Move move = Move.create(DISCARD, c1, null, Collections.<Pile> emptySet(), Collections.singleton(c1), player);
        assertThat(move, is(notNullValue()));
    }

    @Test
    public void createThrowsForNullType() {
        assertExceptionForCreate(null, c1, null, Collections.<Pile> emptySet(), Collections.singleton(c1), player, "type");
    }

    @Test
    public void createThrowsForNullHandCard() {
        assertExceptionForCreate(DISCARD, null, null, Collections.<Pile> emptySet(), Collections.singleton(c1), player, "handCard");
    }

    @Test
    public void createThrowsForNullTable() {
        assertExceptionForCreate(DISCARD, c1, null, null, Collections.singleton(c1), player, "table");
    }

    @Test
    public void createThrowsForNullHand() {
        assertExceptionForCreate(DISCARD, c1, null, Collections.<Pile> emptySet(), null, player, "hand");
    }

    @Test
    public void createThrowsForNullPlayer() {
        assertExceptionForCreate(DISCARD, c1, null, Collections.<Pile> emptySet(), Collections.singleton(c1), null, "player");
    }

    @Test
    public void createReturnsNullWhenExpressionIsNotValid() {
        Move move = Move.create(DISCARD, c1, c2, Collections.<Pile> emptySet(), Collections.singleton(c1), player);
        assertThat(move, is(nullValue()));
    }

    @Test
    public void createReturnsNullWhenCardNotInHand() {
        Move move = Move.create(DISCARD, c1, null, Collections.<Pile> emptySet(), Collections.singleton(c2), player);
        assertThat(move, is(nullValue()));
    }

    private void assertExceptionForCreate(Type type, Card handCard, Expression expression, Collection<Pile> table, Collection<Card> hand,
            Player player, String message) {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(message);

        Move.create(type, handCard, expression, table, hand, player);
    }
}
