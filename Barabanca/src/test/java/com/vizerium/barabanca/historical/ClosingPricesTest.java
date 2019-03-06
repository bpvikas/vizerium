package com.vizerium.barabanca.historical;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.barabanca.trade.Trade;
import com.vizerium.barabanca.trade.TradeBook;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.trade.TradeAction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClosingPricesTest {

	private static final Logger logger = Logger.getLogger(ClosingPricesTest.class);

	private HistoricalDataReader unit;

	@Before
	public void setUp() {
		unit = new HistoricalDataReader();
	}

	// @Test
	public void test01_BankNiftyHourlyChartWithStopAndReverse() {
		testStopAndReverse("BANKNIFTY", TimeFormat._1HOUR, 2017, 2018);
	}

	@Test
	public void test02_BankNiftyDailyChartWithStopAndReverse() {
		testStopAndReverse("BANKNIFTY", TimeFormat._1DAY, 2018, 2018);
	}

	// @Test
	public void test03_NiftyHourlyChartWithStopAndReverse() {
		testStopAndReverse("NIFTY", TimeFormat._1HOUR, 2017, 2018);
	}

	// @Test
	public void test04_NiftyDailyChartWithStopAndReverse() {
		testStopAndReverse("NIFTY", TimeFormat._1DAY, 2017, 2018);
	}

	public TradeBook testStopAndReverse(String scripName, TimeFormat timeFormat, int startYear, int endYear) {
		TradeBook tradeBook = new TradeBook();
		for (int year = startYear; year <= endYear; year++) {
			tradeBook.addAll(testStopAndReverse(scripName, timeFormat, year));
		}
		tradeBook.printAllTrades();
		tradeBook.printStatus(timeFormat);
		return tradeBook;
	}

	public TradeBook testStopAndReverse(String scripName, TimeFormat timeFormat, int year) {
		List<UnitPriceData> unitPriceDataList = unit.getUnitPriceDataForRange(scripName, LocalDateTime.of(year, 1, 1, 6, 0), LocalDateTime.of(year, 12, 31, 17, 00), timeFormat);

		TradeBook tradeBook = new TradeBook();
		TradeAction currentTradeAction = null;
		for (int i = 1; i < unitPriceDataList.size(); i++) {
			if (tradeBook.size() > 0) {
				tradeBook.last().setUnrealisedStatus(unitPriceDataList.get(i));
			}
			if (unitPriceDataList.get(i).getClose() > unitPriceDataList.get(i - 1).getClose()) {
				logger.debug("For date " + unitPriceDataList.get(i).getDateTime() + " New close " + unitPriceDataList.get(i).getClose() + " MORE than old close "
						+ unitPriceDataList.get(i - 1).getClose());

				if (TradeAction.LONG != currentTradeAction) {
					tradeBook.coverShortTrade(unitPriceDataList.get(i));
					currentTradeAction = TradeAction.LONG;
					tradeBook.addLongTrade(new Trade(scripName, currentTradeAction, unitPriceDataList.get(i).getDateTime(), unitPriceDataList.get(i).getClose()));
				}

			} else {
				logger.debug("For date " + unitPriceDataList.get(i).getDateTime() + " New close " + unitPriceDataList.get(i).getClose() + " LESS than old close "
						+ unitPriceDataList.get(i - 1).getClose());
				if (TradeAction.SHORT != currentTradeAction) {
					tradeBook.exitLongTrade(unitPriceDataList.get(i));
					currentTradeAction = TradeAction.SHORT;
					tradeBook.addShortTrade(new Trade(scripName, currentTradeAction, unitPriceDataList.get(i).getDateTime(), unitPriceDataList.get(i).getClose()));
				}
			}
			if (i == unitPriceDataList.size() - 1 && !tradeBook.isLastTradeExited()) {
				tradeBook.exitLastTrade(unitPriceDataList.get(i));
			}
		}
		tradeBook.printAllTrades();
		tradeBook.printStatus(timeFormat);

		return tradeBook;
	}
}
