package com.vizerium.payoffmatrix.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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

import com.vizerium.payoffmatrix.engine.OptionStrategiesWithPayoff;
import com.vizerium.payoffmatrix.io.FileUtils;
import com.vizerium.payoffmatrix.option.Option;
import com.vizerium.payoffmatrix.option.OptionStrategy;
import com.vizerium.payoffmatrix.option.TradeAction;

public class PayoffReportXlsx {

	private static final Logger logger = Logger.getLogger(PayoffReportXlsx.class);

	private static String reportLocation = FileUtils.directoryPath + "output-formatted/";

	private static int initialOpenPositionDataSheetNumber = 5;

	private static final int initialRowNum = 1;

	private static final int initialColNum = 69; // column "E"

	public static void createReport(String payoffName, OptionStrategiesWithPayoff[] payoffs) {
		Workbook workbook = getReportTemplate();
		try {
			int dataSheetNum = initialOpenPositionDataSheetNumber;
			for (OptionStrategiesWithPayoff payoff : payoffs) {
				Sheet dataSheet = workbook.getSheetAt(dataSheetNum++);
				for (OptionStrategy optionStrategy : payoff.getOptions()) {
					int colNum = initialColNum;
					for (Option option : optionStrategy.getOptions()) {
						updateOptionDetailsInOpenPositionDataSheet(dataSheet, colNum++, option);
					}
				}
			}
			revaluateFormulaAndCreateOutputFile(workbook, payoffName);
		} catch (Exception e) {
			logger.error("An error occurred while generating the report.", e);
			throw new RuntimeException(e);
		}
	}

	private static Workbook getReportTemplate() {
		try {
			File reportTemplate = FileUtils.getLastModifiedFileInDirectory(reportLocation, ".xlsx");
			FileInputStream reportTemplateFileInput = new FileInputStream(reportTemplate);
			return new XSSFWorkbook(reportTemplateFileInput);
		} catch (Exception e) {
			logger.error("An error occurred while getting report template.", e);
			throw new RuntimeException(e);
		}
	}

	private static void updateOptionDetailsInOpenPositionDataSheet(Sheet dataSheet, int colNum, Option option) {
		int rowNum = initialRowNum;
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

	private static void revaluateFormulaAndCreateOutputFile(Workbook workbook, String outputFileName) {
		XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

		String currentReportDirectoryPath = reportLocation + System.getProperty("application.run.datetime") + "_" + System.getProperty("application.strategy.name") + "/";
		File currentReportDirectory = new File(currentReportDirectoryPath);
		if (!currentReportDirectory.exists()) {
			currentReportDirectory.mkdir();
		}

		FileOutputStream currentReport = null;
		if (StringUtils.isNotBlank(outputFileName)) {
			try {
				currentReport = new FileOutputStream(currentReportDirectoryPath + outputFileName + ".xlsx");
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
