package com.vizerium.barabanca.trade;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;

import com.vizerium.barabanca.historical.TimeFormat;
import com.vizerium.barabanca.trend.PeriodTrend;
import com.vizerium.barabanca.trend.Trend;
import com.vizerium.barabanca.trend.TrendCheck;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MovingAverage;
import com.vizerium.commons.trade.TradeAction;

public abstract class ClosingPricesWithTrendCheckTest extends ClosingPricesTest {

	private static final Logger logger = Logger.getLogger(ClosingPricesWithTrendCheckTest.class);

	protected TrendCheck trendCheck;

	protected List<PeriodTrend> periodTrends;

	@Before
	public void setUp() {
		super.setUp();
		trendCheck = new TrendCheck(historicalDataReader);
	}

	protected abstract List<PeriodTrend> getPeriodTrends(String scripName, int year, int month, TimeFormat trendTimeFormat);

	protected abstract MovingAverage getMovingAverage();

	@Override
	protected void getAdditionalDataPriorToIteration(String scripName, int year, int month, TimeFormat timeFormat) {
		periodTrends = getPeriodTrends(scripName, year, month, timeFormat.getHigherTimeFormat());
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(String scripName, TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		Trend trend = getPriorTrend(current.getDateTime(), periodTrends);
		if (!Trend.UP.equals(trend) && tradeBook.isLastTradeLong() && !tradeBook.isLastTradeExited()) {
			tradeBook.exitLongTrade(current);
		}

		if (current.getClose() < current.getMovingAverage(getMovingAverage().getNumber()) && tradeBook.isLastTradeLong() && !tradeBook.isLastTradeExited()) {
			tradeBook.exitLongTrade(current);
		}

		if (Trend.DOWN.equals(trend) && tradeBook.isLastTradeExited()) {
			tradeBook.addShortTrade(new Trade(scripName, TradeAction.SHORT, current.getDateTime(), current.getClose()));
		}
	}

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(String scripName, TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		Trend trend = getPriorTrend(current.getDateTime(), periodTrends);
		if (!Trend.DOWN.equals(trend) && tradeBook.isLastTradeShort() && !tradeBook.isLastTradeExited()) {
			tradeBook.coverShortTrade(current);
		}

		if (current.getClose() > current.getMovingAverage(getMovingAverage().getNumber()) && tradeBook.isLastTradeShort() && !tradeBook.isLastTradeExited()) {
			tradeBook.coverShortTrade(current);
		}

		if (Trend.UP.equals(trend) && tradeBook.isLastTradeExited()) {
			tradeBook.addLongTrade(new Trade(scripName, TradeAction.LONG, current.getDateTime(), current.getClose()));
		}
	}

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(UnitPriceData current, UnitPriceData previous) {

	}

	private Trend getPriorTrend(LocalDateTime unitPriceDateTime, List<PeriodTrend> periodTrends) {
		for (int i = 0; i < periodTrends.size() - 1; i++) {
			if (!periodTrends.get(i).getStartDateTime().isAfter(unitPriceDateTime) && !periodTrends.get(i + 1).getStartDateTime().isBefore(unitPriceDateTime)) {
				logger.debug("For " + unitPriceDateTime + ", " + periodTrends.get(i));
				return periodTrends.get(i).getTrend();
			}
		}
		if (unitPriceDateTime.isAfter(periodTrends.get(periodTrends.size() - 1).getStartDateTime())) {
			return periodTrends.get(periodTrends.size() - 1).getTrend();
		}
		throw new RuntimeException("Unable to determine Trend for " + unitPriceDateTime);
	}
}
