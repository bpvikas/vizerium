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

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import com.vizerium.commons.indicators.MovingAverageType;

public class SuperTrendMultipleParametersTradeTestRunner {

	private static SuperTrendMultipleParametersTradeTestRunner instance = new SuperTrendMultipleParametersTradeTestRunner();

	private static int superTrendAtrPeriod = 4;

	private static float superTrendMultiplier = 1.0f;

	private static MovingAverageType superTrendAtrMAType = MovingAverageType.EXPONENTIAL;

	private SuperTrendMultipleParametersTradeTestRunner() {

	}

	public static SuperTrendMultipleParametersTradeTestRunner getInstance() {
		return instance;
	}

	protected int getSuperTrendAtrPeriod() {
		return superTrendAtrPeriod;
	}

	protected float getSuperTrendMultiplier() {
		return superTrendMultiplier;
	}

	protected MovingAverageType getSuperTrendAtrMAType() {
		return superTrendAtrMAType;
	}

	public void runMultipleSuperTrendParameterTests() {

		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));

		for (superTrendAtrPeriod = 4; superTrendAtrPeriod <= 50; superTrendAtrPeriod++) {
			for (superTrendMultiplier = 1.0f; superTrendMultiplier <= 10.0f; superTrendMultiplier += 0.25) {
				for (MovingAverageType maType : MovingAverageType.values()) {
					if (maType.equals(MovingAverageType.SIMPLE)) {
						continue;
					}
					superTrendAtrMAType = maType;
					System.out.println("Running Tests for supertrend" + superTrendAtrPeriod + superTrendAtrMAType.name().substring(0, 1).toLowerCase() + "x"
							+ String.valueOf(superTrendMultiplier));
					Result result = junit.run(SuperTrendMultipleParametersTradeTest.class, SuperTrendMultipleParametersTradeTrailSLInSystemTest.class);
					resultReport(result);
				}
			}
		}
	}

	private void resultReport(Result result) {
		System.out.println("Finished. Result: Failures: " + result.getFailureCount() + ". Ignored: " + result.getIgnoreCount() + ". Tests run: " + result.getRunCount() + ". Time: "
				+ result.getRunTime() + "ms.");
	}

	public static void main(String[] args) {
		instance.runMultipleSuperTrendParameterTests();
	}
}
