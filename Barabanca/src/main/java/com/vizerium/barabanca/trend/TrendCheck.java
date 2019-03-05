package com.vizerium.barabanca.trend;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.vizerium.barabanca.historical.HistoricalDataReader;
import com.vizerium.barabanca.historical.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;

public class TrendCheck {

	private static final int LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND = 3;

	private HistoricalDataReader historicalDataReader;

	public TrendCheck() {
	}

	public TrendCheck(HistoricalDataReader historicalDataReader) {
		this.historicalDataReader = historicalDataReader;
	}

	public List<PeriodTrend> getTrendByEMASlope(String scripName, LocalDateTime startDateTime, LocalDateTime endDateTime, TimeFormat timeFormat, int ma) {

		updateStartAndEndDatesForLookbackPeriods(timeFormat, startDateTime, endDateTime);
		updateStartAndEndDatesForWeekendsAndHolidays(scripName, startDateTime, endDateTime);

		List<UnitPriceData> unitPriceDataList = historicalDataReader.getUnitPriceDataForRange(scripName, startDateTime, endDateTime, timeFormat);
		List<PeriodTrend> periodTrends = new ArrayList<PeriodTrend>();

		for (int i = LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND - 1; i < unitPriceDataList.size(); i++) {
			if (unitPriceDataList.get(i - 2).getMovingAverage(ma) < unitPriceDataList.get(i - 1).getMovingAverage(ma)) {
				periodTrends.add(new PeriodTrend(unitPriceDataList.get(i).getDateTime(), timeFormat, Trend.UP));
			} else if (unitPriceDataList.get(i - 2).getMovingAverage(ma) > unitPriceDataList.get(i - 1).getMovingAverage(ma)) {
				periodTrends.add(new PeriodTrend(unitPriceDataList.get(i).getDateTime(), timeFormat, Trend.DOWN));
			} else {
				periodTrends.add(new PeriodTrend(unitPriceDataList.get(i).getDateTime(), timeFormat, Trend.CHOPPY));
			}
		}
		return periodTrends;
	}

	public List<PeriodTrend> getTrendByMACDHistogramSlope() {

		return null;
	}

	public List<PeriodTrend> getTrendByDirectionalSystemAndADX() {

		return null;
	}

	private void updateStartAndEndDatesForLookbackPeriods(TimeFormat timeFormat, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		if (timeFormat.equals(TimeFormat._1MIN)) {
			startDateTime = startDateTime.minusMinutes(LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND);
			endDateTime = endDateTime.plusMinutes(1);
		} else if (timeFormat.equals(TimeFormat._5MIN)) {
			startDateTime = startDateTime.minusMinutes(LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND * timeFormat.getInterval());
			endDateTime = endDateTime.plusMinutes(1 * timeFormat.getInterval());
		} else if (timeFormat.equals(TimeFormat._1HOUR)) {
			startDateTime = startDateTime.minusHours(LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND);
			endDateTime = endDateTime.plusHours(1);
		} else if (timeFormat.equals(TimeFormat._1DAY)) {
			startDateTime = startDateTime.minusDays(LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND);
			endDateTime = endDateTime.plusDays(1);
		} else if (timeFormat.equals(TimeFormat._1WEEK)) {
			startDateTime = startDateTime.minusWeeks(LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND);
			endDateTime = endDateTime.plusWeeks(1);
		} else if (timeFormat.equals(TimeFormat._1MONTH)) {
			startDateTime = startDateTime.minusMonths(LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND).withDayOfMonth(1);
			endDateTime = endDateTime.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
		} else {
			throw new RuntimeException("Unable to determine timeFormat " + timeFormat);
		}
	}

	private void updateStartAndEndDatesForWeekendsAndHolidays(String scripName, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		// Calculating for Weekends and Holidays
		if (DayOfWeek.SATURDAY.equals(startDateTime.getDayOfWeek())) {
			startDateTime.minusDays(1);
		} else if (DayOfWeek.SUNDAY.equals(startDateTime.getDayOfWeek())) {
			startDateTime.minusDays(2);
		}

		if (DayOfWeek.SATURDAY.equals(endDateTime.getDayOfWeek())) {
			endDateTime.plusDays(2);
		} else if (DayOfWeek.SUNDAY.equals(endDateTime.getDayOfWeek())) {
			endDateTime.plusDays(1);
		}

		while (CollectionUtils.isEmpty(historicalDataReader.getUnitPriceDataForRange(scripName, startDateTime, startDateTime, TimeFormat._1DAY))) {
			startDateTime.minusDays(1);
		}
		while (CollectionUtils.isEmpty(historicalDataReader.getUnitPriceDataForRange(scripName, endDateTime, endDateTime, TimeFormat._1DAY))) {
			endDateTime.plusDays(1);
		}
	}
}
