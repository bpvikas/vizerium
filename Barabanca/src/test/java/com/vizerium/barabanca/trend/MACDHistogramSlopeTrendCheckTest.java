package com.vizerium.barabanca.trend;

import org.junit.Assert;
import org.junit.Before;

import com.vizerium.commons.indicators.MACD;

public class MACDHistogramSlopeTrendCheckTest extends IndicatorTrendCheckTest<MACD> {

	@Before
	public void setUp() throws Exception {
		unit = new MACDHistogramSlopeTrendCheck(new MACD());
	}

	@Override
	public void testGetIndicator() {
		// Thanks to type erasure which prevents us from knowing the Class<I>, we need to have this class in
		// the subclass of IndicatorTrendCheckTest rather than the parent
		// https://stackoverflow.com/questions/3437897/how-to-get-a-class-instance-of-generics-type-t
		Assert.assertEquals(MACD.class, unit.getIndicator().getClass());
	}

	@Override
	public IndicatorTrendCheck<MACD> getUnit() {
		return unit;
	}
}
