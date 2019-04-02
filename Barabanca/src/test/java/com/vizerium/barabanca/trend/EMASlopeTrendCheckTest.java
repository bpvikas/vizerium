package com.vizerium.barabanca.trend;

import org.junit.Assert;
import org.junit.Before;

import com.vizerium.commons.indicators.MovingAverage;
import com.vizerium.commons.indicators.MovingAverageType;

public class EMASlopeTrendCheckTest extends IndicatorTrendCheckTest<MovingAverage> {

	@Before
	public void setUp() throws Exception {
		unit = new EMASlopeTrendCheck(new MovingAverage(13, MovingAverageType.EXPONENTIAL));
	}

	@Override
	public void testGetIndicator() {
		// Thanks to type erasure which prevents us from knowing the Class<I>, we need to have this class in
		// the subclass of IndicatorTrendCheckTest rather than the parent
		// https://stackoverflow.com/questions/3437897/how-to-get-a-class-instance-of-generics-type-t
		Assert.assertEquals(MovingAverage.class, unit.getIndicator().getClass());
	}

	@Override
	public IndicatorTrendCheck<MovingAverage> getUnit() {
		return unit;
	}
}
