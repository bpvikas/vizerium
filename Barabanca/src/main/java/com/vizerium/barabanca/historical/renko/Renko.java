package com.vizerium.barabanca.historical.renko;

import com.vizerium.barabanca.historical.TimeFormat;

public class Renko {

	private float brickSize;

	private float startPrice;

	private float endPrice;

	private float startDate;

	private float endDate;

	private TimeFormat timeFormat;

	private String scripName;

	public Renko() {

	}

	public float getBrickSize() {
		return brickSize;
	}

	public void setBrickSize(float brickSize) {
		this.brickSize = brickSize;
	}

	public float getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(float startPrice) {
		this.startPrice = startPrice;
	}

	public float getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(float endPrice) {
		this.endPrice = endPrice;
	}

	public float getStartDate() {
		return startDate;
	}

	public void setStartDate(float startDate) {
		this.startDate = startDate;
	}

	public float getEndDate() {
		return endDate;
	}

	public void setEndDate(float endDate) {
		this.endDate = endDate;
	}

	public TimeFormat getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(TimeFormat timeFormat) {
		this.timeFormat = timeFormat;
	}

	public String getScripName() {
		return scripName;
	}

	public void setScripName(String scripName) {
		this.scripName = scripName;
	}

	public boolean isUp() {
		return startPrice > endPrice;
	}

	public boolean isDown() {
		return !isUp();
	}
}
