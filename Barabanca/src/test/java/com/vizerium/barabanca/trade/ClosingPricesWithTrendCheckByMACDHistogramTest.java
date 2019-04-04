package com.vizerium.barabanca.trade;

import java.util.List;

import com.vizerium.barabanca.trend.MACDHistogramSlopeTrendCheck;
import com.vizerium.barabanca.trend.PeriodTrends;
import com.vizerium.barabanca.trend.Trend;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MACD;

public abstract class ClosingPricesWithTrendCheckByMACDHistogramTest extends ClosingPricesWithTrendCheckTest {

	protected abstract MACD getMACD();

	@Override
	protected PeriodTrends getPeriodTrends(TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataListCurrentTimeFormat) {
		trendCheck = new MACDHistogramSlopeTrendCheck(getMACD());
		return trendCheck.getTrend(trendTimeFormat, unitPriceDataListCurrentTimeFormat);
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		Trend trend = periodTrends.getPriorTrend(current.getDateTime());
		if (!Trend.UP.equals(trend) && tradeBook.isLastTradeLong() && !tradeBook.isLastTradeExited()) {
			current.setTradedValue(current.getOpen());
			tradeBook.exitLongTrade(current);
		}

		if (Trend.DOWN.equals(trend) && tradeBook.isLastTradeExited()) {
			current.setTradedValue(current.getOpen());
			tradeBook.addShortTrade(current);
		}
	}

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		Trend trend = periodTrends.getPriorTrend(current.getDateTime());
		if (!Trend.DOWN.equals(trend) && tradeBook.isLastTradeShort() && !tradeBook.isLastTradeExited()) {
			current.setTradedValue(current.getOpen());
			tradeBook.coverShortTrade(current);
		}

		if (Trend.UP.equals(trend) && tradeBook.isLastTradeExited()) {
			current.setTradedValue(current.getOpen());
			tradeBook.addLongTrade(current);
		}
	}
}