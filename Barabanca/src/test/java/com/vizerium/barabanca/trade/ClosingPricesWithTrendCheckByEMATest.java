package com.vizerium.barabanca.trade;

import java.time.LocalDateTime;
import java.util.List;

import com.vizerium.barabanca.historical.TimeFormat;
import com.vizerium.barabanca.trend.PeriodTrend;
import com.vizerium.commons.calculators.MovingAverage;

public abstract class ClosingPricesWithTrendCheckByEMATest extends ClosingPricesWithTrendCheckTest {

	protected abstract MovingAverage getMovingAverage();

	@Override
	protected List<PeriodTrend> getPeriodTrends(String scripName, int year, TimeFormat trendTimeFormat) {
		return trendCheck
				.getTrendByEMASlope(scripName, LocalDateTime.of(year, 1, 1, 6, 0), LocalDateTime.of(year, 12, 31, 17, 00), trendTimeFormat, getMovingAverage().getNumber());
	}
}
