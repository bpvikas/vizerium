package com.vizerium.barabanca.trade;

import com.vizerium.commons.indicators.MovingAverage;

public class ClosingPricesWithTrendCheckBy13EMATest extends ClosingPricesWithTrendCheckByEMATest {

	@Override
	protected MovingAverage getMovingAverage() {
		return MovingAverage._13;
	}
}
