package com.vizerium.barabanca.trend;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.vizerium.barabanca.dao.UnitPriceData;
import com.vizerium.barabanca.historical.HistoricalDataReader;
import com.vizerium.barabanca.historical.TimeFormat;

public class TrendCheck {

	private HistoricalDataReader historicalDataReader;

	public TrendCheck() {
	}

	public TrendCheck(HistoricalDataReader historicalDataReader) {
		this.historicalDataReader = historicalDataReader;
	}

	public List<PeriodTrend> getTrendByEMASlope(String scripName, LocalDateTime startDateTime, LocalDateTime endDateTime, TimeFormat timeFormat, int ma) {
		int lookbackPeriod = 4;
		if (timeFormat.equals(TimeFormat._1MIN)) {
			startDateTime = startDateTime.minusMinutes(lookbackPeriod);
		} else if (timeFormat.equals(TimeFormat._5MIN)) {
			startDateTime = startDateTime.minusMinutes(lookbackPeriod * timeFormat.getInterval());
		} else if (timeFormat.equals(TimeFormat._1HOUR)) {
			startDateTime = startDateTime.minusHours(lookbackPeriod);
		} else if (timeFormat.equals(TimeFormat._1DAY)) {
			startDateTime = startDateTime.minusDays(lookbackPeriod);
		} else if (timeFormat.equals(TimeFormat._1WEEK)) {
			startDateTime = startDateTime.minusWeeks(lookbackPeriod).with(DayOfWeek.SUNDAY);
		} else if (timeFormat.equals(TimeFormat._1MONTH)) {
			startDateTime = startDateTime.minusMonths(lookbackPeriod).withDayOfMonth(1);
		} else {
			throw new RuntimeException("Unable to determine timeFormat " + timeFormat);
		}

		List<UnitPriceData> unitPriceDataList = historicalDataReader.getUnitPriceDataForRange(scripName, startDateTime, endDateTime, timeFormat);
		List<PeriodTrend> periodTrends = new ArrayList<PeriodTrend>();

		for (int i = lookbackPeriod - 1; i < unitPriceDataList.size(); i++) {
			if (unitPriceDataList.get(i - 3).getMovingAverage(ma) < unitPriceDataList.get(i - 2).getMovingAverage(ma)
					&& unitPriceDataList.get(i - 2).getMovingAverage(ma) < unitPriceDataList.get(i - 1).getMovingAverage(ma)) {
				periodTrends.add(new PeriodTrend(unitPriceDataList.get(i).getDateTime(), timeFormat, Trend.UP));
			} else if (unitPriceDataList.get(i - 3).getMovingAverage(ma) > unitPriceDataList.get(i - 2).getMovingAverage(ma)
					&& unitPriceDataList.get(i - 2).getMovingAverage(ma) > unitPriceDataList.get(i - 1).getMovingAverage(ma)) {
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
}
