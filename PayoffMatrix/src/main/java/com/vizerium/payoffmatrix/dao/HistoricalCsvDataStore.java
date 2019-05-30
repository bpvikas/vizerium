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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.vizerium.commons.io.FileUtils;
import com.vizerium.commons.util.NumberFormats;
import com.vizerium.payoffmatrix.historical.DayPriceData;
import com.vizerium.payoffmatrix.volatility.DateRange;

public class HistoricalCsvDataStore implements HistoricalDataStore {

	private static final Logger logger = Logger.getLogger(HistoricalCsvDataStore.class);

	private String underlyingName;

	private static final NumberFormat numberformat = NumberFormats.getForPrice();

	public HistoricalCsvDataStore() {

	}

	public HistoricalCsvDataStore(String underlyingName) {
		this.underlyingName = underlyingName;
	}

	@Override
	public DayPriceData[] readHistoricalData(DateRange dateRange) {

		String[] historicalFileLines = readHistoricalDataFileLines(dateRange);

		DayPriceData[] dayPriceData = new DayPriceData[historicalFileLines.length];
		for (int i = 0; i < historicalFileLines.length; i++) {
			String[] csvHistoricalDataDetails = historicalFileLines[i].split("#");
			dayPriceData[i] = new DayPriceData(LocalDate.parse(csvHistoricalDataDetails[0], DateTimeFormatter.ofPattern("MMM dd yyyy")), underlyingName,
					Float.parseFloat(csvHistoricalDataDetails[2]), Float.parseFloat(csvHistoricalDataDetails[3]), Float.parseFloat(csvHistoricalDataDetails[4]),
					Float.parseFloat(csvHistoricalDataDetails[1]));
		}
		return dayPriceData;
	}

	@Override
	public float[] readHistoricalClosingPrices(DateRange dateRange) {
		String[] historicalFileLines = readHistoricalDataFileLines(dateRange);

		float[] closingPrices = new float[historicalFileLines.length];
		for (int i = 0; i < historicalFileLines.length; i++) {
			String[] csvHistoricalDataDetails = historicalFileLines[i].split("#");
			closingPrices[i] = Float.parseFloat(csvHistoricalDataDetails[1]);
		}
		return closingPrices;
	}

	private String[] readHistoricalDataFileLines(DateRange dateRange) {
		BufferedReader br = null;
		List<String> historicalDataFileLines = new ArrayList<String>();

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
					historicalDataFileLines.add(csvHistoricalDataLine.replaceAll("\",", "\"#").replaceAll("\"", "").replaceAll(",", "").trim());
				}
			}

			String[] historicalDataFileLinesArray = historicalDataFileLines.toArray(new String[historicalDataFileLines.size()]);
			// reverse the array as the data in the investing csv files are in reverse order.
			ArrayUtils.reverse(historicalDataFileLinesArray);

			return historicalDataFileLinesArray;

		} catch (Exception e) {
			logger.error("An error occurred while reading historical data file.", e);
			throw new RuntimeException(e);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("An error occurred while closing historical data file.", e);
				throw new RuntimeException(e);
			}
		}
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

	@Override
	public void writeHistoricalData(LocalDate date, float open, float high, float low, float close, long volume) {
		BufferedReader br = null;

		List<String> csvHistoricalDataLines = new ArrayList<String>();

		try {
			File csvHistoricalDataFile = FileUtils.getLastModifiedFileInDirectory(FileUtils.directoryPath + "underlying-historical/", "historical_" + underlyingName + ".csv");
			br = new BufferedReader(new FileReader(csvHistoricalDataFile));

			String csvHistoricalDataLine = null;
			while ((csvHistoricalDataLine = br.readLine()) != null) {
				csvHistoricalDataLines.add(csvHistoricalDataLine);
			}
		} catch (Exception e) {
			logger.error("An error occurred while reading historical data file before writing into it.", e);
			throw new RuntimeException(e);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("An error occurred while closing historical data file.", e);
				throw new RuntimeException(e);
			}
		}

		float previousClose = Float.parseFloat(csvHistoricalDataLines.get(1).replaceAll("\",", "\"#").split("#")[1].replaceAll("\"", "").replaceAll(",", ""));
		String percentageChangeOverPrevious = calculatePercentageChangeOverPrevious(close, previousClose);

		String dateString = DateTimeFormatter.ofPattern("MMM dd, yyyy").format(date);
		String localDateHistoricalData = "\"" + dateString + "\",\"" + numberformat.format(close) + "\",\"" + numberformat.format(open) + "\",\"" + numberformat.format(high)
				+ "\",\"" + numberformat.format(low) + "\",\"" + formatVolumeData(volume) + "\",\"" + percentageChangeOverPrevious + "\"";

		boolean newCsvHistoricalDataLinePreExists = false;
		for (String csvHistoricalDataLine : csvHistoricalDataLines) {
			if (csvHistoricalDataLine.indexOf(dateString) > 0) {
				if (logger.isInfoEnabled()) {
					logger.info("Data for " + underlyingName + " for date " + dateString + " already exists.");
				}
				newCsvHistoricalDataLinePreExists = true;
				break;
			}
		}

		if (!newCsvHistoricalDataLinePreExists) {
			csvHistoricalDataLines.add(1, localDateHistoricalData);
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(new File(FileUtils.directoryPath + "underlying-historical/" + "historical_" + underlyingName + ".csv")));
				for (String csvHistoricalDataLine : csvHistoricalDataLines) {
					bw.write(csvHistoricalDataLine);
					bw.newLine();
				}
			} catch (Exception e) {
				logger.error("An error occurred while writing historical data file.", e);
				throw new RuntimeException(e);
			} finally {
				try {
					bw.close();
				} catch (Exception e) {
					logger.error("An error occurred while closing historical data file.", e);
					throw new RuntimeException(e);
				}
			}
		}
	}

	private String formatVolumeData(long volume) {

		if (volume < Math.pow(10, 3)) {
			return String.valueOf(volume);
		} else if (volume >= Math.pow(10, 3) && volume < Math.pow(10, 6)) {
			return numberformat.format(volume / Math.pow(10, 3)) + "K";
		} else if (volume >= Math.pow(10, 6) && volume < Math.pow(10, 9)) {
			return numberformat.format(volume / Math.pow(10, 6)) + "M";
		} else if (volume >= Math.pow(10, 9)) {
			return numberformat.format(volume / Math.pow(10, 9)) + "B";
		}
		return String.valueOf(volume);
	}

	private String calculatePercentageChangeOverPrevious(float close, float previousClose) {
		double percentageChangeOverPrevious = ((close - previousClose) / previousClose) * 100;
		return numberformat.format(percentageChangeOverPrevious) + "%";
	}

	@Override
	public String getUnderlyingName() {
		return underlyingName;
	}
}
