package com.vizerium.commons.indicators;

public class DirectionalSystem {

	private float dm;

	private float atr;

	private float plusDI;

	private float minusDI;

	private float smoothedPlusDI;

	private float smoothedMinusDI;

	private float dx;

	private float adx;

	private float smoothingPeriod;

	private MovingAverageType smoothingMAType;

	private static final MovingAverageType DEFAULT_SMOOTHING_MA_TYPE = MovingAverageType.SIMPLE;

	private static final int DEFAULT_SMOOTHING_PERIOD_COUNT = 13;

	public DirectionalSystem() {
		this.smoothingPeriod = DEFAULT_SMOOTHING_PERIOD_COUNT;
		this.smoothingMAType = DEFAULT_SMOOTHING_MA_TYPE;
	}

	public float getDM() {
		return dm;
	}

	public void setDM(float dm) {
		this.dm = dm;
	}

	public float getAtr() {
		return atr;
	}

	public void setAtr(float atr) {
		this.atr = atr;
	}

	public float getPlusDI() {
		return plusDI;
	}

	public void setPlusDI(float plusDI) {
		this.plusDI = plusDI;
	}

	public float getMinusDI() {
		return minusDI;
	}

	public void setMinusDI(float minusDI) {
		this.minusDI = minusDI;
	}

	public float getSmoothedPlusDI() {
		return smoothedPlusDI;
	}

	public void setSmoothedPlusDI(float smoothedPlusDI) {
		this.smoothedPlusDI = smoothedPlusDI;
	}

	public float getSmoothedMinusDI() {
		return smoothedMinusDI;
	}

	public void setSmoothedMinusDI(float smoothedMinusDI) {
		this.smoothedMinusDI = smoothedMinusDI;
	}

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getAdx() {
		return adx;
	}

	public void setAdx(float adx) {
		this.adx = adx;
	}

	public float getSmoothingPeriod() {
		return smoothingPeriod;
	}

	public void setSmoothingPeriod(float smoothingPeriod) {
		this.smoothingPeriod = smoothingPeriod;
	}

	public MovingAverageType getSmoothingMAType() {
		return smoothingMAType;
	}

	public void setSmoothingMAType(MovingAverageType smoothingMAType) {
		this.smoothingMAType = smoothingMAType;
	}
}
