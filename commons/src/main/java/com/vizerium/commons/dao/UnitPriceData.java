package com.vizerium.commons.dao;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vizerium.commons.indicators.MovingAverage;
import com.vizerium.commons.indicators.MovingAverageAndValue;

public class UnitPriceData extends UnitPrice {

	private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd,HH:mm");

	private static NumberFormat nf = NumberFormat.getInstance();
	static {
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		nf.setGroupingUsed(false);
	}

	private static NumberFormat manf = NumberFormat.getInstance();
	static {
		manf.setMinimumFractionDigits(4);
		manf.setMaximumFractionDigits(4);
		manf.setGroupingUsed(false);
	}

	private String scripName;

	private LocalDateTime dateTime;

	private List<MovingAverageAndValue> movingAverages = new ArrayList<MovingAverageAndValue>(MovingAverage.values().length);

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
			setMovingAverage(MovingAverage._5, Float.parseFloat(unitPriceDetailsArray[7]));
			setMovingAverage(MovingAverage._13, Float.parseFloat(unitPriceDetailsArray[8]));
			setMovingAverage(MovingAverage._26, Float.parseFloat(unitPriceDetailsArray[9]));
			setMovingAverage(MovingAverage._50, Float.parseFloat(unitPriceDetailsArray[10]));
			setMovingAverage(MovingAverage._100, Float.parseFloat(unitPriceDetailsArray[11]));
			setMovingAverage(MovingAverage._200, Float.parseFloat(unitPriceDetailsArray[12]));
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

	public List<MovingAverageAndValue> getMovingAverages() {
		return movingAverages;
	}

	public float getMovingAverage(int ma) {
		for (MovingAverageAndValue movingAverageAndValue : movingAverages) {
			if (movingAverageAndValue.getMA() == ma) {
				return movingAverageAndValue.getValue();
			}
		}
		throw new RuntimeException("Unable to obtain Moving Average for " + ma);
	}

	public void setMovingAverages(List<MovingAverageAndValue> movingAverages) {
		Collections.sort(movingAverages);
		this.movingAverages = movingAverages;
	}

	public void setMovingAverage(MovingAverage ma, float value) {
		this.movingAverages.add(new MovingAverageAndValue(ma, value));
		Collections.sort(movingAverages);
	}

	/*
	 * This method is used to determine at which value the trade has actually happened. This defaults to the Closing Price, but some traders tend to use the Open as well.
	 */
	public float getTradedValue() {
		return getClose();
	}

	@Override
	public String toString() {
		return scripName + "," + df.format(dateTime) + "," + nf.format(open) + "," + nf.format(high) + "," + nf.format(low) + "," + nf.format(close);
	}

	public String printMovingAverages() {
		String movingAveragesString = "";
		for (MovingAverageAndValue movingAverageAndValue : movingAverages) {
			movingAveragesString += ("," + manf.format(movingAverageAndValue.getValue()));
		}
		return movingAveragesString;
	}
}
