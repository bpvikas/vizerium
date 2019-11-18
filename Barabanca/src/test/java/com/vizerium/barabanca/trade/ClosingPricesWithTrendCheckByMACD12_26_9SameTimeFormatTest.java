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

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MACD;

public class ClosingPricesWithTrendCheckByMACD12_26_9SameTimeFormatTest extends ClosingPricesWithTrendCheckByMACDHistogramTest {

	private MACD macd;

	@Before
	public void setUp() {
		super.setUp();
		// The default MACD constructor has a fastMA=12, slowMA=26, signal=9
		macd = new MACD();
	}

	@Override
	protected MACD getMACD() {
		return macd;
	}

	@Override
	protected void getAdditionalDataPriorToIteration(TimeFormat timeFormat, List<UnitPriceData> unitPriceDataList) {
		periodTrends = getPeriodTrends(timeFormat, unitPriceDataList);
	}

	@Override
	protected String getResultFileName() {
		return "macd_12_26_same_time_format_trend";
	}
}
