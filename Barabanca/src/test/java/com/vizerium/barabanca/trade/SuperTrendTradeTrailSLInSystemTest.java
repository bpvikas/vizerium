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
import com.vizerium.commons.indicators.SuperTrend;

public abstract class SuperTrendTradeTrailSLInSystemTest extends SuperTrendTradeTest {

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
			Trade lastTrade = tradeBook.last();
			if (lastTrade.isExitStopLossHit(current.getHigh())) {
				current.setTradedValue(lastTrade.getExitStoppedPrice(current));
			}
			tradeBook.coverShortTrade(current);
		}

		if (tradeBook.isLastTradeExited()) {
			tradeBook.addLongTrade(current);
			tradeBook.last().setExitStopLoss(current.getIndicator(superTrend.getName()).getValues()[SuperTrend.UPI_POSN_SUPERTREND_VALUE]);
		}
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
			Trade lastTrade = tradeBook.last();
			if (lastTrade.isExitStopLossHit(current.getLow())) {
				current.setTradedValue(lastTrade.getExitStoppedPrice(current));
			}
			tradeBook.exitLongTrade(current);
		}

		if (tradeBook.isLastTradeExited()) {
			tradeBook.addShortTrade(current);
			tradeBook.last().setExitStopLoss(current.getIndicator(superTrend.getName()).getValues()[SuperTrend.UPI_POSN_SUPERTREND_VALUE]);
		}
	}

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
			Trade lastTrade = tradeBook.last();
			if (lastTrade.isExitStopLossHit(current.getHigh())) {
				current.setTradedValue(lastTrade.getExitStoppedPrice(current));
				tradeBook.coverShortTrade(current);
			} else {
				lastTrade.setExitStopLoss(current.getIndicator(superTrend.getName()).getValues()[SuperTrend.UPI_POSN_SUPERTREND_VALUE]);
			}
		}

		if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
			Trade lastTrade = tradeBook.last();
			if (lastTrade.isExitStopLossHit(current.getLow())) {
				current.setTradedValue(lastTrade.getExitStoppedPrice(current));
				tradeBook.exitLongTrade(current);
			} else {
				lastTrade.setExitStopLoss(current.getIndicator(superTrend.getName()).getValues()[SuperTrend.UPI_POSN_SUPERTREND_VALUE]);
			}
		}
	}

	@Override
	protected String getResultFileName() {
		return super.getResultFileName() + "_trail_SL_in_system";
	}
}
