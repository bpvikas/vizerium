package com.vizerium.barabanca.trade;

import java.util.List;

import com.vizerium.barabanca.trend.MACDHistogramSlopeTrendCheck;
import com.vizerium.barabanca.trend.PeriodTrend;
import com.vizerium.barabanca.trend.Trend;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MACD;
import com.vizerium.commons.trade.TradeAction;

public abstract class ClosingPricesWithTrendCheckByMACDHistogramAndIndicatorCheckTest extends ClosingPricesWithTrendCheckTest {

	protected abstract MACD getMACD();

	@Override
	protected List<PeriodTrend> getPeriodTrends(String scripName, TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataListCurrentTimeFormat) {
		trendCheck = new MACDHistogramSlopeTrendCheck(getMACD());
		return trendCheck.getTrend(scripName, trendTimeFormat, unitPriceDataListCurrentTimeFormat);
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(String scripName, TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		Trend trend = getPriorTrend(current.getDateTime(), periodTrends);
		if (!Trend.UP.equals(trend) && tradeBook.isLastTradeLong() && !tradeBook.isLastTradeExited()) {
			tradeBook.exitLongTrade(current);
		}

		// Only take those short trades where the MACD histogram turns down from above the centreline.
		if (Trend.DOWN.equals(trend) && tradeBook.isLastTradeExited() && current.getIndicator(getMACD().getName()).getValues()[MACD.UPI_POSN_HISTOGRAM] > 0.0f) {
			tradeBook.addShortTrade(new Trade(scripName, TradeAction.SHORT, current.getDateTime(), current.getTradedValue()));
		}
	}

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(String scripName, TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		Trend trend = getPriorTrend(current.getDateTime(), periodTrends);
		if (!Trend.DOWN.equals(trend) && tradeBook.isLastTradeShort() && !tradeBook.isLastTradeExited()) {
			tradeBook.coverShortTrade(current);
		}

		// Only take those long trades where the MACD histogram turns up from below the centreline.
		if (Trend.UP.equals(trend) && tradeBook.isLastTradeExited() && current.getIndicator(getMACD().getName()).getValues()[MACD.UPI_POSN_HISTOGRAM] < 0.0f) {
			tradeBook.addLongTrade(new Trade(scripName, TradeAction.LONG, current.getDateTime(), current.getTradedValue()));
		}
	}
}
