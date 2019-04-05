package com.vizerium.barabanca.trade;

import com.vizerium.commons.indicators.MovingAverage;

public class EMA5x13CrossoverSLSlowMATest extends EMA5x13CrossoverTest {

	@Override
	protected MovingAverage getStopLossMA() {
		return getSlowMA();
	}

	@Override
	protected String getPreviousResultFileName() {
		return "5x13crossover_SL13";
	}
}
