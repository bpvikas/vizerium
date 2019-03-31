package com.vizerium.commons.dao;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;

import com.vizerium.commons.indicators.Indicator;
import com.vizerium.commons.indicators.Indicators;
import com.vizerium.commons.indicators.StandardMovingAverages;
import com.vizerium.commons.util.NumberFormats;

public class UnitPriceData extends UnitPrice {

	private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd,HH:mm");

	private static final NumberFormat manf = NumberFormats.getForMovingAverage();

	private String scripName;

	private LocalDateTime dateTime;

	private TimeFormat timeFormat;

	private TreeSet<MovingAverage> movingAverages = new TreeSet<MovingAverage>();

	private Map<Indicators, Indicator> indicators = new TreeMap<Indicators, Indicator>();

	public UnitPriceData() {

	}

	public UnitPriceData(String scripName, String date, String time, String open, String high, String low, String close) {
		this(scripName, LocalDateTime.parse(date + "," + time, df), Float.parseFloat(open), Float.parseFloat(high), Float.parseFloat(low), Float.parseFloat(close));
	}

	public UnitPriceData(String[] unitPriceDetailsArray) {
		this(unitPriceDetailsArray[0], unitPriceDetailsArray[1], unitPriceDetailsArray[2], unitPriceDetailsArray[3], unitPriceDetailsArray[4], unitPriceDetailsArray[5],
				unitPriceDetailsArray[6]);
		if (unitPriceDetailsArray.length > 11) {
			// The above condition is to take care of parsedData which has upto 8 elements. 2 elements as 0,0 beyond the OHLC information.
			int[] standardMovingAverages = StandardMovingAverages.getAllStandardMAsSorted();
			for (int i = 0; i < standardMovingAverages.length; i++) {
				setMovingAverage(standardMovingAverages[i], Float.parseFloat(unitPriceDetailsArray[7 + i]));
			}
		}
	}

	public UnitPriceData(String scripName, LocalDateTime dateTime, float open, float high, float low, float close) {
		this.scripName = scripName;
		this.dateTime = dateTime;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
	}

	public String getScripName() {
		return scripName;
	}

	public void setScripName(String scripName) {
		this.scripName = scripName;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public LocalDate getDate() {
		return dateTime.toLocalDate();
	}

	public LocalTime getTime() {
		return dateTime.toLocalTime();
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public void setTimeFormat(TimeFormat timeFormat) {
		this.timeFormat = timeFormat;
	}

	public TimeFormat getTimeFormat() {
		return timeFormat;
	}

	public float getMovingAverage(int ma) {
		for (MovingAverage movingAverage : movingAverages) {
			if (movingAverage.getMA() == ma) {
				return movingAverage.getValue();
			}
		}
		throw new RuntimeException("Unable to obtain Moving Average for " + ma);
	}

	public void setMovingAverage(int ma, float value) {
		this.movingAverages.add(new MovingAverage(ma, value));
	}

	/*
	 * This method is used to determine at which value the trade has actually happened. This defaults to the Closing Price, but some traders tend to use the Open as well.
	 */
	public float getTradedValue() {
		return getClose();
	}

	public Indicator getIndicator(Indicators indicatorName) {
		return indicators.get(indicatorName);
	}

	public void addIndicator(Indicators indicatorName, Indicator indicatorValue) {
		indicators.put(indicatorName, indicatorValue);
	}

	public Map<Indicators, Indicator> getIndicators() {
		return indicators;
	}

	public void setIndicators(Map<Indicators, Indicator> indicators) {
		this.indicators = indicators;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UnitPriceData other = (UnitPriceData) obj;
		return Objects.equals(scripName, other.scripName) && Objects.equals(timeFormat, other.timeFormat) && Objects.equals(dateTime, other.dateTime)
				&& Objects.equals(open, other.open) && Objects.equals(high, other.high) && Objects.equals(low, other.low) && Objects.equals(close, other.close);

	};

	@Override
	public int hashCode() {
		return Objects.hash(scripName, timeFormat, dateTime, open, high, low, close);
	};

	@Override
	public String toString() {
		return scripName + "," + timeFormat.toString() + "," + df.format(dateTime) + "," + nf.format(open) + "," + nf.format(high) + "," + nf.format(low) + "," + nf.format(close);
	}

	public String printMovingAverages() {
		String movingAveragesString = "";
		for (MovingAverage movingAverage : movingAverages) {
			movingAveragesString += ("," + manf.format(movingAverage.getValue()));
		}
		return movingAveragesString;
	}

	static class MovingAverage implements Comparable<MovingAverage> {

		private int ma;
		private float value;

		public MovingAverage() {

		}

		public MovingAverage(int ma, float value) {
			this.ma = ma;
			this.value = value;
		}

		public int getMA() {
			return ma;
		}

		public float getValue() {
			return value;
		}

		@Override
		public int compareTo(MovingAverage other) {
			return Integer.compare(ma, other.ma);
		}
	}
}
