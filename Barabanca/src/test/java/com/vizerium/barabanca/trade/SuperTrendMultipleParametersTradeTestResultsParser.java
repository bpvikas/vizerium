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

/*
 * Before running this class, ensure that it is pointed to the correct source and destination directory.
 *
 * Source directory to be updated in method getTestResultsDirectoryPath() -- 1 change
 * 
 * Destination directory to be updated in method getParsedResultsFile() -- 2 changes
 * 
 */
public class SuperTrendMultipleParametersTradeTestResultsParser extends MultipleParametersTradeTestResultsParser {

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
		return FileUtils.directoryPath + "output-log-v2/supertrend-v3/";
	}

	@Override
	protected String getResultFileNamePrefix() {
		return "tradessummary_supertrend";
	}

	@Override
	protected String getParsedResultsFile() {
		return FileUtils.directoryPath + "output-log-v2/supertrend-parsed-results-v3/supertrendParsedResults-v3_v0.csv";
	}

	public static void main(String[] args) {
		SuperTrendMultipleParametersTradeTestResultsParser parser = new SuperTrendMultipleParametersTradeTestResultsParser();
		parser.parseAndCompareResults();
	}
}
