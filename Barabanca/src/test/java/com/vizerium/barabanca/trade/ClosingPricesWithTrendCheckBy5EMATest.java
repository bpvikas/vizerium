package com.vizerium.barabanca.trade;

import com.vizerium.commons.calculators.MovingAverage;

public class ClosingPricesWithTrendCheckBy5EMATest extends ClosingPricesWithTrendCheckByEMATest {

	@Override
	protected MovingAverage getMovingAverage() {
		return MovingAverage._5;
	}
}
