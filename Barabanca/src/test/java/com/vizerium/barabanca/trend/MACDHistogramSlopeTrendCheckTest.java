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

import org.junit.Assert;
import org.junit.Before;

import com.vizerium.commons.indicators.MACD;

public class MACDHistogramSlopeTrendCheckTest extends IndicatorTrendCheckTest<MACD> {

	@Before
	public void setUp() throws Exception {
		unit = new MACDHistogramSlopeTrendCheck(new MACD());
	}

	@Override
	public void testGetIndicator() {
		// Thanks to type erasure which prevents us from knowing the Class<I>, we need to have this class in
		// the subclass of IndicatorTrendCheckTest rather than the parent
		// https://stackoverflow.com/questions/3437897/how-to-get-a-class-instance-of-generics-type-t
		Assert.assertEquals(MACD.class, unit.getIndicator().getClass());
	}

	@Override
	public IndicatorTrendCheck<MACD> getUnit() {
		return unit;
	}
}
