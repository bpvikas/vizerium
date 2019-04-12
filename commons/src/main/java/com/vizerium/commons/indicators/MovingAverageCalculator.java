package com.vizerium.commons.indicators;

import java.util.Arrays;
import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public class MovingAverageCalculator implements IndicatorCalculator<MovingAverage> {

	@Override
	public MovingAverage calculate(List<? extends UnitPrice> unitPrices, MovingAverage movingAverage) {
		// I really tried to use lambda expressions here.
		// return calculate(unitPrices.stream().mapToFloat(UnitPrice::getClose).toArray(), ma);
		// but it does not have a mapToFloat.. So, I am looping over it myself
		// https://stackoverflow.com/questions/4837568/java-convert-arraylistfloat-to-float

		float[] closingPrices = new float[unitPrices.size()];
		int i = 0;
		for (UnitPrice unitPrice : unitPrices) {
			closingPrices[i++] = unitPrice.getClose();
		}
		float[] movingAverageValues = calculateArrayMA(movingAverage.getType(), closingPrices, movingAverage.getMA());
		movingAverage.setValues(movingAverageValues);
		return movingAverage;
	}

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
		if (emaArray.length < numberOfPeriods) {
			for (int j = 0; j < emaArray.length; j++) {
				emaArray[j] = 0.0f;
			}
		} else {
			for (int j = 0; j < numberOfPeriods - 1; j++) {
				emaArray[j] = 0.0f;
			}
			float weightingMultiplier = 2.0f / (numberOfPeriods + 1);
			emaArray[numberOfPeriods - 1] = calculateSMA(Arrays.copyOfRange(closingPrices, 0, numberOfPeriods), numberOfPeriods);
			for (int i = numberOfPeriods; i < emaArray.length; i++) {
				emaArray[i] = (closingPrices[i] - emaArray[i - 1]) * weightingMultiplier + emaArray[i - 1];
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
