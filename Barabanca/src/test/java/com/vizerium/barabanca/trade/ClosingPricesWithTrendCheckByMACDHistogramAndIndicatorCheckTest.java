/*
 * Copyright 2019 Vizerium, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vizerium.barabanca.trade;

import java.util.List;

import com.vizerium.barabanca.trend.MACDHistogramSlopeTrendCheck;
import com.vizerium.barabanca.trend.PeriodTrends;
import com.vizerium.barabanca.trend.Trend;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MACD;

public abstract class ClosingPricesWithTrendCheckByMACDHistogramAndIndicatorCheckTest extends ClosingPricesWithTrendCheckTest {

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
			tradeBook.exitLongTrade(current);
		}

		// Only take those short trades where the MACD histogram turns down from above the centreline.
		if (Trend.DOWN.equals(trend) && tradeBook.isLastTradeExited() && current.getIndicator(getMACD().getName()).getValues()[MACD.UPI_POSN_HISTOGRAM] > 0.0f) {
			tradeBook.addShortTrade(current);
		}
	}

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		Trend trend = periodTrends.getPriorTrend(current.getDateTime());
		if (!Trend.DOWN.equals(trend) && tradeBook.isLastTradeShort() && !tradeBook.isLastTradeExited()) {
			tradeBook.coverShortTrade(current);
		}

		// Only take those long trades where the MACD histogram turns up from below the centreline.
		if (Trend.UP.equals(trend) && tradeBook.isLastTradeExited() && current.getIndicator(getMACD().getName()).getValues()[MACD.UPI_POSN_HISTOGRAM] < 0.0f) {
			tradeBook.addLongTrade(current);
		}
	}
}
