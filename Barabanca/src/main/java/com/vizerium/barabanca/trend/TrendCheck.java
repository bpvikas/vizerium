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

		DateTimeTuple dateTimeTuple = new DateTimeTuple(startDateTime, endDateTime);
		dateTimeTuple = updateStartAndEndDatesForLookbackPeriods(scripName, timeFormat, dateTimeTuple.getStartDateTime(), dateTimeTuple.getEndDateTime());
		dateTimeTuple = updateStartAndEndDatesForWeekendsAndHolidays(scripName, dateTimeTuple.getStartDateTime(), dateTimeTuple.getEndDateTime());

		List<UnitPriceData> unitPriceDataList = historicalDataReader.getUnitPriceDataForRange(scripName, dateTimeTuple.getStartDateTime(), dateTimeTuple.getEndDateTime(),
				timeFormat);
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

	private DateTimeTuple updateStartAndEndDatesForLookbackPeriods(String scripName, TimeFormat timeFormat, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		LocalDateTime updatedStartDateTime = null;
		if (timeFormat.equals(TimeFormat._1MIN)) {
			updatedStartDateTime = startDateTime.minusMinutes(LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND);
			endDateTime = endDateTime.plusMinutes(1);
		} else if (timeFormat.equals(TimeFormat._5MIN)) {
			updatedStartDateTime = startDateTime.minusMinutes(LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND * timeFormat.getInterval());
			endDateTime = endDateTime.plusMinutes(1 * timeFormat.getInterval());
		} else if (timeFormat.equals(TimeFormat._1HOUR)) {
			updatedStartDateTime = startDateTime.minusHours(LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND);
			endDateTime = endDateTime.plusHours(1);
		} else if (timeFormat.equals(TimeFormat._1DAY)) {
			updatedStartDateTime = startDateTime.minusDays(LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND);
			endDateTime = endDateTime.plusDays(1);
		} else if (timeFormat.equals(TimeFormat._1WEEK)) {
			updatedStartDateTime = startDateTime.minusWeeks(LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND);
			endDateTime = endDateTime.plusWeeks(1);
		} else if (timeFormat.equals(TimeFormat._1MONTH)) {
			updatedStartDateTime = startDateTime.minusMonths(LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND).withDayOfMonth(1);
			endDateTime = endDateTime.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
		} else {
			throw new RuntimeException("Unable to determine timeFormat " + timeFormat);
		}

		// Need to make a recursive call to take care of a scenario where the original start Date is a Monday, 3 days back goes to Friday.
		// Then we cannot get a trend for it, because trend needs 3 "business" days backward to calculate the trend. In this case, we were getting only
		// one "business" day prior to Monday which was Friday.
		while (historicalDataReader.getUnitPriceDataForRange(scripName, updatedStartDateTime, startDateTime, timeFormat).size() < LOOKBACK_PERIOD_FOR_EMA_SLOPE_TREND) {
			DateTimeTuple dateTimeTuple = updateStartAndEndDatesForLookbackPeriods(scripName, timeFormat, updatedStartDateTime, endDateTime);
			updatedStartDateTime = dateTimeTuple.getStartDateTime();
		}

		return new DateTimeTuple(updatedStartDateTime, endDateTime);
	}

	private DateTimeTuple updateStartAndEndDatesForWeekendsAndHolidays(String scripName, LocalDateTime startDateTime, LocalDateTime endDateTime) {
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
