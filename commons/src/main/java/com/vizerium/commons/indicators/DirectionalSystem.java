package com.vizerium.commons.indicators;

public class DirectionalSystem implements Indicator {

	private float[] smoothedPlusDI;

	private float[] smoothedMinusDI;

	private float[] adx;

	private int smoothingPeriod;

	private MovingAverageType movingAverageType;

	private static final int DEFAULT_SMOOTHING_PERIOD_COUNT = 14;
	private static final MovingAverageType DEFAULT_SMOOTHING_MA_TYPE = MovingAverageType.WELLESWILDER;

	public DirectionalSystem() {
		this.smoothingPeriod = DEFAULT_SMOOTHING_PERIOD_COUNT;
		this.movingAverageType = DEFAULT_SMOOTHING_MA_TYPE;
	}

	public float[] getSmoothedPlusDI() {
		return smoothedPlusDI;
	}

	public void setSmoothedPlusDI(float[] smoothedPlusDIArray) {
		this.smoothedPlusDI = smoothedPlusDIArray;
	}

	public float[] getSmoothedMinusDI() {
		return smoothedMinusDI;
	}

	public void setSmoothedMinusDI(float[] smoothedMinusDI) {
		this.smoothedMinusDI = smoothedMinusDI;
	}

	public float[] getAdx() {
		return adx;
	}

	public void setAdx(float[] adx) {
		this.adx = adx;
	}

	public int getSmoothingPeriod() {
		return smoothingPeriod;
	}

	public void setSmoothingPeriod(int smoothingPeriod) {
		this.smoothingPeriod = smoothingPeriod;
	}

	public MovingAverageType getMovingAverageType() {
		return movingAverageType;
	}

	public void setMovingAverageType(MovingAverageType movingAverageType) {
		this.movingAverageType = movingAverageType;
	}

	@Override
	public float[] getUnitPriceIndicator(int position) {
		return new float[] { smoothingPeriod, smoothedPlusDI[position], smoothedMinusDI[position], adx[position] };
	}

	@Override
	public int getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues() {
		// The value for the lookbackPeriod needs to be a sum of the first smoothing (to get +DI -DI) and the smoothing period (to get ADX) and 1 (to get the initial true
		// range) so that we can get the calculations correct.
		return DirectionalSystemCalculator.DIRECTIONAL_MOVEMENT_CALCULATION_START + getSmoothingPeriod() + getSmoothingPeriod();
	}
}
