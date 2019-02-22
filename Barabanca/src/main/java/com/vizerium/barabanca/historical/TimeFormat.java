package com.vizerium.barabanca.historical;

public enum TimeFormat {
	MIN1("1min"), MIN5("5min"), HOUR1("1hour"), DAY1("1day");

	private String property;

	public static TimeFormat getByProperty(String property) {
		if ("1min".equalsIgnoreCase(property.trim()) || "min1".equalsIgnoreCase(property.trim()) || "1".equalsIgnoreCase(property.trim())) {
			return MIN1;
		}
		if ("5min".equalsIgnoreCase(property.trim()) || "min5".equalsIgnoreCase(property.trim()) || "5".equalsIgnoreCase(property.trim())) {
			return MIN5;
		}
		if ("1hour".equalsIgnoreCase(property.trim()) || "hour1".equalsIgnoreCase(property.trim()) || "60".equalsIgnoreCase(property.trim())) {
			return HOUR1;
		}
		if ("1day".equalsIgnoreCase(property.trim()) || "day1".equalsIgnoreCase(property.trim())) {
			return DAY1;
		} else {
			throw new RuntimeException("Unable to determine time format. " + property);
		}
	}

	private TimeFormat() {

	}

	private TimeFormat(String property) {
		this.property = property;
	}

	public String getProperty() {
		return property;
	}
}
