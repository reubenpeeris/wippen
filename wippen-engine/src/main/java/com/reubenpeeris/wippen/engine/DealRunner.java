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
class DealRunner {
	@NonNull
	private final MoveRunner moveRunner;

	public void runDeal(@NonNull Set<Pile> table, @NonNull Iterable<Player> players, @NonNull ScoreKeeper scoreKeeper, @NonNull Deck deck) {
		log.debug("runDeal");
		
		Player firstPlayer = players.iterator().next();
	
		for (Player p : players) {
			p.addToHand(deck.nextCards(4));
		}

		while (!firstPlayer.isHandEmpty()) {
			for (Player player : players) {
				moveRunner.runMove(table, players, scoreKeeper, player);
			}
		}
	}
}
