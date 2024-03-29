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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import com.vizerium.commons.indicators.MovingAverageType;
import com.vizerium.commons.indicators.SuperTrend;

public class SuperTrendWithMAMultipleParametersTradeTestRunner {

	private static final Logger logger = Logger.getLogger(SuperTrendWithMAMultipleParametersTradeTestRunner.class);

	private static SuperTrendWithMAMultipleParametersTradeTestRunner instance = new SuperTrendWithMAMultipleParametersTradeTestRunner();

	private static int superTrendAtrPeriod = SuperTrendMultipleParametersTradeTestRunner.superTrendAtrPeriod;

	private static float superTrendMultiplier = SuperTrendMultipleParametersTradeTestRunner.superTrendMultiplier;

	private static MovingAverageType superTrendAtrMAType = SuperTrendMultipleParametersTradeTestRunner.superTrendAtrMAType;

	private static int ma = 50;

	private static MovingAverageType maType = MovingAverageType.EXPONENTIAL;

	private String superTrendTestResultsDirectoryPath = "src/test/resources/supertrend-parsed-results/";

	private Set<SuperTrend> superTrendSet;

	private Set<SuperTrend> superTrendTrailSLInSystemSet;

	private SuperTrendWithMAMultipleParametersTradeTestRunner() {

	}

	public static SuperTrendWithMAMultipleParametersTradeTestRunner getInstance() {
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

	protected int getMA() {
		return ma;
	}

	public MovingAverageType getMAType() {
		return maType;
	}

	private File[] getSuperTrendTestResultsFile() {
		FilenameFilter fileNameFilter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.startsWith("supertrendParsedResults") && name.endsWith(".csv")) {
					return true;
				}
				return false;
			}
		};
		return new File(superTrendTestResultsDirectoryPath).listFiles(fileNameFilter);
	}

	private void getSuperTrends(File[] superTrendTestResultFiles) {
		superTrendSet = new HashSet<SuperTrend>();
		superTrendTrailSLInSystemSet = new HashSet<SuperTrend>();

		try {
			for (File superTrendTestResultFile : superTrendTestResultFiles) {
				logger.info("Parsing " + superTrendTestResultFile.getName());

				BufferedReader br = new BufferedReader(new FileReader(superTrendTestResultFile));
				br.readLine(); // This is to read off the header line.

				String superTrendLine = "";
				while ((superTrendLine = br.readLine()) != null) {

					String[] superTrendLineDetails = superTrendLine.split(",");

					String superTrendName = superTrendLineDetails[1];
					if (superTrendName.indexOf(TradeStrategyTest.TRAIL_SL_IN_SYSTEM) > 0) {
						superTrendTrailSLInSystemSet.add(SuperTrendTradeTest.getSuperTrendFromName(superTrendName));
					} else {
						superTrendSet.add(SuperTrendTradeTest.getSuperTrendFromName(superTrendName));
					}
				}
				br.close();
			}
		} catch (IOException ioe) {
			throw new RuntimeException("Error while reading lines from superTrend results file.", ioe);
		}
	}

	public void runSuperTrendWithMAMultipleParameterTests() {

		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));

		File[] superTrendTestResultsFiles = getSuperTrendTestResultsFile();
		getSuperTrends(superTrendTestResultsFiles);

		for (ma = 30; ma <= 200; ma += 5) {
			for (MovingAverageType ma : MovingAverageType.values()) {

				logger.info("Running Tests for supertrendWithMA" + String.valueOf(ma) + maType.toString());

				for (SuperTrend superTrend : superTrendSet) {
					superTrendAtrPeriod = superTrend.getPeriod();
					superTrendMultiplier = superTrend.getMultiplier();
					superTrendAtrMAType = superTrend.getAtrMAType();

					Result result = junit.run(SuperTrendWithMAMultipleParametersTradeTest.class);
					resultReport(result);
				}

				for (SuperTrend superTrend : superTrendTrailSLInSystemSet) {
					superTrendAtrPeriod = superTrend.getPeriod();
					superTrendMultiplier = superTrend.getMultiplier();
					superTrendAtrMAType = superTrend.getAtrMAType();

					Result result = junit.run(SuperTrendWithMAMultipleParametersTradeTrailSLInSystemTest.class);
					resultReport(result);
				}
			}
		}
	}

	private void resultReport(Result result) {
		logger.info("Finished. Result: Failures: " + result.getFailureCount() + ". Ignored: " + result.getIgnoreCount() + ". Tests run: " + result.getRunCount() + ". Time: "
				+ result.getRunTime() + "ms.");
	}

	public static void main(String[] args) {
		instance.runSuperTrendWithMAMultipleParameterTests();
	}
}
