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
import com.vizerium.commons.indicators.RSI;

public abstract class SuperTrendWithRSITradeTest extends SuperTrendTradeTest {

	public abstract int getRsiLookbackPeriod();

	public abstract float getRsiExitForLongPosition();

	public abstract float getRsiExitForShortPosition();

	protected RSI rsi;

	@Override
	protected void getAdditionalDataPriorToIteration(TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		super.getAdditionalDataPriorToIteration(timeFormat, unitPriceDataList);
		rsi = new RSI(getRsiLookbackPeriod());
		updateIndicatorDataInUnitPrices(unitPriceDataList, rsi);
	}

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {

		if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
			if ((current.getIndicator(rsi.getName()).getValues()[RSI.UPI_POSN_RSI_VALUE] > getRsiExitForShortPosition())
					&& (previous.getIndicator(rsi.getName()).getValues()[RSI.UPI_POSN_RSI_VALUE] < getRsiExitForShortPosition())) {
				tradeBook.coverShortTrade(current);
			}
		}

		if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
			if ((current.getIndicator(rsi.getName()).getValues()[RSI.UPI_POSN_RSI_VALUE] < getRsiExitForLongPosition())
					&& (previous.getIndicator(rsi.getName()).getValues()[RSI.UPI_POSN_RSI_VALUE] > getRsiExitForLongPosition())) {
				tradeBook.exitLongTrade(current);
			}
		}
	}

	@Override
	protected String getResultFileName() {
		return super.getResultFileName() + "_rsi" + getRsiLookbackPeriod() + "_" + String.valueOf((int)getRsiExitForLongPosition()) + "_" + String.valueOf((int)getRsiExitForShortPosition());
	}
}
