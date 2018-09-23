package com.vizerium.payoffmatrix.historical;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
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

	private String[] teibComponents = new String[] { "KNABCFDH", "KNABICICI", "KNABKATOK", "KBDNISUDNI", "NIBS", "KNABSIXA", "KNABSEY", "KNABLBR", "KNBLAREDEF", "ADORABKNAB",
			"BNP", "KNABCFDI" };

	// Position of KNABCFDH's details in the Analysis sheet.
	private int teibComponentsCurrentStartLocation = 223;

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
			Sheet analysisSheet = workbook.getSheetAt(0);

			float[] closingPrices = csvDataStore.readHistoricalClosingPrices(new DateRange(null, date));
			float _5dema = csvDataStore.calculateEMA(closingPrices, 5);
			float _9dema = csvDataStore.calculateEMA(closingPrices, 9);
			float _13dema = csvDataStore.calculateEMA(closingPrices, 13);
			float _26dema = csvDataStore.calculateEMA(closingPrices, 26);
			float _50dema = csvDataStore.calculateEMA(closingPrices, 50);
			float _100dema = csvDataStore.calculateEMA(closingPrices, 100);
			float _200dema = csvDataStore.calculateEMA(closingPrices, 200);

			DayPriceData dayPriceData = csvDataStore.readHistoricalData(new DateRange(date, date))[0];

			CellReference cr = new CellReference("A3");
			Row dateRow = analysisSheet.getRow(cr.getRow());

			Iterator<Cell> cellIterator = dateRow.iterator();

			String currentDateColumn = null;
			while (cellIterator.hasNext()) {
				Cell currentCell = cellIterator.next();
				if ((currentCell.getCellType() == CellType.NUMERIC) && DateUtil.isCellDateFormatted(currentCell)) {
					System.out.println(currentCell.getDateCellValue());
					if (currentCell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(date)) {
						currentDateColumn = CellReference.convertNumToColString(currentCell.getColumnIndex());
						System.out.println(currentDateColumn);
						break;
					}
				}
			}

			Cell openCell = getCell(analysisSheet, currentDateColumn + String.valueOf(teibComponentsCurrentStartLocation));
			openCell.setCellValue(dayPriceData.getOpen());
			Cell highCell = getCell(analysisSheet, currentDateColumn + String.valueOf(teibComponentsCurrentStartLocation + 1));
			highCell.setCellValue(dayPriceData.getHigh());
			Cell lowCell = getCell(analysisSheet, currentDateColumn + String.valueOf(teibComponentsCurrentStartLocation + 2));
			lowCell.setCellValue(dayPriceData.getLow());
			Cell closeCell = getCell(analysisSheet, currentDateColumn + String.valueOf(teibComponentsCurrentStartLocation + 3));
			closeCell.setCellValue(dayPriceData.getClose());
			Cell _5demaCell = getCell(analysisSheet, currentDateColumn + String.valueOf(teibComponentsCurrentStartLocation + 21));
			_5demaCell.setCellValue(_5dema);
			Cell _9demaCell = getCell(analysisSheet, currentDateColumn + String.valueOf(teibComponentsCurrentStartLocation + 22));
			_9demaCell.setCellValue(_9dema);
			Cell _13demaCell = getCell(analysisSheet, currentDateColumn + String.valueOf(teibComponentsCurrentStartLocation + 23));
			_13demaCell.setCellValue(_13dema);
			Cell _26demaCell = getCell(analysisSheet, currentDateColumn + String.valueOf(teibComponentsCurrentStartLocation + 24));
			_26demaCell.setCellValue(_26dema);
			Cell _50demaCell = getCell(analysisSheet, currentDateColumn + String.valueOf(teibComponentsCurrentStartLocation + 25));
			_50demaCell.setCellValue(_50dema);
			Cell _100demaCell = getCell(analysisSheet, currentDateColumn + String.valueOf(teibComponentsCurrentStartLocation + 26));
			_100demaCell.setCellValue(_100dema);
			Cell _200demaCell = getCell(analysisSheet, currentDateColumn + String.valueOf(teibComponentsCurrentStartLocation + 27));
			_200demaCell.setCellValue(_200dema);

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

	private Cell getCell(Sheet sheet, String cellReference) {
		CellReference cellRef = new CellReference(cellReference);
		return sheet.getRow(cellRef.getRow()).getCell(cellRef.getCol());
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
			updateHistoricalData(LocalDate.of(2018, 9, 21), csvDataStore);
			teibComponentsCurrentStartLocation += 40;
		}
	}
}
