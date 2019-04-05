package com.vizerium.barabanca.trade;

import com.vizerium.commons.indicators.MovingAverage;
import com.vizerium.commons.indicators.MovingAverageType;

public abstract class EMA5x13CrossoverTest extends EMACrossoverTest {

	private MovingAverage fastMA = new MovingAverage(5, MovingAverageType.EXPONENTIAL);

	private MovingAverage slowMA = new MovingAverage(13, MovingAverageType.EXPONENTIAL);

	@Override
	public MovingAverage getFastMA() {
		return fastMA;
	}

	@Override
	public MovingAverage getSlowMA() {
		return slowMA;
	}
}
