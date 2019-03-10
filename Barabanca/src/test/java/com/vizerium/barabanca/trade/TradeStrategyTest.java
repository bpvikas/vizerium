package com.vizerium.barabanca.trade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.barabanca.historical.HistoricalDataReader;
import com.vizerium.barabanca.historical.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class TradeStrategyTest {

	private static final Logger logger = Logger.getLogger(TradeStrategyTest.class);

	protected HistoricalDataReader historicalDataReader;

	@Before
	public void setUp() {
		historicalDataReader = new HistoricalDataReader();
	}

	@Test
	public void test01_BankNiftyHourlyChart() {
		testMultiYearTradeStrategies("BANKNIFTY", TimeFormat._1HOUR, 2011, 2019);
		testMultiMonthTradeStrategies("BANKNIFTY", TimeFormat._1HOUR, 2011, 1, 2019, 2);
	}

	@Test
	public void test02_BankNiftyDailyChart() {
		testMultiYearTradeStrategies("BANKNIFTY", TimeFormat._1DAY, 2011, 2019);
		testMultiMonthTradeStrategies("BANKNIFTY", TimeFormat._1DAY, 2011, 1, 2019, 2);
	}

	@Test
	public void test03_NiftyHourlyChart() {
		testMultiYearTradeStrategies("NIFTY", TimeFormat._1HOUR, 2011, 2019);
		testMultiMonthTradeStrategies("NIFTY", TimeFormat._1HOUR, 2011, 1, 2019, 2);
	}

	@Test
	public void test04_NiftyDailyChart() {
		testMultiYearTradeStrategies("NIFTY", TimeFormat._1DAY, 2011, 2019);
		testMultiMonthTradeStrategies("NIFTY", TimeFormat._1DAY, 2011, 1, 2019, 2);
	}

	public TradeBook testMultiYearTradeStrategies(String scripName, TimeFormat timeFormat, int startYear, int endYear) {
		String p = "", l = "", t = "";
		TradeBook tradeBook = new TradeBook();
		for (int year = startYear; year <= endYear; year++) {
			TradeBook currentYearTradeBook = testCurrentYearTradeStrategy(scripName, timeFormat, year);
			p += (String.valueOf(currentYearTradeBook.getProfitPayoff()) + ",");
			l += (String.valueOf(currentYearTradeBook.getLossPayoff()) + ",");
			t += (String.valueOf(currentYearTradeBook.getPayoff()) + ",");
			tradeBook.addAll(currentYearTradeBook);
		}
		System.out.println(p + System.lineSeparator() + l + System.lineSeparator() + t);
		tradeBook.printAllTrades();
		tradeBook.printStatus(timeFormat);
		return tradeBook;
	}

	public TradeBook testMultiMonthTradeStrategies(String scripName, TimeFormat timeFormat, int startYear, int startMonth, int endYear, int endMonth) {
		String p = "", l = "", t = "";
		TradeBook tradeBook = new TradeBook();
		for (int year = startYear; year <= endYear; year++) {
			for (int month = ((year == startYear) ? startMonth : 1); month <= ((year == endYear) ? endMonth : 12); month++) {
				TradeBook currentMonthTradeBook = testCurrentMonthTradeStrategy(scripName, timeFormat, year, month);
				p += (String.valueOf(currentMonthTradeBook.getProfitPayoff()) + ",");
				l += (String.valueOf(currentMonthTradeBook.getLossPayoff()) + ",");
				t += (String.valueOf(currentMonthTradeBook.getPayoff()) + ",");
				tradeBook.addAll(currentMonthTradeBook);
			}
		}
		System.out.println(p + System.lineSeparator() + l + System.lineSeparator() + t);
		tradeBook.printAllTrades();
		tradeBook.printStatus(timeFormat);
		return tradeBook;
	}

	public TradeBook testCurrentYearTradeStrategy(String scripName, TimeFormat timeFormat, int year) {
		List<UnitPriceData> unitPriceDataList = historicalDataReader.getUnitPriceDataForRange(scripName, LocalDateTime.of(year, 1, 1, 6, 0),
				LocalDateTime.of(year, 12, 31, 21, 00), timeFormat);

		return testCurrentPeriodTradeStrategy(scripName, timeFormat, year, -1, unitPriceDataList);
	}

	public TradeBook testCurrentMonthTradeStrategy(String scripName, TimeFormat timeFormat, int year, int month) {

		int lastDateOfMonth = LocalDate.of(year, month, 1).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
		List<UnitPriceData> unitPriceDataList = historicalDataReader.getUnitPriceDataForRange(scripName, LocalDateTime.of(year, month, 1, 6, 0),
				LocalDateTime.of(year, month, lastDateOfMonth, 21, 00), timeFormat);

		return testCurrentPeriodTradeStrategy(scripName, timeFormat, year, month, unitPriceDataList);
	}

	public TradeBook testCurrentPeriodTradeStrategy(String scripName, TimeFormat timeFormat, int year, int month, List<UnitPriceData> unitPriceDataList) {
		getAdditionalDataPriorToIteration(scripName, year, month, timeFormat);

		TradeBook tradeBook = new TradeBook();
		for (int i = 1; i < unitPriceDataList.size(); i++) {
			if (tradeBook.size() > 0) {
				tradeBook.last().setUnrealisedStatus(unitPriceDataList.get(i));
			}
			if (testForCurrentUnitGreaterThanPreviousUnit(unitPriceDataList.get(i), unitPriceDataList.get(i - 1))) {
				logger.debug("For date " + unitPriceDataList.get(i).getDateTime() + " New close " + unitPriceDataList.get(i).getClose() + " MORE than old close "
						+ unitPriceDataList.get(i - 1).getClose());

				executeForCurrentUnitGreaterThanPreviousUnit(scripName, tradeBook, unitPriceDataList.get(i));

			} else if (testForCurrentUnitLessThanPreviousUnit(unitPriceDataList.get(i), unitPriceDataList.get(i - 1))) {
				logger.debug("For date " + unitPriceDataList.get(i).getDateTime() + " New close " + unitPriceDataList.get(i).getClose() + " LESS than old close "
						+ unitPriceDataList.get(i - 1).getClose());

				executeForCurrentUnitLessThanPreviousUnit(scripName, tradeBook, unitPriceDataList.get(i));

			} else {
				executeForCurrentUnitChoppyWithPreviousUnit(unitPriceDataList.get(i), unitPriceDataList.get(i - 1));
			}
			if (i == unitPriceDataList.size() - 1 && !tradeBook.isLastTradeExited()) {
				tradeBook.exitLastTrade(unitPriceDataList.get(i));
			}
		}
		tradeBook.printAllTrades();
		tradeBook.printStatus(timeFormat);

		return tradeBook;
	}

	protected abstract void getAdditionalDataPriorToIteration(String scripName, int year, int month, TimeFormat timeFormat);

	protected abstract boolean testForCurrentUnitGreaterThanPreviousUnit(UnitPriceData current, UnitPriceData previous);

	protected abstract void executeForCurrentUnitGreaterThanPreviousUnit(String scripName, TradeBook tradeBook, UnitPriceData unitPriceData);

	protected abstract boolean testForCurrentUnitLessThanPreviousUnit(UnitPriceData current, UnitPriceData previous);

	protected abstract void executeForCurrentUnitLessThanPreviousUnit(String scripName, TradeBook tradeBook, UnitPriceData unitPriceData);

	protected abstract void executeForCurrentUnitChoppyWithPreviousUnit(UnitPriceData current, UnitPriceData previous);
}
