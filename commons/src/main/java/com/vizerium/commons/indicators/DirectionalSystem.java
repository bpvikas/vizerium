package com.vizerium.commons.indicators;

public class DirectionalSystem {

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
}
