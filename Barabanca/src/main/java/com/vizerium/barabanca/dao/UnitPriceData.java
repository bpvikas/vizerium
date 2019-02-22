package com.vizerium.barabanca.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UnitPriceData {

	private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd,HH:mm");

	private String scripName;

	private LocalDateTime dateTime;

	private float open;

	private float high;

	private float low;

	private float close;

	public UnitPriceData() {

	}

	public UnitPriceData(String scripName, String date, String time, String open, String high, String low, String close) {
		this.scripName = scripName;
		this.dateTime = LocalDateTime.parse(date + "," + time, df);
		this.open = Float.parseFloat(open);
		this.high = Float.parseFloat(high);
		this.low = Float.parseFloat(low);
		this.close = Float.parseFloat(close);
	}

	public UnitPriceData(String[] unitPriceDetailsArray) {
		this(unitPriceDetailsArray[0], unitPriceDetailsArray[1], unitPriceDetailsArray[2], unitPriceDetailsArray[3], unitPriceDetailsArray[4], unitPriceDetailsArray[5],
				unitPriceDetailsArray[6]);
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

	public float getOpen() {
		return open;
	}

	public void setOpen(float open) {
		this.open = open;
	}

	public float getHigh() {
		return high;
	}

	public void setHigh(float high) {
		this.high = high;
	}

	public float getLow() {
		return low;
	}

	public void setLow(float low) {
		this.low = low;
	}

	public float getClose() {
		return close;
	}

	public void setClose(float close) {
		this.close = close;
	}

	@Override
	public String toString() {
		return scripName + "," + df.format(dateTime) + "," + open + "," + high + "," + low + "," + close;
	}
}
