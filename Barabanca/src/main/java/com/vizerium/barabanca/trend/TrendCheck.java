package com.vizerium.barabanca.trend;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.vizerium.barabanca.historical.HistoricalDataDateRange;
import com.vizerium.barabanca.historical.HistoricalDataReader;
import com.vizerium.barabanca.historical.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.DirectionalSystem;
import com.vizerium.commons.indicators.DirectionalSystemCalculator;
import com.vizerium.commons.indicators.MACD;
import com.vizerium.commons.indicators.MACDCalculator;

public class TrendCheck {

	private static final int LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND = 3;

	private HistoricalDataReader historicalDataReader;

	public TrendCheck() {
	}

	public TrendCheck(HistoricalDataReader historicalDataReader) {
		this.historicalDataReader = historicalDataReader;
	}

	public List<PeriodTrend> getTrendByEMASlope(String scripName, TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataListCurrentTimeFormat, int ma) {

		List<UnitPriceData> expandedUnitPriceDataList = getUnitPriceDataListTrendTimeFormat(scripName, trendTimeFormat, unitPriceDataListCurrentTimeFormat,
				LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND);
		List<PeriodTrend> periodTrends = new ArrayList<PeriodTrend>();

		for (int i = LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND - 1; i < expandedUnitPriceDataList.size(); i++) {
			if (expandedUnitPriceDataList.get(i - 2).getMovingAverage(ma) < expandedUnitPriceDataList.get(i - 1).getMovingAverage(ma)) {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataList.get(i).getDateTime(), trendTimeFormat, Trend.UP));
			} else if (expandedUnitPriceDataList.get(i - 2).getMovingAverage(ma) > expandedUnitPriceDataList.get(i - 1).getMovingAverage(ma)) {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataList.get(i).getDateTime(), trendTimeFormat, Trend.DOWN));
			} else {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataList.get(i).getDateTime(), trendTimeFormat, Trend.CHOPPY));
			}
		}
		return periodTrends;
	}

	public List<PeriodTrend> getTrendByMACDHistogramSlope(String scripName, TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataList, MACD macdInput) {

		// The value for the lookbackPeriod needs to be a sum of the slower MA and the smoothing period so that we can get the calculations correct.
		int lookbackPeriodForMACDHistogramCalculations = macdInput.getSlowMA() + macdInput.getSmoothingPeriod();

		List<UnitPriceData> expandedUnitPriceDataList = getUnitPriceDataListTrendTimeFormat(scripName, trendTimeFormat, unitPriceDataList,
				lookbackPeriodForMACDHistogramCalculations);
		List<PeriodTrend> periodTrends = new ArrayList<PeriodTrend>();

		MACDCalculator macdCalculator = new MACDCalculator();
		MACD macd = macdCalculator.calculate(expandedUnitPriceDataList, macdInput);
		for (int i = 1; i < expandedUnitPriceDataList.size(); i++) {
			if (macd.getHistogramValues()[i] > macd.getHistogramValues()[i - 1]) {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataList.get(i).getDateTime(), trendTimeFormat, Trend.UP));
			} else if (macd.getHistogramValues()[i] < macd.getHistogramValues()[i - 1]) {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataList.get(i).getDateTime(), trendTimeFormat, Trend.DOWN));
			} else {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataList.get(i).getDateTime(), trendTimeFormat, Trend.CHOPPY));
			}
		}
		return periodTrends;
	}

	public List<PeriodTrend> getTrendByDirectionalSystemAndADX(String scripName, TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataList, DirectionalSystem dsInput) {

		// The value for the lookbackPeriod needs to be a sum of the first smoothing (to get +DI -DI) and the smoothing smoothing period (to get ADX) and 1 (to get the initial true
		// range) so that we can get the calculations correct.
		int lookbackPeriodForDirectionalSystemCalculations = DirectionalSystemCalculator.DIRECTIONAL_MOVEMENT_CALCULATION_START + dsInput.getSmoothingPeriod()
				+ dsInput.getSmoothingPeriod();

		List<UnitPriceData> expandedUnitPriceDataList = getUnitPriceDataListTrendTimeFormat(scripName, trendTimeFormat, unitPriceDataList,
				lookbackPeriodForDirectionalSystemCalculations);
		List<PeriodTrend> periodTrends = new ArrayList<PeriodTrend>();

		DirectionalSystemCalculator dsCalculator = new DirectionalSystemCalculator();
		DirectionalSystem ds = dsCalculator.calculate(expandedUnitPriceDataList, dsInput);

		// TODO: Need to read Alexander Elder's book to determine exact rules for trend being, up, down or choppy

		return periodTrends;
	}

	private List<UnitPriceData> getUnitPriceDataListTrendTimeFormat(String scripName, TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataList, int lookbackPeriod) {
		DateTimeTuple dateTimeTuple = new DateTimeTuple(unitPriceDataList.get(0).getDateTime(), unitPriceDataList.get(unitPriceDataList.size() - 1).getDateTime());
		dateTimeTuple = updateStartAndEndDatesForLookbackPeriods(scripName, trendTimeFormat, dateTimeTuple.getStartDateTime(), dateTimeTuple.getEndDateTime(), lookbackPeriod);
		dateTimeTuple = updateStartAndEndDatesForWeekendsAndHolidays(scripName, trendTimeFormat, dateTimeTuple.getStartDateTime(), dateTimeTuple.getEndDateTime());

		List<UnitPriceData> unitPriceDataListTrendTimeFormat = historicalDataReader.getUnitPriceDataForRange(scripName, dateTimeTuple.getStartDateTime(),
				dateTimeTuple.getEndDateTime(), trendTimeFormat);

		return unitPriceDataListTrendTimeFormat;
	}

	private DateTimeTuple updateStartAndEndDatesForLookbackPeriods(String scripName, TimeFormat trendTimeFormat, LocalDateTime startDateTime, LocalDateTime endDateTime,
			int lookbackPeriod) {
		LocalDateTime updatedStartDateTime = null;
		if (trendTimeFormat.equals(TimeFormat._1MIN)) {
			updatedStartDateTime = startDateTime.minusMinutes(lookbackPeriod);
			endDateTime = endDateTime.plusMinutes(1);
		} else if (trendTimeFormat.equals(TimeFormat._5MIN)) {
			updatedStartDateTime = startDateTime.minusMinutes(lookbackPeriod * trendTimeFormat.getInterval());
			endDateTime = endDateTime.plusMinutes(1 * trendTimeFormat.getInterval());
		} else if (trendTimeFormat.equals(TimeFormat._1HOUR)) {
			updatedStartDateTime = startDateTime.minusHours(lookbackPeriod);
			endDateTime = endDateTime.plusHours(1);
		} else if (trendTimeFormat.equals(TimeFormat._1DAY)) {
			updatedStartDateTime = startDateTime.minusDays(lookbackPeriod);
			endDateTime = endDateTime.plusDays(1);
		} else if (trendTimeFormat.equals(TimeFormat._1WEEK)) {
			updatedStartDateTime = startDateTime.minusWeeks(lookbackPeriod);
			endDateTime = endDateTime.plusWeeks(1);
		} else if (trendTimeFormat.equals(TimeFormat._1MONTH)) {
			updatedStartDateTime = startDateTime.minusMonths(lookbackPeriod).withDayOfMonth(1);
			endDateTime = endDateTime.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
		} else {
			throw new RuntimeException("Unable to determine timeFormat " + trendTimeFormat);
		}

		// This is to check boundary conditions of historical data
		if (startDateTime.isBefore(HistoricalDataDateRange.getStartDateTime(scripName, trendTimeFormat))) {
			startDateTime = HistoricalDataDateRange.getStartDateTime(scripName, trendTimeFormat);
		}
		if (endDateTime.isAfter(HistoricalDataDateRange.getEndDateTime(scripName, trendTimeFormat))) {
			endDateTime = HistoricalDataDateRange.getEndDateTime(scripName, trendTimeFormat);
		}

		// Need to make a recursive call to take care of a scenario where the original start Date is a Monday, 3 days back goes to Friday.
		// Then we cannot get a trend for it, because trend needs 3 "business" days backward to calculate the trend. In this case, we were getting only
		// one "business" day prior to Monday which was Friday.
		while (historicalDataReader.getUnitPriceDataForRange(scripName, updatedStartDateTime, startDateTime, trendTimeFormat).size() < lookbackPeriod) {
			DateTimeTuple dateTimeTuple = updateStartAndEndDatesForLookbackPeriods(scripName, trendTimeFormat, updatedStartDateTime, endDateTime, lookbackPeriod);
			updatedStartDateTime = dateTimeTuple.getStartDateTime();
		}

		return new DateTimeTuple(updatedStartDateTime, endDateTime);
	}

	private DateTimeTuple updateStartAndEndDatesForWeekendsAndHolidays(String scripName, TimeFormat trendTimeFormat, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		// Calculating for Weekends and Holidays
		if (DayOfWeek.SATURDAY.equals(startDateTime.getDayOfWeek())) {
			startDateTime = startDateTime.minusDays(1);
		} else if (DayOfWeek.SUNDAY.equals(startDateTime.getDayOfWeek())) {
			startDateTime = startDateTime.minusDays(2);
		}

		if (DayOfWeek.SATURDAY.equals(endDateTime.getDayOfWeek())) {
			endDateTime = endDateTime.plusDays(2);
		} else if (DayOfWeek.SUNDAY.equals(endDateTime.getDayOfWeek())) {
			endDateTime = endDateTime.plusDays(1);
		}

		// This is to check boundary conditions of historical data
		if (startDateTime.isBefore(HistoricalDataDateRange.getStartDateTime(scripName, trendTimeFormat))) {
			startDateTime = HistoricalDataDateRange.getStartDateTime(scripName, trendTimeFormat);
		}
		if (endDateTime.isAfter(HistoricalDataDateRange.getEndDateTime(scripName, trendTimeFormat))) {
			endDateTime = HistoricalDataDateRange.getEndDateTime(scripName, trendTimeFormat);
		}

		while (CollectionUtils.isEmpty(historicalDataReader.getUnitPriceDataForRange(scripName, startDateTime, startDateTime, TimeFormat._1DAY))) {
			startDateTime = startDateTime.minusDays(1);
		}
		while (CollectionUtils.isEmpty(historicalDataReader.getUnitPriceDataForRange(scripName, endDateTime, endDateTime, TimeFormat._1DAY))) {
			endDateTime = endDateTime.plusDays(1);
		}

		return new DateTimeTuple(startDateTime, endDateTime);
	}

	private static class DateTimeTuple {
		private LocalDateTime startDateTime;

		private LocalDateTime endDateTime;

		DateTimeTuple(LocalDateTime startDateTime, LocalDateTime endDateTime) {
			if (endDateTime.isBefore(startDateTime)) {
				throw new RuntimeException("endDateTime " + endDateTime + " cannot be before startDateTime " + startDateTime);
			}
			this.startDateTime = startDateTime;
			this.endDateTime = endDateTime;
		}

		public LocalDateTime getStartDateTime() {
			return startDateTime;
		}

		public LocalDateTime getEndDateTime() {
			return endDateTime;
		}

		@Override
		public String toString() {
			return startDateTime + " -> " + endDateTime;
		}
	}
}
