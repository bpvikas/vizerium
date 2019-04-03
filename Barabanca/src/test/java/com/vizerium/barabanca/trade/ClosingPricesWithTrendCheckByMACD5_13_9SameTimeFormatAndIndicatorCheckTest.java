package com.vizerium.barabanca.trade;

import java.util.List;

import org.junit.Before;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MACD;
import com.vizerium.commons.indicators.MovingAverageType;

public class ClosingPricesWithTrendCheckByMACD5_13_9SameTimeFormatAndIndicatorCheckTest extends ClosingPricesWithTrendCheckByMACDHistogramAndIndicatorCheckTest {

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

	@Override
	protected void getAdditionalDataPriorToIteration(String scripName, TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		periodTrends = getPeriodTrends(scripName, timeFormat, unitPriceDataList);
		updateIndicatorDataInUnitPrices(unitPriceDataList, macd);
	}
}
