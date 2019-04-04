package com.vizerium.commons.indicators;

import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public class RSI implements Indicator<RSI> {

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

	@Override
	public int getUnitPriceIndicatorValuesLength() {
		return 2;
	}

	@Override
	public int getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues() {
		// The value for the lookbackPeriod needs to be a sum of the start point of the gain loss calculation and the lookback period.
		return RSICalculator.AVERAGE_GAIN_LOSS_CALCULATION_START + lookbackPeriod;
	}

	@Override
	public RSI calculate(List<? extends UnitPrice> unitPrices) {
		RSICalculator rsiCalculator = new RSICalculator();
		return rsiCalculator.calculate(unitPrices, this);

	}

	@Override
	public String getName() {
		return getClass().getSimpleName() + "[" + lookbackPeriod + "]";
	}
}