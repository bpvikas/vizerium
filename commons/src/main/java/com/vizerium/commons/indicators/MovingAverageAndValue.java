package com.vizerium.commons.indicators;

public class MovingAverageAndValue implements Comparable<MovingAverageAndValue>, Indicator {
	private int ma;
	private float value;

	public MovingAverageAndValue() {

	}

	public MovingAverageAndValue(MovingAverage ma, float value) {
		this.ma = ma.getNumber();
		this.value = value;
	}

	public int getMA() {
		return ma;
	}

	public float getValue() {
		return value;
	}

	@Override
	public int compareTo(MovingAverageAndValue other) {
		return (ma < other.ma) ? -1 : ((ma == other.ma) ? 0 : 1);
	}

	public String toString() {
		return ma + "MA : " + value;
	}

	@Override
	public float[] getUnitPriceIndicator(int position) {
		return new float[] { ma, value };
	}

	@Override
	public int getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues() {
		return ma;
	}
}
