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

package com.vizerium.barabanca.trade;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
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

	protected HistoricalDataReader historicalDataReader;

	@BeforeClass
	public static void setUpBeforeClass() {
		TradesReport.initialize();
	}

	@Before
	public void setUp() {
		historicalDataReader = new HistoricalDataReader();
	}

	@Test
	public void test01_BankNifty15minChart() {
		testAndReportTradeStrategy("BANKNIFTY", TimeFormat._15MIN, 2011, 1, 2019, 10);
	}

	@Test
	public void test02_BankNiftyHourlyChart() {
		testAndReportTradeStrategy("BANKNIFTY", TimeFormat._1HOUR, 2011, 1, 2019, 10);
	}

	@Test
	public void test03_BankNiftyDailyChart() {
		testAndReportTradeStrategy("BANKNIFTY", TimeFormat._1DAY, 2011, 1, 2019, 10);
	}

	@Test
	public void test04_Nifty15minChart() {
		testAndReportTradeStrategy("NIFTY", TimeFormat._15MIN, 2011, 1, 2019, 10);
	}

	@Test
	public void test05_NiftyHourlyChart() {
		testAndReportTradeStrategy("NIFTY", TimeFormat._1HOUR, 2011, 1, 2019, 10);
	}

	@Test
	public void test06_NiftyDailyChart() {
		testAndReportTradeStrategy("NIFTY", TimeFormat._1DAY, 2011, 1, 2019, 10);
	}

	@Test
	public void test07_compareWithPreviousResult() {
		try {
			TradesReport.close();

			Files.deleteIfExists(Paths.get(FileUtils.directoryPath + "output-log-v2/testrun_" + getResultFileName() + ".csv"));
			Files.move(Paths.get(FileUtils.directoryPath + "output-log-v2/testrun.csv"),
					Paths.get(FileUtils.directoryPath + "output-log-v2/testrun_" + getResultFileName() + ".csv"));
			List<String> previousResult = TradesReport.readFileAsString("src/test/resources/output/testrun_" + getResultFileName() + ".csv");
			List<String> currentTestRunResult = TradesReport.readFileAsString(FileUtils.directoryPath + "output-log-v2/testrun_" + getResultFileName() + ".csv");

			Files.deleteIfExists(Paths.get(FileUtils.directoryPath + "output-log-v2/tradebook_" + getResultFileName() + ".csv"));
			Files.move(Paths.get(FileUtils.directoryPath + "output-log-v2/tradebook.csv"),
					Paths.get(FileUtils.directoryPath + "output-log-v2/tradebook_" + getResultFileName() + ".csv"));
			List<String> previousTradeBook = TradesReport.readFileAsString("src/test/resources/output/tradebook_" + getResultFileName() + ".csv");
			List<String> currentTradeBook = TradesReport.readFileAsString(FileUtils.directoryPath + "output-log-v2/tradebook_" + getResultFileName() + ".csv");

			Assert.assertEquals(previousResult, currentTestRunResult);
			Assert.assertEquals(previousTradeBook, currentTradeBook);

		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

	protected abstract String getResultFileName();

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
		TradesReport.print(tradeBook, timeFormat, startYear, startMonth);
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
		return tradeBook;
	}

	protected boolean higherClose(UnitPriceData current, UnitPriceData previous) {
		return current.getClose() > previous.getClose();
	}

	protected boolean lowerClose(UnitPriceData current, UnitPriceData previous) {
		return current.getClose() < previous.getClose();
	}

	protected boolean isGreenCandle(UnitPriceData unitPrice) {
		return unitPrice.getClose() > unitPrice.getOpen();
	}

	protected boolean isRedCandle(UnitPriceData unitPrice) {
		return unitPrice.getClose() < unitPrice.getOpen();
	}
}
