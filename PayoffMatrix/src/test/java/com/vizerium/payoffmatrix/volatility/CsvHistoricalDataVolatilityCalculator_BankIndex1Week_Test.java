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

package com.vizerium.payoffmatrix.volatility;

import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CsvHistoricalDataVolatilityCalculator_BankIndex1Week_Test extends CsvHistoricalDataVolatilityCalculatorTest {

	private CsvHistoricalDataVolatilityCalculator unit;

	private float standardDeviationMultipleOfBankIndex = 1.182f;

	private NumberFormat nf = NumberFormat.getInstance();

	@Before
	public void setup() {
		unit = new CsvHistoricalDataVolatilityCalculator("BANKINDEX");

		nf.setMinimumIntegerDigits(2);
	}

	@Test
	public void testCalculateVolatility() {
		unit.calculateVolatility(null);
	}

	@Test
	public void test201501ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 1);
	}

	@Test
	public void test201502ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 2);
	}

	@Test
	public void test201503ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 3);
	}

	@Test
	public void test201504ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 4);
	}

	@Test
	public void test201505ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 5);
	}

	@Test
	public void test201506ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 6);
	}

	@Test
	public void test201507ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 7);
	}

	@Test
	public void test201508ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 8);
	}

	@Test
	public void test201509ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 9);
	}

	@Test
	public void test201510ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 10);
	}

	@Test
	public void test201511ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 11);
	}

	@Test
	public void test201512ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 12);
	}

	@Test
	public void test201513ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 13);
	}

	@Test
	public void test201514ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 14);
	}

	@Test
	public void test201515ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 15);
	}

	@Test
	public void test201516ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 16);
	}

	@Test
	public void test201517ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 17);
	}

	@Test
	public void test201518ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 18);
	}

	@Test
	public void test201519ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 19);
	}

	@Test
	public void test201520ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 20);
	}

	@Test
	public void test201521ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 21);
	}

	@Test
	public void test201522ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 22);
	}

	@Test
	public void test201523ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 23);
	}

	@Test
	public void test201524ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 24);
	}

	@Test
	public void test201525ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 25);
	}

	@Test
	public void test201526ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 26);
	}

	@Test
	public void test201527ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 27);
	}

	@Test
	public void test201528ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 28);
	}

	@Test
	public void test201529ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 29);
	}

	@Test
	public void test201530ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 30);
	}

	@Test
	public void test201531ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 31);
	}

	@Test
	public void test201532ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 32);
	}

	@Test
	public void test201533ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 33);
	}

	@Test
	public void test201534ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 34);
	}

	@Test
	public void test201535ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 35);
	}

	@Test
	public void test201536ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 36);
	}

	@Test
	public void test201537ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 37);
	}

	@Test
	public void test201538ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 38);
	}

	@Test
	public void test201539ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 39);
	}

	@Test
	public void test201540ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 40);
	}

	@Test
	public void test201541ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 41);
	}

	@Test
	public void test201542ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 42);
	}

	@Test
	public void test201543ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 43);
	}

	@Test
	public void test201544ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 44);
	}

	@Test
	public void test201545ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 45);
	}

	@Test
	public void test201546ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 46);
	}

	@Test
	public void test201547ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 47);
	}

	@Test
	public void test201548ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 48);
	}

	@Test
	public void test201549ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 49);
	}

	@Test
	public void test201550ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 50);
	}

	@Test
	public void test201551ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 51);
	}

	@Test
	public void test201552ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 52);
	}

	@Test
	public void test201601ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 1);
	}

	@Test
	public void test201602ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 2);
	}

	@Test
	public void test201603ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 3);
	}

	@Test
	public void test201604ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 4);
	}

	@Test
	public void test201605ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 5);
	}

	@Test
	public void test201606ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 6);
	}

	@Test
	public void test201607ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 7);
	}

	@Test
	public void test201608ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 8);
	}

	@Test
	public void test201609ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 9);
	}

	@Test
	public void test201610ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 10);
	}

	@Test
	public void test201611ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 11);
	}

	@Test
	public void test201612ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 12);
	}

	@Test
	public void test201613ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 13);
	}

	@Test
	public void test201614ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 14);
	}

	@Test
	public void test201615ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 15);
	}

	@Test
	public void test201616ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 16);
	}

	@Test
	public void test201617ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 17);
	}

	@Test
	public void test201618ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 18);
	}

	@Test
	public void test201619ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 19);
	}

	@Test
	public void test201620ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 20);
	}

	@Test
	public void test201621ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 21);
	}

	@Test
	public void test201622ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 22);
	}

	@Test
	public void test201623ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 23);
	}

	@Test
	public void test201624ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 24);
	}

	@Test
	public void test201625ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 25);
	}

	@Test
	public void test201626ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 26);
	}

	@Test
	public void test201627ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 27);
	}

	@Test
	public void test201628ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 28);
	}

	@Test
	public void test201629ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 29);
	}

	@Test
	public void test201630ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 30);
	}

	@Test
	public void test201631ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 31);
	}

	@Test
	public void test201632ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 32);
	}

	@Test
	public void test201633ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 33);
	}

	@Test
	public void test201634ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 34);
	}

	@Test
	public void test201635ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 35);
	}

	@Test
	public void test201636ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 36);
	}

	@Test
	public void test201637ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 37);
	}

	@Test
	public void test201638ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 38);
	}

	@Test
	public void test201639ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 39);
	}

	@Test
	public void test201640ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 40);
	}

	@Test
	public void test201641ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 41);
	}

	@Test
	public void test201642ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 42);
	}

	@Test
	public void test201643ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 43);
	}

	@Test
	public void test201644ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 44);
	}

	@Test
	public void test201645ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 45);
	}

	@Test
	public void test201646ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 46);
	}

	@Test
	public void test201647ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 47);
	}

	@Test
	public void test201648ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 48);
	}

	@Test
	public void test201649ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 49);
	}

	@Test
	public void test201650ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 50);
	}

	@Test
	public void test201651ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 51);
	}

	@Test
	public void test201652ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 52);
	}

	@Test
	public void test201701ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 1);
	}

	@Test
	public void test201702ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 2);
	}

	@Test
	public void test201703ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 3);
	}

	@Test
	public void test201704ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 4);
	}

	@Test
	public void test201705ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 5);
	}

	@Test
	public void test201706ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 6);
	}

	@Test
	public void test201707ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 7);
	}

	@Test
	public void test201708ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 8);
	}

	@Test
	public void test201709ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 9);
	}

	@Test
	public void test201710ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 10);
	}

	@Test
	public void test201711ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 11);
	}

	@Test
	public void test201712ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 12);
	}

	@Test
	public void test201713ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 13);
	}

	@Test
	public void test201714ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 14);
	}

	@Test
	public void test201715ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 15);
	}

	@Test
	public void test201716ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 16);
	}

	@Test
	public void test201717ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 17);
	}

	@Test
	public void test201718ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 18);
	}

	@Test
	public void test201719ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 19);
	}

	@Test
	public void test201720ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 20);
	}

	@Test
	public void test201721ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 21);
	}

	@Test
	public void test201722ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 22);
	}

	@Test
	public void test201723ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 23);
	}

	@Test
	public void test201724ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 24);
	}

	@Test
	public void test201725ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 25);
	}

	@Test
	public void test201726ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 26);
	}

	@Test
	public void test201727ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 27);
	}

	@Test
	public void test201728ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 28);
	}

	@Test
	public void test201729ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 29);
	}

	@Test
	public void test201730ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 30);
	}

	@Test
	public void test201731ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 31);
	}

	@Test
	public void test201732ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 32);
	}

	@Test
	public void test201733ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 33);
	}

	@Test
	public void test201734ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 34);
	}

	@Test
	public void test201735ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 35);
	}

	@Test
	public void test201736ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 36);
	}

	@Test
	public void test201737ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 37);
	}

	@Test
	public void test201738ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 38);
	}

	@Test
	public void test201739ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 39);
	}

	@Test
	public void test201740ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 40);
	}

	@Test
	public void test201741ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 41);
	}

	@Test
	public void test201742ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 42);
	}

	@Test
	public void test201743ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 43);
	}

	@Test
	public void test201744ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 44);
	}

	@Test
	public void test201745ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 45);
	}

	@Test
	public void test201746ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 46);
	}

	@Test
	public void test201747ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 47);
	}

	@Test
	public void test201748ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 48);
	}

	@Test
	public void test201749ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 49);
	}

	@Test
	public void test201750ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 50);
	}

	@Test
	public void test201751ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 51);
	}

	@Test
	public void test201752ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 52);
	}

	@Test
	public void test201801ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 1);
	}

	@Test
	public void test201802ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 2);
	}

	@Test
	public void test201803ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 3);
	}

	@Test
	public void test201804ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 4);
	}

	@Test
	public void test201805ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 5);
	}

	@Test
	public void test201806ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 6);
	}

	@Test
	public void test201807ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 7);
	}

	@Test
	public void test201808ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 8);
	}

	@Test
	public void test201809ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 9);
	}

	@Test
	public void test201810ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 10);
	}

	@Test
	public void test201811ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 11);
	}

	@Test
	public void test201812ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 12);
	}

	@Test
	public void test201813ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 13);
	}

	@Test
	public void test201814ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 14);
	}

	@Test
	public void test201815ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 15);
	}

	@Test
	public void test201816ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 16);
	}

	@Test
	public void test201817ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 17);
	}

	@Test
	public void test201818ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 18);
	}

	@Test
	public void test201819ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 19);
	}

	@Test
	public void test201820ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 20);
	}

	@Test
	public void test201821ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 21);
	}

	@Test
	public void test201822ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 22);
	}

	@Test
	public void test201823ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 23);
	}

	@Test
	public void test201824ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 24);
	}

	@Test
	public void test201825ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 25);
	}

	@Test
	public void test201826ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 26);
	}

	@Test
	public void test201827ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 27);
	}

	@Test
	public void test201828ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 28);
	}

	@Test
	public void test201829ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 29);
	}

	@Test
	public void test201830ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 30);
	}

	@Test
	public void test201831ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 31);
	}

	@Test
	public void test201832ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 32);
	}

	@Test
	public void test201833ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 33);
	}

	@Test
	public void test201834ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 34);
	}

	@Test
	public void test201835ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 35);
	}

	@Test
	public void test201836ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 36);
	}

	@Test
	public void test201837ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 37);
	}

	@Test
	public void test201838ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 38);
	}

	@Test
	public void test201839ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 39);
	}

	@Test
	public void test201840ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 40);
	}

	@Test
	public void test201841ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 41);
	}

	@Test
	public void test201842ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 42);
	}

	@Test
	public void test201843ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 43);
	}

	@Test
	public void test201844ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 44);
	}

	@Test
	public void test201845ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 45);
	}

	@Test
	public void test201846ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 46);
	}

	@Test
	public void test201847ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 47);
	}

	@Test
	public void testPrintOverlapSumsAndViolations() {
		printOverlapSumsAndViolations();
	}

	private void testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(int year, int week) {

		LocalDate expiryDate = LocalDate.parse(year + "-W" + nf.format(week) + "-1", DateTimeFormatter.ISO_WEEK_DATE).with(DayOfWeek.THURSDAY);

		DateRange historicalDateRange = new DateRange(null, expiryDate.minusWeeks(1));
		DateRange futureDateRange = new DateRange(expiryDate.minusWeeks(1).plusDays(1), expiryDate);

		testExpiryDateIsAtWhichStandardDeviationBasedOnDataPrior(historicalDateRange, futureDateRange, standardDeviationMultipleOfBankIndex);
	}

	@Override
	public CsvHistoricalDataVolatilityCalculator getUnit() {
		return unit;
	}
}
