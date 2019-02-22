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
import com.vizerium.commons.io.FileUtils;

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
				if (!(minuteData.substring(0, minuteData.indexOf(',') + 7).replace(",", "").equalsIgnoreCase(outputFileName))) {
					outputFileName = minuteData.substring(0, minuteData.indexOf(',') + 7).replace(",", "");
					if (bw != null) {
						bw.flush();
						bw.close();
					}
					bw = new BufferedWriter(new FileWriter(parsedExtractedDataDirectoryPath + outputFileName + outputFilePathSuffix));
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

	public void validateData(LocalDate startDate, LocalDate endDate) {

		Map<LocalDate, LocalTime> startDateMap = new TreeMap<LocalDate, LocalTime>();
		Map<LocalDate, LocalTime> endDateMap = new TreeMap<LocalDate, LocalTime>();

		try {

			Set<StartTimingPattern> firstFourTimingsSet = new TreeSet<StartTimingPattern>();

			File[] parsedExtractedDataFiles = new File(parsedExtractedDataDirectoryPath).listFiles();
			for (File parsedExtractedDataFile : parsedExtractedDataFiles) {
				LocalDate fileDate = getFileDate(parsedExtractedDataFile.getName());
				if (((fileDate.getYear() == startDate.getYear() && fileDate.getMonth().compareTo(startDate.getMonth()) >= 0) || (fileDate.getYear() > startDate.getYear()))
						&& ((fileDate.getYear() == endDate.getYear() && fileDate.getMonth().compareTo(endDate.getMonth()) <= 0) || (fileDate.getYear() < endDate.getYear()))) {
					BufferedReader br = new BufferedReader(new FileReader(parsedExtractedDataFile));
					String dataLine = null;

					LocalDate currentParsedDate = LocalDate.MIN;
					List<LocalTime> firstFourTimingsOfCurrentDate = new ArrayList<LocalTime>(4);

					while (StringUtils.isNotBlank(dataLine = br.readLine())) {
						String[] dataLineDetails = dataLine.split(",");
						UnitPriceData unitPriceData = new UnitPriceData(dataLineDetails);

						if (!unitPriceData.getDate().isEqual(currentParsedDate)) {
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

						if (!unitPriceData.getDate().isBefore(startDate) && !unitPriceData.getDate().isAfter(endDate)) {
							if (!startDateMap.containsKey(unitPriceData.getDate())) {
								startDateMap.put(unitPriceData.getDate(), unitPriceData.getTime());
							}
							if (!endDateMap.containsKey(unitPriceData.getDate())) {
								endDateMap.put(unitPriceData.getDate(), unitPriceData.getTime());
							}
							if (startDateMap.get(unitPriceData.getDate()).isAfter(unitPriceData.getTime())) {
								startDateMap.put(unitPriceData.getDate(), unitPriceData.getTime());
							}
							if (endDateMap.get(unitPriceData.getDate()).isBefore(unitPriceData.getTime())) {
								endDateMap.put(unitPriceData.getDate(), unitPriceData.getTime());
							}
						}
					}
					br.close();
				}
			}

			for (Map.Entry<LocalDate, LocalTime> entry : startDateMap.entrySet()) {
				if (!entry.getValue().equals(LocalTime.of(9, 8)) && !entry.getValue().equals(LocalTime.of(9, 1)) && !entry.getValue().equals(LocalTime.of(9, 9))
						&& !entry.getValue().equals(LocalTime.of(9, 16)) && !entry.getValue().equals(LocalTime.of(9, 55)) && !entry.getValue().equals(LocalTime.of(9, 56))) {
					System.out.println(entry.getKey() + "\t" + entry.getValue() + "\t" + endDateMap.get(entry.getKey()));
				}
			}

			System.out.println("**********************************");
			for (Map.Entry<LocalDate, LocalTime> entry : endDateMap.entrySet()) {
				if (entry.getValue().isBefore(LocalTime.of(15, 29)) || entry.getValue().isAfter(LocalTime.of(15, 34))) {
					System.out.println(entry.getKey() + "\t" + startDateMap.get(entry.getKey()) + "\t" + entry.getValue());
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

	private LocalDate getFileDate(String name) {
		String year = name.substring(name.lastIndexOf('.') - 6, name.lastIndexOf('.') - 2);
		String month = name.substring(name.lastIndexOf('.') - 2, name.lastIndexOf('.'));
		return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1);
	}

}
