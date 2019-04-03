package com.vizerium.barabanca.trade;

import java.util.List;

import org.junit.Before;

import com.vizerium.barabanca.trend.PeriodTrends;
import com.vizerium.barabanca.trend.TrendCheck;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;

public abstract class ClosingPricesWithTrendCheckTest extends ClosingPricesTest {

	protected TrendCheck trendCheck;

	protected PeriodTrends periodTrends;

	@Before
	public void setUp() {
		super.setUp();
	}

	protected abstract PeriodTrends getPeriodTrends(TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataListCurrentTimeFormat);

	@Override
	protected void getAdditionalDataPriorToIteration(TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		periodTrends = getPeriodTrends(timeFormat.getHigherTimeFormat(), unitPriceDataList);
	}

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(UnitPriceData current, UnitPriceData previous) {

	}

}
