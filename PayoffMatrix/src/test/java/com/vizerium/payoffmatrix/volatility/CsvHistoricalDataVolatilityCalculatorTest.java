/*
 * Copyright 2018 Vizerium, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vizerium.payoffmatrix.volatility;

import org.junit.Assert;

import com.vizerium.commons.indicators.MovingAverageCalculator;
import com.vizerium.commons.indicators.RSICalculator;
import com.vizerium.payoffmatrix.dao.HistoricalDataStore;
import com.vizerium.payoffmatrix.exchange.Exchanges;

public abstract class CsvHistoricalDataVolatilityCalculatorTest {

	protected static float volatilityPercentageOverlapSum = 0.0f;
	protected static int volatilityPercentageOverlapViolations = 0;

	protected void testExpiryDateIsAtWhichStandardDeviationBasedOnDataPrior(DateRange historicalDateRange, DateRange futureDateRange, float standardDeviationMultiple) {

		System.out.println("historicalDateRange : " + historicalDateRange + ", futureDateRange" + futureDateRange);
		Volatility volatility = getUnit().calculateVolatility(historicalDateRange);
		volatility.setStandardDeviationMultiple(standardDeviationMultiple);
		volatility.calculateUnderlyingRange(historicalDateRange.getEndDate(), futureDateRange.getEndDate(), Exchanges.get("TEI"), 25.0f);
		System.out.println("Forecasted " + volatility.getUnderlyingRange());

		HistoricalDataStore historicalDataStore = getUnit().getHistoricalDataStore();
		float[] historicalClosingPrices = historicalDataStore.readHistoricalClosingPrices(historicalDateRange);

		float _9sma = MovingAverageCalculator.calculateSMA(historicalClosingPrices, 9);
		float _26sma = MovingAverageCalculator.calculateSMA(historicalClosingPrices, 26);
		float _9ema = MovingAverageCalculator.calculateEMA(historicalClosingPrices, 9);
		float _26ema = MovingAverageCalculator.calculateEMA(historicalClosingPrices, 26);

		BollingerBand bollingerBand = historicalDataStore.calculateBollingerBand(historicalClosingPrices, 20, 2);
		float bollingerBandHigh = bollingerBand.getHigh();
		float bollingerBandLow = bollingerBand.getLow();
		System.out.println(bollingerBand);
		float[] rsiValues = new RSICalculator().calculate(historicalClosingPrices).getValues();
		float rsi = rsiValues[rsiValues.length - 1];

		int upperEndVolatilityViolations = 0, lowerEndVolatilityViolations = 0, upperEndBollingerViolations = 0, lowerEndBollingerViolations = 0;
		float[] closingPrices = historicalDataStore.readHistoricalClosingPrices(futureDateRange);
		float minClosingPrice = closingPrices[0], maxClosingPrice = closingPrices[0];
		for (float closingPrice : closingPrices) {
			if (closingPrice > volatility.getUnderlyingRange().getHigh()) {
				System.out.println("closing price " + closingPrice + " > volatility range top " + volatility.getUnderlyingRange().getHigh());
				++upperEndVolatilityViolations;
			}
			if (closingPrice < volatility.getUnderlyingRange().getLow()) {
				System.out.println("closing price " + closingPrice + " < volatility range bottom " + volatility.getUnderlyingRange().getLow());
				++lowerEndVolatilityViolations;
			}
			if (closingPrice > bollingerBandHigh) {
				System.out.println("closing price " + closingPrice + " > bollinger band high " + bollingerBandHigh);
				++upperEndBollingerViolations;
			}
			if (closingPrice < bollingerBandLow) {
				System.out.println("closing price " + closingPrice + " < bollinger band low " + bollingerBandLow);
				++lowerEndBollingerViolations;
			}
			if (closingPrice < minClosingPrice) {
				minClosingPrice = closingPrice;
			}
			if (closingPrice > maxClosingPrice) {
				maxClosingPrice = closingPrice;
			}
		}

		float volatilityPercentageOverlap = calculatePercentageOverlap(volatility.getUnderlyingRange().getLow(), volatility.getUnderlyingRange().getHigh(), minClosingPrice,
				maxClosingPrice);
		float bollingerBandPercentageOverlap = calculatePercentageOverlap(bollingerBandLow, bollingerBandHigh, minClosingPrice, maxClosingPrice);
		System.out.println("Actual range: " + minClosingPrice + " -> " + maxClosingPrice + " Volatility Overlap : " + volatilityPercentageOverlap + "%"
				+ " Bollinger Band Overlap : " + bollingerBandPercentageOverlap + "%");
		System.out.println("9SMA : " + _9sma + " 26SMA : " + _26sma + " 9EMA : " + _9ema + " 26EMA : " + _26ema + " RSI : " + rsi + System.lineSeparator());

		if (volatilityPercentageOverlap >= 20.0f && volatilityPercentageOverlap <= 100.0f) {
			volatilityPercentageOverlapSum += volatilityPercentageOverlap;
		} else {
			volatilityPercentageOverlapViolations++;
		}

		if (closingPrices[closingPrices.length - 1] > volatility.getUnderlyingRange().getHigh()) {
			Assert.fail("expiry date closing price " + closingPrices[closingPrices.length - 1] + " > volatility range top " + volatility.getUnderlyingRange().getHigh() + " "
					+ printUpperAndLowerEndViolations(upperEndVolatilityViolations, lowerEndVolatilityViolations, upperEndBollingerViolations, lowerEndBollingerViolations));
		} else if (closingPrices[closingPrices.length - 1] < volatility.getUnderlyingRange().getLow()) {
			Assert.fail("expiry date closing price " + closingPrices[closingPrices.length - 1] + "  < volatility range bottom " + volatility.getUnderlyingRange().getLow() + " "
					+ printUpperAndLowerEndViolations(upperEndVolatilityViolations, lowerEndVolatilityViolations, upperEndBollingerViolations, lowerEndBollingerViolations));
		} else if (upperEndVolatilityViolations > 0 || lowerEndVolatilityViolations > 0) {
			Assert.fail(printUpperAndLowerEndViolations(upperEndVolatilityViolations, lowerEndVolatilityViolations, upperEndBollingerViolations, lowerEndBollingerViolations));
		}
		// @formatter:off
		/*
		if (closingPrices[closingPrices.length - 1] > bollingerBandHigh) {
			Assert.fail("expiry date closing price > bollinger band high " + closingPrices[closingPrices.length - 1] + " "
					+ printUpperAndLowerEndViolations(upperEndVolatilityViolations, lowerEndVolatilityViolations, upperEndBollingerViolations, lowerEndBollingerViolations));
		} else if (closingPrices[closingPrices.length - 1] < bollingerBandLow) {
			Assert.fail("expiry date closing price < bollinger band low " + closingPrices[closingPrices.length - 1] + " "
					+ printUpperAndLowerEndViolations(upperEndVolatilityViolations, lowerEndVolatilityViolations, upperEndBollingerViolations, lowerEndBollingerViolations));
		} else if (upperEndBollingerViolations > 0 || lowerEndBollingerViolations > 0) {
			Assert.fail(printUpperAndLowerEndViolations(upperEndVolatilityViolations, lowerEndVolatilityViolations, upperEndBollingerViolations, lowerEndBollingerViolations));
		}
		 */
		// @formatter:on
	}

	public abstract CsvHistoricalDataVolatilityCalculator getUnit();

	private String printUpperAndLowerEndViolations(int upperEndVolatilityViolations, int lowerEndVolatilityViolations, int upperEndBollingerViolations,
			int lowerEndBollingerViolations) {
		return "upperEndVolatilityViolations : " + upperEndVolatilityViolations + ", lowerEndVolatilityViolations : " + lowerEndVolatilityViolations;
		// + " upperEndBollingerViolations : " + upperEndBollingerViolations + ", lowerEndBollingerViolations : " + lowerEndBollingerViolations;
	}

	private float calculatePercentageOverlap(float forecastedLow, float forecastedHigh, float actualLow, float actualHigh) {
		float percentageOverlap = 0.0f;
		if (actualLow >= forecastedLow && actualHigh <= forecastedHigh) {
			percentageOverlap = (actualHigh - actualLow) / (forecastedHigh - forecastedLow);
		} else if (actualLow < forecastedLow && actualHigh > forecastedHigh) {
			percentageOverlap = (actualHigh - actualLow) / (forecastedHigh - forecastedLow);
		} else if (actualLow < forecastedLow) {
			percentageOverlap = (actualHigh - forecastedLow) / (forecastedHigh - forecastedLow);
		} else if (actualHigh > forecastedHigh) {
			percentageOverlap = (forecastedHigh - actualLow) / (forecastedHigh - forecastedLow);
		}
		return percentageOverlap * 100;

	}

	protected void printOverlapSumsAndViolations() {
		System.out.println("volatilityPercentageOverlapSum : " + volatilityPercentageOverlapSum);
		System.out.println("volatilityPercentageOverlapViolations : " + volatilityPercentageOverlapViolations);
	}
}
