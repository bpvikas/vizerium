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

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.indicators.MovingAverageType;
import com.vizerium.commons.indicators.SuperTrend;

/*
 * CAUTION : IT TAKES 19 HOURS TO RUN THIS PROGRAM WITH THE SET OF PARAMETERS BELOW.
 */

public class SuperTrendWithRSIMultipleParametersTradeTestRunner {

	private static final Logger logger = Logger.getLogger(SuperTrendWithRSIMultipleParametersTradeTestRunner.class);

	private static SuperTrendWithRSIMultipleParametersTradeTestRunner instance = new SuperTrendWithRSIMultipleParametersTradeTestRunner();

	private static int superTrendAtrPeriod = SuperTrendMultipleParametersTradeTestRunner.superTrendAtrPeriod;

	private static float superTrendMultiplier = SuperTrendMultipleParametersTradeTestRunner.superTrendMultiplier;

	private static MovingAverageType superTrendAtrMAType = SuperTrendMultipleParametersTradeTestRunner.superTrendAtrMAType;

	private static int rsiLookbackPeriod = 14;

	private static float rsiExitForLongPosition = 70.0f;

	private static float rsiExitForShortPosition = 30.0f;

	private String superTrendTestResultsDirectoryPath = "src/test/resources/supertrend-parsed-results/";

	private Set<SuperTrend> superTrendSet;

	private Set<SuperTrend> superTrendTrailSLInSystemSet;

	private SuperTrendWithRSIMultipleParametersTradeTestRunner() {

	}

	public static SuperTrendWithRSIMultipleParametersTradeTestRunner getInstance() {
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

	protected int getRsiLookbackPeriod() {
		return rsiLookbackPeriod;
	}

	public float getRsiExitForLongPosition() {
		return rsiExitForLongPosition;
	}

	public float getRsiExitForShortPosition() {
		return rsiExitForShortPosition;
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

					// Ignoring below all the superTrend results which are in Average as they are sub-optimal results.
					// Also ignoring the 1day results, as we are not planning to pursue trades on the daily timeframe
					if (superTrendLineDetails[0].equals("Average") || superTrendLineDetails[3].equals(TimeFormat._1DAY.getProperty())) {
						continue;
					}

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

	public void runSuperTrendWithRSIMultipleParameterTests() {

		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));

		File[] superTrendTestResultsFiles = getSuperTrendTestResultsFile();
		getSuperTrends(superTrendTestResultsFiles);

		for (rsiLookbackPeriod = 6; rsiLookbackPeriod <= 30; rsiLookbackPeriod += 4) {
			for (rsiExitForLongPosition = 60.0f; rsiExitForLongPosition <= 90.0f; rsiExitForLongPosition += 5.0f) {
				for (rsiExitForShortPosition = 10.0f; rsiExitForShortPosition <= 40.0f; rsiExitForShortPosition += 5.0f) {

					System.out.println("Running Tests for supertrendWithRSI" + String.valueOf((int) rsiLookbackPeriod) + "_" + String.valueOf((int) rsiExitForLongPosition) + "_"
							+ String.valueOf((int) rsiExitForShortPosition));

					for (SuperTrend superTrend : superTrendSet) {
						superTrendAtrPeriod = superTrend.getPeriod();
						superTrendMultiplier = superTrend.getMultiplier();
						superTrendAtrMAType = superTrend.getAtrMAType();

						Result result = junit.run(SuperTrendWithRSIMultipleParametersTradeTest.class);
						resultReport(result);
					}

					for (SuperTrend superTrend : superTrendTrailSLInSystemSet) {
						superTrendAtrPeriod = superTrend.getPeriod();
						superTrendMultiplier = superTrend.getMultiplier();
						superTrendAtrMAType = superTrend.getAtrMAType();

						Result result = junit.run(SuperTrendWithRSIMultipleParametersTradeTrailSLInSystemTest.class);
						resultReport(result);
					}
				}
			}
		}
	}

	private void resultReport(Result result) {
		System.out.println("Finished. Result: Failures: " + result.getFailureCount() + ". Ignored: " + result.getIgnoreCount() + ". Tests run: " + result.getRunCount() + ". Time: "
				+ result.getRunTime() + "ms.");
	}

	public static void main(String[] args) {
		instance.runSuperTrendWithRSIMultipleParameterTests();
	}
}
