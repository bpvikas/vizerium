package com.vizerium.barabanca.trade;

import java.time.LocalDateTime;
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
		testMultiYearTradeStrategies("BANKNIFTY", TimeFormat._1HOUR, 2012, 2018);
	}

	@Test
	public void test02_BankNiftyDailyChart() {
		testMultiYearTradeStrategies("BANKNIFTY", TimeFormat._1DAY, 2012, 2018);
	}

	@Test
	public void test03_NiftyHourlyChart() {
		testMultiYearTradeStrategies("NIFTY", TimeFormat._1HOUR, 2012, 2018);
	}

	@Test
	public void test04_NiftyDailyChart() {
		testMultiYearTradeStrategies("NIFTY", TimeFormat._1DAY, 2012, 2018);
	}

	public TradeBook testMultiYearTradeStrategies(String scripName, TimeFormat timeFormat, int startYear, int endYear) {
		TradeBook tradeBook = new TradeBook();
		for (int year = startYear; year <= endYear; year++) {
			tradeBook.addAll(testCurrentYearTradeStrategy(scripName, timeFormat, year));
		}
		tradeBook.printAllTrades();
		tradeBook.printStatus(timeFormat);
		return tradeBook;
	}

	public TradeBook testCurrentYearTradeStrategy(String scripName, TimeFormat timeFormat, int year) {
		List<UnitPriceData> unitPriceDataList = historicalDataReader.getUnitPriceDataForRange(scripName, LocalDateTime.of(year, 1, 1, 6, 0),
				LocalDateTime.of(year, 12, 31, 17, 00), timeFormat);

		getAdditionalDataPriorToIteration(scripName, year, timeFormat);

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

	protected abstract void getAdditionalDataPriorToIteration(String scripName, int year, TimeFormat timeFormat);

	protected abstract boolean testForCurrentUnitGreaterThanPreviousUnit(UnitPriceData current, UnitPriceData previous);

	protected abstract void executeForCurrentUnitGreaterThanPreviousUnit(String scripName, TradeBook tradeBook, UnitPriceData unitPriceData);

	protected abstract boolean testForCurrentUnitLessThanPreviousUnit(UnitPriceData current, UnitPriceData previous);

	protected abstract void executeForCurrentUnitLessThanPreviousUnit(String scripName, TradeBook tradeBook, UnitPriceData unitPriceData);

	protected abstract void executeForCurrentUnitChoppyWithPreviousUnit(UnitPriceData current, UnitPriceData previous);
}
