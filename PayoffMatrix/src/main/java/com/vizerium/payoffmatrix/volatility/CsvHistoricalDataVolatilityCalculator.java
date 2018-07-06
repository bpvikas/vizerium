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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.vizerium.payoffmatrix.io.FileUtils;

public class CsvHistoricalDataVolatilityCalculator implements VolatilityCalculator {

	private String underlyingName;

	public CsvHistoricalDataVolatilityCalculator() {

	}

	public CsvHistoricalDataVolatilityCalculator(String underlyingName) {
		this.underlyingName = underlyingName;
	}

	@Override
	public Volatility calculateVolatility(DateRange dateRange) {
		float[] closingPrices = readClosingPrices(dateRange);
		float[] logNaturalDailyReturns = calculateLogNaturalDailyReturns(closingPrices);

		Volatility volatility = calculateMeanAndStandardDeviation(logNaturalDailyReturns);
		volatility.setUnderlyingValue(closingPrices[closingPrices.length - 1]);
		return volatility;
	}

	public float[] readClosingPrices(DateRange dateRange) {
		BufferedReader br = null;
		List<String> closingPricesString = new ArrayList<String>();

		try {
			File csvHistoricalDataFile = FileUtils.getLastModifiedFileInDirectory(FileUtils.directoryPath + "underlying-historical/", "historical_" + underlyingName + ".csv");
			br = new BufferedReader(new FileReader(csvHistoricalDataFile));
			String[] headers = br.readLine().split(",");

			if (headers.length != 7) {
				throw new RuntimeException("Inv Historical data CSV file usually has the headers. : Date, Close, Open, High, Low, Volume, Change %");
			}

			String csvHistoricalDataLine = null;
			while ((csvHistoricalDataLine = br.readLine()) != null) {
				String[] csvHistoricalDataDetails = csvHistoricalDataLine.replaceAll("\",", "\"#").split("#");
				if (csvHistoricalDataDetails.length != 7) {
					throw new RuntimeException("Historical data details obtained from CSV may not be correct. " + csvHistoricalDataLine);
				}
				if (isDateWithinRange(csvHistoricalDataDetails[0], dateRange)) {
					closingPricesString.add(csvHistoricalDataDetails[1].replaceAll("\"", "").replaceAll(",", "").trim());
				}
			}

			float[] closingPrices = new float[closingPricesString.size()];
			for (int i = 0; i < closingPricesString.size(); i++) {
				closingPrices[i] = Float.parseFloat(closingPricesString.get(i));
			}

			// reverse the array as the data in the investing.com csv files are in reverse order.
			ArrayUtils.reverse(closingPrices);

			return closingPrices;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
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

	private boolean isDateWithinRange(String dateString, DateRange dateRange) {
		dateString = dateString.replace("\"", "").trim();
		LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MMM dd, yyyy"));

		if (dateRange == null) {
			return true;
		} else if (dateRange.getStartDate() == null && dateRange.getEndDate() == null) {
			return true;
		} else if (dateRange.getStartDate() == null) {
			return date.compareTo(dateRange.getEndDate()) <= 0;
		} else if (dateRange.getEndDate() == null) {
			return date.compareTo(dateRange.getStartDate()) >= 0;
		} else {
			return (date.compareTo(dateRange.getStartDate()) >= 0) && (date.compareTo(dateRange.getEndDate()) <= 0);
		}
	}
}
