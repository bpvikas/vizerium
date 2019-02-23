package com.vizerium.barabanca.historical;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.vizerium.barabanca.dao.UnitPriceData;
import com.vizerium.commons.historical.MovingAverage;
import com.vizerium.commons.historical.MovingAverageCalculator;
import com.vizerium.commons.io.FileUtils;
import com.vizerium.commons.util.FloatArrayList;

public class HistoricalDataReader {

	private static final Logger logger = Logger.getLogger(HistoricalDataReader.class);

	private File rawDataZipFile;

	private static String rawDataDirectoryPath = FileUtils.directoryPath + "underlying-raw-data-v2/";
	private static String extractedDataDirectoryPath = FileUtils.directoryPath + "underlying-raw-data-v2-extract/";
	private static String parsedExtractedDataDirectoryPath = extractedDataDirectoryPath + "parsedData/";

	public void extractRawData() {
		try {
			rawDataZipFile = FileUtils.getLastModifiedFileInDirectory(rawDataDirectoryPath, ".zip");
			ZipInputStream localRawDataFileStream = new ZipInputStream(new FileInputStream(rawDataZipFile));

			Set<String> minuteDataSet = new TreeSet<String>(); // TreeSet required for both uniqueness and sort capability.

			ZipEntry entry = null;
			while ((entry = localRawDataFileStream.getNextEntry()) != null) {
				System.out.println(entry.getName());

				if (entry.getName().contains("Consolidated/") && !entry.isDirectory()) {

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] bytesIn = new byte[4096];
					int read = 0;
					while ((read = localRawDataFileStream.read(bytesIn)) != -1) {
						baos.write(bytesIn, 0, read);
					}
					String rawData = baos.toString();
					String[] minuteData = rawData.split(System.lineSeparator());
					minuteDataSet.addAll(Arrays.asList(minuteData));
					baos.flush();
					baos.close();
				}
			}

			localRawDataFileStream.close();

			File extractDirectory = new File(parsedExtractedDataDirectoryPath);
			for (File extractFile : extractDirectory.listFiles()) {
				extractFile.delete();
			}

			String outputFileName = "";
			String outputFilePathSuffix = ".txt";

			BufferedWriter bw = null;
			for (String minuteData : minuteDataSet) {
				String scripName = minuteData.substring(0, minuteData.indexOf(','));
				if (!(minuteData.substring(0, minuteData.indexOf(',') + 7).replace(",", "").equalsIgnoreCase(outputFileName))) {
					outputFileName = minuteData.substring(0, minuteData.indexOf(',') + 7).replace(",", "");
					if (bw != null) {
						bw.flush();
						bw.close();
					}

					String parsedExtractedDataDirectoryForScripFilePath = parsedExtractedDataDirectoryPath + scripName + "/";
					File parsedExtractedDataDirectoryForScripFile = new File(parsedExtractedDataDirectoryForScripFilePath);
					if (!parsedExtractedDataDirectoryForScripFile.exists()) {
						parsedExtractedDataDirectoryForScripFile.mkdir();
					}

					bw = new BufferedWriter(new FileWriter(parsedExtractedDataDirectoryForScripFilePath + outputFileName + outputFilePathSuffix));
				}
				bw.write(minuteData);
				bw.newLine();
			}
			if (bw != null) {
				bw.flush();
				bw.close();
			}
		} catch (IOException ioe) {
			logger.error("An I/O error occurred while reading local historical data.", ioe);
			throw new RuntimeException(ioe);
		}
	}

	public void validateData() {
		Map<LocalDate, LocalTime> startTimeMap = new TreeMap<LocalDate, LocalTime>();
		Map<LocalDate, LocalTime> endTimeMap = new TreeMap<LocalDate, LocalTime>();

		try {
			Set<StartTimingPattern> firstFourTimingsSet = new TreeSet<StartTimingPattern>();

			File[] parsedExtractedScripDataDirectories = new File(parsedExtractedDataDirectoryPath).listFiles();
			for (File parsedExtractedScripDataDirectory : parsedExtractedScripDataDirectories) {
				for (File parsedExtractedDataFile : parsedExtractedScripDataDirectory.listFiles()) {
					BufferedReader br = new BufferedReader(new FileReader(parsedExtractedDataFile));
					String dataLine = null;

					LocalDate currentParsedDate = LocalDate.MIN;
					List<LocalTime> firstFourTimingsOfCurrentDate = new ArrayList<LocalTime>(4);

					while (StringUtils.isNotBlank(dataLine = br.readLine())) {
						String[] dataLineDetails = dataLine.split(",");
						UnitPriceData unitPriceData = new UnitPriceData(dataLineDetails);

						if (!unitPriceData.getDate().isEqual(currentParsedDate)) {
							if (!currentParsedDate.equals(LocalDate.MIN) && firstFourTimingsOfCurrentDate.size() < 4) {
								System.out.println("1 min data for " + currentParsedDate + " has less than 4 entries.");
							}

							currentParsedDate = unitPriceData.getDate();
							firstFourTimingsOfCurrentDate = new ArrayList<LocalTime>(4);
						}
						if (firstFourTimingsOfCurrentDate.size() <= 4) {
							firstFourTimingsOfCurrentDate.add(unitPriceData.getTime());
						}
						if (firstFourTimingsOfCurrentDate.size() == 4) {
							StartTimingPattern startTimingPattern = StartTimingPattern.isValid(unitPriceData.getDate(), firstFourTimingsOfCurrentDate.toArray(new LocalTime[4]));
							firstFourTimingsSet.add(startTimingPattern);
						}

						if (!startTimeMap.containsKey(unitPriceData.getDate())) {
							startTimeMap.put(unitPriceData.getDate(), unitPriceData.getTime());
						}
						if (!endTimeMap.containsKey(unitPriceData.getDate())) {
							endTimeMap.put(unitPriceData.getDate(), unitPriceData.getTime());
						}
						if (startTimeMap.get(unitPriceData.getDate()).isAfter(unitPriceData.getTime())) {
							startTimeMap.put(unitPriceData.getDate(), unitPriceData.getTime());
						}
						if (endTimeMap.get(unitPriceData.getDate()).isBefore(unitPriceData.getTime())) {
							endTimeMap.put(unitPriceData.getDate(), unitPriceData.getTime());
						}
					}
					br.close();
				}
			}

			for (Map.Entry<LocalDate, LocalTime> entry : startTimeMap.entrySet()) {
				if (!entry.getValue().equals(LocalTime.of(9, 8)) && !entry.getValue().equals(LocalTime.of(9, 1)) && !entry.getValue().equals(LocalTime.of(9, 9))
						&& !entry.getValue().equals(LocalTime.of(9, 16)) && !entry.getValue().equals(LocalTime.of(9, 55)) && !entry.getValue().equals(LocalTime.of(9, 56))) {
					System.out.println(entry.getKey() + "\t" + entry.getValue() + "\t" + endTimeMap.get(entry.getKey()));
				}
			}

			System.out.println("**********************************");
			for (Map.Entry<LocalDate, LocalTime> entry : endTimeMap.entrySet()) {
				if (entry.getValue().isBefore(LocalTime.of(15, 29)) || entry.getValue().isAfter(LocalTime.of(15, 34))) {
					System.out.println(entry.getKey() + "\t" + startTimeMap.get(entry.getKey()) + "\t" + entry.getValue());
				}
			}

			System.out.println("**********************************");
			System.out.println("No of unique first four timings : " + firstFourTimingsSet.size());
			for (StartTimingPattern firstFourTimings : firstFourTimingsSet) {
				System.out.println(firstFourTimings);
			}

		} catch (IOException ioe) {
			logger.error("An I/O error occurred while reading parsed historical data.", ioe);
			throw new RuntimeException(ioe);
		}
	}

	public void createTimeSeriesDataFiles(TimeFormat timeFormat) {
		try {
			File[] parsedExtractedScripDataDirectories = new File(parsedExtractedDataDirectoryPath).listFiles();
			for (File parsedExtractedScripDataDirectory : parsedExtractedScripDataDirectories) {
				for (File parsedExtractedDataFile : parsedExtractedScripDataDirectory.listFiles()) {

					BufferedReader br = new BufferedReader(new FileReader(parsedExtractedDataFile));
					String dataLine = null;

					LocalDate currentParsedDate = LocalDate.MIN;
					TreeMap<LocalDate, List<UnitPriceData>> currentMonthUnitPrices = new TreeMap<LocalDate, List<UnitPriceData>>();
					List<UnitPriceData> currentDateUnitPrices = new ArrayList<UnitPriceData>();

					while (StringUtils.isNotBlank(dataLine = br.readLine())) {
						String[] dataLineDetails = dataLine.split(",");
						UnitPriceData unitPriceData = new UnitPriceData(dataLineDetails);
						if (!unitPriceData.getDate().equals(currentParsedDate)) {
							if (!LocalDate.MIN.equals(currentParsedDate)) {
								currentMonthUnitPrices.put(unitPriceData.getDate(), currentDateUnitPrices);
							}
							currentDateUnitPrices = new ArrayList<UnitPriceData>();
							currentParsedDate = unitPriceData.getDate();
						}
						currentDateUnitPrices.add(unitPriceData);
					}
					br.close();

					// we create a file to store current month data with the new interval values.
					// From currentMonthUnitPrices, we get map of currentDate and all UnitPrices (List) of the currentDate
					// from the list of 1 min prices in the current date
					// before iterating, we need to identify if there is a pre-open value in any of them, and remove that because we eventually want to do
					// Moving Average calculations for this, and we do not want pre-open data, modifying the values.
					// we iterate through each of them, and at every interval say 5 min, we pick the first 5 and then next 5 and then next 5 and so on
					// we find o h l c within that interval, and then create a new UnitPriceData object from the first of the five and
					// then populate the h l c values in that object. The scripName, date, time, o is already present in the first of the five.
					// We then write that to the current month file, as soon as we get that UnitPriceData which represents that interval.
					// We will do this as long as data exists for the current date.
					// For the last entry, we may or may not have, the exact count of the values as per the interval length.

					if (currentMonthUnitPrices.size() > 0) {

						File timeFormatDirectory = new File(extractedDataDirectoryPath + timeFormat.getProperty() + "/");
						if (!timeFormatDirectory.exists()) {
							timeFormatDirectory.mkdir();
						}

						String scripName = currentMonthUnitPrices.firstEntry().getValue().get(0).getScripName();
						File timeFormatForScripDirectory = new File(extractedDataDirectoryPath + timeFormat.getProperty() + "/" + scripName + "/");
						if (!timeFormatForScripDirectory.exists()) {
							timeFormatForScripDirectory.mkdir();
						}

						BufferedWriter bw = new BufferedWriter(new FileWriter(extractedDataDirectoryPath + timeFormat.getProperty() + "/" + scripName + "/"
								+ parsedExtractedDataFile.getName()));

						for (LocalDate currentDate : currentMonthUnitPrices.keySet()) {
							List<UnitPriceData> currentDateUnitPricesForIntervalCalculation = currentMonthUnitPrices.get(currentDate);

							// The below if condition is for a special check on date 2009/5/18 when the 2009 election results caused an upper circuit and the NSE had to
							// halt trading for the day within 1 minute twice.
							if (currentDateUnitPricesForIntervalCalculation.size() >= 4) {
								StartTimingPattern currentDateStartTimePattern = StartTimingPattern.isValid(currentDate, new LocalTime[] {
										currentDateUnitPricesForIntervalCalculation.get(0).getTime(), currentDateUnitPricesForIntervalCalculation.get(1).getTime(),
										currentDateUnitPricesForIntervalCalculation.get(2).getTime(), currentDateUnitPricesForIntervalCalculation.get(3).getTime() });

								for (int i = 0; i < currentDateStartTimePattern.getTradingStartTimePosition(); i++) {
									currentDateUnitPricesForIntervalCalculation.remove(0);
								}
							}

							UnitPriceData currentIntervalUnitPriceData = currentDateUnitPricesForIntervalCalculation.get(0);
							for (int i = 0; i < currentDateUnitPricesForIntervalCalculation.size(); i++) {
								if (i % timeFormat.getInterval() == 0) {
									if (i != 0) {
										bw.write(currentIntervalUnitPriceData.toString());
										bw.newLine();
									}
									currentIntervalUnitPriceData = currentDateUnitPricesForIntervalCalculation.get(i);
								} else {
									UnitPriceData currentUnitPriceData = currentDateUnitPricesForIntervalCalculation.get(i);
									if (currentUnitPriceData.getHigh() > currentIntervalUnitPriceData.getHigh()) {
										currentIntervalUnitPriceData.setHigh(currentUnitPriceData.getHigh());
									}
									if (currentUnitPriceData.getLow() < currentIntervalUnitPriceData.getLow()) {
										currentIntervalUnitPriceData.setLow(currentUnitPriceData.getLow());
									}
									currentIntervalUnitPriceData.setClose(currentUnitPriceData.getClose());
								}
								if (i == currentDateUnitPricesForIntervalCalculation.size() - 1) {
									bw.write(currentIntervalUnitPriceData.toString());
									if (!currentDate.equals(currentMonthUnitPrices.lastKey())) {
										bw.newLine();
									}
								}
							}
						}
						bw.flush();
						bw.close();
					}
				}
			}
		} catch (IOException ioe) {
			logger.error("An I/O error occurred while converting parsed historical data to time Series data.", ioe);
			throw new RuntimeException(ioe);
		}
	}

	public void updateMovingAveragesInTimeSeriesDataFiles(TimeFormat timeFormat) {
		try {
			File timeSeriesDirectory = new File(extractedDataDirectoryPath + timeFormat.getProperty() + "/");
			for (File timeSeriesScripDataDirectory : timeSeriesDirectory.listFiles()) {
				FloatArrayList closingPrices = new FloatArrayList();
				for (File timeSeriesScripDataFile : timeSeriesScripDataDirectory.listFiles()) {
					BufferedReader br = new BufferedReader(new FileReader(timeSeriesScripDataFile));
					String dataLine = null;
					List<UnitPriceData> currentMonthUnitPrices = new ArrayList<UnitPriceData>();
					while (StringUtils.isNotBlank(dataLine = br.readLine())) {
						String[] dataLineDetails = dataLine.split(",");
						UnitPriceData unitPriceData = new UnitPriceData(dataLineDetails);
						closingPrices.add(unitPriceData.getClose());
						calculateExponentialMovingAverage(closingPrices, unitPriceData);
						currentMonthUnitPrices.add(unitPriceData);
					}
					br.close();

					BufferedWriter bw = new BufferedWriter(new FileWriter(timeSeriesScripDataFile));
					for (int i = 0; i < currentMonthUnitPrices.size(); i++) {
						UnitPriceData unitPriceData = currentMonthUnitPrices.get(i);
						bw.write(unitPriceData.toString() + unitPriceData.printMovingAverages());
						if (i != currentMonthUnitPrices.size() - 1) {
							bw.newLine();
						}
					}
					bw.flush();
					bw.close();
				}
			}
		} catch (IOException ioe) {
			logger.error("An I/O error occurred while updating moving averages to time Series data.", ioe);
			throw new RuntimeException(ioe);
		}
	}

	private void calculateExponentialMovingAverage(FloatArrayList closingPrices, UnitPriceData unitPriceData) {
		float[] closingPricesArray = closingPrices.toArray();
		for (int ma : MovingAverage.getAllValues()) {
			float ema = MovingAverageCalculator.calculateEMA(closingPricesArray, ma);
			unitPriceData.setMovingAverage(ma, ema);
		}
	}
}
