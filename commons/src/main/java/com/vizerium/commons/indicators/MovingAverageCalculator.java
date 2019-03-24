package com.vizerium.commons.indicators;

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

	public static float calculateWMA(float[] closingPrices, int numberOfPeriods) {
		if (closingPrices.length < numberOfPeriods) {
			return 0.0f;
		} else {
			float wma = calculateSMA(Arrays.copyOfRange(closingPrices, 0, numberOfPeriods), numberOfPeriods);
			for (int i = numberOfPeriods; i < closingPrices.length; i++) {
				wma = (wma * (numberOfPeriods - 1) + closingPrices[i]) / numberOfPeriods;
			}
			return wma;
		}
	}

	public static float calculateMA(MovingAverageType maType, float[] closingPrices, int numberOfPeriods) {
		if (MovingAverageType.SIMPLE.equals(maType)) {
			return calculateSMA(closingPrices, numberOfPeriods);
		} else if (MovingAverageType.EXPONENTIAL.equals(maType)) {
			return calculateEMA(closingPrices, numberOfPeriods);
		} else if (MovingAverageType.WELLESWILDER.equals(maType)) {
			return calculateWMA(closingPrices, numberOfPeriods);
		} else {
			throw new RuntimeException("Unable to identify type of Moving Average. " + maType);
		}
	}

	public static float[] calculateArraySMA(float[] closingPrices, int numberOfPeriods) {
		float[] smaArray = new float[closingPrices.length];
		for (int j = 0; j < closingPrices.length; j++) {
			if (j < numberOfPeriods - 1) {
				smaArray[j] = 0.0f;
			} else {
				float sma = 0.0f;
				for (int i = j - (numberOfPeriods - 1); i <= j; i++) {
					sma += closingPrices[i];
				}
				smaArray[j] = sma / numberOfPeriods;
			}
		}
		return smaArray;
	}

	public static float[] calculateArrayEMA(float[] closingPrices, int numberOfPeriods) {
		float[] emaArray = new float[closingPrices.length];
		for (int j = 0; j < closingPrices.length; j++) {
			if (j < numberOfPeriods - 1) {
				emaArray[j] = 0.0f;
			} else {
				float weightingMultiplier = 2.0f / (numberOfPeriods + 1);
				float ema = calculateSMA(Arrays.copyOfRange(closingPrices, 0, numberOfPeriods), numberOfPeriods);

				for (int i = numberOfPeriods; i <= j; i++) {
					ema = (closingPrices[i] - ema) * weightingMultiplier + ema;
				}
				emaArray[j] = ema;
			}
		}
		return emaArray;
	}

	public static float[] calculateArrayWMA(float[] closingPrices, int numberOfPeriods) {
		float[] wmaArray = new float[closingPrices.length];
		for (int j = 0; j < closingPrices.length; j++) {
			if (j < numberOfPeriods - 1) {
				wmaArray[j] = 0.0f;
			} else {
				float wma = calculateSMA(Arrays.copyOfRange(closingPrices, 0, numberOfPeriods), numberOfPeriods);

				for (int i = numberOfPeriods; i <= j; i++) {
					wma = (wma * (numberOfPeriods - 1) + closingPrices[i]) / numberOfPeriods;
				}
				wmaArray[j] = wma;
			}
		}
		return wmaArray;
	}

	public static float[] calculateArrayMA(MovingAverageType maType, float[] closingPrices, int numberOfPeriods) {
		if (MovingAverageType.SIMPLE.equals(maType)) {
			return calculateArraySMA(closingPrices, numberOfPeriods);
		} else if (MovingAverageType.EXPONENTIAL.equals(maType)) {
			return calculateArrayEMA(closingPrices, numberOfPeriods);
		} else if (MovingAverageType.WELLESWILDER.equals(maType)) {
			return calculateArrayWMA(closingPrices, numberOfPeriods);
		} else {
			throw new RuntimeException("Unable to identify type of Moving Average. " + maType);
		}
	}
}
