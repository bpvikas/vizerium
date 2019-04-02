package com.vizerium.commons.indicators;

import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public interface Indicator<I extends Indicator<I>> {

	public float[] getUnitPriceIndicator(int position);

	public int getUnitPriceIndicatorValuesLength();

	public int getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues();

	public I calculate(List<? extends UnitPrice> unitPrices);

	public String getName();
}