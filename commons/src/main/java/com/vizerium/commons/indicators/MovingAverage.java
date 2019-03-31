package com.vizerium.commons.indicators;

public class MovingAverage implements Indicator {
	private int ma;
	private MovingAverageType type;
	private float[] values;

	public MovingAverage() {

	}

	public MovingAverage(int ma, MovingAverageType type) {
		this.ma = ma;
		this.type = type;
	}

	public int getMA() {
		return ma;
	}

	public MovingAverageType getType() {
		return type;
	}

	public float[] getValues() {
		return values;
	}

	public void setValues(float[] values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return ma + type.toString();
	}

	@Override
	public float[] getUnitPriceIndicator(int position) {
		return new float[] { ma, values[position] };
	}

	@Override
	public int getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues() {
		return ma;
	}

	@Override
	public MovingAverageCalculator getCalculator() {
		return new MovingAverageCalculator();
	}
}
