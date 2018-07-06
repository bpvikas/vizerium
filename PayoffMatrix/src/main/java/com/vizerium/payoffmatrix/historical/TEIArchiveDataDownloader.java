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

package com.vizerium.payoffmatrix.historical;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;

import com.vizerium.payoffmatrix.exchange.Exchanges;
import com.vizerium.payoffmatrix.io.FileUtils;

public class TEIArchiveDataDownloader implements ArchiveDataDownloader {

	@Override
	public void downloadData() {
		downloadDataforDateRange(LocalDate.now(), LocalDate.now());
	}

	public void downloadDataforDate(LocalDate date) {
		downloadDataforDateRange(date, date);
	}

	public void downloadDataforDateFrom(LocalDate fromDate, int numberOfdays) {
		downloadDataforDateRange(fromDate, fromDate.plusDays(numberOfdays));
	}

	public void downloadDataforDateTo(LocalDate toDate, int numberOfdays) {
		downloadDataforDateRange(toDate.minusDays(numberOfdays), toDate);
	}

	public void downloadDataforDateRange(LocalDate fromDate, LocalDate toDate) {
		if (Objects.isNull(fromDate) || Objects.isNull(toDate)) {
			throw new RuntimeException("From Date " + fromDate + " To Date " + toDate + " cannot be null.");
		}
		if (fromDate.compareTo(toDate) > 0) {
			throw new RuntimeException("From Date " + fromDate + " cannot be greater than To Date " + toDate);
		}

		for (LocalDate date = fromDate; date.compareTo(toDate) <= 0; date = date.plusDays(1)) {
			System.out.println(date + " " + date.getDayOfWeek());
			try {
				if (Exchanges.get("TEI").isHoliday(date)) {
					continue;
				}

				DateTimeFormatter monthOnly = DateTimeFormatter.ofPattern("MMM");
				DateTimeFormatter fullDate = DateTimeFormatter.ofPattern("ddMMMyyyy");
				DateTimeFormatter outputFileDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");

				String historicalDataUrlString = new StringBuilder("/SEITIUQE/lacirotsih/tnetnoc/moc.aidniesn//:sptth").reverse().toString() + date.getYear() + "/"
						+ monthOnly.format(date).toUpperCase() + "/cm" + fullDate.format(date).toUpperCase() + new StringBuilder("piz.vsc.vahb").reverse().toString();

				System.out.println(historicalDataUrlString);

				URL url = new URL(historicalDataUrlString);
				BufferedInputStream is = new BufferedInputStream(url.openStream());

				FileOutputStream localRawDataFileStream = new FileOutputStream(FileUtils.directoryPath + "underlying-raw-data/" + "cm" + outputFileDateFormat.format(date)
						+ "bhav.csv.zip");

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
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
}
