package com.reubenpeeris.wippen.engine;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public final class Score {
	private int matchPoints;
	private int setPoints;
	private int gamePoints;

	void incrementPoints(int points) {
		matchPoints += points;
		setPoints += points;
		gamePoints += points;
	}

	void startMatch() {
		matchPoints = 0;
		startSet();
	}

	void startSet() {
		setPoints = 0;
		startGame();
	}

	void startGame() {
		gamePoints = 0;
	}
}
