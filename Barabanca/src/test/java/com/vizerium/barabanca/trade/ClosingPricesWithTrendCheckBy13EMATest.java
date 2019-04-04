package com.vizerium.barabanca.trade;

import com.vizerium.commons.indicators.MovingAverage;
import com.vizerium.commons.indicators.MovingAverageType;

public class ClosingPricesWithTrendCheckBy13EMATest extends ClosingPricesWithTrendCheckByEMATest {

	@Override
	protected MovingAverage getMovingAverage() {
		return new MovingAverage(13, MovingAverageType.EXPONENTIAL);
	}

	@Override
	protected String getPreviousResultFileName() {
		return "testrun_13ema_slope.csv";
	}
}
