package com.vizerium.barabanca.trade;

import com.vizerium.commons.dao.UnitPriceData;

public abstract class ClosingPricesTest extends TradeStrategyTest {

	@Override
	protected boolean testForCurrentUnitGreaterThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		return higherClose(current, previous);
	}

	@Override
	protected boolean testForCurrentUnitLessThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		return lowerClose(current, previous);
	}
}
