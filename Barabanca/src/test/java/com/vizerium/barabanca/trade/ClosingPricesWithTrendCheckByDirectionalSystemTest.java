package com.vizerium.barabanca.trade;

import java.util.List;

import com.vizerium.barabanca.trend.DirectionalSystemADXTrendCheck;
import com.vizerium.barabanca.trend.PeriodTrends;
import com.vizerium.barabanca.trend.Trend;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.DirectionalSystem;

public abstract class ClosingPricesWithTrendCheckByDirectionalSystemTest extends ClosingPricesWithTrendCheckTest {

	protected abstract DirectionalSystem getDirectionalSystem();

	@Override
	protected PeriodTrends getPeriodTrends(TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataListCurrentTimeFormat) {
		trendCheck = new DirectionalSystemADXTrendCheck(getDirectionalSystem());
		return trendCheck.getTrend(trendTimeFormat, unitPriceDataListCurrentTimeFormat);
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		Trend trend = periodTrends.getPriorTrend(current.getDateTime());
		if (!Trend.UP.equals(trend) && tradeBook.isLastTradeLong() && !tradeBook.isLastTradeExited()) {
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

		if (Trend.UP.equals(trend) && tradeBook.isLastTradeExited()) {
			tradeBook.addLongTrade(current);
		}
	}
}
