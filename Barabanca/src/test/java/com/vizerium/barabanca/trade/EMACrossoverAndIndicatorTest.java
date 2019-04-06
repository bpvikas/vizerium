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
		if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
			float stoppedPrice = Float.MAX_VALUE;
			Trade lastTrade = tradeBook.last();
			if (trailingStopLossInSystem) {
				if (lastTrade.isExitStopLossHit(current.getHigh())) {
					float trailingStopLoss = lastTrade.getExitStoppedPrice(current);
					if (trailingStopLoss < stoppedPrice) {
						logger.debug("Trailing SL in System Hit. Covering short trade.");
						stoppedPrice = trailingStopLoss;
					}
				} else if (current.getClose() > current.getMovingAverage(getStopLossMA())) {
					logger.debug("MA SL Hit. Covering short trade.");
					if (current.getClose() < stoppedPrice) {
						stoppedPrice = current.getClose();
					}
				}
			} else {
				if (lastTrade.isExitStopLossHit(current.getClose())) {
					if (current.getMovingAverage(getStopLossMA()) < lastTrade.getExitStopLoss()) {
						logger.debug("MA SL Hit. Covering short trade.");
					} else {
						logger.debug("Trailing SL Not in System Hit. Covering short trade.");
					}
					if (current.getClose() < stoppedPrice) {
						stoppedPrice = current.getClose();
					}
				} else if (current.getClose() > current.getMovingAverage(getStopLossMA())) {
					logger.debug("MA SL Hit. Covering short trade.");
					if (current.getClose() < stoppedPrice) {
						stoppedPrice = current.getClose();
					}
				}
			}
			if (stoppedPrice < Float.MAX_VALUE) {
				current.setTradedValue(stoppedPrice);
				tradeBook.coverShortTrade(current);
			}
		}

		updateTrailingStopLossBasedOnMACDHistogramSlope(tradeBook, current, previous);

		if (positiveCrossover(current, previous)) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
				logger.debug("Positive crossover Hit. Covering short trade.");
				tradeBook.coverShortTrade(current);
			}

			if (current.getClose() > current.getMovingAverage(getFastMA()) && current.getClose() > current.getMovingAverage(getSlowMA())) {
				// Backtesting for all conditions proves that presence or absence of above if condition has no effect on the overall result
				// Positive crossover indicates that close has gone above both MAs
				tradeBook.addLongTrade(current);
				float previousLow = historicalDataReader.getPreviousN(current.getScripName(), current.getTimeFormat().getHigherTimeFormat(), current.getDateTime(), 1).get(0)
						.getLow();
				tradeBook.last().setExitStopLoss(previousLow);
			}
		}
	}

	@Override
	protected boolean testForCurrentUnitLessThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		return negativeCrossover(current, previous) || lowerClose(current, previous);
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
			float stoppedPrice = Float.MIN_VALUE;
			Trade lastTrade = tradeBook.last();
			if (trailingStopLossInSystem) {
				if (lastTrade.isExitStopLossHit(current.getLow())) {
					float trailingStopLoss = lastTrade.getExitStoppedPrice(current);
					if (trailingStopLoss > stoppedPrice) {
						logger.debug("Trailing SL in System Hit. Exiting long trade.");
						stoppedPrice = trailingStopLoss;
					}
				} else if (current.getClose() < current.getMovingAverage(getStopLossMA())) {
					logger.debug("MA SL Hit. Exiting long trade.");
					if (current.getClose() > stoppedPrice) {
						stoppedPrice = current.getClose();
					}
				}
			} else {
				if (lastTrade.isExitStopLossHit(current.getClose())) {
					if (current.getMovingAverage(getStopLossMA()) > lastTrade.getExitStopLoss()) {
						logger.debug("MA SL Hit. Exiting long trade.");
					} else {
						logger.debug("Trailing SL Not in System Hit. Exiting long trade.");
					}
					if (current.getClose() > stoppedPrice) {
						stoppedPrice = current.getClose();
					}
				} else if (current.getClose() < current.getMovingAverage(getStopLossMA())) {
					logger.debug("MA SL Hit. Exiting long trade.");
					if (current.getClose() > stoppedPrice) {
						stoppedPrice = current.getClose();
					}
				}
			}
			if (stoppedPrice > Float.MIN_VALUE) {
				current.setTradedValue(stoppedPrice);
				tradeBook.exitLongTrade(current);
			}
		}

		updateTrailingStopLossBasedOnMACDHistogramSlope(tradeBook, current, previous);

		if (negativeCrossover(current, previous)) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
				logger.debug("Negative crossover Hit. Exiting long trade.");
				tradeBook.exitLongTrade(current);
			}

			if (current.getClose() < current.getMovingAverage(getFastMA()) && current.getClose() < current.getMovingAverage(getSlowMA())) {
				// Backtesting for all conditions proves that presence or absence of above if condition has no effect on the overall result
				// Negative crossover indicates that close has gone below both MAs
				tradeBook.addShortTrade(current);
				float previousHigh = historicalDataReader.getPreviousN(current.getScripName(), current.getTimeFormat().getHigherTimeFormat(), current.getDateTime(), 1).get(0)
						.getHigh();
				tradeBook.last().setExitStopLoss(previousHigh);
			}
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
				float lowerLow = Float.min(current.getLow(), previousLow);
				float previousExitStopLoss = tradeBook.last().getExitStopLoss();
				if (previousExitStopLoss == 0.0f || lowerLow > previousExitStopLoss) {
					logger.debug("Long Trailing SL updated to " + lowerLow);
					tradeBook.last().setExitStopLoss(lowerLow);
				}
			}
		}

		if (current.getIndicator(macd.getName()).getValues()[MACD.UPI_POSN_HISTOGRAM] > previous.getIndicator(macd.getName()).getValues()[MACD.UPI_POSN_HISTOGRAM]) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
				float previousHigh = historicalDataReader.getPreviousN(current.getScripName(), current.getTimeFormat(), current.getDateTime(), 1).get(0).getHigh();
				float higherHigh = Float.max(current.getHigh(), previousHigh);
				float previousExitStopLoss = tradeBook.last().getExitStopLoss();
				if (previousExitStopLoss == 0.0f || higherHigh < previousExitStopLoss) {
					logger.debug("Short Trailing SL updated to " + higherHigh);
					tradeBook.last().setExitStopLoss(higherHigh);
				}
			}
		}
	}
}
