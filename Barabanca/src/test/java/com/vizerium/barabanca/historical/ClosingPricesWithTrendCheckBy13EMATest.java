package com.vizerium.barabanca.historical;

import com.vizerium.commons.calculators.MovingAverage;

public class ClosingPricesWithTrendCheckBy13EMATest extends ClosingPricesWithTrendCheckByEMATest {

	@Override
	protected MovingAverage getMovingAverage() {
		return MovingAverage._13;
	}
}
