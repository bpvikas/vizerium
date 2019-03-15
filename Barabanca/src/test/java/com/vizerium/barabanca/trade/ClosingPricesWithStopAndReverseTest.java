package com.vizerium.barabanca.trade;

import com.vizerium.barabanca.historical.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.trade.TradeAction;

public class ClosingPricesWithStopAndReverseTest extends ClosingPricesTest {

	protected TradeAction currentTradeAction;

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(String scripName, TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (TradeAction.LONG != currentTradeAction) {
			tradeBook.coverShortTrade(current);
			currentTradeAction = TradeAction.LONG;
			tradeBook.addLongTrade(new Trade(scripName, currentTradeAction, current.getDateTime(), current.getTradedValue()));
		}
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(String scripName, TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (TradeAction.SHORT != currentTradeAction) {
			tradeBook.exitLongTrade(current);
			currentTradeAction = TradeAction.SHORT;
			tradeBook.addShortTrade(new Trade(scripName, currentTradeAction, current.getDateTime(), current.getTradedValue()));
		}
	}

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(UnitPriceData current, UnitPriceData previous) {

	}

	@Override
	protected void getAdditionalDataPriorToIteration(String scripName, int year, int month, TimeFormat timeFormat) {
		currentTradeAction = null;
	}
}