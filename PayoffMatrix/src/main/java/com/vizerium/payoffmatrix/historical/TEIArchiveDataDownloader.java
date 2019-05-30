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

package com.vizerium.payoffmatrix.historical;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

import com.vizerium.commons.io.FileUtils;
import com.vizerium.payoffmatrix.exchange.Exchanges;

public class TEIArchiveDataDownloader implements ArchiveDataDownloader {

	private static final Logger logger = Logger.getLogger(TEIArchiveDataDownloader.class);

	DateTimeFormatter localFileDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");

	@Override
	public void downloadData() {
		downloadDataForDateRange(LocalDate.now(), LocalDate.now());
	}

	public void downloadDataForDate(LocalDate date) {
		downloadDataForDateRange(date, date);
	}

	public void downloadDataForDateFrom(LocalDate fromDate, int numberOfdays) {
		downloadDataForDateRange(fromDate, fromDate.plusDays(numberOfdays));
	}

	public void downloadDataForDateTo(LocalDate toDate, int numberOfdays) {
		downloadDataForDateRange(toDate.minusDays(numberOfdays), toDate);
	}

	public void downloadDataForDateRange(LocalDate fromDate, LocalDate toDate) {
		if (Objects.isNull(fromDate) || Objects.isNull(toDate)) {
			throw new RuntimeException("From Date " + fromDate + " To Date " + toDate + " cannot be null.");
		}
		if (fromDate.compareTo(toDate) > 0) {
			throw new RuntimeException("From Date " + fromDate + " cannot be greater than To Date " + toDate);
		}

		for (LocalDate date = fromDate; date.compareTo(toDate) <= 0; date = date.plusDays(1)) {
			if (logger.isInfoEnabled()) {
				logger.info(date + " " + date.getDayOfWeek());
			}
			try {
				if (Exchanges.get("TEI").isHoliday(date)) {
					continue;
				}

				File localRawDataFile = new File(FileUtils.directoryPath + "underlying-raw-data/" + "cm" + localFileDateFormat.format(date) + ".zip");
				if (localRawDataFile.exists()) {
					if (logger.isInfoEnabled()) {
						logger.info("Raw data file already exists.");
					}
					continue;
				}

				DateTimeFormatter monthOnly = DateTimeFormatter.ofPattern("MMM");
				DateTimeFormatter fullDate = DateTimeFormatter.ofPattern("ddMMMyyyy");

				String historicalDataUrlString = new StringBuilder("/SEITIUQE/lacirotsih/tnetnoc/moc.aidniesn//:sptth").reverse().toString() + date.getYear() + "/"
						+ monthOnly.format(date).toUpperCase() + "/cm" + fullDate.format(date).toUpperCase() + new StringBuilder("piz.vsc.vahb").reverse().toString();

				if (logger.isInfoEnabled()) {
					logger.info(historicalDataUrlString);
				}
				URL url = new URL(historicalDataUrlString);
				BufferedInputStream is = new BufferedInputStream(url.openStream());

				FileOutputStream localRawDataFileStream = new FileOutputStream(localRawDataFile);

				int i = -1;
				while ((i = is.read()) != -1) {
					localRawDataFileStream.write(i);
				}
				localRawDataFileStream.flush();
				localRawDataFileStream.close();

				is.close();

				int sleepTime = Math.abs((new Random().nextInt(20000)) % 20000);
				Thread.sleep(sleepTime);

			} catch (FileNotFoundException e) {
				// This will happen in case of holidays, as the archive data file is not present for that day.
				logger.error("File not found while downloading archive data file.", e);
			} catch (Exception e) {
				logger.error("An error occurred while downloading archive data file.", e);
				throw new RuntimeException(e);
			}
		}
	}

	public DayPriceData[] readData() {
		return readDataForDateAndScripNames(LocalDate.now(), null);
	}

	public DayPriceData[] readDataForDate(LocalDate date) {
		return readDataForDateAndScripNames(date, null);
	}

	public DayPriceData readDataForDateAndScripName(LocalDate date, String scripName) {
		DayPriceData[] dayPriceDataArray = readDataForDateAndScripNames(date, new String[] { scripName });
		if (dayPriceDataArray != null && dayPriceDataArray.length > 0) {
			return dayPriceDataArray[0];
		} else {
			return null;
		}
	}

	private DayPriceData[] readDataForDateAndScripNames(LocalDate date, String[] scripNames) {

		List<DayPriceData> dayPriceDataList = new ArrayList<DayPriceData>();

		try {
			ZipInputStream localRawDataFileStream = new ZipInputStream(
					new FileInputStream(FileUtils.directoryPath + "underlying-raw-data/" + "cm" + localFileDateFormat.format(date) + ".zip"));
			ZipEntry entry = localRawDataFileStream.getNextEntry();

			String outputFilePath = FileUtils.directoryPath + "underlying-raw-data-extract/" + entry.getName();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFilePath));
			byte[] bytesIn = new byte[4096];
			int read = 0;
			while ((read = localRawDataFileStream.read(bytesIn)) != -1) {
				bos.write(bytesIn, 0, read);
			}
			bos.flush();
			bos.close();
			localRawDataFileStream.close();

			BufferedReader br = new BufferedReader(new FileReader(new File(outputFilePath)));
			String scripDayData = "";
			while ((scripDayData = br.readLine()) != null) {
				String[] scripDayDataDetails = scripDayData.split(",");

				if (scripNames == null || scripNames.length == 0 || (scripNames != null && scripNames.length > 0
						&& Arrays.stream(scripNames).anyMatch(scripDayDataDetails[0]::equals) && "EQ".equals(scripDayDataDetails[1].trim()))) {
					DayPriceData dayPriceData = new DayPriceData(date, scripDayDataDetails[0], scripDayDataDetails[1], Float.parseFloat(scripDayDataDetails[2]),
							Float.parseFloat(scripDayDataDetails[3]), Float.parseFloat(scripDayDataDetails[4]), Float.parseFloat(scripDayDataDetails[5]),
							Float.parseFloat(scripDayDataDetails[6]), Float.parseFloat(scripDayDataDetails[7]), Long.parseLong(scripDayDataDetails[8]));
					dayPriceDataList.add(dayPriceData);
				}
			}
			br.close();

		} catch (Exception e) {
			logger.error("An error occurred while reading local historical data.", e);
			throw new RuntimeException(e);
		}
		return dayPriceDataList.toArray(new DayPriceData[dayPriceDataList.size()]);
	}
}
