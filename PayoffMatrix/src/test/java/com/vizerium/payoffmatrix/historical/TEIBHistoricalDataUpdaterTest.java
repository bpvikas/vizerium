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
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

import com.vizerium.commons.io.FileUtils;
import com.vizerium.payoffmatrix.dao.HistoricalCsvDataStore;
import com.vizerium.payoffmatrix.dao.HistoricalDataStore;
import com.vizerium.payoffmatrix.volatility.DateRange;

public class TEIBHistoricalDataUpdaterTest {

	private TEIArchiveDataDownloader archiveDataDownloader;

	private String teib = "XEDNIKNAB";

	// Position of KNABCFDH's details in the Analysis sheet.
	private int teibCurrentStartLocation = 6;

	private String[] teibComponents = new String[] { "KNABCFDH", "KNABKATOK", "KNABICICI", "KBDNISUDNI", "NIBS", "KNABSIXA", "KNABSEY", "KNABLBR", "KNBLAREDEF", "ADORABKNAB",
			"BNP", "KNABCFDI" };

	// Position of KNABCFDH's details in the Analysis sheet.
	private int teibComponentsCurrentStartLocation = 223;

	private String[] nonTeibComponents = new String[] { "RNIDSU", "XEDNIRALLOD", "EDURCITW", "EDURCTNERB" };

	// Position of RNIDSU's details in the Analysis sheet.
	private int nonTeibComponentsCurrentStartLocation = 63;

	private String[] testComponents = new String[] { "KNABSIXA" };

	// Position of KNABSIXA's details in the Analysis sheet.
	private int testComponentsCurrentStartLocation = 423;

	@Before
	public void setup() {
		archiveDataDownloader = new TEIArchiveDataDownloader();
	}

	public void updateHistoricalData(HistoricalDataStore csvDataStore, int startLocation) {
		updateHistoricalData(LocalDate.now(), csvDataStore, startLocation);
	}

	public void updateHistoricalData(LocalDate date, HistoricalDataStore csvDataStore, int startLocation) {
		archiveDataDownloader.downloadDataForDate(date);
		DayPriceData dayPriceData = archiveDataDownloader.readDataForDateAndScripName(date, csvDataStore.getUnderlyingName());
		if (dayPriceData != null) { // This is for all the nonTeib components which are not in the downloaded data.
			csvDataStore.writeHistoricalData(dayPriceData.getDate(), dayPriceData.getOpen(), dayPriceData.getHigh(), dayPriceData.getLow(), dayPriceData.getClose(),
					dayPriceData.getVolume());
		}
		updateAnalysisExcelSheet(date, csvDataStore, startLocation);
	}

	private void updateAnalysisExcelSheet(LocalDate date, HistoricalDataStore csvDataStore, int startLocation) {
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

			Cell openCell = getCell(analysisSheet, currentDateColumn + String.valueOf(startLocation));
			openCell.setCellValue(dayPriceData.getOpen());
			Cell highCell = getCell(analysisSheet, currentDateColumn + String.valueOf(startLocation + 1));
			highCell.setCellValue(dayPriceData.getHigh());
			Cell lowCell = getCell(analysisSheet, currentDateColumn + String.valueOf(startLocation + 2));
			lowCell.setCellValue(dayPriceData.getLow());
			Cell closeCell = getCell(analysisSheet, currentDateColumn + String.valueOf(startLocation + 3));
			closeCell.setCellValue(dayPriceData.getClose());

			if (new StringBuilder(csvDataStore.getUnderlyingName()).reverse().toString().equals(teib)) {
				startLocation = startLocation + 17;
			}

			Cell _5demaCell = getCell(analysisSheet, currentDateColumn + String.valueOf(startLocation + 21));
			_5demaCell.setCellValue(_5dema);
			Cell _9demaCell = getCell(analysisSheet, currentDateColumn + String.valueOf(startLocation + 22));
			_9demaCell.setCellValue(_9dema);
			Cell _13demaCell = getCell(analysisSheet, currentDateColumn + String.valueOf(startLocation + 23));
			_13demaCell.setCellValue(_13dema);
			Cell _26demaCell = getCell(analysisSheet, currentDateColumn + String.valueOf(startLocation + 24));
			_26demaCell.setCellValue(_26dema);
			Cell _50demaCell = getCell(analysisSheet, currentDateColumn + String.valueOf(startLocation + 25));
			_50demaCell.setCellValue(_50dema);
			Cell _100demaCell = getCell(analysisSheet, currentDateColumn + String.valueOf(startLocation + 26));
			_100demaCell.setCellValue(_100dema);
			Cell _200demaCell = getCell(analysisSheet, currentDateColumn + String.valueOf(startLocation + 27));
			_200demaCell.setCellValue(_200dema);

			XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
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
		updateHistoricalData(LocalDate.of(2018, 8, 24), csvDataStore, testComponentsCurrentStartLocation);
		updateHistoricalData(LocalDate.of(2018, 8, 27), csvDataStore, testComponentsCurrentStartLocation);
		updateHistoricalData(LocalDate.of(2018, 8, 28), csvDataStore, testComponentsCurrentStartLocation);
	}

	@Test
	public void testAllTeibComponentsHistoricalDataUpdate() {
		for (String component : teibComponents) {
			HistoricalDataStore csvDataStore = new HistoricalCsvDataStore(new StringBuilder(component).reverse().toString());
			updateHistoricalData(csvDataStore, teibComponentsCurrentStartLocation);
			teibComponentsCurrentStartLocation += 40;
		}
	}

	@Test
	public void testAllNonTeibComponentsHistoricalDataUpdate() {
		for (String component : nonTeibComponents) {
			HistoricalDataStore csvDataStore = new HistoricalCsvDataStore(new StringBuilder(component).reverse().toString());
			updateHistoricalData(LocalDate.of(2018, 10, 9), csvDataStore, nonTeibComponentsCurrentStartLocation);
			nonTeibComponentsCurrentStartLocation += 40;
		}
	}

	@Test
	public void testTeibHistoricalDataUpdate() {
		HistoricalDataStore csvDataStore = new HistoricalCsvDataStore(new StringBuilder(teib).reverse().toString());
		updateHistoricalData(csvDataStore, teibCurrentStartLocation);
	}
}
