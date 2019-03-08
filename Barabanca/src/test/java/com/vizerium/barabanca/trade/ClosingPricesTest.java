package com.vizerium.barabanca.trade;

import com.vizerium.commons.dao.UnitPriceData;

public abstract class ClosingPricesTest extends TradeStrategyTest {

	@Override
	protected boolean testForCurrentUnitGreaterThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		return current.getClose() > previous.getClose();
	}

	@Override
	protected boolean testForCurrentUnitLessThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		return current.getClose() < previous.getClose();
	}
}
