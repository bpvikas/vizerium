package com.vizerium.barabanca.trade;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.barabanca.historical.HistoricalDataReader;
import com.vizerium.barabanca.historical.UnitPriceIndicatorData;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.Indicator;
import com.vizerium.commons.io.FileUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class TradeStrategyTest {

	private static final Logger logger = Logger.getLogger(TradeStrategyTest.class);

	private HistoricalDataReader historicalDataReader;

	private static BufferedWriter bw = null;

	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			File csvOutputFile = new File(FileUtils.directoryPath + "output-log-v2/testrun.csv");
			bw = new BufferedWriter(new FileWriter(csvOutputFile));
		} catch (IOException e) {
			throw new RuntimeException("Error while creating CSV file for writing P L T results.", e);
		}
	}

	@Before
	public void setUp() {
		historicalDataReader = new HistoricalDataReader();
	}

	@Test
	public void test01_BankNiftyHourlyChart() {
		testAndReportTradeStrategy("BANKNIFTY", TimeFormat._1HOUR, 2011, 1, 2019, 2);
	}

	@Test
	public void test02_BankNiftyDailyChart() {
		testAndReportTradeStrategy("BANKNIFTY", TimeFormat._1DAY, 2011, 1, 2019, 2);
	}

	@Test
	public void test03_NiftyHourlyChart() {
		testAndReportTradeStrategy("NIFTY", TimeFormat._1HOUR, 2011, 1, 2019, 2);
	}

	@Test
	public void test04_NiftyDailyChart() {
		testAndReportTradeStrategy("NIFTY", TimeFormat._1DAY, 2011, 1, 2019, 2);
	}

	@Test
	public void test05_compareWithPreviousResult() {
		try {
			if (bw != null) {
				bw.flush();
				bw.close();
			}
			File previousResultFile = new File("src/test/resources/output/" + getPreviousResultFileName());
			List<String> previousResultLines = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(previousResultFile));
			String s = "";
			while ((s = br.readLine()) != null) {
				previousResultLines.add(s);
			}
			br.close();

			File currentTestRunLogFile = new File(FileUtils.directoryPath + "output-log-v2/testrun.csv");
			List<String> currentTestRunResultLines = new ArrayList<String>();
			br = new BufferedReader(new FileReader(currentTestRunLogFile));
			while ((s = br.readLine()) != null) {
				currentTestRunResultLines.add(s);
			}
			br.close();

			Assert.assertEquals(previousResultLines, currentTestRunResultLines);
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

	protected abstract String getPreviousResultFileName();

	protected abstract void getAdditionalDataPriorToIteration(TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList);

	protected abstract boolean testForCurrentUnitGreaterThanPreviousUnit(UnitPriceData current, UnitPriceData previous);

	protected abstract void executeForCurrentUnitGreaterThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous);

	protected abstract boolean testForCurrentUnitLessThanPreviousUnit(UnitPriceData current, UnitPriceData previous);

	protected abstract void executeForCurrentUnitLessThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous);

	protected abstract void executeForCurrentUnitChoppyWithPreviousUnit(UnitPriceData current, UnitPriceData previous);

	protected <I extends Indicator<I>> void updateIndicatorDataInUnitPrices(List<UnitPriceData> unitPriceDataList, I indicator) {
		UnitPriceIndicatorData<I> unitPriceIndicatorData = new UnitPriceIndicatorData<I>();
		unitPriceIndicatorData.updateIndicatorDataInUnitPriceDataList(unitPriceDataList, indicator);
	}

	protected void testAndReportTradeStrategy(String scripName, TimeFormat timeFormat, int startYear, int startMonth, int endYear, int endMonth) {
		TradeBook tradeBook = testTradeStrategy(scripName, timeFormat, startYear, startMonth, endYear, endMonth);
		printReport(tradeBook, timeFormat, ReportTimeFormat._1YEAR, startYear, startMonth);
		printReport(tradeBook, timeFormat, ReportTimeFormat._1MONTH, startYear, startMonth);
	}

	protected TradeBook testTradeStrategy(String scripName, TimeFormat timeFormat, int startYear, int startMonth, int endYear, int endMonth) {
		if (startMonth < 0 || startMonth > 12) {
			startMonth = 1;
		}
		if (endMonth < 0 || endMonth > 12) {
			endMonth = 12;
		}

		int lastDateOfMonth = LocalDate.of(endYear, endMonth, 1).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
		List<UnitPriceData> unitPriceDataList = historicalDataReader.getUnitPriceDataForRange(scripName, LocalDateTime.of(startYear, startMonth, 1, 6, 0),
				LocalDateTime.of(endYear, endMonth, lastDateOfMonth, 21, 00), timeFormat);

		return testTradeStrategy(timeFormat, unitPriceDataList);
	}

	protected TradeBook testTradeStrategy(TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		getAdditionalDataPriorToIteration(timeFormat, unitPriceDataList);

		TradeBook tradeBook = new TradeBook();
		for (int i = 1; i < unitPriceDataList.size(); i++) {
			if (testForCurrentUnitGreaterThanPreviousUnit(unitPriceDataList.get(i), unitPriceDataList.get(i - 1))) {
				logger.debug("For date " + unitPriceDataList.get(i).getDateTime() + " New close " + unitPriceDataList.get(i).getClose() + " MORE than old close "
						+ unitPriceDataList.get(i - 1).getClose());

				executeForCurrentUnitGreaterThanPreviousUnit(tradeBook, unitPriceDataList.get(i), unitPriceDataList.get(i - 1));

			} else if (testForCurrentUnitLessThanPreviousUnit(unitPriceDataList.get(i), unitPriceDataList.get(i - 1))) {
				logger.debug("For date " + unitPriceDataList.get(i).getDateTime() + " New close " + unitPriceDataList.get(i).getClose() + " LESS than old close "
						+ unitPriceDataList.get(i - 1).getClose());

				executeForCurrentUnitLessThanPreviousUnit(tradeBook, unitPriceDataList.get(i), unitPriceDataList.get(i - 1));

			} else {
				executeForCurrentUnitChoppyWithPreviousUnit(unitPriceDataList.get(i), unitPriceDataList.get(i - 1));
			}
			if (tradeBook.size() > 0) {
				tradeBook.last().setUnrealisedStatus(unitPriceDataList.get(i));
			}
			if (i == unitPriceDataList.size() - 1 && !tradeBook.isLastTradeExited()) {
				tradeBook.exitLastTrade(unitPriceDataList.get(i));
			}
		}
		tradeBook.printAllTrades();
		tradeBook.printStatus(timeFormat);

		return tradeBook;
	}

	protected void printReport(TradeBook tradeBook, TimeFormat timeFormat, ReportTimeFormat reportTimeFormat, int startYear, int startMonth) {
		StringBuilder p = new StringBuilder(), l = new StringBuilder(), t = new StringBuilder();

		if (ReportTimeFormat._1YEAR.equals(reportTimeFormat)) {
			int currentYear = startYear;
			TradeBook currentDurationTradeBook = new TradeBook();
			for (Trade trade : tradeBook) {
				if (trade.getExitDateTime().getYear() != currentYear) {
					updatePLT(currentDurationTradeBook, p, l, t);
					currentDurationTradeBook = new TradeBook();
					currentYear = trade.getExitDateTime().getYear();
				}
				currentDurationTradeBook.add(trade);
			}
			updatePLT(currentDurationTradeBook, p, l, t);
		} else if (ReportTimeFormat._1MONTH.equals(reportTimeFormat)) {
			int currentYear = startYear;
			int currentMonth = startMonth;
			TradeBook currentDurationTradeBook = new TradeBook();
			for (Trade trade : tradeBook) {
				if (trade.getExitDateTime().getYear() != currentYear || trade.getExitDateTime().getMonthValue() != currentMonth) {
					updatePLT(currentDurationTradeBook, p, l, t);
					while (!isAdjacentMonth(trade.getExitDateTime().getYear(), trade.getExitDateTime().getMonthValue(), currentYear, currentMonth)) {
						// The above while condition checks for those months where no trades took place at all.
						// While a similar case can be added for the years, usually there is at least one trade that is placed in a year.
						updatePLT(new TradeBook(), p, l, t);
						LocalDateTime nextMonth = getNextMonth(currentYear, currentMonth);
						currentYear = nextMonth.getYear();
						currentMonth = nextMonth.getMonthValue();
					}
					currentDurationTradeBook = new TradeBook();
					currentYear = trade.getExitDateTime().getYear();
					currentMonth = trade.getExitDateTime().getMonthValue();
				}
				currentDurationTradeBook.add(trade);
			}
			updatePLT(currentDurationTradeBook, p, l, t);
		}
		try {
			bw.write(p.toString() + System.lineSeparator() + l.toString() + System.lineSeparator() + t.toString() + System.lineSeparator());
		} catch (IOException ioe) {
			logger.error("Error while writing P L T results to CSV file.", ioe);
		}

		tradeBook.printAllTrades();
		tradeBook.printStatus(timeFormat);
	}

	protected void updatePLT(TradeBook currentDurationTradeBook, StringBuilder p, StringBuilder l, StringBuilder t) {
		if (currentDurationTradeBook.size() == 0) {
			logger.debug("Updating PLT for no trades here.");
		} else {
			logger.debug("Updating PLT for " + currentDurationTradeBook.last().getEntryDateTime() + " -> " + currentDurationTradeBook.last().getExitDateTime());
		}
		p.append(currentDurationTradeBook.getProfitPayoff()).append(",");
		l.append(currentDurationTradeBook.getLossPayoff()).append(",");
		t.append(currentDurationTradeBook.getPayoff()).append(",");
	}

	private boolean isAdjacentMonth(int currentYear, int currentMonth, int previousYear, int previousMonth) {
		if (currentYear == previousYear) {
			return currentMonth - previousMonth == 1;
		} else {
			return currentMonth - previousMonth == -11;
		}
	}

	private LocalDateTime getNextMonth(int previousYear, int previousMonth) {
		return LocalDateTime.of(previousYear, previousMonth, 1, 6, 0).plusMonths(1);
	}

	private static enum ReportTimeFormat {
		_1MONTH, _1YEAR;
	}
}
