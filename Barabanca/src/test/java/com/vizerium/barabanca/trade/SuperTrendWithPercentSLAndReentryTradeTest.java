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

import com.vizerium.commons.dao.UnitPriceData;

public abstract class SuperTrendWithPercentSLAndReentryTradeTest extends SuperTrendWithPercentSLTradeTest {

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {

		super.executeForCurrentUnitChoppyWithPreviousUnit(tradeBook, current, previous);

		if (tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort() && current.getClose() <= tradeBook.last().getEntryPrice()) {
			tradeBook.addShortTrade(current);
			tradeBook.last().setExitStopLoss(current.getTradedValue() * (1.0f + (getPercentSL() / 100.0f)));
		}

		if (tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong() && current.getClose() >= tradeBook.last().getEntryPrice()) {
			tradeBook.addLongTrade(current);
			tradeBook.last().setExitStopLoss(current.getTradedValue() * (1.0f - (getPercentSL() / 100.0f)));
		}
	}

	@Override
	protected String getResultFileName() {
		return super.getResultFileName() + "_reentry";
	}
}
