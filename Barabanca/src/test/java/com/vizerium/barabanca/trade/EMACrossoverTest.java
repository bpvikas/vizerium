package com.vizerium.barabanca.trade;

import java.util.List;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MovingAverage;

public abstract class EMACrossoverTest extends TradeStrategyTest {

	protected abstract MovingAverage getFastMA();

	protected abstract MovingAverage getSlowMA();

	protected abstract MovingAverage getStopLossMA();

	@Override
	protected void getAdditionalDataPriorToIteration(TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
	}

	@Override
	protected boolean testForCurrentUnitGreaterThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		return positiveCrossover(current, previous) || higherClose(current, previous);
	}

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (current.getClose() > current.getMovingAverage(getStopLossMA())) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
				tradeBook.coverShortTrade(current);
			}
		}

		if (positiveCrossover(current, previous)) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
				tradeBook.coverShortTrade(current);
			}
			tradeBook.addLongTrade(current);
		}
	}

	@Override
	protected boolean testForCurrentUnitLessThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		return negativeCrossover(current, previous) || lowerClose(current, previous);
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (current.getClose() < current.getMovingAverage(getStopLossMA())) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
				tradeBook.exitLongTrade(current);
			}
		}

		if (negativeCrossover(current, previous)) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
				tradeBook.exitLongTrade(current);
			}
			tradeBook.addShortTrade(current);
		}
	}

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(UnitPriceData current, UnitPriceData previous) {

	}

	protected boolean positiveCrossover(UnitPriceData current, UnitPriceData previous) {
		return ((current.getMovingAverage(getFastMA()) > current.getMovingAverage(getSlowMA()))
				&& (previous.getMovingAverage(getFastMA()) < previous.getMovingAverage(getSlowMA())));
	}

	protected boolean negativeCrossover(UnitPriceData current, UnitPriceData previous) {
		return ((current.getMovingAverage(getFastMA()) < current.getMovingAverage(getSlowMA()))
				&& (previous.getMovingAverage(getFastMA()) > previous.getMovingAverage(getSlowMA())));
	}
}
