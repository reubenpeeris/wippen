/**
 * Wippen is a Dutch fishing card game. This package contains a text based game engine that understands the rules and game play of Wippen.
 * To play the game you must implement the {@link org.homeftp.reubenpeeris.wippen.robot.Robot} interface.
 * 
 *  <h2>Wippen Rules</h2>
 *  <p>
 *  The games is for 2 to 4 players.
 *  </p>
 *  <p>
 *  A standard 52 card deck is used. The aim is to capture cards from the table by using a card from the hand. Captured cards are
 *  collected by each player and contribute towards their score.
 *  </p>
 * 
 *  <h3>The Deal</h3>
 *  At the start of the game, the dealer deals 2 face-down cards at a time to each of the other players (in a clockwise direction), then
 *  places 2 face-up cards on the table, then finally 2 face-down cards to himself. This is repeated a second time, so that each player
 *  receives 4 cards. In subsequent deals, the dealer replenishes the players' hands, but does not deal any cards to the table. The deal
 *  rotates clockwise at the end of a complete round, when the deck has been exhausted. A game consists of one round dealt per player.
 * 
 *  <h3>The Play</h3>
 *  Beginning with the player to the dealer's left, each player plays one card at a time, performing one of the following actions:
 *  <ul>
 *   <li>Discard: a card is discarded face up to the table from the players hand</li>
 *   <li>Capture: one or more cards are taken from the table using a suitable value card from the players hand</li>
 *   <li>Build: one or more cards from the table are combined with a card from the players hand to build a new pile on the table that has a specified value</li>
 *   </ul>
 *  <p>
 *  When all cards from the deck have been exhausted, if any cards remain on the table, they are captured by the last player to have captured cards from the table.
 *  </p>
 *  <p>
 *  When capturing or building, the cards are used to form a mathematical expression. The rank of each card represents its mathematical value,
 *  including the Jack, Queen and King, i.e. Ace = 1, Two = 2... Ten = 10, Jack = 11, Queen = 12 and King = 13. The valid mathematical operators
 *  which can be used are add (+), subtract (-), multiple (*) and divide (/) (implicitly parenthesis are allowed too where required).
 *  </p>
 * 
 *  <h4>Capture</h4>
 *  A capture is performed by making a mathematical expression from the table cards that is equal to a card from the hand. E.g.
 *  <ul>
 *   <li>Equal cards<br/>
 *    E.g. 3S=3C: 3S is captured from the table using the 3C from the hand.
 *   </li>
 *   <li>Basic arithmetic<br/>
 *    E.g. (3S*2D)+4C=10H: 3S, 2D and 5C captured from the table using the 10H from the hand.
 *   </li>
 *   <li>Multi-pickup<br/>
 *    E.g. 3C+2D=5D=5S: 3C, 2D and 5D are captured from the table using the 5S from the hand.
 *   </li>
 *  </ul>
 * 
 *  <h4>Build</h4>
 *  A build is performed by making a mathematical expression from one or more cards on the table with one card from the hand
 *  that is equal in value to another card in the hand. Effectively a player that builds in promising that they have a card of
 *  that value, but they are not obliged to show it.
 *  <ul>
 *   <li>Simple<br/>
 *   </li>
 *   <li>Dual<br/>
 *   </li>
 *   <li>Overbuild<br/>
 *   </li>
 *   <li>Rebuild<br/>
 *    A player cannot use a pile they have built in a new building of a new value. They can however use it in a mathematical expression
 *    to build a new pile of the same value.<br/>
 *    E.g. 12H-6B:
 *   </li>
 *  </ul>
 * 
 *  <h3>Scoring</h3>
 *  At the end of a round the cards captured by each player are used to award points. The following criteria are used:
 *  <ul>
 *   <li><strong>Greatest number of cards</strong> 2 points<sup>*</sup></li>
 *   <li><strong>Greatest number of spades</strong> 2 points<sup>*</sup></li>
 *   <li><strong>10D</strong> 2 points</li>
 *   <li><strong>2S</strong> 1 point</li>
 *   <li><strong>Ace</strong> 1 point each (i.e. 4 points total)</li>
 *  </ul>
 * 
 *  <p>
 *   <sup>*</sup> If 2 players have the equal largest number of cards, then each will be awarded 1 point. If 3 or more players have the equal largest number of cards, no one is awarded points.
 *  </p>
 * 
 *  <h2>Sweep</h2>
 *  A sweep is declared if a player manages to capture all cards from the layout. For every sweep one bonus point is awarded.
 * 
 */
package com.reubenpeeris.wippen;