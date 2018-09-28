package com.vizerium.payoffmatrix.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.lang3.StringUtils;
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

	private static String reportLocation = FileUtils.directoryPath + "output-formatted/";

	private static final int initialRowNum = 1;

	private static final int initialColNum = 69; // column "E"

	public static void createReport(OptionStrategiesWithPayoff[] payoffs) {
		for (OptionStrategiesWithPayoff payoff : payoffs) {
			createReport(payoff);
		}
	}

	public static void createReport(OptionStrategiesWithPayoff payoff) {
		FileInputStream reportTemplateFileInput = null;
		Workbook workbook = null;
		try {
			String currentReportDirectoryPath = reportLocation + System.getProperty("application.run.datetime") + "_" + System.getProperty("application.strategy.name") + "/";
			File currentReportDirectory = new File(currentReportDirectoryPath);
			if (!currentReportDirectory.exists()) {
				currentReportDirectory.mkdir();
			}

			File reportTemplate = FileUtils.getLastModifiedFileInDirectory(reportLocation, ".xlsx");

			reportTemplateFileInput = new FileInputStream(reportTemplate);
			workbook = new XSSFWorkbook(reportTemplateFileInput);
			Sheet dataSheet = workbook.getSheetAt(1);

			String outputFileName = "";
			for (OptionStrategy optionStrategy : payoff.getOptions()) {
				int colNum = initialColNum;
				for (Option option : optionStrategy.getOptions()) {
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

					outputFileName += (((int) option.getStrike()) + option.getType().getShortName() + option.getTradeAction().name().substring(0, 2) + "_");
					++colNum;
				}
			}

			XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

			if (StringUtils.isNotBlank(outputFileName)) {
				FileOutputStream currentReport = new FileOutputStream(currentReportDirectoryPath + outputFileName + ".xlsx");
				workbook.write(currentReport);
				currentReport.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("An error occurred while generating the report.", e);
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
					reportTemplateFileInput.close();
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("An error occurred while closing input resources.", e);
				}
			}
		}
	}
}
