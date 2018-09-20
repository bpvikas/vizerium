package com.vizerium.payoffmatrix.historical;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.vizerium.payoffmatrix.dao.HistoricalCsvDataStore;
import com.vizerium.payoffmatrix.dao.HistoricalDataStore;

public class TEIBHistoricalDataUpdaterTest {

	private TEIArchiveDataDownloader archiveDataDownloader;

	private String[] teibComponents = new String[] { "KNABSIXA", "ADORABKNAB", "KNBLAREDEF", "KNABCFDH", "KNABICICI", "KNABCFDI", "KBDNISUDNI", "KNABKATOK", "BNP", "KNABLBR",
			"NIBS", "KNABSEY" };

	private String[] testComponents = new String[] { "KNABSIXA" };

	@Before
	public void setup() {
		archiveDataDownloader = new TEIArchiveDataDownloader();
	}

	public void updateHistoricalData(HistoricalDataStore csvDataStore) {
		updateHistoricalData(LocalDate.now(), csvDataStore);
	}

	public void updateHistoricalData(LocalDate date, HistoricalDataStore csvDataStore) {
		archiveDataDownloader.downloadDataForDate(date);
		DayPriceData dayPriceData = archiveDataDownloader.readDataForDateAndScripName(date, csvDataStore.getUnderlyingName());
		csvDataStore.writeHistoricalData(dayPriceData.getDate(), dayPriceData.getOpen(), dayPriceData.getHigh(), dayPriceData.getLow(), dayPriceData.getClose(),
				dayPriceData.getVolume());
		updateAnalysisExcelSheet();
	}

	private void updateAnalysisExcelSheet() {
		// TODO Auto-generated method stub
	}

	@Test
	public void testIndividualHistoricalDataUpdate() {
		HistoricalDataStore csvDataStore = new HistoricalCsvDataStore(new StringBuilder(testComponents[0]).reverse().toString());
		updateHistoricalData(LocalDate.of(2018, 8, 14), csvDataStore);
	}
}
