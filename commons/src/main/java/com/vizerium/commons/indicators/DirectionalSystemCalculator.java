package com.vizerium.commons.indicators;

import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public class DirectionalSystemCalculator {

	public DirectionalSystem calculate(List<? extends UnitPrice> unitPrices) {
		return calculate(unitPrices, new DirectionalSystem());
	}

	public DirectionalSystem calculate(List<? extends UnitPrice> unitPrices, DirectionalSystem ds) {

		float atr = AverageTrueRangeCalculator.calculate(unitPrices);
		ds.setAtr(atr);
		return ds;
	}
}
