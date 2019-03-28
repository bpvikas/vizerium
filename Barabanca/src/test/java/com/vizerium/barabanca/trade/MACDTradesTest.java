package com.vizerium.barabanca.trade;

import java.util.List;

import com.vizerium.barabanca.historical.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;

public class MACDTradesTest extends TradeStrategyTest {

	@Override
	protected void getAdditionalDataPriorToIteration(String scripName, TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean testForCurrentUnitGreaterThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(String scripName, TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean testForCurrentUnitLessThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(String scripName, TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		// TODO Auto-generated method stub

	}

}
