package com.vizerium.barabanca.historical;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.barabanca.dao.UnitPriceData;
import com.vizerium.barabanca.trade.Trade;
import com.vizerium.barabanca.trade.TradeBook;
import com.vizerium.commons.trade.TradeAction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClosingPricesTest {

	private HistoricalDataReader unit;

	@Before
	public void setUp() {
		unit = new HistoricalDataReader();
	}

	// @Test
	public void test01_BankNiftyHourlyChartWithStopAndReverse() {
		testStopAndReverse("BANKNIFTY", TimeFormat._1HOUR, 2018, 2018);
	}

	@Test
	public void test02_BankNiftyDailyChartWithStopAndReverse() {
		testStopAndReverse("BANKNIFTY", TimeFormat._1DAY, 2018, 2018);
	}

	// @Test
	public void test03_NiftyHourlyChartWithStopAndReverse() {
		testStopAndReverse("NIFTY", TimeFormat._1HOUR, 2012, 2018);
	}

	// @Test
	public void test04_NiftyDailyChartWithStopAndReverse() {
		testStopAndReverse("NIFTY", TimeFormat._1DAY, 2016, 2016);
	}

	public TradeBook testStopAndReverse(String scripName, TimeFormat timeFormat, int startYear, int endYear) {
		TradeBook tradeBook = new TradeBook();
		for (int year = startYear; year <= endYear; year++) {
			tradeBook.addAll(testStopAndReverse(scripName, timeFormat, year));
		}
		return tradeBook;
	}

	public TradeBook testStopAndReverse(String scripName, TimeFormat timeFormat, int year) {
		List<UnitPriceData> unitPriceDataList = unit.getUnitPriceDataForRange(scripName, LocalDateTime.of(year, 1, 1, 6, 0), LocalDateTime.of(year, 12, 31, 17, 00), timeFormat);

		TradeBook tradeBook = new TradeBook();
		TradeAction currentTradeAction = null;
		for (int i = 1; i < unitPriceDataList.size(); i++) {
			if (unitPriceDataList.get(i).getClose() > unitPriceDataList.get(i - 1).getClose()) {
				if (TradeAction.LONG != currentTradeAction) {
					tradeBook.coverShortTrade(unitPriceDataList.get(i));
					currentTradeAction = TradeAction.LONG;
					tradeBook.addLongTrade(new Trade(scripName, currentTradeAction, unitPriceDataList.get(i).getDateTime(), unitPriceDataList.get(i).getClose()));
				}

			} else {
				if (TradeAction.SHORT != currentTradeAction) {
					tradeBook.exitLongTrade(unitPriceDataList.get(i));
					currentTradeAction = TradeAction.SHORT;
					tradeBook.addShortTrade(new Trade(scripName, currentTradeAction, unitPriceDataList.get(i).getDateTime(), unitPriceDataList.get(i).getClose()));
				}
			}
		}
		tradeBook.printAllTrades();
		tradeBook.printStatus(timeFormat);

		return tradeBook;
	}
}
