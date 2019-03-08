package com.vizerium.barabanca.trade;

import com.vizerium.commons.calculators.MovingAverage;

public class EMA5x13CrossoverTest extends EMACrossoverTest {

	@Override
	public int getFastMA() {
		return MovingAverage._5.getNumber();
	}

	@Override
	public int getSlowMA() {
		return MovingAverage._13.getNumber();
	}
}
