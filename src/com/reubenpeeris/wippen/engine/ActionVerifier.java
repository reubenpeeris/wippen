package com.reubenpeeris.wippen.engine;

import static com.reubenpeeris.wippen.engine.Move.Type.BUILD;
import static com.reubenpeeris.wippen.engine.Move.Type.DISCARD;
import static com.reubenpeeris.wippen.engine.Move.Type.CAPTURE;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.reubenpeeris.wippen.engine.Move.Type;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Equals;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.expression.Rank;

public class ActionVerifier {
	private ActionVerifier() {}
	
	public static Move verifyAction(Expression expression, int playerPosition, Collection<Pile> table, Collection<Card> hand) throws ParseException {
		Set<Pile> tablePilesUsed = new HashSet<Pile>();
		Card handCardUsed = null;
		Set<Rank> ownBuildingsUsed = new HashSet<Rank>();
		
		final int value = expression.getValue();
		
		pile:
		for (Pile pile : expression.getPiles()) {
			if (pile.getCards().size() == 0) {
				//Is a Building, so must be on table
				for (Pile tablePile: table) {
					if (tablePile.getCards().size() > 1 && tablePile.getRank().equals(pile.getRank())) {
						if (!tablePilesUsed.add(tablePile)) {
							throw new WippenRuleException("Trying to use Pile multiple times in expression: " + expression);
						}
						
						if (tablePile.wasBuiltByPlayerInPosion(playerPosition)) {
							ownBuildingsUsed.add(tablePile.getRank());
						}
						continue pile;
					}
				}
			} else {
				if (table.contains(pile)) {
					if (!tablePilesUsed.add(pile)) {
						throw new WippenRuleException("Trying to use Pile multiple times in expression: " + expression);
					}
					continue pile;
				}
				
				if (hand.contains(pile)) {
					if (handCardUsed != null) {
						throw new WippenRuleException("Trying to use multiple cards from hand in expression: " + expression);
					}
					handCardUsed = (Card)pile;
					
					continue pile;
				}
				
				throw new WippenRuleException("Pile not found on table or in hand in expression: " + expression);
			}
		}
		
		if (handCardUsed == null) {
			throw new WippenRuleException("No card from hand used in expression: " + expression);
		}
		
		final Type type = determineType(expression, handCardUsed);
		
		if (type == CAPTURE) {
			//Will consume any Buildings of the same value captured
			//TODO should not capture buildings!
			for (Pile tablePile : table) {
				if (tablePile.getCards().size() > 1 &&
						handCardUsed.getRank().equals(tablePile.getRank())) {
					tablePilesUsed.add(tablePile);
				}
			}
		} else if (type == BUILD) {
			//Will consume any Piles of the same value built 
			for (Pile tablePile : table) {
				if (value == tablePile.getRank().getValue()) {
					tablePilesUsed.add(tablePile);
				}
			}
		}
		
		//If building check that player has card they claim to
		if (type == BUILD) {
			boolean validBuild = false;
			
			card:
			for (Card card : hand) {
				if (!card.equals(handCardUsed) && card.getRank().getValue() == value) {
					validBuild = true;
					break card;
				}
			}
			
			if (!validBuild) {
				throw new WippenRuleException("Trying to build for a value not contained in hand in expression: " + expression);
			}
		}
		
		//Check for rebuilding to new value which is illegal
		if (type == BUILD) {
			for (Rank ownBuildingValue : ownBuildingsUsed) {
				if (ownBuildingValue.getValue() != value) {
					throw new WippenRuleException("Trying to build to new value using own building in expression: " + expression);
				}
			}
		}
		
		//Check for non-rebuild/build capture with own build value
		for (Pile p : table) {
			if (p.wasBuiltByPlayerInPosion(playerPosition)
					&& handCardUsed.getValue() == p.getValue()
					&& !tablePilesUsed.contains(p)) {
				throw new WippenRuleException("Trying to use promised card without consuming pile: " + expression);
			}
		}
		
		return new Move(expression, playerPosition, type, tablePilesUsed, handCardUsed);
	}
	
	private static Type determineType(Expression expression, Card handCardUsed) {
		if (expression.getPiles().size() == 1) {
			return DISCARD;
		}
		
		// The first card returned by getPiles() will be the first card in the expression
		if (expression.getValue() == handCardUsed.getRank().getValue()
				&& expression.getPiles().iterator().next().equals(handCardUsed)
				&& (expression.getClass().equals(Equals.class)
						|| expression instanceof Pile)) {
			return CAPTURE;
		} else {
			return BUILD;
		}
	}
}
