package com.vizerium.barabanca.trade;

import com.vizerium.commons.indicators.MovingAverage;
import com.vizerium.commons.indicators.MovingAverageType;

public class ClosingPricesWithTrendCheckBy5EMATest extends ClosingPricesWithTrendCheckByEMATest {

	@Override
	protected MovingAverage getMovingAverage() {
		return new MovingAverage(5, MovingAverageType.EXPONENTIAL);
	}

	@Override
	protected String getPreviousResultFileName() {
		return "5ema_slope";
	}
}
