package com.reubenpeeris.wippen.engine;

import java.util.Set;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reubenpeeris.wippen.expression.Pile;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
class GameRunner {
	@NonNull
	private final DealRunner dealRunner;

	public void runGame(@NonNull Set<Pile> table, @NonNull Iterable<Player> players, @NonNull ScoreKeeper scoreKeeper, @NonNull Deck deck) {
		if (!table.isEmpty()) {
			throw new IllegalArgumentException("Table is not empty");
		}
		if (!players.iterator().hasNext()) {
			throw new IllegalArgumentException("No players supplied");
		}
		
		log.debug("runGame");
		Player firstPlayer = players.iterator().next();

		table.addAll(deck.nextCards(4));

		for (Player player : players) {
			player.startGame(firstPlayer);
		}
		
		while (!deck.isEmpty()) {
			dealRunner.runDeal(table, players, scoreKeeper, deck);
		}

		scoreKeeper.calculateGameScores(table);

		for (Player player : players) {
			player.gameComplete(scoreKeeper.getScores());
		}
	}
}
