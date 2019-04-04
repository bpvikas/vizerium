package com.vizerium.barabanca.trade;

import java.util.List;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.trade.TradeAction;

public class ClosingPricesWithStopAndReverseTest extends ClosingPricesTest {

	protected TradeAction currentTradeAction;

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (TradeAction.LONG != currentTradeAction) {
			tradeBook.coverShortTrade(current);
			currentTradeAction = TradeAction.LONG;
			tradeBook.addLongTrade(current);
		}
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (TradeAction.SHORT != currentTradeAction) {
			tradeBook.exitLongTrade(current);
			currentTradeAction = TradeAction.SHORT;
			tradeBook.addShortTrade(current);
		}
	}

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(UnitPriceData current, UnitPriceData previous) {

	}

	@Override
	protected void getAdditionalDataPriorToIteration(TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		currentTradeAction = null;
	}

	@Override
	protected String getPreviousResultFileName() {
		return "SR";
	}
}