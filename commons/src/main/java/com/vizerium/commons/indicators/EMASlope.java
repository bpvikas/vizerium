package com.vizerium.commons.indicators;

public class EMASlope implements Indicator {

	@Override
	public float[] getUnitPriceIndicator(int position) {
		throw new UnsupportedOperationException("This method is not applicable for EMA Slope indicator.");
	}

	@Override
	public int getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues() {
		return 3;
	}

	@Override
	public IndicatorCalculator<? extends Indicator> getCalculator() {
		throw new UnsupportedOperationException("This method is not applicable for EMA Slope indicator.");
	}
}
