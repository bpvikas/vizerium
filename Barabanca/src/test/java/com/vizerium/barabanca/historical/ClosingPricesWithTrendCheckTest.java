package com.vizerium.barabanca.historical;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.vizerium.barabanca.trade.Trade;
import com.vizerium.barabanca.trade.TradeBook;
import com.vizerium.barabanca.trend.PeriodTrend;
import com.vizerium.barabanca.trend.Trend;
import com.vizerium.barabanca.trend.TrendCheck;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.trade.TradeAction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class ClosingPricesWithTrendCheckTest extends ClosingPricesTest {

	private static final Logger logger = Logger.getLogger(ClosingPricesWithTrendCheckTest.class);

	protected TrendCheck trendCheck;

	protected List<PeriodTrend> periodTrends;

	@Before
	public void setUp() {
		super.setUp();
		trendCheck = new TrendCheck(historicalDataReader);
	}

	protected abstract List<PeriodTrend> getPeriodTrends(String scripName, int year, TimeFormat trendTimeFormat);

	@Override
	protected void getAdditionalDataPriorToIteration(String scripName, int year, TimeFormat timeFormat) {
		periodTrends = getPeriodTrends(scripName, year, timeFormat.getHigherTimeFormat());

	}

	protected void executeForCurrentPriceLessThanPreviousPrice(String scripName, TradeBook tradeBook, UnitPriceData unitPriceData) {

		Trend trend = getPriorTrend(unitPriceData.getDateTime(), periodTrends);
		if (!Trend.DOWN.equals(trend) && tradeBook.isLastTradeShort() && !tradeBook.isLastTradeExited()) {
			tradeBook.coverShortTrade(unitPriceData);
		}
		if (Trend.UP.equals(trend) && tradeBook.isLastTradeExited()) {
			tradeBook.addLongTrade(new Trade(scripName, TradeAction.LONG, unitPriceData.getDateTime(), unitPriceData.getClose()));
		}

	}

	protected void executeForCurrentPriceGreaterThanPreviousPrice(String scripName, TradeBook tradeBook, UnitPriceData unitPriceData) {

		Trend trend = getPriorTrend(unitPriceData.getDateTime(), periodTrends);
		if (!Trend.UP.equals(trend) && tradeBook.isLastTradeLong() && !tradeBook.isLastTradeExited()) {
			tradeBook.exitLongTrade(unitPriceData);
		}

		if (Trend.DOWN.equals(trend) && tradeBook.isLastTradeExited()) {
			tradeBook.addShortTrade(new Trade(scripName, TradeAction.SHORT, unitPriceData.getDateTime(), unitPriceData.getClose()));
		}
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
