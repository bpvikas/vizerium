package com.vizerium.barabanca.historical;

public class PriceRange {

	private float low;

	private float high;

	public PriceRange(float low, float high) {
		if (high < low) {
			throw new RuntimeException("high " + high + " cannot be less than low " + low);
		}
		this.low = low;
		this.high = high;
	}

	public float getLow() {
		return low;
	}

	public float getHigh() {
		return high;
	}

	public boolean isPriceWithinRange(float price) {
		return price >= low && price < high;
	}

	@Override
	public String toString() {
		return low + " -> " + high;
	}
}
