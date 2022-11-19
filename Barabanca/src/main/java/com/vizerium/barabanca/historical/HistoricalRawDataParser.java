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
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MovingAverageCalculator;
import com.vizerium.commons.indicators.StandardMovingAverages;
import com.vizerium.commons.io.FileUtils;
import com.vizerium.commons.util.NumberFormats;

public class HistoricalRawDataParser {

	private static final Logger logger = Logger.getLogger(HistoricalRawDataParser.class);

	private File rawDataZipFile;

	private static final String rawDataDirectoryPath = FileUtils.directoryPath + "underlying-raw-data-v2/";
	private static final String extractedDataDirectoryPath = FileUtils.directoryPath + "underlying-raw-data-v2-extract/";
	private static final String parsedExtractedDataDirectoryPath = extractedDataDirectoryPath + "parsedData/";

	public void extractRawData() {
		try {
			rawDataZipFile = FileUtils.getLastModifiedFileInDirectory(rawDataDirectoryPath, ".zip");
			ZipInputStream localRawDataFileStream = new ZipInputStream(new FileInputStream(rawDataZipFile));

			Set<String> minuteDataSet = new TreeSet<String>(); // TreeSet required for both uniqueness and sort capability.

			ZipEntry entry = null;
			while ((entry = localRawDataFileStream.getNextEntry()) != null) {
				logger.info(entry.getName());

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

			File extractDirectory = new File(extractedDataDirectoryPath);
			if (!extractDirectory.exists()) {
				extractDirectory.mkdir();
			}

			File parsedExtractDirectory = new File(parsedExtractedDataDirectoryPath);
			if (!parsedExtractDirectory.exists()) {
				parsedExtractDirectory.mkdir();
			}
			for (File parsedExtractFile : parsedExtractDirectory.listFiles()) {
				if (parsedExtractFile.isDirectory()) {
					for (File parsedExtractSubFile : parsedExtractFile.listFiles()) {
						parsedExtractSubFile.delete();
					}
				}
				parsedExtractFile.delete();
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
								logger.warn("1 min data for " + currentParsedDate + " has less than 4 entries.");
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
					logger.info(entry.getKey() + "\t" + entry.getValue() + "\t" + endTimeMap.get(entry.getKey()));
				}
			}

			logger.info("**********************************");
			for (Map.Entry<LocalDate, LocalTime> entry : endTimeMap.entrySet()) {
				if (entry.getValue().isBefore(LocalTime.of(15, 29)) || entry.getValue().isAfter(LocalTime.of(15, 34))) {
					logger.info(entry.getKey() + "\t" + startTimeMap.get(entry.getKey()) + "\t" + entry.getValue());
				}
			}

			logger.info("**********************************");
			logger.info("No of unique first four timings : " + firstFourTimingsSet.size());
			for (StartTimingPattern firstFourTimings : firstFourTimingsSet) {
				logger.info(firstFourTimings);
			}

		} catch (IOException ioe) {
			logger.error("An I/O error occurred while reading parsed historical data.", ioe);
			throw new RuntimeException(ioe);
		}
	}

