package com.vizerium.commons.indicators;

import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public interface IndicatorCalculator<E extends Indicator> {

	public E calculate(List<? extends UnitPrice> unitPrices, E indicator);
}
