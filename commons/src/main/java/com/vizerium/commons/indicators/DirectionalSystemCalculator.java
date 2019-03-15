package com.vizerium.commons.indicators;

import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public class DirectionalSystemCalculator {

	public DirectionalSystem calculate(List<UnitPrice> unitPrices) {
		return calculate(unitPrices, new DirectionalSystem());
	}

	public DirectionalSystem calculate(List<UnitPrice> unitPrices, DirectionalSystem dms) {
		return null;
	}
}
