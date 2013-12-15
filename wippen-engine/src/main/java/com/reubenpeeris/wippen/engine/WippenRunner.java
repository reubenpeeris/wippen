package com.reubenpeeris.wippen.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

import com.reubenpeeris.wippen.expression.Building;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.robot.Robot;
import com.reubenpeeris.wippen.util.RoundIterable;

@Slf4j
public class WippenRunner {
	public void playMatch(List<Robot> robots, int firstGame, int sets) {
		List<Player> players = new ArrayList<>(robots.size());
		for (int i = 0; i < robots.size(); i++) {
			players.add(new Player(i, robots.get(i)));
		}

		System.out.println("Running");
		System.out.println(" Sets:       " + sets);
		System.out.println(" First game: " + firstGame);
		System.out.println(" Players:    " + players.size());

		Scorer scoreKeeper = new Scorer(players);

		// Match
		for (Player player : players) {
			player.startMatch(players, sets);
		}

		// Set
		long start = System.currentTimeMillis();
		for (int set = 0; set < sets; set++) {
			for (Player player : players) {
				player.startSet();
			}

			// Game
			for (Player firstPlayer : players) {
				Deck deck = Deck.newDeck(new Random(firstGame + set));

				Collection<Pile> table = new ArrayList<>();
				for (int i = 0; i < 4; i++) {
					Card card = deck.nextCard();
					table.add(card);
				}
				Collection<Pile> immutableTable = Collections.unmodifiableCollection(table);

				for (Player player : players) {
					player.startGame(firstPlayer, immutableTable);
				}

				Player lastPlayerToCapture = null;
				while (!deck.isEmpty()) {
					// Deal
					for (Player p : players) {
						p.setHand(deck.nextCards(4));
					}

					while (!firstPlayer.isHandEmpty()) {
						for (Player player : new RoundIterable<>(players, firstPlayer)) {
							Move move = player.takeTurn(immutableTable);

							log.debug("Player: {}", player);
							log.debug("Table: {}", table);
							log.debug("Hand:  {}", player.getHand());
							log.debug("Move:  {}", move);

							if (move == null) {
								throw new IllegalStateException("Null Move returned by player: " + player);
							}

							// Inform players
							for (Player observer : players) {
								if (!observer.equals(player)) {
									observer.turnPlayed(player, immutableTable, move);
								}
							}

							player.removeFromHand(move.getHandCard());
							table.removeAll(move.getTablePilesUsed());
							switch (move.getType()) {
							case BUILD:
								table.add(new Building(move, player));
								break;
							case DISCARD:
								table.add(move.getHandCard());
								break;
							case CAPTURE:
								player.addToCapturedCards(move.getCards());
								if (table.isEmpty()) {
									player.addSweep();
								}
								lastPlayerToCapture = player;
								break;
							default:
								throw new IllegalStateException();
							}
						}
					}
				}

				if (lastPlayerToCapture != null) {
					for (Pile pile : table) {
						lastPlayerToCapture.addToCapturedCards(pile.getCards());
					}
				}

				scoreKeeper.calculateGameScores();

				for (Player player : players) {
					player.gameComplete(scoreKeeper.getScores());
				}
			}

			for (Player player : players) {
				player.setComplete(scoreKeeper.getScores());
			}
		}

		for (Player player : players) {
			player.matchComplete(scoreKeeper.getScores());
		}

		log.info("Time: {}", (System.currentTimeMillis() - start));
		System.out.println(scoreKeeper);
	}
}
