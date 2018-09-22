package com.vizerium.payoffmatrix.historical;

import java.time.LocalDate;

public class DayPriceData {

	private LocalDate date;

	private String scripName;

	private String series;

	private float open;

	private float high;

	private float low;

	private float close;

	private float last;

	private float prevClose;

	private long volume;

	private float percentageChange;

	public DayPriceData() {

	}

	/*
	 * This constructor is used to get the TEI data format into the DayPriceData object.
	 */
	public DayPriceData(LocalDate date, String scripName, String series, float open, float high, float low, float close, float last, float prevClose, long volume) {
		this.date = date;
		this.scripName = scripName;
		this.series = series;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.last = last;
		this.prevClose = prevClose;
		this.volume = volume;
	}

	/*
	 * This constructor is used to get the Inv data format into the DayPriceData object.
	 */
	public DayPriceData(LocalDate date, String scripName, float open, float high, float low, float close) {
		this.date = date;
		this.scripName = scripName;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getScripName() {
		return scripName;
	}

	public void setScripName(String scripName) {
		this.scripName = scripName;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
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

	public float getLast() {
		return last;
	}

	public void setLast(float last) {
		this.last = last;
	}

	public float getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(float prevClose) {
		this.prevClose = prevClose;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public float getPercentageChange() {
		return percentageChange;
	}

	public void setPercentageChange(float percentageChange) {
		this.percentageChange = percentageChange;
	}

	@Override
	public String toString() {
		return "DayPriceData [date=" + date + ", scripName=" + scripName + ", series=" + series + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close
				+ ", last=" + last + ", prevClose=" + prevClose + ", volume=" + volume + "]";
	}
}
