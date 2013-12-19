package com.reubenpeeris.wippen.engine;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reubenpeeris.wippen.expression.Pile;
import com.reubenpeeris.wippen.robot.Robot;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
class MatchRunner {
	@NonNull
	private final SetRunner setRunner;

	public ScoreKeeper runMatch(@NonNull List<Robot> robots, int firstSet, int sets) {
		if (sets < 0) {
			throw new IllegalArgumentException("Negative number of sets specified: " + sets);
		}
		List<Player> players = new ArrayList<>(robots.size());
		for (int i = 0; i < robots.size(); i++) {
			players.add(new Player(i, robots.get(i)));
		}

		ScoreKeeper scoreKeeper = new ScoreKeeper(players);
		Set<Pile> table = new LinkedHashSet<>();
		for (Player player : players) {
			player.startMatch(table, players, sets);
		}

		long start = System.currentTimeMillis();
		for (int set = 0; set < sets; set++) {
			setRunner.runSet(table, players, scoreKeeper, new Random(firstSet + set));
		}

		for (Player player : players) {
			player.matchComplete(scoreKeeper.getScores());
		}

		log.info("Time: {}", (System.currentTimeMillis() - start));
		return scoreKeeper;
	}
}
