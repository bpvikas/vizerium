package com.vizerium.barabanca.trade;

import org.junit.Before;

import com.vizerium.commons.indicators.MACD;
import com.vizerium.commons.indicators.MovingAverageType;

public class ClosingPricesWithTrendCheckByMACD5_13_9HigherTimeFormatTest extends ClosingPricesWithTrendCheckByMACDHistogramTest {

	/*
	 * The higher timeFormat is the default option which is implemented in
	 * 
	 * @see com.vizerium.barabanca.trade.ClosingPricesWithTrendCheckTest#getAdditionalDataPriorToIteration
	 */

	private MACD macd;

	@Before
	public void setUp() {
		super.setUp();
		macd = new MACD(5, 13, MovingAverageType.EXPONENTIAL, MovingAverageType.EXPONENTIAL, 9);
	}

	@Override
	protected MACD getMACD() {
		return macd;
	}
}
