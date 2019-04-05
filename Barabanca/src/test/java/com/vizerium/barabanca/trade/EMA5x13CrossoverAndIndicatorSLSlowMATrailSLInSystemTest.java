package com.vizerium.barabanca.trade;

import org.junit.Before;

import com.vizerium.commons.indicators.MovingAverage;

public class EMA5x13CrossoverAndIndicatorSLSlowMATrailSLInSystemTest extends EMA5x13CrossoverAndIndicatorTest {

	@Before
	public void setUp() {
		super.setUp();
		setTrailingStopLossInSystem(true);
	}

	@Override
	protected MovingAverage getStopLossMA() {
		return getSlowMA();
	}

	@Override
	protected String getPreviousResultFileName() {
		return "5x13crossover_indicator_SL13_trail_SL_in_system";
	}
}
