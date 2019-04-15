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
import com.vizerium.commons.indicators.DirectionalSystem;

public class DirectionalSystemADXTrendCheck implements IndicatorTrendCheck<DirectionalSystem> {

	private DirectionalSystem ds;

	public DirectionalSystemADXTrendCheck(DirectionalSystem ds) {
		this.ds = ds;
	}

	@Override
	public PeriodTrends getTrend(TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataList) {

		List<UnitPriceData> expandedUnitPriceDataList = getLookbackPeriodCalculator().getUnitPricesIncludingLookbackPeriodWithTimeFormat(trendTimeFormat, unitPriceDataList, ds);

		ds = ds.calculate(expandedUnitPriceDataList);
		PeriodTrends periodTrends = new PeriodTrends();

		// TODO: Need to read Alexander Elder's book to determine exact rules for trend being, up, down or choppy
		return periodTrends;
	}

	@Override
	public DirectionalSystem getIndicator() {
		return ds;
	}

	@Override
	public LookbackPeriodCalculator<DirectionalSystem> getLookbackPeriodCalculator() {
		return new LookbackPeriodCalculator<DirectionalSystem>();
	}
}
