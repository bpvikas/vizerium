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

	private static NumberFormat numberformat = NumberFormat.getInstance();

	static {
		numberformat.setMinimumFractionDigits(2);
		numberformat.setMaximumFractionDigits(2);
		numberformat.setGroupingUsed(true);
	}

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

		float previousClose = Float.parseFloat(csvHistoricalDataLines.get(1).replaceAll("\",", "\"#").split("#")[1].replaceAll("\"", "").replaceAll(",", ""));
		String percentageChangeOverPrevious = calculatePercentageChangeOverPrevious(close, previousClose);

		String dateString = DateTimeFormatter.ofPattern("MMM dd, yyyy").format(date);
		String localDateHistoricalData = "\"" + dateString + "\",\"" + numberformat.format(close) + "\",\"" + numberformat.format(open) + "\",\"" + numberformat.format(high)
				+ "\",\"" + numberformat.format(low) + "\",\"" + formatVolumeData(volume) + "\",\"" + percentageChangeOverPrevious + "\"";

		boolean newCsvHistoricalDataLinePreExists = false;
		for (String csvHistoricalDataLine : csvHistoricalDataLines) {
			if (csvHistoricalDataLine.indexOf(dateString) > 0) {
				System.out.println("Data for " + underlyingName + " for date " + dateString + " already exists.");
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
