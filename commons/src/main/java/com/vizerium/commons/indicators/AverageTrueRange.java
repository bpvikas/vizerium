package com.vizerium.commons.indicators;

public class AverageTrueRange implements Indicator {

	private int smoothingPeriod;

	private MovingAverageType smoothingMAType;

	private float[] values;

	private static final int DEFAULT_SMOOTHING_PERIOD = 14;
	private static final MovingAverageType DEFAULT_SMOOTHING_MA_TYPE = MovingAverageType.WELLESWILDER;

	public AverageTrueRange() {
		this.smoothingPeriod = DEFAULT_SMOOTHING_PERIOD;
		this.smoothingMAType = DEFAULT_SMOOTHING_MA_TYPE;
	}

	public AverageTrueRange(int smoothingPeriod, MovingAverageType smoothingMAType) {
		this.smoothingPeriod = smoothingPeriod;
		this.smoothingMAType = smoothingMAType;
	}

	public int getSmoothingPeriod() {
		return smoothingPeriod;
	}

	public MovingAverageType getSmoothingMAType() {
		return smoothingMAType;
	}

	public float[] getValues() {
		return values;
	}

	public void setValues(float[] values) {
		this.values = values;
	}

	@Override
	public float[] getUnitPriceIndicator(int position) {
		return new float[] { smoothingPeriod, values[position] };
	}

	@Override
	public int getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues() {
		// The value for the lookbackPeriod needs to be a sum of the start point of the gain loss calculation and the smoothing period.
		return AverageTrueRangeCalculator.TRUE_RANGE_CALCULATION_START + smoothingPeriod;
	}

	@Override
	public AverageTrueRangeCalculator getCalculator() {
		return new AverageTrueRangeCalculator();
	}
}
