package com.vizerium.commons.dao;

public enum TimeFormat {
	// This needs to necessarily be in the ascending order as there are calculations based on this below.
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

	public TimeFormat getHigherTimeFormat() {
		for (int i = 0; i < values().length - 1; i++) {
			if (values()[i].equals(this)) {
				return values()[i + 1];
			}
		}
		return null;
	}

	public TimeFormat getLowerTimeFormat() {
		for (int i = values().length - 1; i > 0; i--) {
			if (values()[i].equals(this)) {
				return values()[i - 1];
			}
		}
		return null;
	}

	public boolean isHigherTimeFormatThan(TimeFormat timeFormat) {
		if (timeFormat == null) {
			throw new RuntimeException("null time format supplied to check isHigherTimeFormat.");
		}
		if (this.equals(timeFormat)) {
			return false;
		}
		for (int i = 0; i < values().length; i++) {
			if (values()[i].equals(timeFormat)) {
				return true;
			} else if (values()[i].equals(this)) {
				return false;
			}
		}
		throw new RuntimeException("Unable to determine if the supplied timeFormat " + timeFormat.getProperty() + " is higher than " + this.getProperty());
	}

	public boolean isLowerTimeFormatThan(TimeFormat timeFormat) {
		if (timeFormat == null) {
			throw new RuntimeException("null time format supplied to check isLowerTimeFormat.");
		}
		if (this.equals(timeFormat)) {
			return false;
		}
		for (int i = 0; i < values().length; i++) {
			if (values()[i].equals(this)) {
				return true;
			} else if (values()[i].equals(timeFormat)) {
				return false;
			}
		}
		throw new RuntimeException("Unable to determine if the supplied timeFormat " + timeFormat.getProperty() + " is higher than " + this.getProperty());
	}

	@Override
	public String toString() {
		return getProperty();
	}
}