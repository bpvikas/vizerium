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
import com.vizerium.barabanca.trend.PeriodTrend;
import com.vizerium.barabanca.trend.Trend;
import com.vizerium.barabanca.trend.TrendCheck;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.trade.TradeAction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class ClosingPricesWithTrendCheckTest {

	private static final Logger logger = Logger.getLogger(ClosingPricesWithTrendCheckTest.class);

	private HistoricalDataReader historicalDataReader;
	protected TrendCheck trendCheck;

	@Before
	public void setUp() throws Exception {
		historicalDataReader = new HistoricalDataReader();
		trendCheck = new TrendCheck(historicalDataReader);
	}

	protected abstract List<PeriodTrend> getPeriodTrends(String scripName, int year, TimeFormat trendTimeFormat);

	@Test
	public void test01_BankNiftyHourlyChartWithClosingPricesAndTrendCheck() {
		testScripWithClosingPricesAndTrendCheck("BANKNIFTY", TimeFormat._1HOUR, TimeFormat._1DAY, 2012, 2018);
	}

	@Test
	public void test02_BankNiftyDailyChartWithClosingPricesAndTrendCheck() {
		testScripWithClosingPricesAndTrendCheck("BANKNIFTY", TimeFormat._1DAY, TimeFormat._1WEEK, 2012, 2018);
	}

	@Test
	public void test03_NiftyHourlyChartWithClosingPricesAndTrendCheck() {
		testScripWithClosingPricesAndTrendCheck("NIFTY", TimeFormat._1HOUR, TimeFormat._1DAY, 2012, 2018);
	}

	@Test
	public void test04_NiftyDailyChartWithClosingPricesAndTrendCheck() {
		testScripWithClosingPricesAndTrendCheck("NIFTY", TimeFormat._1DAY, TimeFormat._1WEEK, 2012, 2018);
	}

	private TradeBook testScripWithClosingPricesAndTrendCheck(String scripName, TimeFormat timeFormat, TimeFormat trendTimeFormat, int startYear, int endYear) {
		TradeBook tradeBook = new TradeBook();
		for (int year = startYear; year <= endYear; year++) {
			tradeBook.addAll(testScripWithClosingPricesAndTrendCheck(scripName, timeFormat, trendTimeFormat, year));
		}
		tradeBook.printAllTrades();
		tradeBook.printStatus(timeFormat);
		return tradeBook;
	}

	private TradeBook testScripWithClosingPricesAndTrendCheck(String scripName, TimeFormat timeFormat, TimeFormat trendTimeFormat, int year) {

		List<UnitPriceData> unitPriceDataList = historicalDataReader.getUnitPriceDataForRange(scripName, LocalDateTime.of(year, 1, 1, 6, 0),
				LocalDateTime.of(year, 12, 31, 17, 00), timeFormat);
		List<PeriodTrend> periodTrends = getPeriodTrends(scripName, year, trendTimeFormat);

		TradeBook tradeBook = new TradeBook();
		TradeAction currentTradeAction = null;
		for (int i = 1; i < unitPriceDataList.size(); i++) {
			if (tradeBook.size() > 0) {
				tradeBook.last().setUnrealisedStatus(unitPriceDataList.get(i));
			}

			if (unitPriceDataList.get(i).getClose() > unitPriceDataList.get(i - 1).getClose()) {
				logger.debug("For date " + unitPriceDataList.get(i).getDateTime() + " New close " + unitPriceDataList.get(i).getClose() + " MORE than old close "
						+ unitPriceDataList.get(i - 1).getClose());

				Trend trend = getPriorTrend(unitPriceDataList.get(i).getDateTime(), periodTrends);
				if (!Trend.DOWN.equals(trend) && tradeBook.isLastTradeShort() && !tradeBook.isLastTradeExited()) {
					tradeBook.coverShortTrade(unitPriceDataList.get(i));
				}
				if (Trend.UP.equals(trend) && tradeBook.isLastTradeExited()) {
					currentTradeAction = TradeAction.LONG;
					tradeBook.addLongTrade(new Trade(scripName, currentTradeAction, unitPriceDataList.get(i).getDateTime(), unitPriceDataList.get(i).getClose()));
				}

			} else {
				logger.debug("For date " + unitPriceDataList.get(i).getDateTime() + " New close " + unitPriceDataList.get(i).getClose() + " LESS than old close "
						+ unitPriceDataList.get(i - 1).getClose());
				Trend trend = getPriorTrend(unitPriceDataList.get(i).getDateTime(), periodTrends);
				if (!Trend.UP.equals(trend) && tradeBook.isLastTradeLong() && !tradeBook.isLastTradeExited()) {
					tradeBook.exitLongTrade(unitPriceDataList.get(i));
				}

				if (Trend.DOWN.equals(trend) && tradeBook.isLastTradeExited()) {
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

	private Trend getPriorTrend(LocalDateTime unitPriceDateTime, List<PeriodTrend> periodTrends) {
		for (int i = 0; i < periodTrends.size() - 1; i++) {
			if (!periodTrends.get(i).getStartDateTime().isAfter(unitPriceDateTime) && !periodTrends.get(i + 1).getStartDateTime().isBefore(unitPriceDateTime)) {
				logger.debug(periodTrends.get(i));
				return periodTrends.get(i).getTrend();
			}
		}
		throw new RuntimeException("Unable to determine Trend for " + unitPriceDateTime);
	}
}
