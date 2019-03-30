package com.vizerium.barabanca.trade;

import com.vizerium.commons.indicators.MACD;

public class ClosingPricesWithTrendCheckByMACD12_26_9HigherTimeFormatTest extends ClosingPricesWithTrendCheckByMACDHistogramTest {

	/*
	 * The higher timeFormat is the default option which is implemented in
	 * @see com.vizerium.barabanca.trade.ClosingPricesWithTrendCheckTest#getAdditionalDataPriorToIteration
	 */

	@Override
	protected MACD getMACD() {
		// The default MACD constructor has a fastMA=12, slowMA=26, signal=9
		return new MACD();
	}

}