	public void createTimeSeriesDataFiles(TimeFormat timeFormat) {
		if (timeFormat.getInterval() <= 0) {
			throw new RuntimeException("This method is not supported for creating multi day time Series data files ");
		}

		try {
			File[] parsedExtractedScripDataDirectories = new File(parsedExtractedDataDirectoryPath).listFiles();
			for (File parsedExtractedScripDataDirectory : parsedExtractedScripDataDirectories) {
				for (File parsedExtractedDataFile : parsedExtractedScripDataDirectory.listFiles()) {

					BufferedReader br = new BufferedReader(new FileReader(parsedExtractedDataFile));
					String dataLine = null;

					LocalDate currentParsedDate = LocalDate.MIN;
					// TreeMap required for sorting capability, and ensures that the keys stored in it are in a natural order by design.
					TreeMap<LocalDate, List<UnitPriceData>> currentMonthUnitPrices = new TreeMap<LocalDate, List<UnitPriceData>>();
					List<UnitPriceData> currentDateUnitPrices = new ArrayList<UnitPriceData>();

					while (StringUtils.isNotBlank(dataLine = br.readLine())) {
						String[] dataLineDetails = dataLine.split(",");
						UnitPriceData unitPriceData = new UnitPriceData(dataLineDetails);
						if (!unitPriceData.getDate().equals(currentParsedDate)) {
							if (!LocalDate.MIN.equals(currentParsedDate)) {
								currentMonthUnitPrices.put(currentParsedDate, currentDateUnitPrices);
							}
							currentDateUnitPrices = new ArrayList<UnitPriceData>();
							currentParsedDate = unitPriceData.getDate();
						}
						currentDateUnitPrices.add(unitPriceData);
					}
					// The line below has been added at the end as the last entry was not getting inserted because the condition
					// of if(!unitPriceData.getDate().equals(currentParsedDate)) will not be true as the last entry will have the
					// same date as that of the currentParsedDate.
					currentMonthUnitPrices.put(currentParsedDate, currentDateUnitPrices);
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
						BufferedWriter bw = new BufferedWriter(
								new FileWriter(extractedDataDirectoryPath + timeFormat.getProperty() + "/" + scripName + "/" + parsedExtractedDataFile.getName()));

						for (LocalDate currentDate : currentMonthUnitPrices.keySet()) {
							List<UnitPriceData> currentDateUnitPricesForIntervalCalculation = currentMonthUnitPrices.get(currentDate);

							// The below if (>=4) condition is for a special check on date 2009/5/18 when the 2009 election results caused an upper circuit and the NSE had to
							// halt trading for the day within 1 minute twice.
							if (currentDateUnitPricesForIntervalCalculation.size() >= 4) {
								StartTimingPattern currentDateStartTimePattern = StartTimingPattern.isValid(currentDate,
										new LocalTime[] { currentDateUnitPricesForIntervalCalculation.get(0).getTime(),
												currentDateUnitPricesForIntervalCalculation.get(1).getTime(), currentDateUnitPricesForIntervalCalculation.get(2).getTime(),
												currentDateUnitPricesForIntervalCalculation.get(3).getTime() });

								for (int i = 0; i < currentDateStartTimePattern.getTradingStartTimePosition(); i++) {
									currentDateUnitPricesForIntervalCalculation.remove(0);
								}
							}

							UnitPriceData currentIntervalUnitPriceData = currentDateUnitPricesForIntervalCalculation.get(0);
							for (int i = 0; i < currentDateUnitPricesForIntervalCalculation.size(); i++) {
								if (i % timeFormat.getInterval() == 0) {
									if (i != 0) {
										HistoricalDataDateRange.setStartDateTime(scripName, timeFormat, currentIntervalUnitPriceData.getDateTime());
										bw.write(currentIntervalUnitPriceData.toString());
										bw.newLine();
									}
									currentIntervalUnitPriceData = currentDateUnitPricesForIntervalCalculation.get(i);
								} else {
									UnitPriceData currentUnitPriceData = currentDateUnitPricesForIntervalCalculation.get(i);
									updateCurrentUnitPriceDataIntoCurrentIntervalUnitPriceData(currentUnitPriceData, currentIntervalUnitPriceData);
								}
								if (i == currentDateUnitPricesForIntervalCalculation.size() - 1) {
									if (TimeFormat._1DAY.equals(timeFormat)) {
										HistoricalDataDateRange.setStartDateTime(scripName, timeFormat, currentIntervalUnitPriceData.getDateTime());
									}
									HistoricalDataDateRange.setEndDateTime(scripName, timeFormat, currentIntervalUnitPriceData.getDateTime());
									bw.write(currentIntervalUnitPriceData.toString());
									if (!currentDate.equals(currentMonthUnitPrices.lastKey())) {
										// The above if condition is so that a blank line is not created at the end of the file,
										// which contains all data for all dates in that month.
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

	public void updateStandardMovingAveragesInTimeSeriesDataFiles(TimeFormat timeFormat) {
		logger.debug("Reading all " + timeFormat + " files for updating moving averages.");
		try {
			File timeSeriesDirectory = new File(extractedDataDirectoryPath + timeFormat.getProperty() + "/");
			for (File timeSeriesScripDataDirectory : timeSeriesDirectory.listFiles()) {

				List<UnitPriceData> scripUnitPrices = new ArrayList<UnitPriceData>();
				for (File timeSeriesScripDataFile : timeSeriesScripDataDirectory.listFiles()) {
					BufferedReader br = new BufferedReader(new FileReader(timeSeriesScripDataFile));
					String dataLine = null;
					while (StringUtils.isNotBlank(dataLine = br.readLine())) {
						String[] dataLineDetails = dataLine.split(",");
						UnitPriceData unitPriceData = new UnitPriceData(dataLineDetails);
						scripUnitPrices.add(unitPriceData);
					}
					br.close();
				}
				logger.debug("Finished reading all input files required to update moving averages. ");
				if (scripUnitPrices.size() > 0) {
					float[] closingPrices = new float[scripUnitPrices.size()];
					int l = 0;
					for (UnitPriceData scripUnitPrice : scripUnitPrices) {
						closingPrices[l++] = scripUnitPrice.getClose();
					}
					logger.debug("Created closing prices array.");

					float[][] movingAverages = new float[StandardMovingAverages.values().length][];
					int[] standardMovingAverages = StandardMovingAverages.getAllStandardMAsSorted();
					int k = 0;
					for (int standardMA : standardMovingAverages) {
						movingAverages[k++] = MovingAverageCalculator.calculateArrayEMA(closingPrices, standardMA);
					}
					logger.debug("Calculated all moving averages.");

					for (int i = 0; i < scripUnitPrices.size(); i++) {
						for (int j = 0; j < standardMovingAverages.length; j++) {
							scripUnitPrices.get(i).setMovingAverage(standardMovingAverages[j], movingAverages[j][i]);
						}
					}
					logger.debug("Updated all moving averages into unit prices.");

					String scripName = scripUnitPrices.get(0).getScripName();
					if (timeFormat.getInterval() < 0) {
						BufferedWriter bw = new BufferedWriter(new FileWriter(extractedDataDirectoryPath + timeFormat.getProperty() + "/" + scripName + "/" + scripName + ".txt"));
						for (int i = 0; i < scripUnitPrices.size(); i++) {
							bw.write(scripUnitPrices.get(i).toString() + scripUnitPrices.get(i).printMovingAverages());
							if (i != scripUnitPrices.size() - 1) {
								bw.newLine();
							}
						}
						bw.flush();
						bw.close();

					} else {
						String scripNameYearMonth = getUnitPriceScripNameYearMonth(scripUnitPrices.get(0));
						BufferedWriter bw = new BufferedWriter(
								new FileWriter(extractedDataDirectoryPath + timeFormat.getProperty() + "/" + scripName + "/" + scripNameYearMonth + ".txt"));
						for (int i = 0; i < scripUnitPrices.size(); i++) {
							if (scripNameYearMonth.equals(getUnitPriceScripNameYearMonth(scripUnitPrices.get(i)))) {
								bw.write(scripUnitPrices.get(i).toString() + scripUnitPrices.get(i).printMovingAverages());
								if ((i + 1 < scripUnitPrices.size()) && scripNameYearMonth.equals(getUnitPriceScripNameYearMonth(scripUnitPrices.get(i + 1)))) {
									bw.newLine();
								}
							} else {
								bw.flush();
								bw.close();
								logger.debug("Updated moving averages for " + scripNameYearMonth);
								scripNameYearMonth = getUnitPriceScripNameYearMonth(scripUnitPrices.get(i));
								bw = new BufferedWriter(
										new FileWriter(extractedDataDirectoryPath + timeFormat.getProperty() + "/" + scripName + "/" + scripNameYearMonth + ".txt"));
								bw.write(scripUnitPrices.get(i).toString() + scripUnitPrices.get(i).printMovingAverages());
								bw.newLine();
							}
						}
						bw.flush();
						bw.close();
					}
				}
			}
		} catch (IOException ioe) {
			logger.error("An I/O error occurred while updating moving averages to time Series data.", ioe);
			throw new RuntimeException(ioe);
		}
	}

	private String getUnitPriceScripNameYearMonth(UnitPriceData unitPrice) {
		String scripName = unitPrice.getScripName();
		LocalDate scripUnitPriceLocalDate = unitPrice.getDate();
		String yearMonth = String.valueOf(scripUnitPriceLocalDate.getYear()) + NumberFormats.getForMonth().format(scripUnitPriceLocalDate.getMonthValue());
		return scripName + yearMonth;
	}

	public void createMultiDayTimeSeriesDataFiles(TimeFormat timeFormat) {
		if (timeFormat.getInterval() > 0) {
			throw new RuntimeException("This method is not supported for creating time Series data files for upto 1 day. ");
		}

		try {
			File[] _1DayTimeSeriesScripDataDirectories = new File(extractedDataDirectoryPath + TimeFormat._1DAY.getProperty() + "/").listFiles();
			for (File _1DayTimeSeriesScripDataDirectory : _1DayTimeSeriesScripDataDirectories) {

				List<UnitPriceData> _1DayUnitPriceDataList = new ArrayList<UnitPriceData>();
				for (File _1DayTimeSeriesScripDataFile : _1DayTimeSeriesScripDataDirectory.listFiles()) {

					BufferedReader br = new BufferedReader(new FileReader(_1DayTimeSeriesScripDataFile));
					String dataLine = null;

					while (StringUtils.isNotBlank(dataLine = br.readLine())) {
						String[] dataLineDetails = dataLine.split(",");
						UnitPriceData unitPriceData = new UnitPriceData(dataLineDetails);
						_1DayUnitPriceDataList.add(unitPriceData);
					}
					br.close();
				}

				if (_1DayUnitPriceDataList.size() > 0) {

					File timeFormatDirectory = new File(extractedDataDirectoryPath + timeFormat.getProperty() + "/");
					if (!timeFormatDirectory.exists()) {
						timeFormatDirectory.mkdir();
					}

					String scripName = _1DayUnitPriceDataList.get(0).getScripName();
					File timeFormatForScripDirectory = new File(extractedDataDirectoryPath + timeFormat.getProperty() + "/" + scripName + "/");
					if (!timeFormatForScripDirectory.exists()) {
						timeFormatForScripDirectory.mkdir();
					}

					BufferedWriter bw = new BufferedWriter(new FileWriter(extractedDataDirectoryPath + timeFormat.getProperty() + "/" + scripName + "/" + scripName + ".txt"));

					if (TimeFormat._1WEEK.equals(timeFormat)) {
						int currentParsedDateWeekOfYear = 0;
						UnitPriceData currentIntervalUnitPriceData = _1DayUnitPriceDataList.get(0);

						for (UnitPriceData currentUnitPriceData : _1DayUnitPriceDataList) {
							if (!(currentUnitPriceData.getDate().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()) == currentParsedDateWeekOfYear)) {
								if (currentParsedDateWeekOfYear > 0) {
									HistoricalDataDateRange.setStartDateTime(scripName, timeFormat, currentIntervalUnitPriceData.getDateTime());
									bw.write(currentIntervalUnitPriceData.toString());
									bw.newLine();
								}
								currentIntervalUnitPriceData = currentUnitPriceData;
								currentParsedDateWeekOfYear = currentUnitPriceData.getDate().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());

							} else {
								updateCurrentUnitPriceDataIntoCurrentIntervalUnitPriceData(currentUnitPriceData, currentIntervalUnitPriceData);
							}
							if (_1DayUnitPriceDataList.get(_1DayUnitPriceDataList.size() - 1).equals(currentUnitPriceData)) {
								HistoricalDataDateRange.setEndDateTime(scripName, timeFormat, currentIntervalUnitPriceData.getDateTime());
								bw.write(currentIntervalUnitPriceData.toString());
							}
						}
					} else if (TimeFormat._1MONTH.equals(timeFormat)) {
						int currentMonth = 0;
						UnitPriceData currentIntervalUnitPriceData = _1DayUnitPriceDataList.get(0);

						for (UnitPriceData currentUnitPriceData : _1DayUnitPriceDataList) {
							if (!(currentUnitPriceData.getDate().getMonthValue() == currentMonth)) {
								if (currentMonth > 0) {
									HistoricalDataDateRange.setStartDateTime(scripName, timeFormat, currentIntervalUnitPriceData.getDateTime());
									bw.write(currentIntervalUnitPriceData.toString());
									bw.newLine();
								}
								currentIntervalUnitPriceData = currentUnitPriceData;
								currentMonth = currentUnitPriceData.getDate().getMonthValue();

							} else {
								updateCurrentUnitPriceDataIntoCurrentIntervalUnitPriceData(currentUnitPriceData, currentIntervalUnitPriceData);
							}
							if (_1DayUnitPriceDataList.get(_1DayUnitPriceDataList.size() - 1).equals(currentUnitPriceData)) {
								HistoricalDataDateRange.setEndDateTime(scripName, timeFormat, currentIntervalUnitPriceData.getDateTime());
								bw.write(currentIntervalUnitPriceData.toString());
							}
						}
					} else {
						bw.flush();
						bw.close();
						throw new RuntimeException("The supplied timeFormat is unsupported for this method " + timeFormat);
					}

					bw.flush();
					bw.close();
				}
			}
		} catch (IOException ioe) {
			logger.error("An I/O error occurred while converting parsed historical data to time Series data.", ioe);
			throw new RuntimeException(ioe);
		}
	}

	private void updateCurrentUnitPriceDataIntoCurrentIntervalUnitPriceData(UnitPriceData currentUnitPriceData, UnitPriceData currentIntervalUnitPriceData) {
		if (currentUnitPriceData.getHigh() > currentIntervalUnitPriceData.getHigh()) {
			currentIntervalUnitPriceData.setHigh(currentUnitPriceData.getHigh());
		}
		if (currentUnitPriceData.getLow() < currentIntervalUnitPriceData.getLow()) {
			currentIntervalUnitPriceData.setLow(currentUnitPriceData.getLow());
		}
		currentIntervalUnitPriceData.setClose(currentUnitPriceData.getClose());
	}
}
