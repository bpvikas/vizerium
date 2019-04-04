package com.vizerium.barabanca.trade;

public class EMA5x13CrossoverSLFastMATest extends EMA5x13CrossoverTest {

	@Override
	protected int getStopLossMA() {
		return getFastMA();
	}

	@Override
	protected String getPreviousResultFileName() {
		return "testrun_5x13crossover_SL5.csv";
	}
}
