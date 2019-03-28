package com.vizerium.barabanca.trade;

import com.vizerium.commons.indicators.MACD;

public class ClosingPricesWithTrendCheckByMACD12_26_9Test extends ClosingPricesWithTrendCheckByMACDHistogramTest {

	@Override
	protected MACD getMACD() {
		// The default MACD constructor has a fastMA=12, slowMA=26, signal=9
		return new MACD();
	}

}
