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

import com.vizerium.commons.io.FileUtils;

public class SuperTrendWithPercentSLMultipleParametersTradeTestResultsParser extends MultipleParametersTradeTestResultsParser {

	@Override
	protected float getBn15MinMinimumAveragePayoff() {
		return 0.0f;
	}

	@Override
	protected float getBn15MinMinimumTotalPayoff() {
		return 0.0f;
	}

	@Override
	protected float getBn1HourMinimumAveragePayoff() {
		return 0.0f;
	}

	@Override
	protected float getBn1HourMinimumTotalPayoff() {
		return 0.0f;
	}

	@Override
	protected float getBn1DayMinimumAveragePayoff() {
		return 0.0f;
	}

	@Override
	protected float getBn1DayMinimumTotalPayoff() {
		return 0.0f;
	}

	@Override
	protected float getN15MinMinimumAveragePayoff() {
		return 0.0f;
	}

	@Override
	protected float getN15MinMinimumTotalPayoff() {
		return 0.0f;
	}

	@Override
	protected float getN1HourMinimumAveragePayoff() {
		return 0.0f;
	}

	@Override
	protected float getN1HourMinimumTotalPayoff() {
		return 0.0f;
	}

	@Override
	protected float getN1DayMinimumAveragePayoff() {
		return 0.0f;
	}

	@Override
	protected float getN1DayMinimumTotalPayoff() {
		return 0.0f;
	}

	@Override
	protected String getTestResultsDirectoryPath() {
		return FileUtils.directoryPath + "output-log-v2/supertrend-with-percentsl-v2/";
	}

	@Override
	protected String getResultFileNamePrefix() {
		return "tradessummary_supertrend";
	}

	@Override
	protected String getParsedResultsFile() {
		return FileUtils.directoryPath + "output-log-v2/supertrend-with-percentsl-parsed-results-v2/supertrendWithPercentSLParsedResults-v2_v0.csv";
	}

	public static void main(String[] args) {
		SuperTrendWithPercentSLMultipleParametersTradeTestResultsParser parser = new SuperTrendWithPercentSLMultipleParametersTradeTestResultsParser();
		parser.parseAndCompareResults();
	}
}
