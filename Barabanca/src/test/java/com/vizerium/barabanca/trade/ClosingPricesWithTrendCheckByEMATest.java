package com.vizerium.barabanca.trade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import com.vizerium.barabanca.historical.TimeFormat;
import com.vizerium.barabanca.trend.PeriodTrend;

public abstract class ClosingPricesWithTrendCheckByEMATest extends ClosingPricesWithTrendCheckTest {

	@Override
	protected List<PeriodTrend> getPeriodTrends(String scripName, int year, int month, TimeFormat trendTimeFormat) {
		if (month < 0) {
			return trendCheck.getTrendByEMASlope(scripName, LocalDateTime.of(year, 1, 1, 6, 0), LocalDateTime.of(year, 12, 31, 21, 00), trendTimeFormat, getMovingAverage()
					.getNumber());
		} else {
			int lastDateOfMonth = LocalDate.of(year, month, 1).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
			return trendCheck.getTrendByEMASlope(scripName, LocalDateTime.of(year, month, 1, 6, 0), LocalDateTime.of(year, month, lastDateOfMonth, 21, 00), trendTimeFormat,
					getMovingAverage().getNumber());
		}
	}
}
