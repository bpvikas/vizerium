package com.vizerium.commons.indicators;

public class RSI implements Indicator {

	private int lookbackPeriod;

	private MovingAverageType maType;

	private float[] values;

	private static final int DEFAULT_LOOKBACK_PERIOD = 14;

	private static final MovingAverageType DEFAULT_MOVING_AVERAGE_TYPE = MovingAverageType.WELLESWILDER;

	public RSI() {
		this.lookbackPeriod = DEFAULT_LOOKBACK_PERIOD;
		this.maType = DEFAULT_MOVING_AVERAGE_TYPE;
	}

	public RSI(int lookbackPeriod, MovingAverageType maType) {
		this.lookbackPeriod = lookbackPeriod;
		this.maType = maType;
	}

	public int getLookbackPeriod() {
		return lookbackPeriod;
	}

	public MovingAverageType getMaType() {
		return maType;
	}

	public float[] getValues() {
		return values;
	}

	public void setValues(float[] values) {
		this.values = values;
	}

	@Override
	public float[] getUnitPriceIndicator(int position) {
		return new float[] { lookbackPeriod, values[position] };
	}
}
