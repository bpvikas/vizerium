package com.vizerium.barabanca.trade;

import com.vizerium.barabanca.historical.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.trade.TradeAction;

public abstract class EMACrossoverTest extends TradeStrategyTest {

	protected TradeAction currentTradeAction;

	protected abstract int getFastMA();

	protected abstract int getSlowMA();

	@Override
	protected void getAdditionalDataPriorToIteration(String scripName, int year, TimeFormat timeFormat) {
		currentTradeAction = null;
	}

	@Override
	protected boolean testForCurrentUnitGreaterThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		return ((current.getMovingAverage(getFastMA()) > current.getMovingAverage(getSlowMA())) && (previous.getMovingAverage(getFastMA()) < current.getMovingAverage(getSlowMA())));
	}

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(String scripName, TradeBook tradeBook, UnitPriceData unitPriceData) {
		if (TradeAction.LONG != currentTradeAction) {
			tradeBook.coverShortTrade(unitPriceData);
			currentTradeAction = TradeAction.LONG;
			tradeBook.addLongTrade(new Trade(scripName, currentTradeAction, unitPriceData.getDateTime(), unitPriceData.getClose()));
		}
	}

	@Override
	protected boolean testForCurrentUnitLessThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		return ((current.getMovingAverage(getFastMA()) < current.getMovingAverage(getSlowMA())) && (previous.getMovingAverage(getFastMA()) > current.getMovingAverage(getSlowMA())));
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(String scripName, TradeBook tradeBook, UnitPriceData unitPriceData) {
		if (TradeAction.SHORT != currentTradeAction) {
			tradeBook.exitLongTrade(unitPriceData);
			currentTradeAction = TradeAction.SHORT;
			tradeBook.addShortTrade(new Trade(scripName, currentTradeAction, unitPriceData.getDateTime(), unitPriceData.getClose()));
		}
	}

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(UnitPriceData current, UnitPriceData previous) {

	}
}
