package com.vizerium.commons.indicators;

import java.util.Arrays;
import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public class MACDCalculator {

	public MACD calculate(List<? extends UnitPrice> unitPrices) {
		return calculate(unitPrices, new MACD());
	}

	public MACD calculate(List<? extends UnitPrice> unitPrices, MACD macd) {
		int size = unitPrices.size();
		int smooth = macd.getSmoothingPeriod();
		int fast = macd.getFastMA();
		int slow = macd.getSlowMA();

		float[] closingPrices = new float[size];
		int i = 0;
		for (UnitPrice unitPrice : unitPrices) {
			closingPrices[i++] = unitPrice.getClose();
		}

		float[] fastMA = MovingAverageCalculator.calculateArrayMA(macd.getFastSlowMAType(), closingPrices, fast);
		float[] slowMA = MovingAverageCalculator.calculateArrayMA(macd.getFastSlowMAType(), closingPrices, slow);

		float[] differenceMA = new float[size];
		for (int j = 0; j < size; j++) {
			if (j < slow - 1) {
				differenceMA[j] = 0.0f;
			} else {
				differenceMA[j] = fastMA[j] - slowMA[j];
			}
		}

		float[] signalMA = new float[size];
		float[] histogramMA = new float[size];
		int firstSignalValue = slow + smooth - 1 - 1;
		if (size >= firstSignalValue) {
			float[] calculableDifferenceMA = Arrays.copyOfRange(differenceMA, slow - 1, size);
			float[] calculableSignalMA = MovingAverageCalculator.calculateArrayMA(macd.getSmoothingMAType(), calculableDifferenceMA, macd.getSmoothingPeriod());

			for (int k = (slow - 1); k < size; k++) {
				signalMA[k] = calculableSignalMA[k - (slow - 1)];
			}

			for (int l = 0; l < size; l++) {
				if (l < firstSignalValue) {
					histogramMA[l] = 0.0f;
				} else {
					histogramMA[l] = differenceMA[l] - signalMA[l];
				}
			}
		}

		macd.setFastMAValues(fastMA);
		macd.setSlowMAValues(slowMA);
		macd.setDifferenceMAValues(differenceMA);
		macd.setSignalValues(signalMA);
		macd.setHistogramValues(histogramMA);
		return macd;
	}
}