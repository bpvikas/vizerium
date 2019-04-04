package com.vizerium.barabanca.trade;

import java.util.List;

import org.junit.Before;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MACD;

public class ClosingPricesWithTrendCheckByMACD12_26_9SameTimeFormatTest extends ClosingPricesWithTrendCheckByMACDHistogramTest {

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
	protected void getAdditionalDataPriorToIteration(TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		periodTrends = getPeriodTrends(timeFormat, unitPriceDataList);
	}

	@Override
	protected String getPreviousResultFileName() {
		return "testrun_macd_12_26_same_time_format_trend.csv";
	}
}
