package com.vizerium.barabanca.historical;

public enum TimeFormat {
	_1MIN(1), _5MIN(5), _1HOUR(60), _1DAY(1440), _1WEEK(-1), _1MONTH(-1);

	private int interval;

	public static TimeFormat getByNameOrInterval(String propertyOrInterval) {
		if ("1min".equalsIgnoreCase(propertyOrInterval.trim()) || "1".equalsIgnoreCase(propertyOrInterval.trim())) {
			return _1MIN;
		} else if ("5min".equalsIgnoreCase(propertyOrInterval.trim()) || "5".equalsIgnoreCase(propertyOrInterval.trim())) {
			return _5MIN;
		} else if ("1hour".equalsIgnoreCase(propertyOrInterval.trim()) || "60".equalsIgnoreCase(propertyOrInterval.trim())) {
			return _1HOUR;
		} else if ("1day".equalsIgnoreCase(propertyOrInterval.trim()) || "1440".equalsIgnoreCase(propertyOrInterval.trim())) {
			return _1DAY;
		} else if ("1week".equalsIgnoreCase(propertyOrInterval.trim())) {
			return _1WEEK;
		} else if ("1month".equalsIgnoreCase(propertyOrInterval.trim())) {
			return _1MONTH;
		} else {
			throw new RuntimeException("Unable to determine time format. " + propertyOrInterval);
		}
	}

	private TimeFormat() {

	}

	private TimeFormat(int interval) {
		this.interval = interval;
	}

	public String getProperty() {
		return name().substring(1).toLowerCase();
	}

	public int getInterval() {
		return interval;
	}
}