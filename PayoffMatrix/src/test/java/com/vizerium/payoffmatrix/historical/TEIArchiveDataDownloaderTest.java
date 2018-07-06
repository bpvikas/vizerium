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
		unit.downloadDataforDate(LocalDate.of(2018, 2, 2));
	}

	@Test
	public void testDownloadDataforDateFrom() {
		unit.downloadDataforDateFrom(LocalDate.of(2018, 4, 23), 15);
	}

	@Test
	public void testDownloadDataforDateTo() {
		unit.downloadDataforDateTo(LocalDate.of(2018, 6, 28), 2);
	}

	@Test
	public void testDownloadDataforDateRange() {
		unit.downloadDataforDateRange(LocalDate.of(2018, 5, 9), LocalDate.of(2018, 5, 27));
	}
}
