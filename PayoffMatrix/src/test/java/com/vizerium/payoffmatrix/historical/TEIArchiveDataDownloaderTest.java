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

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.vizerium.payoffmatrix.historical.TEIArchiveDataDownloader;

public class TEIArchiveDataDownloaderTest {

	private TEIArchiveDataDownloader unit;

	@Before
	public void setup() {
		unit = new TEIArchiveDataDownloader();
	}

	@Test
	public void testDownloadData() {
		unit.downloadData();
	}

	@Test
	public void testDownloadDataforDate() {
		unit.downloadDataForDate(LocalDate.of(2018, 2, 2));
	}

	@Test
	public void testDownloadDataforDateFrom() {
		unit.downloadDataForDateFrom(LocalDate.of(2018, 4, 23), 15);
	}

	@Test
	public void testDownloadDataforDateTo() {
		unit.downloadDataForDateTo(LocalDate.of(2018, 6, 28), 2);
	}

	@Test
	public void testDownloadDataforDateRange() {
		unit.downloadDataForDateRange(LocalDate.of(2018, 5, 9), LocalDate.of(2018, 5, 27));
	}
}
