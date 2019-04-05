package com.vizerium.barabanca.trade;

import com.vizerium.commons.indicators.MovingAverage;

public class EMA5x13CrossoverAndIndicatorSLFastMATest extends EMA5x13CrossoverAndIndicatorTest {

	@Override
	protected MovingAverage getStopLossMA() {
		return getFastMA();
	}

	@Override
	protected String getPreviousResultFileName() {
		return "5x13crossover_indicator_SL5";
	}
}
