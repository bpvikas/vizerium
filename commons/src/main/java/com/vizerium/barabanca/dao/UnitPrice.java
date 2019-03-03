package com.vizerium.barabanca.dao;

public class UnitPrice {

	protected float open;

	protected float high;

	protected float low;

	protected float close;

	public UnitPrice() {

	}

	public UnitPrice(float open, float high, float low, float close) {
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
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

}
