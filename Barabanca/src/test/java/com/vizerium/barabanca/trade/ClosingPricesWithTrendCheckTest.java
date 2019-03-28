package com.vizerium.barabanca.trade;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;

import com.vizerium.barabanca.historical.TimeFormat;
import com.vizerium.barabanca.trend.PeriodTrend;
import com.vizerium.barabanca.trend.Trend;
import com.vizerium.barabanca.trend.TrendCheck;
import com.vizerium.commons.dao.UnitPriceData;

public abstract class ClosingPricesWithTrendCheckTest extends ClosingPricesTest {

	private static final Logger logger = Logger.getLogger(ClosingPricesWithTrendCheckTest.class);

	protected TrendCheck trendCheck;

	protected List<PeriodTrend> periodTrends;

	@Before
	public void setUp() {
		super.setUp();
		trendCheck = new TrendCheck(historicalDataReader);
	}

	protected abstract List<PeriodTrend> getPeriodTrends(String scripName, TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataListCurrentTimeFormat);

	@Override
	protected void getAdditionalDataPriorToIteration(String scripName, TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		periodTrends = getPeriodTrends(scripName, timeFormat.getHigherTimeFormat(), unitPriceDataList);
	}

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(UnitPriceData current, UnitPriceData previous) {

	}

	protected Trend getPriorTrend(LocalDateTime unitPriceDateTime, List<PeriodTrend> periodTrends) {
		for (int i = 0; i < periodTrends.size() - 1; i++) {
			if (!periodTrends.get(i).getStartDateTime().isAfter(unitPriceDateTime) && !periodTrends.get(i + 1).getStartDateTime().isBefore(unitPriceDateTime)) {
				logger.debug("For " + unitPriceDateTime + ", " + periodTrends.get(i));
				return periodTrends.get(i).getTrend();
			}
		}
		if (unitPriceDateTime.isAfter(periodTrends.get(periodTrends.size() - 1).getStartDateTime())) {
			return periodTrends.get(periodTrends.size() - 1).getTrend();
		}
		throw new RuntimeException("Unable to determine Trend for " + unitPriceDateTime);
	}
}
