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

import org.apache.log4j.Logger;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import com.vizerium.commons.indicators.MovingAverageType;

/*
 * This program, (in its current form), takes a minimum of 3.5 hours to run.
 * 
 * BEFORE running this class, ensure that all the output files need to go into a separate directory. For that, 
 *
 * src/test/resources/log4j.properties = 
 * 			log4j.appender.file.File=C:/Work/Vizerium/data/output-log-v2/supertrend-v3/testrun.log
 * 
 * src/test/java/com/vizerium/barabanca/trade/TradesReport.java
 * 			static final String outputDirectoryPath = FileUtils.directoryPath + "output-log-v2/supertrend-v3/";
 * 
 * 
 * To cross check with a small number of tests if this is working, change the for loop to 
 * 			for (superTrendAtrPeriod = 5; superTrendAtrPeriod <= 5; superTrendAtrPeriod++) {
 *			for (superTrendMultiplier = 2.0f; superTrendMultiplier <= 2.02f; superTrendMultiplier += 0.1) {
 * 
 */

public class SuperTrendMultipleParametersTradeTestRunner {

	private static final Logger logger = Logger.getLogger(SuperTrendMultipleParametersTradeTestRunner.class);

	private static SuperTrendMultipleParametersTradeTestRunner instance = new SuperTrendMultipleParametersTradeTestRunner();

	static int superTrendAtrPeriod = 5;

	static float superTrendMultiplier = 2.0f;

	static MovingAverageType superTrendAtrMAType = MovingAverageType.WELLESWILDER;

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

		for (superTrendAtrPeriod = 5; superTrendAtrPeriod <= 50; superTrendAtrPeriod++) {
			for (superTrendMultiplier = 2.0f; superTrendMultiplier <= 10.02f; superTrendMultiplier += 0.1) {
				logger.info("Running Tests for supertrend" + superTrendAtrPeriod + superTrendAtrMAType.name().substring(0, 1).toLowerCase() + "x"
						+ String.valueOf(superTrendMultiplier));
				Result result = junit.run(SuperTrendMultipleParametersTradeTest.class, SuperTrendMultipleParametersTradeTrailSLInSystemTest.class);
				resultReport(result);
			}
		}
	}

	private void resultReport(Result result) {
		logger.info("Finished. Result: Failures: " + result.getFailureCount() + ". Ignored: " + result.getIgnoreCount() + ". Tests run: " + result.getRunCount() + ". Time: "
				+ result.getRunTime() + "ms.");
	}

	public static void main(String[] args) {
		instance.runMultipleSuperTrendParameterTests();
	}
}
