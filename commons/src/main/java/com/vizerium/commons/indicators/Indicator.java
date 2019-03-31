package com.vizerium.commons.indicators;

public interface Indicator {
	public float[] getUnitPriceIndicator(int position);

	public int getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues();

	public IndicatorCalculator<? extends Indicator> getCalculator();
}