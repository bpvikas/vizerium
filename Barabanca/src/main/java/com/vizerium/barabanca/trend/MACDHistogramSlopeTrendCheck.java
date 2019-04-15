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

package com.vizerium.barabanca.trend;

import java.util.List;

import com.vizerium.barabanca.historical.LookbackPeriodCalculator;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MACD;

public class MACDHistogramSlopeTrendCheck implements IndicatorTrendCheck<MACD> {

	private MACD macd;

	public MACDHistogramSlopeTrendCheck(MACD macd) {
		this.macd = macd;
	}

	@Override
	public PeriodTrends getTrend(TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataListCurrentTimeFormat) {

		List<UnitPriceData> expandedUnitPriceDataListTrendTimeFormat = getLookbackPeriodCalculator().getUnitPricesIncludingLookbackPeriodWithTimeFormat(trendTimeFormat,
				unitPriceDataListCurrentTimeFormat, macd);

		macd = macd.calculate(expandedUnitPriceDataListTrendTimeFormat);

		PeriodTrends periodTrends = new PeriodTrends();
		for (int i = 1; i < expandedUnitPriceDataListTrendTimeFormat.size(); i++) {
			if (macd.getHistogramValues()[i] > macd.getHistogramValues()[i - 1]) {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataListTrendTimeFormat.get(i).getDateTime(), trendTimeFormat, Trend.UP));
			} else if (macd.getHistogramValues()[i] < macd.getHistogramValues()[i - 1]) {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataListTrendTimeFormat.get(i).getDateTime(), trendTimeFormat, Trend.DOWN));
			} else {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataListTrendTimeFormat.get(i).getDateTime(), trendTimeFormat, Trend.CHOPPY));
			}
		}
		return periodTrends;
	}

	@Override
	public MACD getIndicator() {
		return macd;
	}

	@Override
	public LookbackPeriodCalculator<MACD> getLookbackPeriodCalculator() {
		return new LookbackPeriodCalculator<MACD>();
	}
}