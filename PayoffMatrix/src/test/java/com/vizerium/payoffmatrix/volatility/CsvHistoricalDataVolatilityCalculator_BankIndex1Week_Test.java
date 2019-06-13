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

import com.vizerium.payoffmatrix.dao.HistoricalCsvDataStore;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CsvHistoricalDataVolatilityCalculator_BankIndex1Week_Test extends CsvHistoricalDataVolatilityCalculatorTest {

	private float standardDeviationMultipleOfBankIndex = 1.235f;

	private NumberFormat nf = NumberFormat.getInstance();

	@Before
	public void setup() {
		underlyingName = "BANKINDEX";
		nf.setMinimumIntegerDigits(2);
	}

	@Test
	public void testCalculateVolatility() {
		unit.calculateVolatility(new HistoricalCsvDataStore(underlyingName).readHistoricalData(null));
	}

	@Test
	public void test201201ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 1);
	}

	@Test
	public void test201202ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 2);
	}

	@Test
	public void test201203ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 3);
	}

	@Test
	public void test201204ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 4);
	}

	@Test
	public void test201205ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 5);
	}

	@Test
	public void test201206ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 6);
	}

	@Test
	public void test201207ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 7);
	}

	@Test
	public void test201208ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 8);
	}

	@Test
	public void test201209ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 9);
	}

	@Test
	public void test201210ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 10);
	}

	@Test
	public void test201211ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 11);
	}

	@Test
	public void test201212ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 12);
	}

	@Test
	public void test201213ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 13);
	}

	@Test
	public void test201214ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 14);
	}

	@Test
	public void test201215ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 15);
	}

	@Test
	public void test201216ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 16);
	}

	@Test
	public void test201217ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 17);
	}

	@Test
	public void test201218ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 18);
	}

	@Test
	public void test201219ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 19);
	}

	@Test
	public void test201220ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 20);
	}

	@Test
	public void test201221ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 21);
	}

	@Test
	public void test201222ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 22);
	}

	@Test
	public void test201223ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 23);
	}

	@Test
	public void test201224ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 24);
	}

	@Test
	public void test201225ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 25);
	}

	@Test
	public void test201226ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 26);
	}

	@Test
	public void test201227ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 27);
	}

	@Test
	public void test201228ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 28);
	}

	@Test
	public void test201229ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 29);
	}

	@Test
	public void test201230ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 30);
	}

	@Test
	public void test201231ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 31);
	}

	@Test
	public void test201232ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 32);
	}

	@Test
	public void test201233ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 33);
	}

	@Test
	public void test201234ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 34);
	}

	@Test
	public void test201235ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 35);
	}

	@Test
	public void test201236ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 36);
	}

	@Test
	public void test201237ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 37);
	}

	@Test
	public void test201238ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 38);
	}

	@Test
	public void test201239ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 39);
	}

	@Test
	public void test201240ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 40);
	}

	@Test
	public void test201241ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 41);
	}

	@Test
	public void test201242ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 42);
	}

	@Test
	public void test201243ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 43);
	}

	@Test
	public void test201244ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 44);
	}

	@Test
	public void test201245ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 45);
	}

	@Test
	public void test201246ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 46);
	}

	@Test
	public void test201247ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 47);
	}

	@Test
	public void test201248ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 48);
	}

	@Test
	public void test201249ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 49);
	}

	@Test
	public void test201250ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 50);
	}

	@Test
	public void test201251ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 51);
	}

	@Test
	public void test201252ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 52);
	}

	@Test
	public void test201301ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 1);
	}

	@Test
	public void test201302ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 2);
	}

	@Test
	public void test201303ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 3);
	}

	@Test
	public void test201304ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 4);
	}

	@Test
	public void test201305ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 5);
	}

	@Test
	public void test201306ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 6);
	}

	@Test
	public void test201307ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 7);
	}

	@Test
	public void test201308ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 8);
	}

	@Test
	public void test201309ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 9);
	}

	@Test
	public void test201310ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 10);
	}

	@Test
	public void test201311ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 11);
	}

	@Test
	public void test201312ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 12);
	}

	@Test
	public void test201313ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 13);
	}

	@Test
	public void test201314ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 14);
	}

	@Test
	public void test201315ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 15);
	}

	@Test
	public void test201316ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 16);
	}

	@Test
	public void test201317ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 17);
	}

	@Test
	public void test201318ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 18);
	}

	@Test
	public void test201319ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 19);
	}

	@Test
	public void test201320ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 20);
	}

	@Test
	public void test201321ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 21);
	}

	@Test
	public void test201322ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 22);
	}

	@Test
	public void test201323ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 23);
	}

	@Test
	public void test201324ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 24);
	}

	@Test
	public void test201325ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 25);
	}

	@Test
	public void test201326ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 26);
	}

	@Test
	public void test201327ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 27);
	}

	@Test
	public void test201328ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 28);
	}

	@Test
	public void test201329ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 29);
	}

	@Test
	public void test201330ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 30);
	}

	@Test
	public void test201331ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 31);
	}

	@Test
	public void test201332ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 32);
	}

	@Test
	public void test201333ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 33);
	}

	@Test
	public void test201334ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 34);
	}

	@Test
	public void test201335ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 35);
	}

	@Test
	public void test201336ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 36);
	}

	@Test
	public void test201337ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 37);
	}

	@Test
	public void test201338ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 38);
	}

	@Test
	public void test201339ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 39);
	}

	@Test
	public void test201340ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 40);
	}

	@Test
	public void test201341ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 41);
	}

	@Test
	public void test201342ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 42);
	}

	@Test
	public void test201343ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 43);
	}

	@Test
	public void test201344ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 44);
	}

	@Test
	public void test201345ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 45);
	}

	@Test
	public void test201346ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 46);
	}

	@Test
	public void test201347ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 47);
	}

	@Test
	public void test201348ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 48);
	}

	@Test
	public void test201349ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 49);
	}

	@Test
	public void test201350ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 50);
	}

	@Test
	public void test201351ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 51);
	}

	@Test
	public void test201352ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 52);
	}

	@Test
	public void test201401ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 1);
	}

	@Test
	public void test201402ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 2);
	}

	@Test
	public void test201403ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 3);
	}

	@Test
	public void test201404ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 4);
	}

	@Test
	public void test201405ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 5);
	}

	@Test
	public void test201406ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 6);
	}

	@Test
	public void test201407ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 7);
	}

	@Test
	public void test201408ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 8);
	}

	@Test
	public void test201409ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 9);
	}

	@Test
	public void test201410ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 10);
	}

	@Test
	public void test201411ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 11);
	}

	@Test
	public void test201412ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 12);
	}

	@Test
	public void test201413ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 13);
	}

	@Test
	public void test201414ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 14);
	}

	@Test
	public void test201415ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 15);
	}

	@Test
	public void test201416ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 16);
	}

	@Test
	public void test201417ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 17);
	}

	@Test
	public void test201418ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 18);
	}

	@Test
	public void test201419ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 19);
	}

	@Test
	public void test201420ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 20);
	}

	@Test
	public void test201421ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 21);
	}

	@Test
	public void test201422ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 22);
	}

	@Test
	public void test201423ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 23);
	}

	@Test
	public void test201424ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 24);
	}

	@Test
	public void test201425ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 25);
	}

	@Test
	public void test201426ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 26);
	}

	@Test
	public void test201427ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 27);
	}

	@Test
	public void test201428ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 28);
	}

	@Test
	public void test201429ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 29);
	}

	@Test
	public void test201430ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 30);
	}

	@Test
	public void test201431ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 31);
	}

	@Test
	public void test201432ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 32);
	}

	@Test
	public void test201433ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 33);
	}

	@Test
	public void test201434ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 34);
	}

	@Test
	public void test201435ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 35);
	}

	@Test
	public void test201436ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 36);
	}

	@Test
	public void test201437ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 37);
	}

	@Test
	public void test201438ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 38);
	}

	@Test
	public void test201439ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 39);
	}

	@Test
	public void test201440ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 40);
	}

	@Test
	public void test201441ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 41);
	}

	@Test
	public void test201442ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 42);
	}

	@Test
	public void test201443ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 43);
	}

	@Test
	public void test201444ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 44);
	}

	@Test
	public void test201445ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 45);
	}

	@Test
	public void test201446ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 46);
	}

	@Test
	public void test201447ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 47);
	}

	@Test
	public void test201448ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 48);
	}

	@Test
	public void test201449ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 49);
	}

	@Test
	public void test201450ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 50);
	}

	@Test
	public void test201451ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 51);
	}

	@Test
	public void test201452ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 52);
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
	public void test201848ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 48);
	}

	@Test
	public void test201849ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 49);
	}

	@Test
	public void test201850ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 50);
	}

	@Test
	public void test201851ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 51);
	}

	@Test
	public void test201852ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 52);
	}

	@Test
	public void test201901ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 1);
	}

	@Test
	public void test201902ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 2);
	}

	@Test
	public void test201903ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 3);
	}

	@Test
	public void test201904ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 4);
	}

	@Test
	public void test201905ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 5);
	}

	@Test
	public void test201906ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 6);
	}

	@Test
	public void test201907ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 7);
	}

	@Test
	public void test201908ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 8);
	}

	@Test
	public void test201909ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 9);
	}

	@Test
	public void test201910ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 10);
	}

	@Test
	public void test201911ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 11);
	}

	@Test
	public void test201912ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 12);
	}

	@Test
	public void test201913ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 13);
	}

	@Test
	public void test201914ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 14);
	}

	@Test
	public void test201915ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 15);
	}

	@Test
	public void test201916ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 16);
	}

	@Test
	public void test201917ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 17);
	}

	@Test
	public void test201918ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 18);
	}

	@Test
	public void test201919ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 19);
	}

	@Test
	public void test201920ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 20);
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
}
