package com.vizerium.barabanca.trade;

import java.util.List;

import com.vizerium.barabanca.historical.TimeFormat;
import com.vizerium.barabanca.trend.PeriodTrend;
import com.vizerium.barabanca.trend.Trend;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.DirectionalSystem;
import com.vizerium.commons.trade.TradeAction;

public abstract class ClosingPricesWithTrendCheckByDirectionalSystemTest extends ClosingPricesWithTrendCheckTest {

	protected abstract DirectionalSystem getDirectionalSystem();

	@Override
	protected List<PeriodTrend> getPeriodTrends(String scripName, TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataListCurrentTimeFormat) {
		return trendCheck.getTrendByDirectionalSystemAndADX(scripName, trendTimeFormat, unitPriceDataListCurrentTimeFormat, getDirectionalSystem());
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(String scripName, TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		Trend trend = getPriorTrend(current.getDateTime(), periodTrends);
		if (!Trend.UP.equals(trend) && tradeBook.isLastTradeLong() && !tradeBook.isLastTradeExited()) {
			tradeBook.exitLongTrade(current);
		}

		if (Trend.DOWN.equals(trend) && tradeBook.isLastTradeExited()) {
			tradeBook.addShortTrade(new Trade(scripName, TradeAction.SHORT, current.getDateTime(), current.getTradedValue()));
		}
	}

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(String scripName, TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		Trend trend = getPriorTrend(current.getDateTime(), periodTrends);
		if (!Trend.DOWN.equals(trend) && tradeBook.isLastTradeShort() && !tradeBook.isLastTradeExited()) {
			tradeBook.coverShortTrade(current);
		}

		if (Trend.UP.equals(trend) && tradeBook.isLastTradeExited()) {
			tradeBook.addLongTrade(new Trade(scripName, TradeAction.LONG, current.getDateTime(), current.getTradedValue()));
		}
	}
}
