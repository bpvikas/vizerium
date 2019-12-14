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

public class SuperTrendMultipleParametersTradeTestResultsParserV1 extends SuperTrendMultipleParametersTradeTestResultsParser {

	@Override
	protected float getBn15MinMinimumAveragePayoff() {
		return 36.0f;
	}

	@Override
	protected float getBn15MinMinimumTotalPayoff() {
		return 31000.0f;
	}

	@Override
	protected float getBn1HourMinimumAveragePayoff() {
		return 81.0f;
	}

	@Override
	protected float getBn1HourMinimumTotalPayoff() {
		return 18500.0f;
	}

	@Override
	protected float getN15MinMinimumAveragePayoff() {
		return 16.0f;
	}

	@Override
	protected float getN15MinMinimumTotalPayoff() {
		return 10000.0f;
	}

	@Override
	protected float getN1HourMinimumAveragePayoff() {
		return 28.0f;
	}

	@Override
	protected float getN1HourMinimumTotalPayoff() {
		return 6600.0f;
	}

	@Override
	protected String getParsedResultsFile() {
		return FileUtils.directoryPath + "output-log-v2/supertrend-parsed-results-v2/supertrendParsedResults-v2_v1.csv";
	}

	public static void main(String[] args) {
		SuperTrendMultipleParametersTradeTestResultsParserV1 parser = new SuperTrendMultipleParametersTradeTestResultsParserV1();
		parser.parseAndCompareResults();
	}
}
