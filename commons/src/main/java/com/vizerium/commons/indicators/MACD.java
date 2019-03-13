package com.vizerium.commons.indicators;

public class MACD {

	// The default value for the fast MA for the MACD.
	private static final int DEFAULT_FAST_MA = 13;

	// The default value for the slow MA for the MACD.
	private static final int DEFAULT_SLOW_MA = 26;

	private static final int DEFAULT_SMOOTHING_PERIOD_COUNT = 9;

	private static final MovingAverageType DEFAULT_SMOOTHING_MA_TYPE = MovingAverageType.EXPONENTIAL;

	private MovingAverageAndValue fastMA;

	private MovingAverageAndValue slowMA;

	private int smoothingPeriod;

	private float signalValue;

	public MACD() {
		fastMA = new MovingAverageAndValue(MovingAverage.getMAByNumber(DEFAULT_FAST_MA), 0.0f);
		slowMA = new MovingAverageAndValue(MovingAverage.getMAByNumber(DEFAULT_SLOW_MA), 0.0f);
		smoothingPeriod = DEFAULT_SMOOTHING_PERIOD_COUNT;
	}

	public MovingAverageAndValue getFastMA() {
		return fastMA;
	}

	public MovingAverageAndValue getSlowMA() {
		return slowMA;
	}

	public float getValue() {
		return fastMA.getValue() - slowMA.getValue();
	}

	public float getSignalValue() {
		return signalValue;
	}

	public float getHistogramLength() {
		return signalValue - getValue();
	}

	public String toString() {
		return "MACD[" + fastMA.getMA() + "," + slowMA.getMA() + "," + smoothingPeriod + " -> " + getValue() + ", signal " + signalValue + ", histogram " + getHistogramLength()
				+ "]";
	}
}
