package com.vizerium.barabanca.trade;

import java.util.List;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;

public class MACDTradesTest extends TradeStrategyTest {

	@Override
	protected void getAdditionalDataPriorToIteration(TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean testForCurrentUnitGreaterThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean testForCurrentUnitLessThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getPreviousResultFileName() {
		// TODO Auto-generated method stub
		return null;
	}
}
