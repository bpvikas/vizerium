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

public abstract class EMACrossoverTest extends TradeStrategyTest {

	protected abstract MovingAverage getFastMA();

	protected abstract MovingAverage getSlowMA();

	protected abstract MovingAverage getStopLossMA();

	@Override
	protected void getAdditionalDataPriorToIteration(TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
	}

	@Override
	protected boolean testForCurrentUnitGreaterThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		return positiveMACrossover(current, previous) || higherClose(current, previous);
	}

	@Override
	protected void executeForCurrentUnitGreaterThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (current.getClose() > current.getMovingAverage(getStopLossMA())) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
				tradeBook.coverShortTrade(current);
			}
		}

		if (positiveMACrossover(current, previous)) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeShort()) {
				tradeBook.coverShortTrade(current);
			}
			tradeBook.addLongTrade(current);
		}
	}

	@Override
	protected boolean testForCurrentUnitLessThanPreviousUnit(UnitPriceData current, UnitPriceData previous) {
		return negativeMACrossover(current, previous) || lowerClose(current, previous);
	}

	@Override
	protected void executeForCurrentUnitLessThanPreviousUnit(TradeBook tradeBook, UnitPriceData current, UnitPriceData previous) {
		if (current.getClose() < current.getMovingAverage(getStopLossMA())) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
				tradeBook.exitLongTrade(current);
			}
		}

		if (negativeMACrossover(current, previous)) {
			if (!tradeBook.isLastTradeExited() && tradeBook.isLastTradeLong()) {
				tradeBook.exitLongTrade(current);
			}
			tradeBook.addShortTrade(current);
		}
	}

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(UnitPriceData current, UnitPriceData previous) {

	}

	protected boolean positiveMACrossover(UnitPriceData current, UnitPriceData previous) {
		return ((current.getMovingAverage(getFastMA()) > current.getMovingAverage(getSlowMA()))
				&& (previous.getMovingAverage(getFastMA()) < previous.getMovingAverage(getSlowMA())));
	}

	protected boolean negativeMACrossover(UnitPriceData current, UnitPriceData previous) {
		return ((current.getMovingAverage(getFastMA()) < current.getMovingAverage(getSlowMA()))
				&& (previous.getMovingAverage(getFastMA()) > previous.getMovingAverage(getSlowMA())));
	}
}
