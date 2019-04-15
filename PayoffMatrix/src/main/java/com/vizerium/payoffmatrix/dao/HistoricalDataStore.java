/*
 * Copyright 2019 Vizerium, Inc.
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

import java.time.LocalDate;
import java.util.Arrays;

import com.vizerium.commons.indicators.MovingAverageCalculator;
import com.vizerium.payoffmatrix.historical.DayPriceData;
import com.vizerium.payoffmatrix.volatility.BollingerBand;
import com.vizerium.payoffmatrix.volatility.DateRange;

public interface HistoricalDataStore {

	public float[] readHistoricalClosingPrices(DateRange dateRange);

	public DayPriceData[] readHistoricalData(DateRange dateRange);

	public void writeHistoricalData(LocalDate date, float open, float high, float low, float close, long volume);

	public String getUnderlyingName();

	public default BollingerBand calculateBollingerBand(float[] closingPrices, int numberOfDays, float standardDeviationMultiple) {
		float mid = MovingAverageCalculator.calculateSMA(closingPrices, numberOfDays);

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
