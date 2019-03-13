package com.vizerium.commons.indicators;

public class RSICalculator {

	public static float calculateRSI(float[] closingPrices, int numberOfPeriods) {
		float initialTotalGain = 0.0f;
		float initialTotalLoss = 0.0f;

		for (int i = 1; i <= numberOfPeriods; i++) {
			float currentClose = closingPrices[i];
			float previousClose = closingPrices[i - 1];
			if (currentClose >= previousClose) {
				initialTotalGain += (currentClose - previousClose);
			} else {
				initialTotalLoss += (previousClose - currentClose);
			}
		}

		float averageGain = initialTotalGain / numberOfPeriods;
		float averageLoss = initialTotalLoss / numberOfPeriods;

		for (int i = numberOfPeriods + 1; i < closingPrices.length; i++) {
			float currentClose = closingPrices[i];
			float previousClose = closingPrices[i - 1];

			averageGain = (averageGain * (numberOfPeriods - 1) + ((currentClose >= previousClose) ? (currentClose - previousClose) : 0)) / numberOfPeriods;
			averageLoss = (averageLoss * (numberOfPeriods - 1) + ((currentClose >= previousClose) ? 0 : (previousClose - currentClose))) / numberOfPeriods;
		}

		float rs = averageGain / averageLoss;
		float rsi = 100.0f - (100.0f / (1.0f + rs));
		return rsi;
	}

}
