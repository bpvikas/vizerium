package com.vizerium.barabanca.historical;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.barabanca.trade.TradeBook;
import com.vizerium.commons.dao.UnitPriceData;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class ClosingPricesTest {

	private static final Logger logger = Logger.getLogger(ClosingPricesTest.class);

	protected HistoricalDataReader historicalDataReader;

	@Before
	public void setUp() {
		historicalDataReader = new HistoricalDataReader();
	}

	@Test
	public void test01_BankNiftyHourlyChart() {
		testClosingPrices("BANKNIFTY", TimeFormat._1HOUR, 2012, 2018);
	}

	@Test
	public void test02_BankNiftyDailyChart() {
		testClosingPrices("BANKNIFTY", TimeFormat._1DAY, 2012, 2018);
	}

	@Test
	public void test03_NiftyHourlyChart() {
		testClosingPrices("NIFTY", TimeFormat._1HOUR, 2012, 2018);
	}

	@Test
	public void test04_NiftyDailyChart() {
		testClosingPrices("NIFTY", TimeFormat._1DAY, 2012, 2018);
	}

	public TradeBook testClosingPrices(String scripName, TimeFormat timeFormat, int startYear, int endYear) {
		TradeBook tradeBook = new TradeBook();
		for (int year = startYear; year <= endYear; year++) {
			tradeBook.addAll(testClosingPrices(scripName, timeFormat, year));
		}
		tradeBook.printAllTrades();
		tradeBook.printStatus(timeFormat);
		return tradeBook;
	}

	public TradeBook testClosingPrices(String scripName, TimeFormat timeFormat, int year) {
		List<UnitPriceData> unitPriceDataList = historicalDataReader.getUnitPriceDataForRange(scripName, LocalDateTime.of(year, 1, 1, 6, 0),
				LocalDateTime.of(year, 12, 31, 17, 00), timeFormat);

		getAdditionalDataPriorToIteration(scripName, year, timeFormat);

		TradeBook tradeBook = new TradeBook();
		for (int i = 1; i < unitPriceDataList.size(); i++) {
			if (tradeBook.size() > 0) {
				tradeBook.last().setUnrealisedStatus(unitPriceDataList.get(i));
			}
			if (unitPriceDataList.get(i).getClose() > unitPriceDataList.get(i - 1).getClose()) {
				logger.debug("For date " + unitPriceDataList.get(i).getDateTime() + " New close " + unitPriceDataList.get(i).getClose() + " MORE than old close "
						+ unitPriceDataList.get(i - 1).getClose());

				executeForCurrentPriceGreaterThanPreviousPrice(scripName, tradeBook, unitPriceDataList.get(i));

			} else {
				logger.debug("For date " + unitPriceDataList.get(i).getDateTime() + " New close " + unitPriceDataList.get(i).getClose() + " LESS than old close "
						+ unitPriceDataList.get(i - 1).getClose());

				executeForCurrentPriceLessThanPreviousPrice(scripName, tradeBook, unitPriceDataList.get(i));

			}
			if (i == unitPriceDataList.size() - 1 && !tradeBook.isLastTradeExited()) {
				tradeBook.exitLastTrade(unitPriceDataList.get(i));
			}
		}
		tradeBook.printAllTrades();
		tradeBook.printStatus(timeFormat);

		return tradeBook;
	}

	protected abstract void getAdditionalDataPriorToIteration(String scripName, int year, TimeFormat trendTimeFormat);

	protected abstract void executeForCurrentPriceGreaterThanPreviousPrice(String scripName, TradeBook tradeBook, UnitPriceData unitPriceData);

	protected abstract void executeForCurrentPriceLessThanPreviousPrice(String scripName, TradeBook tradeBook, UnitPriceData unitPriceData);
}
