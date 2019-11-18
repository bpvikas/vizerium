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

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MovingAverageType;
import com.vizerium.commons.indicators.SuperTrend;

public abstract class SuperTrendTradeTest extends TradeStrategyTest {

	protected abstract int getSuperTrendAtrPeriod();

	protected abstract float getSuperTrendMultiplier();

	protected abstract MovingAverageType getSuperTrendAtrMAType();

	protected SuperTrend superTrend;

	@Override
	protected void getAdditionalDataPriorToIteration(TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		superTrend = new SuperTrend(getSuperTrendAtrPeriod(), getSuperTrendMultiplier(), getSuperTrendAtrMAType());
		updateIndicatorDataInUnitPrices(unitPriceDataList, superTrend);
	}

	@Override
	protected boolean testForCurrentUnitGreaterThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		return (current.getIndicator(superTrend.getName()).getValues()[SuperTrend.UPI_POSN_TREND] > previous.getIndicator(superTrend.getName())
				.getValues()[SuperTrend.UPI_POSN_TREND]) ? true : false;
	}

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
			tradeBook.coverShortTrade(current);
		}
		if (tradeBook.isLastTradeExited()) {
			tradeBook.addLongTrade(current);
		}
	}

	@Override
	protected boolean testForCurrentUnitLessThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		return (current.getIndicator(superTrend.getName()).getValues()[SuperTrend.UPI_POSN_TREND] < previous.getIndicator(superTrend.getName())
				.getValues()[SuperTrend.UPI_POSN_TREND]) ? true : false;
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
			tradeBook.exitLongTrade(current);
		}
		if (tradeBook.isLastTradeExited()) {
			tradeBook.addShortTrade(current);
		}
	}

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(UnitPriceData current, UnitPriceData previous) {

	}
}
