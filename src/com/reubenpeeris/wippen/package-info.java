/**
 * Wippen is a Dutch fishing card game. This package contains a text based game engine that understands the rules and game play of Wippen.
 * The players of the game must implement the {@link com.reubenpeeris.wippen.robot.Robot} interface.
 * 
 *  <h2>Wippen Rules</h2>
 *  <p>
 *  The games is for 2 to 4 players. Four players can play as single players, or in fixed partnership, partners sitting opposite (<strong>this is not yet
 *  implemented</strong>).
 *  </p><p>
 *  A standard 52 card deck is used. During the play of the game, each card has the value of its rank, including the Jack, Queen and
 *  King, i.e. Ace = 1, Two = 2... Ten = 10, Jack = 11, Queen = 12 and King = 13. The aim is to capture cards from the layout on the table
 *  by using a card from the hand. Captured cards are collected by each team and contribute towards scoring. 
 *  </p>
 *  
 *  <h3>The Deal</h3>
 *  The dealer deals four cards to each player, two at a time, and, in the first deal, four cards face up to the table. In subsequent deals,
 *  the dealer replenishes the players' hands, but not the table. The deal rotates only at the end of a complete round, when the deck has
 *  been exhausted. A game consists of one round dealt per player.
 *  
 *  <h3>The Play</h3>
 *  Beginning with the player to the dealer's left, each player plays one card at a time, performing one of the following actions:
 *  <ul>
 *   <li>Discard: a card is discarded face up to the table from the players hand</li>
 *   <li>Capture: one or more cards are taken from the table using a suitable value card from the players hand</li>
 *   <li>Build: one or more cards from the table are combined with a card from the players hand to build a new pile on the table that has a specified value</li>
 *   </ul>
 *  When all cards from the deck have been exhausted, if any cards remain on the table, they are captured by the last player to have captured cards from the table.
 *  
 *  <h4>Capture</h4>
 *  -simple
 *  -equality
 *  -dual
 *  <h4>Build</h4>
 *  -simple
 *  -dual
 *  -overbuild
 *  -rebuild (to same value only - in a real life version of the game, if they could build to a new value, people would have to keep track of all the values they promised to have)
 *  
 *  <h3>Scoring</h3>
 *  At the end of a round the cards captured by each team are used to award points. The following criterion are used:
 *  <ul>
 *   <li><strong>Highest number of cards</strong>: 2 points (shared if 2 teams draw, not awarded if 3 or more teams draw)<strong>not implemented
 *   like this now!</strong></li>
 *   <li><strong>Highest number of spades:</strong> 2 points (shared if 2 teams draw, not awarded if 3 or more teams draw)<strong>not implemented
 *   like this now!</strong>
 *   <li><strong>10\u2666:</strong> 2 points</li>
 *   <li><strong>2\u2660:</strong> 1 point</li>
 *   <li><strong>Ace</strong>1 point each (i.e. 4 points total)</li>
 *  </ul>
 *  
 *  <h2>Sweep</h2>
 *  A sweep is declared if a player manages to capture all cards from the layout. For every sweep one bonus point is awarded.
 *  
 */
package com.reubenpeeris.wippen;