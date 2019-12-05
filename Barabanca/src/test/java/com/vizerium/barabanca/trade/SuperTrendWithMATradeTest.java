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
import com.vizerium.commons.indicators.MovingAverage;

public abstract class SuperTrendWithMATradeTest extends SuperTrendTradeTest {

	protected MovingAverage ma;

	protected abstract MovingAverage getMA();

	@Override
	protected void getAdditionalDataPriorToIteration(TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		super.getAdditionalDataPriorToIteration(timeFormat, unitPriceDataList);
		ma = getMA();
		updateIndicatorDataInUnitPrices(unitPriceDataList, ma);
	}

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {

		if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
			if ((current.getIndicator(ma.getName()).getValues()[MovingAverage.UPI_POSN_MA_VALUE] < current.getClose())
					&& (previous.getIndicator(ma.getName()).getValues()[MovingAverage.UPI_POSN_MA_VALUE] >= previous.getClose())) {
				tradeBook.coverShortTrade(current);
			}
		}

		if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
			if ((current.getIndicator(ma.getName()).getValues()[MovingAverage.UPI_POSN_MA_VALUE] > current.getClose())
					&& (previous.getIndicator(ma.getName()).getValues()[MovingAverage.UPI_POSN_MA_VALUE] <= previous.getClose())) {
				tradeBook.exitLongTrade(current);
			}
		}
	}

	@Override
	protected String getResultFileName() {
		return super.getResultFileName() + "_" + getMA().toString();
	}
}
