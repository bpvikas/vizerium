package com.vizerium.commons.indicators;

public class Stochastic implements Indicator {

	// The default value for the look back period for the Stochastic indicator.
	public static final int DEFAULT_STOCHASTIC_LOOK_BACK_PERIOD = 14;
	private static final int DEFAULT_MA_PERIOD_COUNT = 3;
	private static final MovingAverageType DEFAULT_MA_TYPE = MovingAverageType.EXPONENTIAL;

	private int lookbackPeriod;

	private float[] fastPercentKArray;

	private float[] slowPercentKArray;

	private float[] slowPercentDArray;

	private int maPeriodCountForCalculatingDFromK;

	private MovingAverageType maTypeForCalculatingDFromK;

	private int maPeriodCountForCalculatingSlowFromFast;

	private MovingAverageType maTypeForCalculatingSlowFromFast;

	public Stochastic() {
		this.lookbackPeriod = DEFAULT_STOCHASTIC_LOOK_BACK_PERIOD;
		maPeriodCountForCalculatingDFromK = DEFAULT_MA_PERIOD_COUNT;
		maTypeForCalculatingDFromK = DEFAULT_MA_TYPE;
		maPeriodCountForCalculatingSlowFromFast = DEFAULT_MA_PERIOD_COUNT;
		maTypeForCalculatingSlowFromFast = DEFAULT_MA_TYPE;
	}

	public Stochastic(int lookbackPeriod) {
		this();
		this.lookbackPeriod = lookbackPeriod;
	}

	public Stochastic(int lookbackPeriod, int maPeriodCount, MovingAverageType maType) {
		this(lookbackPeriod);
		this.maPeriodCountForCalculatingDFromK = maPeriodCount;
		this.maTypeForCalculatingDFromK = maType;
		this.maPeriodCountForCalculatingSlowFromFast = maPeriodCount;
		this.maTypeForCalculatingSlowFromFast = maType;
	}

	public Stochastic(int lookbackPeriod, int maPeriodCountForCalculatingDFromK, MovingAverageType maTypeForCalculatingDFromK, int maPeriodCountForCalculatingSlowFromFast,
			MovingAverageType maTypeForCalculatingSlowFromFast) {
		this(lookbackPeriod);
		this.maPeriodCountForCalculatingDFromK = maPeriodCountForCalculatingDFromK;
		this.maTypeForCalculatingDFromK = maTypeForCalculatingDFromK;
		this.maPeriodCountForCalculatingSlowFromFast = maPeriodCountForCalculatingSlowFromFast;
		this.maTypeForCalculatingSlowFromFast = maTypeForCalculatingSlowFromFast;
	}

	public int getLookbackPeriod() {
		return lookbackPeriod;
	}

	public float[] getFastPercentKArray() {
		return fastPercentKArray;
	}

	public void setFastPercentKArray(float[] fastPercentKArray) {
		this.fastPercentKArray = fastPercentKArray;
	}

	public float[] getSlowPercentKArray() {
		return slowPercentKArray;
	}

	public void setSlowPercentKArray(float[] slowPercentKArray) {
		this.slowPercentKArray = slowPercentKArray;
	}

	/*
	 * As per the Stochastic calculations, the slow %K is the fast%D array while double smoothing.
	 */
	public float[] getFastPercentDArray() {
		return getSlowPercentKArray();
	}

	/*
	 * As per the Stochastic calculations, the slow %K is the fast%D array while double smoothing.
	 */
	public void setFastPercentDArray(float[] slowPercentKArray) {
		setSlowPercentKArray(slowPercentKArray);
	}

	public float[] getSlowPercentDArray() {
		return slowPercentDArray;
	}

	public void setSlowPercentDArray(float[] slowPercentDArray) {
		this.slowPercentDArray = slowPercentDArray;
	}

	public int getMaPeriodCountForCalculatingDFromK() {
		return maPeriodCountForCalculatingDFromK;
	}

	public MovingAverageType getMaTypeForCalculatingDFromK() {
		return maTypeForCalculatingDFromK;
	}

	public int getMaPeriodCountForCalculatingSlowFromFast() {
		return maPeriodCountForCalculatingSlowFromFast;
	}

	public MovingAverageType getMaTypeForCalculatingSlowFromFast() {
		return maTypeForCalculatingSlowFromFast;
	}

	@Override
	public float[] getUnitPriceIndicator(int position) {
		return new float[] { lookbackPeriod, maPeriodCountForCalculatingSlowFromFast, maPeriodCountForCalculatingDFromK, fastPercentKArray[position], slowPercentKArray[position],
				slowPercentKArray[position], slowPercentDArray[position] };
	}
}
