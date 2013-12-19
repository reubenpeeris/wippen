package com.reubenpeeris.wippen.engine;

import java.util.List;
import java.util.Random;
import java.util.Set;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.util.RoundIterable;

@Component
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
class SetRunner {
	@NonNull
	private final GameRunner gameRunner;

	public void runSet(@NonNull Set<Pile> table, @NonNull List<Player> players, @NonNull ScoreKeeper scoreKeeper, @NonNull Random shuffler) {
		for (Player player : players) {
			player.startSet();
		}

		for (int i = 0; i < players.size(); i++) {
			gameRunner.runGame(table, new RoundIterable<>(players, i), scoreKeeper, Deck.newDeck(shuffler));
		}

		for (Player player : players) {
			player.setComplete(scoreKeeper.getScores());
		}
	}
}
