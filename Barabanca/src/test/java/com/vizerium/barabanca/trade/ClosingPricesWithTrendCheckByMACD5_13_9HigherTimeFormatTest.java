package com.vizerium.barabanca.trade;

import com.vizerium.commons.indicators.MACD;
import com.vizerium.commons.indicators.MovingAverageType;

public class ClosingPricesWithTrendCheckByMACD5_13_9HigherTimeFormatTest extends ClosingPricesWithTrendCheckByMACDHistogramTest {

	/*
	 * The higher timeFormat is the default option which is implemented in
	 * 
	 * @see com.vizerium.barabanca.trade.ClosingPricesWithTrendCheckTest#getAdditionalDataPriorToIteration
	 */

	@Override
	protected MACD getMACD() {
		return new MACD(5, 13, MovingAverageType.EXPONENTIAL, MovingAverageType.EXPONENTIAL, 9);
	}

}
