package com.vizerium.barabanca.historical;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.barabanca.dao.UnitPriceData;
import com.vizerium.commons.trade.Trade;
import com.vizerium.commons.trade.TradeAction;
import com.vizerium.commons.trade.TradeList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClosingPricesTest {

	private HistoricalDataReader unit;

	@Before
	public void setUp() {
		unit = new HistoricalDataReader();
	}

	@Test
	public void test01_BankNiftyIn2018OnHourlyBasisWithStopAndReverse() {
		testStopAndReverse("BANKNIFTY", TimeFormat._1HOUR, 2018);
	}

	@Test
	public void test02_BankNiftyIn2018OnDailyBasisWithStopAndReverse() {
		testStopAndReverse("BANKNIFTY", TimeFormat._1DAY, 2018);
	}

	@Test
	public void test03_NiftyIn2018OnHourlyBasisWithStopAndReverse() {
		testStopAndReverse("NIFTY", TimeFormat._1HOUR, 2018);
	}

	@Test
	public void test04_NiftyIn2018OnHourlyBasisWithStopAndReverse() {
		testStopAndReverse("NIFTY", TimeFormat._1DAY, 2018);
	}

	private TradeList testStopAndReverse(String scripName, TimeFormat timeFormat, int year) {
		List<UnitPriceData> _2018HourlyData = unit.getUnitPriceDataForRange(scripName, LocalDateTime.of(year, 1, 1, 6, 0), LocalDateTime.of(year, 12, 31, 17, 00), timeFormat);

		TradeList tradeList = new TradeList();
		TradeAction currentTradeAction = null;
		for (int i = 1; i < _2018HourlyData.size(); i++) {
			if (_2018HourlyData.get(i).getClose() > _2018HourlyData.get(i - 1).getClose()) {
				if (TradeAction.LONG != currentTradeAction) {
					if (tradeList.size() != 0) {
						Trade currentTrade = tradeList.get(tradeList.size() - 1);
						currentTrade.setExitDateTime(_2018HourlyData.get(i).getDateTime());
						currentTrade.setExitPrice(_2018HourlyData.get(i).getClose());
					}
					currentTradeAction = TradeAction.LONG;
					Trade trade = new Trade(scripName, currentTradeAction, _2018HourlyData.get(i).getDateTime(), _2018HourlyData.get(i).getClose());
					tradeList.add(trade);
				}

			} else {
				if (TradeAction.SHORT != currentTradeAction) {
					if (tradeList.size() != 0) {
						Trade currentTrade = tradeList.get(tradeList.size() - 1);
						currentTrade.setExitDateTime(_2018HourlyData.get(i).getDateTime());
						currentTrade.setExitPrice(_2018HourlyData.get(i).getClose());
					}
					currentTradeAction = TradeAction.SHORT;
					Trade trade = new Trade(scripName, currentTradeAction, _2018HourlyData.get(i).getDateTime(), _2018HourlyData.get(i).getClose());
					tradeList.add(trade);
				}
			}
		}
		// tradeList.printAllTrades();
		System.out.println((tradeList.isProfitable() ? "PROFIT" : "LOSS") + "\t" + tradeList.getPayoff() + "\t" + tradeList.size() + " trades.");

		return tradeList;
	}
}
