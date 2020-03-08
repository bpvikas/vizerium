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

/*
 * Before running this class, ensure that it is pointed to the correct source and destination directory.
 *
 * The changes are to be made in the parent class only. See comments within parent class for instructions. 
 * 
 */
public class SuperTrendMultipleParametersTradeTestResultsParserV1 extends SuperTrendMultipleParametersTradeTestResultsParser {

	@Override
	protected float getBn15MinMinimumAveragePayoff() {
		return 42.0f;
	}

	@Override
	protected float getBn15MinMinimumTotalPayoff() {
		return 29000.0f;
	}

	@Override
	protected float getBn1HourMinimumAveragePayoff() {
		return 55.0f;
	}

	@Override
	protected float getBn1HourMinimumTotalPayoff() {
		return 17000.0f;
	}

	@Override
	protected float getN15MinMinimumAveragePayoff() {
		return 17.0f;
	}

	@Override
	protected float getN15MinMinimumTotalPayoff() {
		return 12000.0f;
	}

	@Override
	protected float getN1HourMinimumAveragePayoff() {
		return 23.0f;
	}

	@Override
	protected float getN1HourMinimumTotalPayoff() {
		return 9000.0f;
	}

	@Override
	protected String getParsedResultsFile() {
		return super.getParsedResultsFile().replace("_v0.csv", "_v1.csv");
	}

	public static void main(String[] args) {
		SuperTrendMultipleParametersTradeTestResultsParserV1 parser = new SuperTrendMultipleParametersTradeTestResultsParserV1();
		parser.parseAndCompareResults();
	}
}
