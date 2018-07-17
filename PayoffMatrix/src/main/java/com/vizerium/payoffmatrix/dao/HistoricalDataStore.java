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

package com.vizerium.payoffmatrix.dao;

import java.util.Arrays;

import com.vizerium.payoffmatrix.volatility.BollingerBand;
import com.vizerium.payoffmatrix.volatility.DateRange;

public interface HistoricalDataStore {

	public float[] readHistoricalData(DateRange dateRange);

	public default float calculateSMA(float[] closingPrices, int numberOfDays) {
		float sma = 0.0f;
		for (int i = closingPrices.length - numberOfDays; i < closingPrices.length; i++) {
			sma += closingPrices[i];
		}
		return sma / numberOfDays;
	}

	public default float calculateEMA(float[] closingPrices, int numberOfDays) {
		float weightingMultiplier = 2.0f / (numberOfDays + 1);
		float ema = calculateSMA(Arrays.copyOfRange(closingPrices, 0, numberOfDays), numberOfDays);

		for (int i = numberOfDays; i < closingPrices.length; i++) {
			ema = (closingPrices[i] - ema) * weightingMultiplier + ema;
		}
		return ema;
	}

	public default float calculateRSI(float[] closingPrices, int numberOfDays) {
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

			averageGain = (averageGain * (numberOfDays - 1) + ((currentClose >= previousClose) ? (currentClose - previousClose) : 0)) / numberOfDays;
			averageLoss = (averageLoss * (numberOfDays - 1) + ((currentClose >= previousClose) ? 0 : (previousClose - currentClose))) / numberOfDays;
		}

		float rs = averageGain / averageLoss;
		float rsi = 100.0f - (100.0f / (1.0f + rs));
		return rsi;
	}

	public default BollingerBand calculateBollingerBand(float[] closingPrices, int numberOfDays, float standardDeviationMultiple) {
		float mid = calculateSMA(closingPrices, numberOfDays);

		float[] closingPricesNDays = Arrays.copyOfRange(closingPrices, closingPrices.length - numberOfDays, closingPrices.length);

		float sum = 0.0f, standardDeviation = 0.0f;

		for (float closingPricesNday : closingPricesNDays) {
			sum += closingPricesNday;
		}

		float mean = sum / closingPricesNDays.length;
		// squaring, summing, and averaging the divergence from the mean.
		for (float closingPricesNday : closingPricesNDays) {
			standardDeviation += Math.pow(closingPricesNday - mean, 2);
		}

		standardDeviation = (float) Math.sqrt(standardDeviation / closingPricesNDays.length);
		float low = mid - (standardDeviationMultiple * standardDeviation);
		float high = mid + (standardDeviationMultiple * standardDeviation);

		return new BollingerBand(low, mid, high);
	}
}
