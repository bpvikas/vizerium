package com.vizerium.barabanca.trade;

import java.util.List;

import org.apache.log4j.Logger;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MACD;
import com.vizerium.commons.indicators.MovingAverage;
import com.vizerium.commons.indicators.StandardMovingAverages;
import com.vizerium.commons.indicators.StochasticMomentum;
import com.vizerium.commons.trade.TradeAction;

public abstract class EMACrossoverAndIndicatorTest extends EMACrossoverTest {

	private static final Logger logger = Logger.getLogger(EMACrossoverTest.class);

	protected abstract MovingAverage getFastMA();

	protected abstract MovingAverage getSlowMA();

	protected abstract MovingAverage getStopLossMA();

	protected boolean trailingStopLossInSystem = false;

	protected MACD macd;

	protected StochasticMomentum sm;

	@Override
	protected void getAdditionalDataPriorToIteration(TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		macd = new MACD(getFastMA(), getSlowMA());
		updateIndicatorDataInUnitPrices(unitPriceDataList, macd);
		// sm = new StochasticMomentum();
		// updateIndicatorDataInUnitPrices(unitPriceDataList, sm);
	}

	@Override
	protected boolean testForCurrentUnitGreaterThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		return positiveMACrossover(current, previous) || higherClose(current, previous);
	}

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
			float stoppedPrice = Float.MAX_VALUE;
			Trade lastTrade = tradeBook.last();
			// This if else condition below needs to be the first check going in as here it is checked whether the SL is hit, and if so, close the trade.
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

		updateTrailingStopLossOrExitTradeIfPriceStrugglingToCrossHighMA(tradeBook, current, previous);

		if (positiveMACrossover(current, previous) || positiveCloseMACrossover(current, previous)) {
			// Backtesting for all conditions proves that presence of if (positiveCloseMACrossover(current, previous)) condition at this point improves the overall profitability by
			// more than 20%
			// Positive MA crossover is usually late and that close has gone above both MAs already earlier
			// On closer analysis, found that the number of transactions has nearly doubled, and though the profitability is up by 20%, need to get a better filter to cut the
			// losses.

			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
				if (positiveMACrossover(current, previous) && positiveCloseMACrossover(current, previous)) {
					logger.debug("Both Positive crossover and Close above both MAs Hit. Covering short trade.");
				} else if (positiveMACrossover(current, previous)) {
					logger.debug("Positive crossover Hit. Covering short trade.");
				} else if (positiveCloseMACrossover(current, previous)) {
					logger.debug("Close above both MAs Hit. Covering short trade.");
				}
				tradeBook.coverShortTrade(current);
			}
			// Added the Stochastic Momentum check as follows
			// if (tradeBook.isLastTradeExited() && current.getIndicator(sm.getName()).getValues()[StochasticMomentum.UPI_POSN_SMINDEX] < 60.0f) {
			// and tested it for all values 90 -> 40, but the profits were falling much faster than the losses, so reverting to the
			// simpler if condition if (tradeBook.isLastTradeExited())
			if (tradeBook.isLastTradeExited()) {
				tradeBook.addLongTrade(current);
				float previousLow = historicalDataReader.getPreviousN(current.getScripName(), current.getTimeFormat().getHigherTimeFormat(), current.getDateTime(), 1).get(0)
						.getLow();
				tradeBook.last().setExitStopLoss(previousLow * 0.999f); // The opening SL is set 0.1% below yesterday low for long trades.
			}

			// Still checking the feasibility of the idea below
			// exitIntraDayTradeIfMakingLossByEOD(tradeBook, current);
		}
	}

	@Override
	protected boolean testForCurrentUnitLessThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		return negativeMACrossover(current, previous) || lowerClose(current, previous);
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
			float stoppedPrice = Float.MIN_VALUE;
			Trade lastTrade = tradeBook.last();
			// This if else condition below needs to be the first check going in as here it is checked whether the SL is hit, and if so, close the trade.
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

		updateTrailingStopLossOrExitTradeIfPriceStrugglingToCrossHighMA(tradeBook, current, previous);

		if (negativeMACrossover(current, previous) || negativeCloseMACrossover(current, previous)) {
			// Backtesting for all conditions proves that presence of if (negativeCloseMACrossover(current, previous)) condition at this point improves the overall profitability by
			// more than 20%
			// Negative MA crossover is usually late and that close has gone below both MAs already earlier
			// On closer analysis, found that the number of transactions has nearly doubled, and though the profitability is up by 20%, need to get a better filter to cut the
			// losses.

			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
				if (negativeMACrossover(current, previous) && negativeCloseMACrossover(current, previous)) {
					logger.debug("Both Negative crossover and Close below both MAs Hit. Exiting long trade.");
				} else if (negativeMACrossover(current, previous)) {
					logger.debug("Negative crossover Hit. Exiting long trade.");
				} else if (negativeCloseMACrossover(current, previous)) {
					logger.debug("Close below both MAs Hit. Exiting long trade.");
				}
				tradeBook.exitLongTrade(current);
			}
			if (tradeBook.isLastTradeExited()) {
				tradeBook.addShortTrade(current);
				float previousHigh = historicalDataReader.getPreviousN(current.getScripName(), current.getTimeFormat().getHigherTimeFormat(), current.getDateTime(), 1).get(0)
						.getHigh();
				tradeBook.last().setExitStopLoss(previousHigh * 1.001f); // The opening SL is set 0.1% above yesterday high for short trades.
			}

			// Still checking the feasibility of the idea below
			// exitIntraDayTradeIfMakingLossByEOD(tradeBook, current);
		}
	}

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(UnitPriceData current, UnitPriceData previous) {

	}

	protected void setTrailingStopLossInSystem(boolean trailingStopLossInSystem) {
		this.trailingStopLossInSystem = trailingStopLossInSystem;
	}

	protected void updateTrailingStopLossBasedOnMACDHistogramSlope(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (current.getIndicator(macd.getName()).getValues()[MACD.UPI_POSN_HISTOGRAM] < previous.getIndicator(macd.getName()).getValues()[MACD.UPI_POSN_HISTOGRAM]) {
			setLongTradeTrailingStopLossToLowerLow(tradeBook, current, previous);
		}

		if (current.getIndicator(macd.getName()).getValues()[MACD.UPI_POSN_HISTOGRAM] > previous.getIndicator(macd.getName()).getValues()[MACD.UPI_POSN_HISTOGRAM]) {
			setShortTradeTrailingStopLossToHigherHigh(tradeBook, current, previous);
		}
	}

	protected void updateTrailingStopLossOrExitTradeIfPriceStrugglingToCrossHighMA(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		// This check is for 100 MA and 200 MA only
		if (!tradeBook.isEmpty() && !tradeBook.isLastTradeExited()) {
			Trade lastTrade = tradeBook.last();
			if (TradeAction.LONG.equals(lastTrade.getAction())) {
				if (isPriceStrugglingToCrossAboveHighMA(current, StandardMovingAverages._100.getNumber())
						|| isPriceStrugglingToCrossAboveHighMA(current, StandardMovingAverages._200.getNumber())) {
					setLongTradeTrailingStopLossToLowerLow(tradeBook, current, previous);
				}

				// This check below of closing the long trade if prices are struggling for 2 consecutive candles is counter-productive as
				// the overall profitability is decreasing by up to 3%. See results written in diary (date 1-Jun-2013)

				// if (arePricesStrugglingToCrossAboveHighMA(current, previous, StandardMovingAverages._100.getNumber())
				// || arePricesStrugglingToCrossAboveHighMA(current, previous, StandardMovingAverages._200.getNumber())) {
				// logger.debug("Prices struggling to cross above High MA. Exiting long trade.");
				// tradeBook.exitLongTrade(current);
				// }

			} else if (TradeAction.SHORT.equals(lastTrade.getAction())) {
				if (isPriceStrugglingToCrossBelowHighMA(current, StandardMovingAverages._100.getNumber())
						|| isPriceStrugglingToCrossBelowHighMA(current, StandardMovingAverages._200.getNumber())) {
					setShortTradeTrailingStopLossToHigherHigh(tradeBook, current, previous);
				}
				// This check below of closing the short trade if prices are struggling for 2 consecutive candles is counter-productive as
				// the overall profitability is decreasing by up to 3%. See results written in diary (date 1-Jun-2013)

				// if (arePricesStrugglingToCrossBelowHighMA(current, previous, StandardMovingAverages._100.getNumber())
				// || arePricesStrugglingToCrossBelowHighMA(current, previous, StandardMovingAverages._200.getNumber())) {
				// logger.debug("Prices struggling to cross below High MA. Covering short trade.");
				// tradeBook.coverShortTrade(current);
				// }
			}
		}
	}

	protected boolean closeBelowBothMAs(UnitPriceData unitPrice) {
		return unitPrice.getClose() < unitPrice.getMovingAverage(getFastMA()) && unitPrice.getClose() < unitPrice.getMovingAverage(getSlowMA());
	}

	protected boolean closeAboveBothMAs(UnitPriceData unitPrice) {
		return unitPrice.getClose() > unitPrice.getMovingAverage(getFastMA()) && unitPrice.getClose() > unitPrice.getMovingAverage(getSlowMA());
	}

	protected boolean positiveCloseMACrossover(UnitPriceData current, UnitPriceData previous) {
		return closeAboveBothMAs(current) && !closeAboveBothMAs(previous);
	}

	protected boolean negativeCloseMACrossover(UnitPriceData current, UnitPriceData previous) {
		return closeBelowBothMAs(current) && !closeBelowBothMAs(previous);
	}

	protected void setLongTradeTrailingStopLossToLowerLow(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
			float lowerLow = Float.min(current.getLow(), previous.getLow());
			float previousExitStopLoss = tradeBook.last().getExitStopLoss();
			if (previousExitStopLoss == 0.0f || lowerLow > previousExitStopLoss) {
				logger.debug("Long Trailing SL updated to " + lowerLow);
				tradeBook.last().setExitStopLoss(lowerLow);
			}
		}
	}

	protected void setShortTradeTrailingStopLossToHigherHigh(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
			float higherHigh = Float.max(current.getHigh(), previous.getHigh());
			float previousExitStopLoss = tradeBook.last().getExitStopLoss();
			if (previousExitStopLoss == 0.0f || higherHigh < previousExitStopLoss) {
				logger.debug("Short Trailing SL updated to " + higherHigh);
				tradeBook.last().setExitStopLoss(higherHigh);
			}
		}
	}

	// This one should be used more to tighten stops on long positions if it is unable to cross above 100 or 200 MA.
	protected boolean isPriceStrugglingToCrossAboveHighMA(UnitPriceData unitPrice, int ma) {
		if (unitPrice.getHigh() > unitPrice.getMovingAverage(ma) && unitPrice.getLow() < unitPrice.getMovingAverage(ma) && unitPrice.getClose() < unitPrice.getMovingAverage(ma)) {
			return true;
		} else {
			return false;
		}
	}

	// This one should be used to close the long trade completely if candles are unable to cross above 100 or 200 MA.
	protected boolean arePricesStrugglingToCrossAboveHighMA(UnitPriceData current, UnitPriceData previous, int ma) {
		if (isPriceStrugglingToCrossAboveHighMA(current, ma) && isPriceStrugglingToCrossAboveHighMA(previous, ma)) {
			return true;
		} else {
			return false;
		}
	}

	// This one should be used more to tighten stops on short positions if it is unable to cross below 100 or 200 MA.
	protected boolean isPriceStrugglingToCrossBelowHighMA(UnitPriceData unitPrice, int ma) {
		if (unitPrice.getHigh() > unitPrice.getMovingAverage(ma) && unitPrice.getLow() < unitPrice.getMovingAverage(ma) && unitPrice.getClose() > unitPrice.getMovingAverage(ma)) {
			return true;
		} else {
			return false;
		}
	}

	// This one should be used to close the short trade completely if candles are unable to cross below 100 or 200 MA.
	protected boolean arePricesStrugglingToCrossBelowHighMA(UnitPriceData current, UnitPriceData previous, int ma) {
		if (isPriceStrugglingToCrossBelowHighMA(current, ma) && isPriceStrugglingToCrossBelowHighMA(previous, ma)) {
			return true;
		} else {
			return false;
		}
	}

	protected void exitIntraDayTradeIfMakingLossByEOD(TradeBook tradeBook, UnitPriceData current) {
		if (!tradeBook.isLastTradeExited()) {
			Trade lastTrade = tradeBook.last();
			// The below if condition is to check for those conditions where a trade was taken on an intra-day time frame and then was resulting in a loss
			// by the end of the day already. So better to exit the trade.
			if (lastTrade.getTimeFormat().isLowerTimeFormatThan(TimeFormat._1DAY) || lastTrade.getTimeFormat().equals(TimeFormat._1DAY)) {
				if (historicalDataReader.isLastCandleOfTimePeriod(current, TimeFormat._1DAY) && lastTrade.getCurrentUnrealisedPL(current) < 0.0f) {
					logger.debug("Exiting trade taken on an intra day chart at the EOD as it was in a loss @ EOD.");
					current.setTradedValue(current.getClose());
					tradeBook.exitLastTrade(current);
				}
			}
		}
	}
}