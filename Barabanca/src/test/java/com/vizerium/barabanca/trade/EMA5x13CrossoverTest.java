package com.vizerium.barabanca.trade;

public abstract class EMA5x13CrossoverTest extends EMACrossoverTest {

	@Override
	public int getFastMA() {
		return 5;
	}

	@Override
	public int getSlowMA() {
		return 13;
	}
}
