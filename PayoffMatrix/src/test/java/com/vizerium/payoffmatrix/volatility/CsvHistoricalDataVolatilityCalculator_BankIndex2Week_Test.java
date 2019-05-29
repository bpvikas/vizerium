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

import com.vizerium.payoffmatrix.volatility.CsvHistoricalDataVolatilityCalculator;
import com.vizerium.payoffmatrix.volatility.DateRange;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CsvHistoricalDataVolatilityCalculator_BankIndex2Week_Test extends CsvHistoricalDataVolatilityCalculatorTest {

	private CsvHistoricalDataVolatilityCalculator unit;

	private float standardDeviationMultipleOfBankIndex = 1.334f;

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
	public void test201201ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 1);
	}

	@Test
	public void test201202ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 2);
	}

	@Test
	public void test201203ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 3);
	}

	@Test
	public void test201204ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 4);
	}

	@Test
	public void test201205ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 5);
	}

	@Test
	public void test201206ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 6);
	}

	@Test
	public void test201207ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 7);
	}

	@Test
	public void test201208ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 8);
	}

	@Test
	public void test201209ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 9);
	}

	@Test
	public void test201210ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 10);
	}

	@Test
	public void test201211ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 11);
	}

	@Test
	public void test201212ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 12);
	}

	@Test
	public void test201213ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 13);
	}

	@Test
	public void test201214ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 14);
	}

	@Test
	public void test201215ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 15);
	}

	@Test
	public void test201216ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 16);
	}

	@Test
	public void test201217ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 17);
	}

	@Test
	public void test201218ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 18);
	}

	@Test
	public void test201219ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 19);
	}

	@Test
	public void test201220ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 20);
	}

	@Test
	public void test201221ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 21);
	}

	@Test
	public void test201222ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 22);
	}

	@Test
	public void test201223ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 23);
	}

	@Test
	public void test201224ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 24);
	}

	@Test
	public void test201225ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 25);
	}

	@Test
	public void test201226ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 26);
	}

	@Test
	public void test201227ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 27);
	}

	@Test
	public void test201228ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 28);
	}

	@Test
	public void test201229ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 29);
	}

	@Test
	public void test201230ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 30);
	}

	@Test
	public void test201231ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 31);
	}

	@Test
	public void test201232ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 32);
	}

	@Test
	public void test201233ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 33);
	}

	@Test
	public void test201234ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 34);
	}

	@Test
	public void test201235ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 35);
	}

	@Test
	public void test201236ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 36);
	}

	@Test
	public void test201237ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 37);
	}

	@Test
	public void test201238ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 38);
	}

	@Test
	public void test201239ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 39);
	}

	@Test
	public void test201240ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 40);
	}

	@Test
	public void test201241ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 41);
	}

	@Test
	public void test201242ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 42);
	}

	@Test
	public void test201243ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 43);
	}

	@Test
	public void test201244ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 44);
	}

	@Test
	public void test201245ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 45);
	}

	@Test
	public void test201246ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 46);
	}

	@Test
	public void test201247ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 47);
	}

	@Test
	public void test201248ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 48);
	}

	@Test
	public void test201249ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 49);
	}

	@Test
	public void test201250ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 50);
	}

	@Test
	public void test201251ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 51);
	}

	@Test
	public void test201252ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 52);
	}

	@Test
	public void test201301ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 1);
	}

	@Test
	public void test201302ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 2);
	}

	@Test
	public void test201303ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 3);
	}

	@Test
	public void test201304ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 4);
	}

	@Test
	public void test201305ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 5);
	}

	@Test
	public void test201306ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 6);
	}

	@Test
	public void test201307ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 7);
	}

	@Test
	public void test201308ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 8);
	}

	@Test
	public void test201309ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 9);
	}

	@Test
	public void test201310ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 10);
	}

	@Test
	public void test201311ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 11);
	}

	@Test
	public void test201312ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 12);
	}

	@Test
	public void test201313ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 13);
	}

	@Test
	public void test201314ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 14);
	}

	@Test
	public void test201315ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 15);
	}

	@Test
	public void test201316ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 16);
	}

	@Test
	public void test201317ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 17);
	}

	@Test
	public void test201318ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 18);
	}

	@Test
	public void test201319ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 19);
	}

	@Test
	public void test201320ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 20);
	}

	@Test
	public void test201321ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 21);
	}

	@Test
	public void test201322ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 22);
	}

	@Test
	public void test201323ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 23);
	}

	@Test
	public void test201324ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 24);
	}

	@Test
	public void test201325ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 25);
	}

	@Test
	public void test201326ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 26);
	}

	@Test
	public void test201327ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 27);
	}

	@Test
	public void test201328ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 28);
	}

	@Test
	public void test201329ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 29);
	}

	@Test
	public void test201330ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 30);
	}

	@Test
	public void test201331ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 31);
	}

	@Test
	public void test201332ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 32);
	}

	@Test
	public void test201333ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 33);
	}

	@Test
	public void test201334ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 34);
	}

	@Test
	public void test201335ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 35);
	}

	@Test
	public void test201336ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 36);
	}

	@Test
	public void test201337ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 37);
	}

	@Test
	public void test201338ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 38);
	}

	@Test
	public void test201339ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 39);
	}

	@Test
	public void test201340ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 40);
	}

	@Test
	public void test201341ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 41);
	}

	@Test
	public void test201342ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 42);
	}

	@Test
	public void test201343ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 43);
	}

	@Test
	public void test201344ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 44);
	}

	@Test
	public void test201345ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 45);
	}

	@Test
	public void test201346ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 46);
	}

	@Test
	public void test201347ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 47);
	}

	@Test
	public void test201348ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 48);
	}

	@Test
	public void test201349ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 49);
	}

	@Test
	public void test201350ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 50);
	}

	@Test
	public void test201351ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 51);
	}

	@Test
	public void test201352ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 52);
	}

	@Test
	public void test201401ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 1);
	}

	@Test
	public void test201402ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 2);
	}

	@Test
	public void test201403ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 3);
	}

	@Test
	public void test201404ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 4);
	}

	@Test
	public void test201405ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 5);
	}

	@Test
	public void test201406ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 6);
	}

	@Test
	public void test201407ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 7);
	}

	@Test
	public void test201408ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 8);
	}

	@Test
	public void test201409ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 9);
	}

	@Test
	public void test201410ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 10);
	}

	@Test
	public void test201411ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 11);
	}

	@Test
	public void test201412ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 12);
	}

	@Test
	public void test201413ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 13);
	}

	@Test
	public void test201414ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 14);
	}

	@Test
	public void test201415ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 15);
	}

	@Test
	public void test201416ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 16);
	}

	@Test
	public void test201417ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 17);
	}

	@Test
	public void test201418ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 18);
	}

	@Test
	public void test201419ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 19);
	}

	@Test
	public void test201420ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 20);
	}

	@Test
	public void test201421ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 21);
	}

	@Test
	public void test201422ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 22);
	}

	@Test
	public void test201423ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 23);
	}

	@Test
	public void test201424ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 24);
	}

	@Test
	public void test201425ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 25);
	}

	@Test
	public void test201426ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 26);
	}

	@Test
	public void test201427ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 27);
	}

	@Test
	public void test201428ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 28);
	}

	@Test
	public void test201429ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 29);
	}

	@Test
	public void test201430ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 30);
	}

	@Test
	public void test201431ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 31);
	}

	@Test
	public void test201432ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 32);
	}

	@Test
	public void test201433ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 33);
	}

	@Test
	public void test201434ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 34);
	}

	@Test
	public void test201435ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 35);
	}

	@Test
	public void test201436ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 36);
	}

	@Test
	public void test201437ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 37);
	}

	@Test
	public void test201438ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 38);
	}

	@Test
	public void test201439ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 39);
	}

	@Test
	public void test201440ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 40);
	}

	@Test
	public void test201441ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 41);
	}

	@Test
	public void test201442ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 42);
	}

	@Test
	public void test201443ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 43);
	}

	@Test
	public void test201444ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 44);
	}

	@Test
	public void test201445ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 45);
	}

	@Test
	public void test201446ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 46);
	}

	@Test
	public void test201447ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 47);
	}

	@Test
	public void test201448ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 48);
	}

	@Test
	public void test201449ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 49);
	}

	@Test
	public void test201450ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 50);
	}

	@Test
	public void test201451ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 51);
	}

	@Test
	public void test201452ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 52);
	}

	@Test
	public void test201501ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 1);
	}

	@Test
	public void test201502ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 2);
	}

	@Test
	public void test201503ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 3);
	}

	@Test
	public void test201504ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 4);
	}

	@Test
	public void test201505ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 5);
	}

	@Test
	public void test201506ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 6);
	}

	@Test
	public void test201507ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 7);
	}

	@Test
	public void test201508ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 8);
	}

	@Test
	public void test201509ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 9);
	}

	@Test
	public void test201510ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 10);
	}

	@Test
	public void test201511ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 11);
	}

	@Test
	public void test201512ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 12);
	}

	@Test
	public void test201513ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 13);
	}

	@Test
	public void test201514ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 14);
	}

	@Test
	public void test201515ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 15);
	}

	@Test
	public void test201516ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 16);
	}

	@Test
	public void test201517ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 17);
	}

	@Test
	public void test201518ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 18);
	}

	@Test
	public void test201519ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 19);
	}

	@Test
	public void test201520ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 20);
	}

	@Test
	public void test201521ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 21);
	}

	@Test
	public void test201522ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 22);
	}

	@Test
	public void test201523ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 23);
	}

	@Test
	public void test201524ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 24);
	}

	@Test
	public void test201525ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 25);
	}

	@Test
	public void test201526ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 26);
	}

	@Test
	public void test201527ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 27);
	}

	@Test
	public void test201528ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 28);
	}

	@Test
	public void test201529ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 29);
	}

	@Test
	public void test201530ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 30);
	}

	@Test
	public void test201531ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 31);
	}

	@Test
	public void test201532ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 32);
	}

	@Test
	public void test201533ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 33);
	}

	@Test
	public void test201534ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 34);
	}

	@Test
	public void test201535ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 35);
	}

	@Test
	public void test201536ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 36);
	}

	@Test
	public void test201537ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 37);
	}

	@Test
	public void test201538ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 38);
	}

	@Test
	public void test201539ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 39);
	}

	@Test
	public void test201540ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 40);
	}

	@Test
	public void test201541ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 41);
	}

	@Test
	public void test201542ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 42);
	}

	@Test
	public void test201543ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 43);
	}

	@Test
	public void test201544ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 44);
	}

	@Test
	public void test201545ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 45);
	}

	@Test
	public void test201546ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 46);
	}

	@Test
	public void test201547ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 47);
	}

	@Test
	public void test201548ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 48);
	}

	@Test
	public void test201549ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 49);
	}

	@Test
	public void test201550ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 50);
	}

	@Test
	public void test201551ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 51);
	}

	@Test
	public void test201552ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 52);
	}

	@Test
	public void test201601ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 1);
	}

	@Test
	public void test201602ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 2);
	}

	@Test
	public void test201603ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 3);
	}

	@Test
	public void test201604ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 4);
	}

	@Test
	public void test201605ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 5);
	}

	@Test
	public void test201606ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 6);
	}

	@Test
	public void test201607ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 7);
	}

	@Test
	public void test201608ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 8);
	}

	@Test
	public void test201609ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 9);
	}

	@Test
	public void test201610ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 10);
	}

	@Test
	public void test201611ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 11);
	}

	@Test
	public void test201612ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 12);
	}

	@Test
	public void test201613ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 13);
	}

	@Test
	public void test201614ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 14);
	}

	@Test
	public void test201615ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 15);
	}

	@Test
	public void test201616ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 16);
	}

	@Test
	public void test201617ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 17);
	}

	@Test
	public void test201618ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 18);
	}

	@Test
	public void test201619ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 19);
	}

	@Test
	public void test201620ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 20);
	}

	@Test
	public void test201621ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 21);
	}

	@Test
	public void test201622ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 22);
	}

	@Test
	public void test201623ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 23);
	}

	@Test
	public void test201624ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 24);
	}

	@Test
	public void test201625ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 25);
	}

	@Test
	public void test201626ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 26);
	}

	@Test
	public void test201627ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 27);
	}

	@Test
	public void test201628ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 28);
	}

	@Test
	public void test201629ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 29);
	}

	@Test
	public void test201630ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 30);
	}

	@Test
	public void test201631ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 31);
	}

	@Test
	public void test201632ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 32);
	}

	@Test
	public void test201633ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 33);
	}

	@Test
	public void test201634ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 34);
	}

	@Test
	public void test201635ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 35);
	}

	@Test
	public void test201636ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 36);
	}

	@Test
	public void test201637ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 37);
	}

	@Test
	public void test201638ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 38);
	}

	@Test
	public void test201639ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 39);
	}

	@Test
	public void test201640ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 40);
	}

	@Test
	public void test201641ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 41);
	}

	@Test
	public void test201642ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 42);
	}

	@Test
	public void test201643ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 43);
	}

	@Test
	public void test201644ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 44);
	}

	@Test
	public void test201645ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 45);
	}

	@Test
	public void test201646ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 46);
	}

	@Test
	public void test201647ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 47);
	}

	@Test
	public void test201648ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 48);
	}

	@Test
	public void test201649ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 49);
	}

	@Test
	public void test201650ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 50);
	}

	@Test
	public void test201651ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 51);
	}

	@Test
	public void test201652ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 52);
	}

	@Test
	public void test201701ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 1);
	}

	@Test
	public void test201702ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 2);
	}

	@Test
	public void test201703ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 3);
	}

	@Test
	public void test201704ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 4);
	}

	@Test
	public void test201705ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 5);
	}

	@Test
	public void test201706ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 6);
	}

	@Test
	public void test201707ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 7);
	}

	@Test
	public void test201708ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 8);
	}

	@Test
	public void test201709ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 9);
	}

	@Test
	public void test201710ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 10);
	}

	@Test
	public void test201711ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 11);
	}

	@Test
	public void test201712ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 12);
	}

	@Test
	public void test201713ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 13);
	}

	@Test
	public void test201714ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 14);
	}

	@Test
	public void test201715ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 15);
	}

	@Test
	public void test201716ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 16);
	}

	@Test
	public void test201717ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 17);
	}

	@Test
	public void test201718ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 18);
	}

	@Test
	public void test201719ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 19);
	}

	@Test
	public void test201720ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 20);
	}

	@Test
	public void test201721ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 21);
	}

	@Test
	public void test201722ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 22);
	}

	@Test
	public void test201723ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 23);
	}

	@Test
	public void test201724ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 24);
	}

	@Test
	public void test201725ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 25);
	}

	@Test
	public void test201726ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 26);
	}

	@Test
	public void test201727ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 27);
	}

	@Test
	public void test201728ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 28);
	}

	@Test
	public void test201729ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 29);
	}

	@Test
	public void test201730ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 30);
	}

	@Test
	public void test201731ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 31);
	}

	@Test
	public void test201732ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 32);
	}

	@Test
	public void test201733ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 33);
	}

	@Test
	public void test201734ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 34);
	}

	@Test
	public void test201735ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 35);
	}

	@Test
	public void test201736ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 36);
	}

	@Test
	public void test201737ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 37);
	}

	@Test
	public void test201738ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 38);
	}

	@Test
	public void test201739ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 39);
	}

	@Test
	public void test201740ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 40);
	}

	@Test
	public void test201741ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 41);
	}

	@Test
	public void test201742ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 42);
	}

	@Test
	public void test201743ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 43);
	}

	@Test
	public void test201744ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 44);
	}

	@Test
	public void test201745ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 45);
	}

	@Test
	public void test201746ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 46);
	}

	@Test
	public void test201747ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 47);
	}

	@Test
	public void test201748ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 48);
	}

	@Test
	public void test201749ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 49);
	}

	@Test
	public void test201750ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 50);
	}

	@Test
	public void test201751ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 51);
	}

	@Test
	public void test201752ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 52);
	}

	@Test
	public void test201801ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 1);
	}

	@Test
	public void test201802ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 2);
	}

	@Test
	public void test201803ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 3);
	}

	@Test
	public void test201804ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 4);
	}

	@Test
	public void test201805ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 5);
	}

	@Test
	public void test201806ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 6);
	}

	@Test
	public void test201807ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 7);
	}

	@Test
	public void test201808ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 8);
	}

	@Test
	public void test201809ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 9);
	}

	@Test
	public void test201810ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 10);
	}

	@Test
	public void test201811ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 11);
	}

	@Test
	public void test201812ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 12);
	}

	@Test
	public void test201813ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 13);
	}

	@Test
	public void test201814ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 14);
	}

	@Test
	public void test201815ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 15);
	}

	@Test
	public void test201816ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 16);
	}

	@Test
	public void test201817ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 17);
	}

	@Test
	public void test201818ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 18);
	}

	@Test
	public void test201819ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 19);
	}

	@Test
	public void test201820ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 20);
	}

	@Test
	public void test201821ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 21);
	}

	@Test
	public void test201822ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 22);
	}

	@Test
	public void test201823ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 23);
	}

	@Test
	public void test201824ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 24);
	}

	@Test
	public void test201825ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 25);
	}

	@Test
	public void test201826ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 26);
	}

	@Test
	public void test201827ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 27);
	}

	@Test
	public void test201828ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 28);
	}

	@Test
	public void test201829ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 29);
	}

	@Test
	public void test201830ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 30);
	}

	@Test
	public void test201831ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 31);
	}

	@Test
	public void test201832ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 32);
	}

	@Test
	public void test201833ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 33);
	}

	@Test
	public void test201834ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 34);
	}

	@Test
	public void test201835ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 35);
	}

	@Test
	public void test201836ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 36);
	}

	@Test
	public void test201837ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 37);
	}

	@Test
	public void test201838ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 38);
	}

	@Test
	public void test201839ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 39);
	}

	@Test
	public void test201840ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 40);
	}

	@Test
	public void test201841ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 41);
	}

	@Test
	public void test201842ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 42);
	}

	@Test
	public void test201843ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 43);
	}

	@Test
	public void test201844ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 44);
	}

	@Test
	public void test201845ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 45);
	}

	@Test
	public void test201846ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 46);
	}

	@Test
	public void test201847ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 47);
	}

	private void testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(int year, int week) {

		LocalDate expiryDate = LocalDate.parse(year + "-W" + nf.format(week) + "-1", DateTimeFormatter.ISO_WEEK_DATE).with(DayOfWeek.THURSDAY);

		DateRange historicalDateRange = new DateRange(null, expiryDate.minusWeeks(2));
		DateRange futureDateRange = new DateRange(expiryDate.minusWeeks(2).plusDays(1), expiryDate);

		testExpiryDateIsAtWhichStandardDeviationBasedOnDataPrior(historicalDateRange, futureDateRange, standardDeviationMultipleOfBankIndex);
	}

	@Override
	public CsvHistoricalDataVolatilityCalculator getUnit() {
		return unit;
	}
}
