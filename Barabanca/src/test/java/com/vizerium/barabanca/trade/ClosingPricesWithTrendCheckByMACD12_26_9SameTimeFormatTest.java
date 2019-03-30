package com.vizerium.barabanca.trade;

import java.util.List;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MACD;

public class ClosingPricesWithTrendCheckByMACD12_26_9SameTimeFormatTest extends ClosingPricesWithTrendCheckByMACDHistogramTest {

	@Override
	protected MACD getMACD() {
		// The default MACD constructor has a fastMA=12, slowMA=26, signal=9
		return new MACD();
	}

	@Override
	protected void getAdditionalDataPriorToIteration(String scripName, TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		periodTrends = getPeriodTrends(scripName, timeFormat, unitPriceDataList);
	}
}
