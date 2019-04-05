package com.vizerium.barabanca.trade;

import com.vizerium.commons.indicators.MovingAverage;

public class EMA5x13CrossoverAndIndicatorSLSlowMATest extends EMA5x13CrossoverAndIndicatorTest {

	@Override
	protected MovingAverage getStopLossMA() {
		return getSlowMA();
	}

	@Override
	protected String getPreviousResultFileName() {
		return "5x13crossover_indicator_SL13";
	}
}
