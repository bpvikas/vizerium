package com.vizerium.barabanca.historical.renko;

import java.time.LocalDateTime;

import com.vizerium.barabanca.historical.TimeFormat;

public class Renko implements Cloneable {

	private float brickSize;

	private float startPrice;

	private float endPrice;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

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

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
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

	public boolean isPriceWithinRange(float price) {
		return (isUp()) ? ((startPrice > price && price > endPrice) ? true : false) : ((startPrice <= price && price <= endPrice) ? true : false);
	}

	@Override
	public Renko clone() {
		Renko other = new Renko();
		other.brickSize = this.brickSize;
		other.startPrice = this.startPrice;
		other.endPrice = this.endPrice;
		other.startDate = this.startDate;
		other.endDate = this.endDate;
		other.timeFormat = this.timeFormat;
		other.scripName = this.scripName;
		return other;
	}
}
