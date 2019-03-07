package com.vizerium.barabanca.historical;

import com.vizerium.barabanca.trade.Trade;
import com.vizerium.barabanca.trade.TradeBook;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.trade.TradeAction;

public class ClosingPricesWithStopAndReverseTest extends ClosingPricesTest {

	protected TradeAction currentTradeAction;

	@Override
	protected void executeForCurrentPriceGreaterThanPreviousPrice(String scripName, TradeBook tradeBook, UnitPriceData unitPriceData) {
		if (TradeAction.LONG != currentTradeAction) {
			tradeBook.coverShortTrade(unitPriceData);
			currentTradeAction = TradeAction.LONG;
			tradeBook.addLongTrade(new Trade(scripName, currentTradeAction, unitPriceData.getDateTime(), unitPriceData.getClose()));
		}
	}

	@Override
	protected void executeForCurrentPriceLessThanPreviousPrice(String scripName, TradeBook tradeBook, UnitPriceData unitPriceData) {
		if (TradeAction.SHORT != currentTradeAction) {
			tradeBook.exitLongTrade(unitPriceData);
			currentTradeAction = TradeAction.SHORT;
			tradeBook.addShortTrade(new Trade(scripName, currentTradeAction, unitPriceData.getDateTime(), unitPriceData.getClose()));
		}
	}

	@Override
	protected void getAdditionalDataPriorToIteration(String scripName, int year, TimeFormat trendTimeFormat) {
		currentTradeAction = null;
	}
}
