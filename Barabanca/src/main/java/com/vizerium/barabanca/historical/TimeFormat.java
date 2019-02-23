package com.vizerium.barabanca.historical;

public enum TimeFormat {
	_1MIN("1min", 1), _5MIN("5min", 5), _1HOUR("1hour", 60), _1DAY("1day", 1440);

	private String property;

	private int interval;

	public static TimeFormat getByPropertyOrInterval(String propertyOrInterval) {
		if ("1min".equalsIgnoreCase(propertyOrInterval.trim()) || "1".equalsIgnoreCase(propertyOrInterval.trim())) {
			return _1MIN;
		} else if ("5min".equalsIgnoreCase(propertyOrInterval.trim()) || "5".equalsIgnoreCase(propertyOrInterval.trim())) {
			return _5MIN;
		} else if ("1hour".equalsIgnoreCase(propertyOrInterval.trim()) || "60".equalsIgnoreCase(propertyOrInterval.trim())) {
			return _1HOUR;
		} else if ("1day".equalsIgnoreCase(propertyOrInterval.trim()) || "1440".equalsIgnoreCase(propertyOrInterval.trim())) {
			return _1DAY;
		} else {
			throw new RuntimeException("Unable to determine time format. " + propertyOrInterval);
		}
	}

	private TimeFormat() {

	}

	private TimeFormat(String property, int interval) {
		this.property = property;
		this.interval = interval;
	}

	public String getProperty() {
		return property;
	}

	public int getInterval() {
		return interval;
	}
}