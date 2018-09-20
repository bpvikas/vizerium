package com.vizerium.payoffmatrix.historical;

import java.time.LocalDate;

public class DayPriceData {

	private LocalDate date;

	private String scripName;

	private String series;

	private String open;

	private String high;

	private String low;

	private String close;

	private String last;

	private String prevClose;

	private String volume;

	public DayPriceData() {

	}

	public DayPriceData(LocalDate date, String scripName, String series, String open, String high, String low, String close, String last, String prevClose, String volume) {
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

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(String prevClose) {
		this.prevClose = prevClose;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	@Override
	public String toString() {
		return "DayPriceData [date=" + date + ", scripName=" + scripName + ", series=" + series + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close
				+ ", last=" + last + ", prevClose=" + prevClose + ", volume=" + volume + "]";
	}
}
