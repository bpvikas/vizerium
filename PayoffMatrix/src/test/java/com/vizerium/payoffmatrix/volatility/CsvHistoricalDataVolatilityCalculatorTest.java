package com.vizerium.payoffmatrix.volatility;

import java.util.Arrays;

import org.junit.Assert;

import com.vizerium.payoffmatrix.exchange.Exchanges;

public abstract class CsvHistoricalDataVolatilityCalculatorTest {

	protected static float volatilityPercentageOverlapSum = 0.0f;
	protected static float emaPercentageOverlapSum = 0.0f;
	protected static int volatilityPercentageOverlapViolations = 0;
	protected static int emaPercentageOverlapViolations = 0;

	protected void testExpiryDateIsAtWhichStandardDeviationBasedOnDataPrior(DateRange historicalDateRange, DateRange futureDateRange, float standardDeviationMultiple) {

		System.out.println("historicalDateRange : " + historicalDateRange + ", futureDateRange" + futureDateRange);
		Volatility volatility = getUnit().calculateVolatility(historicalDateRange);
		volatility.setStandardDeviationMultiple(standardDeviationMultiple);
		volatility.calculateUnderlyingRange(historicalDateRange.getEndDate(), futureDateRange.getEndDate(), Exchanges.get("TEI"), 25.0f);
		System.out.println("Forecasted " + volatility.getUnderlyingRange());

		float[] historicalClosingPrices = getUnit().readClosingPrices(historicalDateRange);
		float[] closingPrices = getUnit().readClosingPrices(futureDateRange);

		float _9sma = calculateSMA(historicalClosingPrices, 9);
		float _26sma = calculateSMA(historicalClosingPrices, 26);
		float _9ema = calculateEMA(historicalClosingPrices, 9);
		float _26ema = calculateEMA(historicalClosingPrices, 26);
		float emaRangeTop = _9ema + Math.abs(_26ema - _9ema);
		float emaRangeBottom = _9ema - Math.abs(_26ema - _9ema);
		System.out.println("EMA Range: " + emaRangeBottom + " -> " + emaRangeTop);
		float rsi = calculateRSI(historicalClosingPrices, 14);

		int upperEndViolations = 0, lowerEndViolations = 0;
		float minClosingPrice = closingPrices[0], maxClosingPrice = closingPrices[0];
		for (float closingPrice : closingPrices) {
			if (closingPrice > volatility.getUnderlyingRange().getHigh()) {
				System.out.println("closing price > volatility range top " + closingPrice);
				++upperEndViolations;
			}
			if (closingPrice < volatility.getUnderlyingRange().getLow()) {
				System.out.println("closing price < volatility range bottom " + closingPrice);
				++lowerEndViolations;
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
		float emaPercentageOverlap = calculatePercentageOverlap(emaRangeBottom, emaRangeTop, minClosingPrice, maxClosingPrice);
		System.out.println("Actual range: " + minClosingPrice + " -> " + maxClosingPrice + " Volatility Overlap : " + volatilityPercentageOverlap + "%" + " EMA Overlap : "
				+ emaPercentageOverlap + "%");
		System.out.println("9SMA : " + _9sma + " 26SMA : " + _26sma + " 9EMA : " + _9ema + " 26EMA : " + _26ema + " RSI : " + rsi + System.lineSeparator());

		if (volatilityPercentageOverlap >= 20.0f && volatilityPercentageOverlap <= 100.0f) {
			volatilityPercentageOverlapSum += volatilityPercentageOverlap;
		} else {
			volatilityPercentageOverlapViolations++;
		}
		if (emaPercentageOverlap >= 20.0f && emaPercentageOverlap <= 100.0f) {
			emaPercentageOverlapSum += emaPercentageOverlap;
		} else {
			emaPercentageOverlapViolations++;
		}

		if (closingPrices[closingPrices.length - 1] > volatility.getUnderlyingRange().getHigh()) {
			Assert.fail("expiry date closing price > volatility range top " + closingPrices[closingPrices.length - 1] + " "
					+ printUpperAndLowerEndViolations(upperEndViolations, lowerEndViolations));
		} else if (closingPrices[closingPrices.length - 1] < volatility.getUnderlyingRange().getLow()) {
			Assert.fail("expiry date closing price < volatility range bottom " + closingPrices[closingPrices.length - 1] + " "
					+ printUpperAndLowerEndViolations(upperEndViolations, lowerEndViolations));
		} else if (upperEndViolations > 0 || lowerEndViolations > 0) {
			Assert.fail(printUpperAndLowerEndViolations(upperEndViolations, lowerEndViolations));
		}
	}

	public abstract CsvHistoricalDataVolatilityCalculator getUnit();

	private String printUpperAndLowerEndViolations(int upperEndViolations, int lowerEndViolations) {
		return "upperEndViolations : " + upperEndViolations + ", lowerEndViolations : " + lowerEndViolations;
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

	private float calculateSMA(float[] closingPrices, int numberOfDays) {
		float sma = 0.0f;
		for (int i = closingPrices.length - numberOfDays; i < closingPrices.length; i++) {
			sma += closingPrices[i];
		}
		return sma / numberOfDays;
	}

	private float calculateEMA(float[] closingPrices, int numberOfDays) {
		float weightingMultiplier = 2.0f / (numberOfDays + 1);
		float ema = calculateSMA(Arrays.copyOfRange(closingPrices, 0, numberOfDays), numberOfDays);

		for (int i = numberOfDays; i < closingPrices.length; i++) {
			ema = (closingPrices[i] - ema) * weightingMultiplier + ema;
		}
		return ema;
	}

	private float calculateRSI(float[] closingPrices, int numberOfDays) {
		float initialTotalGain = 0.0f;
		float initialTotalLoss = 0.0f;

		for (int i = 1; i <= numberOfDays; i++) {
			float currentClose = closingPrices[i];
			float previousClose = closingPrices[i - 1];
			if (currentClose >= previousClose) {
				initialTotalGain += (currentClose - previousClose);
			} else {
				initialTotalLoss += (previousClose - currentClose);
			}
		}

		float averageGain = initialTotalGain / numberOfDays;
		float averageLoss = initialTotalLoss / numberOfDays;

		for (int i = numberOfDays + 1; i < closingPrices.length; i++) {
			float currentClose = closingPrices[i];
			float previousClose = closingPrices[i - 1];
			if (currentClose >= previousClose) {
				averageGain = (averageGain * (numberOfDays - 1) + (currentClose - previousClose)) / numberOfDays;
			} else {
				averageLoss = (averageLoss * (numberOfDays - 1) + (previousClose - currentClose)) / numberOfDays;
			}
		}

		float rs = averageGain / averageLoss;

		float rsi = 100.0f - (100.0f / (1.0f + rs));

		return rsi;
	}

	protected void printOverlapSumsAndViolations() {
		System.out.println("volatilityPercentageOverlapSum : " + volatilityPercentageOverlapSum);
		System.out.println("emaPercentageOverlapSum : \t" + emaPercentageOverlapSum);
		System.out.println("volatilityPercentageOverlapViolations : " + volatilityPercentageOverlapViolations);
		System.out.println("emaPercentageOverlapViolations : \t" + emaPercentageOverlapViolations);
	}

}
