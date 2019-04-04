package com.vizerium.barabanca.trade;

import org.junit.Before;

import com.vizerium.commons.indicators.MACD;

public class ClosingPricesWithTrendCheckByMACD12_26_9HigherTimeFormatTest extends ClosingPricesWithTrendCheckByMACDHistogramTest {

	/*
	 * The higher timeFormat is the default option which is implemented in
	 * 
	 * @see com.vizerium.barabanca.trade.ClosingPricesWithTrendCheckTest#getAdditionalDataPriorToIteration
	 */

	private MACD macd;

	@Before
	public void setUp() {
		super.setUp();
		// The default MACD constructor has a fastMA=12, slowMA=26, signal=9
		macd = new MACD();
	}

	@Override
	protected MACD getMACD() {
		return macd;
	}

	@Override
	protected String getPreviousResultFileName() {
		return "testrun_macd_12_26_higher_time_format_trend.csv";
	}
}
