package com.vizerium.commons.calculators;

import java.util.Arrays;

public class MovingAverageCalculator {

	public static float calculateSMA(float[] closingPrices, int numberOfPeriods) {
		if (closingPrices.length < numberOfPeriods) {
			return 0.0f;
		} else {
			float sma = 0.0f;
			for (int i = closingPrices.length - numberOfPeriods; i < closingPrices.length; i++) {
				sma += closingPrices[i];
			}
			return sma / numberOfPeriods;
		}
	}

	public static float calculateEMA(float[] closingPrices, int numberOfPeriods) {
		if (closingPrices.length < numberOfPeriods) {
			return 0.0f;
		} else {
			float weightingMultiplier = 2.0f / (numberOfPeriods + 1);
			float ema = calculateSMA(Arrays.copyOfRange(closingPrices, 0, numberOfPeriods), numberOfPeriods);

			for (int i = numberOfPeriods; i < closingPrices.length; i++) {
				ema = (closingPrices[i] - ema) * weightingMultiplier + ema;
			}
			return ema;
		}
	}

	public static float calculateMA(MovingAverageType maType, float[] closingPrices, int numberOfPeriods) {
		if (MovingAverageType.SIMPLE.equals(maType)) {
			return calculateSMA(closingPrices, numberOfPeriods);
		} else if (MovingAverageType.EXPONENTIAL.equals(maType)) {
			return calculateEMA(closingPrices, numberOfPeriods);
		} else {
			throw new RuntimeException("Unable to identify type of Moving Average. " + maType);
		}
	}
}
