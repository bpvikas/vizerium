package com.vizerium.commons.indicators;

import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public interface IndicatorCalculator<I extends Indicator<I>> {

	public I calculate(List<? extends UnitPrice> unitPrices, I indicator);
}
