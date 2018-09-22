package com.vizerium.payoffmatrix.historical;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

import com.vizerium.payoffmatrix.dao.HistoricalCsvDataStore;
import com.vizerium.payoffmatrix.dao.HistoricalDataStore;
import com.vizerium.payoffmatrix.io.FileUtils;
import com.vizerium.payoffmatrix.volatility.DateRange;

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
		updateAnalysisExcelSheet(date, csvDataStore);
	}

	private void updateAnalysisExcelSheet(LocalDate date, HistoricalDataStore csvDataStore) {

		try {
			File analysisExcelFile = FileUtils.getLastModifiedFileInDirectory(FileUtils.directoryPath + "bn-analysis/", ".xlsx");

			FileInputStream analysisFileInput = new FileInputStream(analysisExcelFile);
			Workbook workbook = new XSSFWorkbook(analysisFileInput);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					System.out.println();
					System.out.print(currentCell.getAddress() + "--");
					if (currentCell.getCellType() == CellType.STRING) {
						System.out.print(currentCell.getStringCellValue() + "--");
					} else if (currentCell.getCellType() == CellType.NUMERIC) {
						if (DateUtil.isCellDateFormatted(currentCell)) {
							System.out.print(currentCell.getDateCellValue() + "--");
						} else {
							System.out.print(currentCell.getNumericCellValue() + "--");
						}
					}
				}
			}

			float[] closingPrices = csvDataStore.readHistoricalClosingPrices(new DateRange(null, date));
			float _5dema = csvDataStore.calculateEMA(closingPrices, 5);
			float _9dema = csvDataStore.calculateEMA(closingPrices, 9);
			float _13dema = csvDataStore.calculateEMA(closingPrices, 13);
			float _26dema = csvDataStore.calculateEMA(closingPrices, 26);
			float _50dema = csvDataStore.calculateEMA(closingPrices, 50);
			float _100dema = csvDataStore.calculateEMA(closingPrices, 100);
			float _200dema = csvDataStore.calculateEMA(closingPrices, 200);

			DayPriceData dayPriceData = csvDataStore.readHistoricalData(new DateRange(date, date))[0];

			for (int i = 4; i <= 6; i++) {
				CellReference cr = new CellReference("BA" + i);
				Row row = datatypeSheet.getRow(cr.getRow());
				Cell cell = row.getCell(cr.getCol());
				System.out.println(cell.getAddress() + " : " + cell.getStringCellValue());
				cell.setCellValue(cell.getStringCellValue() + "--" + cell.getStringCellValue());
			}

			analysisFileInput.close();

			FileOutputStream analysisFileOutput = new FileOutputStream(analysisExcelFile);
			workbook.write(analysisFileOutput);
			workbook.close();
			analysisFileOutput.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testIndividualHistoricalDataUpdate() {
		HistoricalDataStore csvDataStore = new HistoricalCsvDataStore(new StringBuilder(testComponents[0]).reverse().toString());
		updateHistoricalData(LocalDate.of(2018, 8, 20), csvDataStore);
		updateHistoricalData(LocalDate.of(2018, 8, 21), csvDataStore);
		// updateHistoricalData(LocalDate.of(2018, 8, 22), csvDataStore);
		updateHistoricalData(LocalDate.of(2018, 8, 23), csvDataStore);
		updateHistoricalData(LocalDate.of(2018, 8, 24), csvDataStore);
		updateHistoricalData(LocalDate.of(2018, 8, 27), csvDataStore);
		updateHistoricalData(LocalDate.of(2018, 8, 28), csvDataStore);
	}

	@Test
	public void testAllTeibComponentsHistoricalDataUpdate() {
		for (String component : teibComponents) {
			HistoricalDataStore csvDataStore = new HistoricalCsvDataStore(new StringBuilder(component).reverse().toString());
			updateHistoricalData(csvDataStore);
		}
	}
}
