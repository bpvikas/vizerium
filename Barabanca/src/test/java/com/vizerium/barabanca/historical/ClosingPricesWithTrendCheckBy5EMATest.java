package com.vizerium.barabanca.historical;

import java.time.LocalDateTime;
import java.util.List;

import com.vizerium.barabanca.trend.PeriodTrend;
import com.vizerium.commons.calculators.MovingAverage;

public class ClosingPricesWithTrendCheckBy5EMATest extends ClosingPricesWithTrendCheckTest {

	private static final MovingAverage EMA_TO_CALCULATE_SLOPE = MovingAverage._5;

	@Override
	protected List<PeriodTrend> getPeriodTrends(String scripName, int year, TimeFormat trendTimeFormat) {
		return trendCheck.getTrendByEMASlope(scripName, LocalDateTime.of(year, 1, 1, 6, 0), LocalDateTime.of(year, 12, 31, 17, 00), trendTimeFormat,
				EMA_TO_CALCULATE_SLOPE.getNumber());
	}
}
