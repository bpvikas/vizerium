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

import org.junit.Before;

import com.vizerium.commons.indicators.MACD;
import com.vizerium.commons.indicators.MovingAverageType;

public class ClosingPricesWithTrendCheckByMACD5_13_9HigherTimeFormatTest extends ClosingPricesWithTrendCheckByMACDHistogramTest {

	/*
	 * The higher timeFormat is the default option which is implemented in
	 * 
	 * @see com.vizerium.barabanca.trade.ClosingPricesWithTrendCheckTest#getAdditionalDataPriorToIteration
	 */

	private MACD macd;

	@Before
	public void setUp() {
		super.setUp();
		macd = new MACD(5, 13, MovingAverageType.EXPONENTIAL, MovingAverageType.EXPONENTIAL, 9);
	}

	@Override
	protected MACD getMACD() {
		return macd;
	}

	@Override
	protected String getResultFileName() {
		return "macd_5_13_higher_time_format_trend";
	}
}
