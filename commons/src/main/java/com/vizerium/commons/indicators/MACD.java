package com.vizerium.commons.indicators;

public class MACD {

	// The default value for the fast MA for the MACD.
	private static final int DEFAULT_FAST_MA = 13;

	// The default value for the slow MA for the MACD.
	private static final int DEFAULT_SLOW_MA = 26;

	private static final MovingAverageType DEFAULT_SMOOTHING_MA_TYPE = MovingAverageType.SIMPLE;

	private static final int DEFAULT_SMOOTHING_PERIOD_COUNT = 9;

	private MovingAverageAndValue fastMA;

	private MovingAverageAndValue slowMA;

	private MovingAverageType smoothingMAType;

	private int smoothingPeriod;

	private float signalValue = Float.NaN;

	public MACD() {
		this.fastMA = new MovingAverageAndValue(MovingAverage.getMAByNumber(DEFAULT_FAST_MA), 0.0f);
		this.slowMA = new MovingAverageAndValue(MovingAverage.getMAByNumber(DEFAULT_SLOW_MA), 0.0f);
		this.smoothingMAType = DEFAULT_SMOOTHING_MA_TYPE;
		this.smoothingPeriod = DEFAULT_SMOOTHING_PERIOD_COUNT;
	}

	public MACD(int fastMANumber, int slowMANumber, MovingAverageType maType, int smoothingPeriod) {
		this.fastMA = new MovingAverageAndValue(MovingAverage.getMAByNumber(fastMANumber), 0.0f);
		this.slowMA = new MovingAverageAndValue(MovingAverage.getMAByNumber(slowMANumber), 0.0f);
		this.smoothingMAType = maType;
		this.smoothingPeriod = smoothingPeriod;
	}

	public MovingAverageAndValue getFastMA() {
		return fastMA;
	}

	public void setFastMA(MovingAverageAndValue fastMA) {
		this.fastMA = fastMA;
	}

	public MovingAverageAndValue getSlowMA() {
		return slowMA;
	}

	public void setSlowMA(MovingAverageAndValue slowMA) {
		this.slowMA = slowMA;
	}

	public float getValue() {
		return fastMA.getValue() - slowMA.getValue();
	}

	public float getSignalValue() {
		return signalValue;
	}

	public void setSignalValue(float signalValue) {
		this.signalValue = signalValue;
	}

	public float getHistogramLength() {
		return (signalValue != Float.NaN) ? getValue() - signalValue : Float.NaN;
	}

	public MovingAverageType getSmoothingMAType() {
		return smoothingMAType;
	}

	public int getSmoothingPeriod() {
		return smoothingPeriod;
	}

	public String toString() {
		return "MACD[" + fastMA.getMA() + "," + slowMA.getMA() + "," + smoothingPeriod + "," + smoothingMAType.toString() + " -> " + getValue() + ", signal " + signalValue
				+ ", histogram " + getHistogramLength() + "]";
	}
}
