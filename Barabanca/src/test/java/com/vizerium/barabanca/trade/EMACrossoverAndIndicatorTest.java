package com.vizerium.barabanca.trade;

import java.util.List;

import org.apache.log4j.Logger;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MACD;
import com.vizerium.commons.indicators.MovingAverage;

public abstract class EMACrossoverAndIndicatorTest extends EMACrossoverTest {

	private static final Logger logger = Logger.getLogger(EMACrossoverTest.class);

	protected abstract MovingAverage getFastMA();

	protected abstract MovingAverage getSlowMA();

	protected abstract MovingAverage getStopLossMA();

	protected boolean trailingStopLossInSystem = false;

	protected MACD macd;

	@Override
	protected void getAdditionalDataPriorToIteration(TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		macd = new MACD(getFastMA(), getSlowMA());
		updateIndicatorDataInUnitPrices(unitPriceDataList, macd);
	}

	@Override
	protected boolean testForCurrentUnitGreaterThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		return positiveCrossover(current, previous) || higherClose(current, previous);
	}

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (current.getClose() > current.getMovingAverage(getStopLossMA())) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
				logger.debug("MA SL Hit. Covering short trade.");
				tradeBook.coverShortTrade(current);
			}
		}

		if (!tradeBook.isEmpty() && tradeBook.last().isExitStopLossHit(current.getClose())) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
				logger.debug("Trailing SL Hit. Covering short trade.");
				if (trailingStopLossInSystem) {
					current.setTradedValue(tradeBook.last().getExitStoppedPrice(current));
				}
				tradeBook.coverShortTrade(current);
			}
		}
		updateTrailingStopLossBasedOnMACDHistogramSlope(tradeBook, current, previous);

		if (positiveCrossover(current, previous)) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
				logger.debug("Positive crossover Hit. Covering short trade.");
				tradeBook.coverShortTrade(current);
			}

			tradeBook.addLongTrade(current);
			float previousLow = historicalDataReader.getPreviousN(current.getScripName(), current.getTimeFormat().getHigherTimeFormat(), current.getDateTime(), 1).get(0).getLow();
			tradeBook.last().setExitStopLoss(previousLow);
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
				logger.debug("MA SL Hit. Exiting long trade.");
				tradeBook.exitLongTrade(current);
			}
		}

		if (!tradeBook.isEmpty() && tradeBook.last().isExitStopLossHit(current.getClose())) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
				logger.debug("Trailing SL Hit. Exiting long trade.");
				if (trailingStopLossInSystem) {
					current.setTradedValue(tradeBook.last().getExitStoppedPrice(current));
				}
				tradeBook.exitLongTrade(current);
			}
		}
		updateTrailingStopLossBasedOnMACDHistogramSlope(tradeBook, current, previous);

		if (negativeCrossover(current, previous)) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
				logger.debug("Negative crossover Hit. Exiting long trade.");
				tradeBook.exitLongTrade(current);
			}

			tradeBook.addShortTrade(current);
			float previousHigh = historicalDataReader.getPreviousN(current.getScripName(), current.getTimeFormat().getHigherTimeFormat(), current.getDateTime(), 1).get(0)
					.getHigh();
			tradeBook.last().setExitStopLoss(previousHigh);
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

	protected void setTrailingStopLossInSystem(boolean trailingStopLossInSystem) {
		this.trailingStopLossInSystem = trailingStopLossInSystem;
	}

	protected void updateTrailingStopLossBasedOnMACDHistogramSlope(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (current.getIndicator(macd.getName()).getValues()[MACD.UPI_POSN_HISTOGRAM] < previous.getIndicator(macd.getName()).getValues()[MACD.UPI_POSN_HISTOGRAM]) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
				float previousLow = historicalDataReader.getPreviousN(current.getScripName(), current.getTimeFormat(), current.getDateTime(), 1).get(0).getLow();
				float previousExitStopLoss = tradeBook.last().getExitStopLoss();
				if (previousExitStopLoss == 0.0f || previousLow > previousExitStopLoss) {
					logger.debug("Long Trailing SL updated to " + previousLow);
					tradeBook.last().setExitStopLoss(previousLow);
				}
			}
		}

		if (current.getIndicator(macd.getName()).getValues()[MACD.UPI_POSN_HISTOGRAM] > previous.getIndicator(macd.getName()).getValues()[MACD.UPI_POSN_HISTOGRAM]) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
				float previousHigh = historicalDataReader.getPreviousN(current.getScripName(), current.getTimeFormat(), current.getDateTime(), 1).get(0).getHigh();
				float previousExitStopLoss = tradeBook.last().getExitStopLoss();
				if (previousExitStopLoss == 0.0f || previousHigh < previousExitStopLoss) {
					logger.debug("Short Trailing SL updated to " + previousHigh);
					tradeBook.last().setExitStopLoss(previousHigh);
				}
			}
		}
	}
}
