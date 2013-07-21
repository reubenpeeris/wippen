package com.reubenpeeris.wippen.robot;

import java.util.Collection;

import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.Pile;

public abstract class BaseRobot implements Robot {
	private int position = -1;

	@Override
	public String matchStart(int players, int rounds, int position) {
		this.position = position;
		return toString();
	}
	
	protected int getPosition() {
		if (position == -1) {
			throw new IllegalStateException("Requesting position before it has been initialised");
		}
		
		return position;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	//No-op implementations
	@Override
	public void gameComplete(String scores, String totals) {
	}

	@Override
	public void gameStart(int first) {
	}

	@Override
	public void turnPlayed(int player, Collection<Pile> table, Expression expression) {
	}
}
