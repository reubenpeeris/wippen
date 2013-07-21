package com.reubenpeeris.wippen.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.robotloader.RobotLoaderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wippen {
    private static final Logger logger = LoggerFactory.getLogger(Wippen.class);
    
	private final List<Player> players;
	
	private Wippen(List<Player> players) {
		this.players = players;
	}
	
	public static void main(String[] args) {
		//System.setSecurityManager(new SecurityManager());//Make sure people cannot get our privates
		//This should be more intelligent, to stop for example starting multiple threads too
		List<Player> players = createPlayers(args);
		
		Wippen wippen = new Wippen(players);
		wippen.run();
	}
	
	private static List<Player> createPlayers(String[] args) {
		if (args.length < 2 || args.length > 4) {
			throw new IllegalArgumentException("Must supply 2-4 robots to play");
		}
		
		List<Player> players = new ArrayList<Player>();
		
		try {
			Class.forName(com.reubenpeeris.wippen.robotloader.SpringRobotLoader.class.getName());
			Class.forName(com.reubenpeeris.wippen.robotloader.ConstructorRobotLoader.class.getName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		int position = 0;
		for (String arg : args) {
			players.add(new Player(position++, RobotLoaderManager.createInstance(arg)));
		}
		
		return players;
	}
	
	private void run() {
		System.out.println("running");
		
		final int rounds = 100;
		
		ScoreKeeper scoreKeeper = new ScoreKeeper(players);
		
		//Match start
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			p.matchStart(players.size(), rounds, i + 1);
		}
		
		//Start round
		long start = System.currentTimeMillis(); 
		for (int round = 0; round < rounds; round++) {
		
			//Must be same number of Games started by each player
			for (int firstPlayer = 0; firstPlayer < players.size(); firstPlayer++) {
				//Game start
				scoreKeeper.startNewGame();
				Deck deck = Deck.newDeck(new Random(round));
				
				for (Player p : players) {
					p.gameStart(firstPlayer);
				}
				
				Collection<Pile> table = new ArrayList<Pile>();
				
				for (int i = 0; i < 4; i++) {
					Card card = deck.nextCard();
					
					table.add(card);
				}
				
				Player lastPlayerToTake = null;
				while (!deck.isEmpty()) {
					//Hand start
					for (Player p : players) {
						p.setHand(deck.nextCards(4));
					}
					
					while (!players.get(firstPlayer).hasEmptyHand()) {
						for (int position = 0; position < players.size(); position++) {
							Player player = players.get((position + firstPlayer) % players.size());
							
							Expression expression = player.takeTurn(table);
							//Validate move
							try {
								Move move = ActionVerifier.verifyAction(expression, player.getPosition(), table, player.getHand());
							
								if (logger.isDebugEnabled()) {
									logger.debug(player.toString());
									logger.debug("Table: {}", table);
									logger.debug("Hand:  {}", player.getHand());
									logger.debug("Move:  {}", move);
								}
								
								//Inform players
								for (Player observer : players) {
									if (!observer.equals(player)) {
										observer.turnPlayed(player.getPosition(), table, move.getExpression());
									}
								}
								
								player.removeFromHand(move.getHandCardUsed());
								table.removeAll(move.getTablePilesUsed());
								switch (move.getType()) {
									case BUILD: 
									case DISCARD:
										table.add(move.getPileGenerated());
										break;
									case CAPTURE:
										player.addCardsToWinnings(move.getCardsUsed());
										if (table.size() == 0) {
											scoreKeeper.addClearUp(player);
										}
										lastPlayerToTake = player;
										break;
									default: throw new IllegalStateException();
								}
							} catch (ParseException e) {
								throw new RuntimeException("Invalid move from player: '" + player + "' with expression: '" + expression + "'", e);
							}
						}
					}
				}
				
				if (lastPlayerToTake != null) {
					for (Pile pile : table) {
						lastPlayerToTake.addCardsToWinnings(pile.getCards());
					}
				}
				
				scoreKeeper.calculateGameScores();
			}
		}

		logger.info("Time: {}", (System.currentTimeMillis() - start));
		logger.info(scoreKeeper.toString());
	}
}
