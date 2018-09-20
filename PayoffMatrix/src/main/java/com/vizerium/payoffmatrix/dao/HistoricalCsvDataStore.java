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

import com.vizerium.payoffmatrix.io.FileUtils;
import com.vizerium.payoffmatrix.volatility.DateRange;

public class HistoricalCsvDataStore implements HistoricalDataStore {

	private String underlyingName;

	public HistoricalCsvDataStore() {

	}

	public HistoricalCsvDataStore(String underlyingName) {
		this.underlyingName = underlyingName;
	}

	@Override
	public float[] readHistoricalData(DateRange dateRange) {
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

			// reverse the array as the data in the investing csv files are in reverse order.
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
	public void writeHistoricalData(LocalDate date, String open, String high, String low, String close, String volume) {
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

		String previousClose = csvHistoricalDataLines.get(1).replaceAll("\",", "\"#").split("#")[1].replaceAll("\"", "");
		String percentageChangeOverPrevious = calculatePercentageChangeOverPrevious(close, previousClose);

		volume = formatVolumeData(volume);
		String dateString = DateTimeFormatter.ofPattern("MMM dd, yyyy").format(date);
		String localDateHistoricalData = "\"" + dateString + "\",\"" + close + "\",\"" + open + "\",\"" + high + "\",\"" + low + "\",\"" + volume + "\",\""
				+ percentageChangeOverPrevious + "\"";

		csvHistoricalDataLines.add(1, localDateHistoricalData);

		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(new File(FileUtils.directoryPath + "underlying-historical/" + "historical_" + underlyingName + ".csv")));
			for (String csvHistoricalDataLine : csvHistoricalDataLines) {
				bw.write(csvHistoricalDataLine);
				bw.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	private String formatVolumeData(String volume) {
		NumberFormat numberformat = NumberFormat.getInstance();
		numberformat.setMinimumFractionDigits(2);

		int volumeInt = Integer.parseInt(volume);
		if (volumeInt < Math.pow(10, 3)) {
			// volume remains as it is
		} else if (volumeInt >= Math.pow(10, 3) && volumeInt < Math.pow(10, 6)) {
			volume = numberformat.format(volumeInt / Math.pow(10, 3)) + "K";
		} else if (volumeInt >= Math.pow(10, 6) && volumeInt < Math.pow(10, 9)) {
			volume = numberformat.format(volumeInt / Math.pow(10, 6)) + "M";
		} else if (volumeInt >= Math.pow(10, 9)) {
			volume = numberformat.format(volumeInt / Math.pow(10, 9)) + "B";
		}
		return volume;
	}

	private String calculatePercentageChangeOverPrevious(String close, String previousClose) {

		NumberFormat numberformat = NumberFormat.getInstance();
		numberformat.setMinimumFractionDigits(2);

		double percentageChangeOverPrevious = (((Float.parseFloat(close) - Float.parseFloat(previousClose)) / Float.parseFloat(previousClose)) - 1) * 100;

		return numberformat.format(percentageChangeOverPrevious) + "%";
	}

	@Override
	public String getUnderlyingName() {
		return underlyingName;
	}
}
