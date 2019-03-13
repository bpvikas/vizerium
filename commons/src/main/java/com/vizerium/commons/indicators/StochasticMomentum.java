package com.vizerium.commons.indicators;

public class StochasticMomentum {

	// The default value for the look back period for the Stochastic Momentum indicator.
	public static final int DEFAULT_STOCHASTIC_MOMENTUM_LOOK_BACK_PERIOD = 10;
	private static final int DEFAULT_MA_PERIOD_COUNT_FOR_SMOOTHING_K = 3;
	private static final MovingAverageType DEFAULT_MA_TYPE_FOR_CALCULATING_D_FROM_K = MovingAverageType.EXPONENTIAL;

	private int percentKLookbackPeriod;

	private int maPeriodCountForFirstSmoothingK;

	private int maPeriodCountForDoubleSmoothingK;

	private MovingAverageType maTypeForCalculatingDFromK;

	private int percentDLookbackPeriod;

	private float smi;

	private float signal;

	public StochasticMomentum() {
		this.percentKLookbackPeriod = DEFAULT_STOCHASTIC_MOMENTUM_LOOK_BACK_PERIOD;
		this.maPeriodCountForFirstSmoothingK = DEFAULT_MA_PERIOD_COUNT_FOR_SMOOTHING_K;
		this.maPeriodCountForDoubleSmoothingK = DEFAULT_MA_PERIOD_COUNT_FOR_SMOOTHING_K;
		this.maTypeForCalculatingDFromK = DEFAULT_MA_TYPE_FOR_CALCULATING_D_FROM_K;
		this.percentDLookbackPeriod = DEFAULT_STOCHASTIC_MOMENTUM_LOOK_BACK_PERIOD;
	}

	public StochasticMomentum(int percentKLookbackPeriod, int maPeriodCountForFirstSmoothingK, int maPeriodCountForDoubleSmoothingK, MovingAverageType maTypeForCalculatingDFromK,
			int percentDLookbackPeriod) {
		this.percentKLookbackPeriod = percentKLookbackPeriod;
		this.maPeriodCountForFirstSmoothingK = maPeriodCountForFirstSmoothingK;
		this.maPeriodCountForDoubleSmoothingK = maPeriodCountForDoubleSmoothingK;
		this.maTypeForCalculatingDFromK = maTypeForCalculatingDFromK;
		this.percentDLookbackPeriod = percentDLookbackPeriod;
	}

	public StochasticMomentum(MovingAverageType maTypeForCalculatingDFromK) {
		this();
		this.maTypeForCalculatingDFromK = maTypeForCalculatingDFromK;
	}

	public float getSmi() {
		return smi;
	}

	public void setSmi(float smi) {
		this.smi = smi;
	}

	public float getSignal() {
		return signal;
	}

	public void setSignal(float signal) {
		this.signal = signal;
	}

	public int getPercentKLookbackPeriod() {
		return percentKLookbackPeriod;
	}

	public int getMaPeriodCountForFirstSmoothingK() {
		return maPeriodCountForFirstSmoothingK;
	}

	public int getMaPeriodCountForDoubleSmoothingK() {
		return maPeriodCountForDoubleSmoothingK;
	}

	public MovingAverageType getMaTypeForCalculatingDFromK() {
		return maTypeForCalculatingDFromK;
	}

	public void setMaTypeForCalculatingDFromK(MovingAverageType maTypeForCalculatingDFromK) {
		this.maTypeForCalculatingDFromK = maTypeForCalculatingDFromK;
	}

	public int getPercentDLookbackPeriod() {
		return percentDLookbackPeriod;
	}

	@Override
	public String toString() {
		return "StochasticMomentum [" + percentKLookbackPeriod + ", " + maPeriodCountForFirstSmoothingK + ", " + maPeriodCountForDoubleSmoothingK + ", " + percentDLookbackPeriod
				+ ", " + maTypeForCalculatingDFromK.toString() + ", smi=" + smi + ", signal=" + signal + "]";
	}

}
