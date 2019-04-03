package com.vizerium.barabanca.trade;

import java.util.List;

import com.vizerium.barabanca.trend.EMASlopeTrendCheck;
import com.vizerium.barabanca.trend.PeriodTrends;
import com.vizerium.barabanca.trend.Trend;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MovingAverage;

public abstract class ClosingPricesWithTrendCheckByEMATest extends ClosingPricesWithTrendCheckTest {

	protected abstract MovingAverage getMovingAverage();

	@Override
	protected PeriodTrends getPeriodTrends(TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataListCurrentTimeFormat) {
		trendCheck = new EMASlopeTrendCheck(getMovingAverage());
		return trendCheck.getTrend(trendTimeFormat, unitPriceDataListCurrentTimeFormat);
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		Trend trend = periodTrends.getPriorTrend(current.getDateTime());
		if (!Trend.UP.equals(trend) && tradeBook.isLastTradeLong() && !tradeBook.isLastTradeExited()) {
			tradeBook.exitLongTrade(current);
		}

		if (current.getClose() < current.getMovingAverage(getMovingAverage().getMA()) && tradeBook.isLastTradeLong() && !tradeBook.isLastTradeExited()) {
			tradeBook.exitLongTrade(current);
		}

		if (Trend.DOWN.equals(trend) && tradeBook.isLastTradeExited()) {
			tradeBook.addShortTrade(current);
		}
	}

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		Trend trend = periodTrends.getPriorTrend(current.getDateTime());
		if (!Trend.DOWN.equals(trend) && tradeBook.isLastTradeShort() && !tradeBook.isLastTradeExited()) {
			tradeBook.coverShortTrade(current);
		}

		if (current.getClose() > current.getMovingAverage(getMovingAverage().getMA()) && tradeBook.isLastTradeShort() && !tradeBook.isLastTradeExited()) {
			tradeBook.coverShortTrade(current);
		}

		if (Trend.UP.equals(trend) && tradeBook.isLastTradeExited()) {
			tradeBook.addLongTrade(current);
		}
	}

}
