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

package com.vizerium.payoffmatrix.volatility;

import com.vizerium.payoffmatrix.dao.HistoricalCsvDataStore;
import com.vizerium.payoffmatrix.dao.HistoricalDataStore;

public class CsvHistoricalDataVolatilityCalculator implements VolatilityCalculator {

	private HistoricalCsvDataStore csvDataStore;

	public CsvHistoricalDataVolatilityCalculator() {

	}

	public CsvHistoricalDataVolatilityCalculator(String underlyingName) {
		this.csvDataStore = new HistoricalCsvDataStore(underlyingName);
	}

	public HistoricalDataStore getHistoricalDataStore() {
		return csvDataStore;
	}

	@Override
	public Volatility calculateVolatility(DateRange dateRange) {
		float[] closingPrices = csvDataStore.readHistoricalClosingPrices(dateRange);
		float[] logNaturalDailyReturns = calculateLogNaturalDailyReturns(closingPrices);

		Volatility volatility = calculateMeanAndStandardDeviation(logNaturalDailyReturns);
		volatility.setUnderlyingValue(closingPrices[closingPrices.length - 1]);
		return volatility;
	}

	private float[] calculateLogNaturalDailyReturns(float[] closingPrices) {
		float[] logNaturalDailyReturns = new float[closingPrices.length - 1];
		for (int i = 0; i < closingPrices.length - 1; i++) {
			logNaturalDailyReturns[i] = (float) Math.log(closingPrices[i + 1] / closingPrices[i]);
		}
		return logNaturalDailyReturns;
	}

	private Volatility calculateMeanAndStandardDeviation(float[] logNaturalDailyReturns) {

		float sum = 0.0f, standardDeviation = 0.0f;

		for (float logNaturalDailyReturn : logNaturalDailyReturns) {
			sum += logNaturalDailyReturn;
		}

		float mean = sum / logNaturalDailyReturns.length;
		// squaring, summing, and averaging the divergence from the mean.
		for (float logNaturalDailyReturn : logNaturalDailyReturns) {
			standardDeviation += Math.pow(logNaturalDailyReturn - mean, 2);
		}

		standardDeviation = (float) Math.sqrt(standardDeviation / logNaturalDailyReturns.length);
		return new Volatility(mean, standardDeviation);
	}

}
