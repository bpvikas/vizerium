package com.vizerium.barabanca.trend;

import com.vizerium.commons.indicators.MovingAverage;
import com.vizerium.commons.indicators.MovingAverageType;

public class EMA5SlopeTrendCheckTest extends EMASlopeTrendCheckTest {

	private MovingAverage ma = new MovingAverage(5, MovingAverageType.EXPONENTIAL);

	@Override
	protected MovingAverage getMovingAverage() {
		return ma;
	}
}
