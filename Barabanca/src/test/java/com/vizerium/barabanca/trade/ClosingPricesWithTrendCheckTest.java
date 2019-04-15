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

import org.junit.Before;

import com.vizerium.barabanca.trend.PeriodTrends;
import com.vizerium.barabanca.trend.TrendCheck;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;

public abstract class ClosingPricesWithTrendCheckTest extends ClosingPricesTest {

	protected TrendCheck trendCheck;

	protected PeriodTrends periodTrends;

	@Before
	public void setUp() {
		super.setUp();
	}

	protected abstract PeriodTrends getPeriodTrends(TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataListCurrentTimeFormat);

	@Override
	protected void getAdditionalDataPriorToIteration(TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		periodTrends = getPeriodTrends(timeFormat.getHigherTimeFormat(), unitPriceDataList);
	}

	@Override
	protected void executeForCurrentUnitChoppyWithPreviousUnit(UnitPriceData current, UnitPriceData previous) {

	}

}
