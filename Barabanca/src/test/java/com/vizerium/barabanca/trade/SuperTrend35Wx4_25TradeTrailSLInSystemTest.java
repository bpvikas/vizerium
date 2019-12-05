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

import com.vizerium.commons.indicators.MovingAverageType;

public class SuperTrend35Wx4_25TradeTrailSLInSystemTest extends SuperTrendTradeTrailSLInSystemTest {

	@Override
	protected int getSuperTrendAtrPeriod() {
		return 35;
	}

	@Override
	protected float getSuperTrendMultiplier() {
		return 4.25f;
	}

	@Override
	protected MovingAverageType getSuperTrendAtrMAType() {
		return MovingAverageType.WELLESWILDER;
	}
}
