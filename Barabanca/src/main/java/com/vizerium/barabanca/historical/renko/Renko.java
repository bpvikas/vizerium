package com.vizerium.barabanca.historical.renko;

import java.text.NumberFormat;
import java.time.LocalDateTime;

import com.vizerium.barabanca.historical.TimeFormat;
import com.vizerium.commons.util.NumberFormats;

public class Renko implements Cloneable {

	private float brickSize;

	private float startPrice;

	private float endPrice;

	private LocalDateTime startDateTime;

	private LocalDateTime endDateTime;

	private TimeFormat timeFormat;

	private String scripName;

	private boolean smoothPriceRange;

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

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDate) {
		this.startDateTime = startDate;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDate) {
		this.endDateTime = endDate;
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

	public boolean isSmoothPriceRange() {
		return smoothPriceRange;
	}

	public void setSmoothPriceRange(boolean smoothPriceRange) {
		this.smoothPriceRange = smoothPriceRange;
	}

	public boolean isUp() {
		return startPrice < endPrice;
	}

	public boolean isDown() {
		return !isUp();
	}

	public boolean isPriceWithinRange(float price) {
		return (isUp()) ? ((startPrice < price && price < endPrice) ? true : false) : ((startPrice >= price && price >= endPrice) ? true : false);
	}

	@Override
	public Renko clone() {
		Renko other = new Renko();
		other.brickSize = this.brickSize;
		other.startPrice = this.startPrice;
		other.endPrice = this.endPrice;
		other.startDateTime = this.startDateTime;
		other.endDateTime = this.endDateTime;
		other.timeFormat = this.timeFormat;
		other.scripName = this.scripName;
		return other;
	}

	@Override
	public String toString() {
		NumberFormat pnf = NumberFormats.getForPrice();
		return "Renko[" + scripName + "," + pnf.format(brickSize) + "," + (isUp() ? "Up" : "Down") + "\tstart " + startDateTime + "@" + pnf.format(startPrice) + "\t\tend "
				+ endDateTime + "@" + pnf.format(endPrice) + "]";
	}
}
