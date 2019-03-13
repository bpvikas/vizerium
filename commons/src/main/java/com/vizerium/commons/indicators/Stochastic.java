package com.vizerium.commons.indicators;

public class Stochastic {

	// The default value for the look back period for the Stochastic indicator.
	public static final int DEFAULT_STOCHASTIC_LOOK_BACK_PERIOD = 14;
	private static final int DEFAULT_MA_PERIOD_COUNT = 3;
	private static final MovingAverageType DEFAULT_MA_TYPE = MovingAverageType.SIMPLE;

	private int lookbackPeriod;

	private float fastPercentD;

	private float fastPercentK;

	private float slowPercentD;

	private float slowPercentK;

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

	public float getFastPercentD() {
		return fastPercentD;
	}

	public float getFastPercentK() {
		return fastPercentK;
	}

	public float getSlowPercentD() {
		return slowPercentD;
	}

	public float getSlowPercentK() {
		return slowPercentK;
	}

	void setFastPercentD(float fastPercentD) {
		this.fastPercentD = fastPercentD;
	}

	void setFastPercentK(float fastPercentK) {
		this.fastPercentK = fastPercentK;
	}

	void setSlowPercentD(float slowPercentD) {
		this.slowPercentD = slowPercentD;
	}

	void setSlowPercentK(float slowPercentK) {
		this.slowPercentK = slowPercentK;
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
	public String toString() {
		return "Stochastic [lookbackPeriod=" + lookbackPeriod + ", fast %K=" + fastPercentK + ", fast %D=" + fastPercentD + ", slow %K=" + slowPercentK + ", slow %D="
				+ slowPercentD + "]";
	}
}
