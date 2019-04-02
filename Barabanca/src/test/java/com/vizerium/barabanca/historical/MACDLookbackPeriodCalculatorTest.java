package com.vizerium.barabanca.historical;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.vizerium.commons.indicators.MACD;
import com.vizerium.commons.indicators.MovingAverageType;

public class MACDLookbackPeriodCalculatorTest {

	private LookbackPeriodCalculator<MACD> unit;

	@Before
	public void setUp() throws Exception {
		unit = new LookbackPeriodCalculator<MACD>();
	}

	@Test
	public void testGetLookbackPeriodForIndicator() {
		MovingAverageType EMA = MovingAverageType.EXPONENTIAL;
		Assert.assertEquals(35, unit.getLookbackPeriodForIndicator(new MACD()));
		Assert.assertEquals(22, unit.getLookbackPeriodForIndicator(new MACD(5, 13, EMA, EMA, 9)));
		Assert.assertEquals(35, unit.getLookbackPeriodForIndicator(new MACD(12, 26, EMA, EMA, 9)));
		Assert.assertEquals(209, unit.getLookbackPeriodForIndicator(new MACD(50, 200, EMA, EMA, 9)));
	}

	@Test
	public void testGetUnitPricesIncludingLookbackPeriodWithTimeFormatStringTimeFormatListOfUnitPriceDataI() {
		unit.getUnitPricesIncludingLookbackPeriodWithTimeFormat(null, null, null, null);
	}
}
