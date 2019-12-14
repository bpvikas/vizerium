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

import org.junit.BeforeClass;

import com.vizerium.commons.indicators.MovingAverageType;

public class SuperTrendWithRSIMultipleParametersTradeTrailSLInSystemTest extends SuperTrendWithRSITradeTrailSLInSystemTest {

	private static SuperTrendWithRSIMultipleParametersTradeTestRunner runner;

	@BeforeClass
	public static void setUpBeforeClass() {
		runner = SuperTrendWithRSIMultipleParametersTradeTestRunner.getInstance();
	}

	@Override
	protected int getSuperTrendAtrPeriod() {
		return runner.getSuperTrendAtrPeriod();
	}

	@Override
	protected float getSuperTrendMultiplier() {
		return runner.getSuperTrendMultiplier();
	}

	@Override
	protected MovingAverageType getSuperTrendAtrMAType() {
		return runner.getSuperTrendAtrMAType();
	}

	@Override
	public int getRsiLookbackPeriod() {
		return runner.getRsiLookbackPeriod();
	}

	@Override
	public float getRsiExitForLongPosition() {
		return runner.getRsiExitForLongPosition();
	}

	@Override
	public float getRsiExitForShortPosition() {
		return runner.getRsiExitForShortPosition();
	}
}
