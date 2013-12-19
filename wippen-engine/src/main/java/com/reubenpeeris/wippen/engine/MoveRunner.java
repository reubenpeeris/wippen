package com.reubenpeeris.wippen.engine;

import static com.reubenpeeris.wippen.expression.Move.Type.*;

import java.util.Set;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.reubenpeeris.wippen.expression.Move;
import com.reubenpeeris.wippen.expression.Pile;

@Slf4j
@Component
class MoveRunner {
	public void runMove(@NonNull Set<Pile> table, @NonNull Iterable<Player> players, @NonNull ScoreKeeper scoreKeeper, @NonNull Player player) {
		Move move = player.takeTurn();

		log.debug("Player: {}", player);
		log.debug("Table:  {}", table);
		log.debug("Hand:   {}", player.getHand());
		log.debug("Move:   {}", move);

		if (move == null) {
			throw new WippenRuleException("Null Move returned by player: " + player);
		} else if (!move.isSound(table, player.getHand(), player)) {
			throw new WippenRuleException("Invalid Move returned by player: " + player);
		}

		for (Player observer : players) {
			if (!observer.equals(player)) {
				observer.turnPlayed(player, move);
			}
		}

		player.removeFromHand(move.getHandCard());
		table.removeAll(move.getTablePilesUsed());
		if (move.getPileCreated() != null) {
			table.add(move.getPileCreated());
		}

		if (move.getType() == CAPTURE) {
			player.addToCapturedCards(move.getCards());
			scoreKeeper.playerCaptured(player);
			if (table.isEmpty()) {
				player.addSweep();
			}
		}
	}
}
