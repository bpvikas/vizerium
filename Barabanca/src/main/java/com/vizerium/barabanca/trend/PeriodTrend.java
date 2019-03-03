package com.vizerium.barabanca.trend;

import java.time.LocalDateTime;

import com.vizerium.barabanca.historical.TimeFormat;

public class PeriodTrend {

	private LocalDateTime startDateTime;

	private TimeFormat timeFormat;

	private Trend trend;

	public PeriodTrend() {
	}

	public PeriodTrend(LocalDateTime startDateTime, TimeFormat timeFormat, Trend trend) {
		this.startDateTime = startDateTime;
		this.timeFormat = timeFormat;
		this.trend = trend;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public TimeFormat getTimeFormat() {
		return timeFormat;
	}

	public Trend getTrend() {
		return trend;
	}

	@Override
	public String toString() {
		return trend.name() + " trend seen for " + startDateTime + " on " + timeFormat.getProperty() + " chart.";
	}
}