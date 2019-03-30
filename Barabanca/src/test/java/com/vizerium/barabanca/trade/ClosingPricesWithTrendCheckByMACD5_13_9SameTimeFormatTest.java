package com.vizerium.barabanca.trade;

import java.util.List;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MACD;
import com.vizerium.commons.indicators.MovingAverageType;

public class ClosingPricesWithTrendCheckByMACD5_13_9SameTimeFormatTest extends ClosingPricesWithTrendCheckByMACDHistogramTest {

	@Override
	protected MACD getMACD() {
		return new MACD(5, 13, MovingAverageType.EXPONENTIAL, MovingAverageType.EXPONENTIAL, 9);
	}

	@Override
	protected void getAdditionalDataPriorToIteration(String scripName, TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		periodTrends = getPeriodTrends(scripName, timeFormat, unitPriceDataList);
	}
}
