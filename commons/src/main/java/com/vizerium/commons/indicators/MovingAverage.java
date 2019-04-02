package com.vizerium.commons.indicators;

import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public class MovingAverage implements Indicator<MovingAverage> {
	private int ma;
	private MovingAverageType type;
	private float[] values;

	public MovingAverage(int ma, MovingAverageType type) {
		this.ma = ma;
		this.type = type;
	}

	public int getMA() {
		return ma;
	}

	public MovingAverageType getType() {
		return type;
	}

	public float[] getValues() {
		return values;
	}

	public void setValues(float[] values) {
		this.values = values;
	}

	@Override
	public float[] getUnitPriceIndicator(int position) {
		return new float[] { ma, values[position] };
	}

	@Override
	public int getUnitPriceIndicatorValuesLength() {
		return 2;
	}

	@Override
	public int getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues() {
		return ma;
	}

	@Override
	public MovingAverage calculate(List<? extends UnitPrice> unitPrices) {
		MovingAverageCalculator maCalculator = new MovingAverageCalculator();
		return maCalculator.calculate(unitPrices, this);
	}

	@Override
	public String getName() {
		return "[" + ma + type.toString() + "]";
	}
}
