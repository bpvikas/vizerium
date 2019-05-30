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

package com.vizerium.payoffmatrix.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vizerium.commons.io.FileUtils;
import com.vizerium.commons.trade.TradeAction;
import com.vizerium.payoffmatrix.engine.OptionStrategiesWithPayoff;
import com.vizerium.payoffmatrix.engine.PayoffMatrix;
import com.vizerium.payoffmatrix.io.Output;
import com.vizerium.payoffmatrix.option.Option;
import com.vizerium.payoffmatrix.option.OptionStrategy;
import com.vizerium.payoffmatrix.volatility.Range;

public class PayoffReportXlsx {

	private static final Logger logger = Logger.getLogger(PayoffReportXlsx.class);

	private static String reportLocation = FileUtils.directoryPath + "output-formatted/";

	private static int initialOpenPositionDataSheetNumber = Output.analysisPayoffsLengths;

	private static final int openPositionDataSheetInitialRowNum = 1;

	private static final int openPositionDataSheetInitialColNum = (int) 'E'; // 69 is column 'E'

	private static final int pivotChartSheetInitialRowNum = 3;

	private static final int pivotChartSheetInitialColNum = (int) 'Y'; // 89 is column 'E'

	public static void createReport(String underlyingName, String payoffName, TreeSet<OptionStrategiesWithPayoff> payoffs, int optionStrategiesCount, Range underlyingRange) {
		Workbook workbook = getReportTemplate(underlyingName);
		try {
			int dataSheetNum = initialOpenPositionDataSheetNumber;
			for (OptionStrategiesWithPayoff payoff : payoffs) {
				Sheet dataSheet = workbook.getSheetAt(dataSheetNum);
				int colNum = openPositionDataSheetInitialColNum;
				for (OptionStrategy optionStrategy : payoff.getOptions()) {
					for (Option option : optionStrategy.getOptions()) {
						updateOptionDetailsInOpenPositionDataSheet(dataSheet, colNum++, option);
					}
				}
				Sheet pivotChartSheet = workbook.getSheetAt(dataSheetNum - Output.analysisPayoffsLengths);
				updatePayoffMatrixDetailsInPivotChartSheet(pivotChartSheet, payoff.getPayoffMatrix(), underlyingRange);
				dataSheetNum++;
			}
			revaluateFormulaAndCreateOutputFile(workbook, payoffName, optionStrategiesCount, underlyingName, underlyingRange);
		} catch (Exception e) {
			logger.error("An error occurred while generating the report.", e);
			throw new RuntimeException(e);
		}
	}

	private static Workbook getReportTemplate(String underlyingName) {
		try {
			File reportTemplate = FileUtils.getLastModifiedFileInDirectory(reportLocation, underlyingName + ".xlsx");
			FileInputStream reportTemplateFileInput = new FileInputStream(reportTemplate);
			return new XSSFWorkbook(reportTemplateFileInput);
		} catch (Exception e) {
			logger.error("An error occurred while getting report template.", e);
			throw new RuntimeException(e);
		}
	}

	private static void updateOptionDetailsInOpenPositionDataSheet(Sheet dataSheet, int colNum, Option option) {
		int rowNum = openPositionDataSheetInitialRowNum;
		CellReference cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(rowNum));
		Row row = dataSheet.getRow(cr.getRow());
		Cell cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);

		if (option.isExisting()) {
			cell.setCellValue(option.getTradedPremium());
		} else {
			cell.setCellValue(option.getCurrentPremium());
		}

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(++rowNum));
		row = dataSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		if (TradeAction.LONG.equals(option.getTradeAction())) {
			cell.setCellValue("Buy");
		} else if (TradeAction.SHORT.equals(option.getTradeAction())) {
			cell.setCellValue("Sell");
		} else {
			throw new RuntimeException("Unable to determine contract series." + option.getTradeAction());
		}

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(++rowNum));
		row = dataSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(option.getType().getShortName());

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(++rowNum));
		row = dataSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(option.getStrike());

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(++rowNum));
		row = dataSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(option.getLotSize());

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(++rowNum));
		row = dataSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(option.getNumberOfLots());
	}

	private static void updatePayoffMatrixDetailsInPivotChartSheet(Sheet pivotChartSheet, PayoffMatrix payoffMatrix, Range underlyingRange) {
		int rowNum = pivotChartSheetInitialRowNum;
		int colNum = pivotChartSheetInitialColNum;

		CellReference cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(rowNum));
		Row row = pivotChartSheet.getRow(cr.getRow());
		Cell cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(payoffMatrix.getUnderlyingCurrentPrice());

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(++rowNum));
		row = pivotChartSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(underlyingRange.getHigh());

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(++rowNum));
		row = pivotChartSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(underlyingRange.getLow());

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(rowNum += 2));
		row = pivotChartSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(payoffMatrix.getPositivePayoffsCount());

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(++rowNum));
		row = pivotChartSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(payoffMatrix.getNegativePayoffsCount());

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(rowNum += 2));
		row = pivotChartSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(payoffMatrix.getProfitProbability());

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(++rowNum));
		row = pivotChartSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(payoffMatrix.getRiskRewardRatioAsString());

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(++rowNum));
		row = pivotChartSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(payoffMatrix.getPayoffAverage());

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(rowNum += 2));
		row = pivotChartSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(payoffMatrix.getMaxPositivePayoff()[1]);

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(++rowNum));
		row = pivotChartSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(payoffMatrix.getMaxPositivePayoff()[0]);

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(rowNum += 2));
		row = pivotChartSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(payoffMatrix.getMinNegativePayoff()[1]);

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(++rowNum));
		row = pivotChartSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(payoffMatrix.getMinNegativePayoff()[0]);

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(rowNum += 2));
		row = pivotChartSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(payoffMatrix.getPositivePayoffSum());

		cr = new CellReference((String.valueOf((char) colNum)) + String.valueOf(++rowNum));
		row = pivotChartSheet.getRow(cr.getRow());
		cell = row.getCell(cr.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellValue(payoffMatrix.getNegativePayoffSum());
	}

	private static void revaluateFormulaAndCreateOutputFile(Workbook workbook, String outputFileName, int optionStrategiesCount, String underlyingName, Range underlyingRange) {
		XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

		String currentReportDirectoryPath = reportLocation + System.getProperty("application.run.datetime") + "_" + underlyingName + "_"
				+ System.getProperty("application.strategy.name") + "_" + String.valueOf(optionStrategiesCount)
				+ System.getProperty("application.strategy.name").toLowerCase().substring(0, 3) + "_" + (int) underlyingRange.getLow() + "_" + (int) underlyingRange.getHigh()
				+ "/";
		File currentReportDirectory = new File(currentReportDirectoryPath);
		if (!currentReportDirectory.exists()) {
			currentReportDirectory.mkdir();
		}

		FileOutputStream currentReport = null;
		if (StringUtils.isNotBlank(outputFileName)) {
			try {
				currentReport = new FileOutputStream(currentReportDirectoryPath + outputFileName + "_" + System.getProperty("application.run.datetime") + ".xlsx");
				workbook.write(currentReport);
			} catch (Exception e) {
				logger.error("An error occurred while writing output report file.", e);
				throw new RuntimeException(e);
			} finally {
				if (currentReport != null) {
					try {
						currentReport.close();
					} catch (Exception e) {
						logger.error("An error occurred while closing output report file.", e);
					}
				}
			}
		}
	}
}
