package com.reubenpeeris.wippen.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reubenpeeris.wippen.expression.Building;
import com.reubenpeeris.wippen.expression.Card;
import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.robotloader.RobotLoaderManager;
import com.reubenpeeris.wippen.util.RoundIterable;

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
        final int sets = 100;

        System.out.println("Running");
        System.out.println(" Sets:    " + sets);
        System.out.println(" Players: " + players.size());

        Scorer scoreKeeper = new Scorer(players);

        //Match
        for (Player player : players) {
            player.startMatch(players, sets);
        }

        //Set
        long start = System.currentTimeMillis();
        for (int set = 0; set < sets; set++) {
            for (Player player : players) {
                player.startSet();
            }

            //Game
            for (Player firstPlayer : players) {
                Deck deck = Deck.newDeck(new Random(set));

                Collection<Pile> table = new ArrayList<Pile>();
                for (int i = 0; i < 4; i++) {
                    Card card = deck.nextCard();
                    table.add(card);
                }
                Collection<Pile> immutableTable = Collections.unmodifiableCollection(table);

                for (Player player : players) {
                    player.startGame(firstPlayer, immutableTable);
                }

                Player lastPlayerToTake = null;
                while (!deck.isEmpty()) {
                    //Deal
                    for (Player p : players) {
                        p.setHand(deck.nextCards(4));
                    }

                    while (!firstPlayer.isHandEmpty()) {
                        for (Player player : new RoundIterable<>(players, firstPlayer)) {
                            Move move = player.takeTurn(immutableTable);

                            if (logger.isDebugEnabled()) {
                                logger.debug(player.toString());
                                logger.debug("Table: {}", table);
                                logger.debug("Hand:  {}", player.getHand());
                                logger.debug("Move:  {}", move);
                            }

                            if (move == null) {
                                throw new IllegalStateException("Null Move returned by player: " + player);
                            }

                            //Inform players
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
                                lastPlayerToTake = player;
                                break;
                            default:
                                throw new IllegalStateException();
                            }
                        }
                    }
                }

                if (lastPlayerToTake != null) {
                    for (Pile pile : table) {
                        lastPlayerToTake.addToCapturedCards(pile.getCards());
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

        logger.info("Time: {}", (System.currentTimeMillis() - start));
        System.out.println(scoreKeeper);
    }
}
