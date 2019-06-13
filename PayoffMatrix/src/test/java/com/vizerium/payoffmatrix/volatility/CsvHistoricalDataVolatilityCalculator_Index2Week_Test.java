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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.payoffmatrix.dao.HistoricalCsvDataStore;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CsvHistoricalDataVolatilityCalculator_Index2Week_Test extends CsvHistoricalDataVolatilityCalculatorTest {

	private float standardDeviationMultipleOfIndex = 1.488f;

	@Before
	public void setup() {
		underlyingName = "INDEX";
	}

	@Test
	public void testCalculateVolatility() {
		unit.calculateVolatility(new HistoricalCsvDataStore(underlyingName).readHistoricalData(null));
	}

	@Test
	public void test201201ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 1);
	}

	@Test
	public void test201202ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 2);
	}

	@Test
	public void test201203ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 3);
	}

	@Test
	public void test201204ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 4);
	}

	@Test
	public void test201205ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 5);
	}

	@Test
	public void test201206ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 6);
	}

	@Test
	public void test201207ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 7);
	}

	@Test
	public void test201208ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 8);
	}

	@Test
	public void test201209ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 9);
	}

	@Test
	public void test201210ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 10);
	}

	@Test
	public void test201211ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 11);
	}

	@Test
	public void test201212ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2012, 12);
	}

	@Test
	public void test201301ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 1);
	}

	@Test
	public void test201302ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 2);
	}

	@Test
	public void test201303ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 3);
	}

	@Test
	public void test201304ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 4);
	}

	@Test
	public void test201305ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 5);
	}

	@Test
	public void test201306ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 6);
	}

	@Test
	public void test201307ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 7);
	}

	@Test
	public void test201308ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 8);
	}

	@Test
	public void test201309ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 9);
	}

	@Test
	public void test201310ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 10);
	}

	@Test
	public void test201311ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 11);
	}

	@Test
	public void test201312ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2013, 12);
	}

	@Test
	public void test201401ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 1);
	}

	@Test
	public void test201402ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 2);
	}

	@Test
	public void test201403ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 3);
	}

	@Test
	public void test201404ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 4);
	}

	@Test
	public void test201405ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 5);
	}

	@Test
	public void test201406ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 6);
	}

	@Test
	public void test201407ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 7);
	}

	@Test
	public void test201408ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 8);
	}

	@Test
	public void test201409ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 9);
	}

	@Test
	public void test201410ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 10);
	}

	@Test
	public void test201411ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 11);
	}

	@Test
	public void test201412ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2014, 12);
	}

	@Test
	public void test201501ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 1);
	}

	@Test
	public void test201502ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 2);
	}

	@Test
	public void test201503ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 3);
	}

	@Test
	public void test201504ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 4);
	}

	@Test
	public void test201505ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 5);
	}

	@Test
	public void test201506ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 6);
	}

	@Test
	public void test201507ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 7);
	}

	@Test
	public void test201508ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 8);
	}

	@Test
	public void test201509ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 9);
	}

	@Test
	public void test201510ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 10);
	}

	@Test
	public void test201511ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 11);
	}

	@Test
	public void test201512ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2015, 12);
	}

	@Test
	public void test201601ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 1);
	}

	@Test
	public void test201602ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 2);
	}

	@Test
	public void test201603ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 3);
	}

	@Test
	public void test201604ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 4);
	}

	@Test
	public void test201605ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 5);
	}

	@Test
	public void test201606ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 6);
	}

	@Test
	public void test201607ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 7);
	}

	@Test
	public void test201608ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 8);
	}

	@Test
	public void test201609ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 9);
	}

	@Test
	public void test201610ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 10);
	}

	@Test
	public void test201611ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 11);
	}

	@Test
	public void test201612ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2016, 12);
	}

	@Test
	public void test201701ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 1);
	}

	@Test
	public void test201702ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 2);
	}

	@Test
	public void test201703ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 3);
	}

	@Test
	public void test201704ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 4);
	}

	@Test
	public void test201705ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 5);
	}

	@Test
	public void test201706ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 6);
	}

	@Test
	public void test201707ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 7);
	}

	@Test
	public void test201708ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 8);
	}

	@Test
	public void test201709ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 9);
	}

	@Test
	public void test201710ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 10);
	}

	@Test
	public void test201711ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 11);
	}

	@Test
	public void test201712ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 12);
	}

	@Test
	public void test201801ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 1);
	}

	@Test
	public void test201802ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 2);
	}

	@Test
	public void test201803ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 3);
	}

	@Test
	public void test201804ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 4);
	}

	@Test
	public void test201805ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 5);
	}

	@Test
	public void test201806ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 6);
	}

	@Test
	public void test201807ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 7);
	}

	@Test
	public void test201808ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 8);
	}

	@Test
	public void test201809ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 9);
	}

	@Test
	public void test201810ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 10);
	}

	@Test
	public void test201811ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 11);
	}

	@Test
	public void test201812ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 12);
	}

	@Test
	public void test201901ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2019, 1);
	}

	@Test
	public void test201902ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2019, 2);
	}

	@Test
	public void test201903ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2019, 3);
	}

	@Test
	public void test201904ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2019, 4);
	}

	private void testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(int year, int month) {

		LocalDate expiryDate = LocalDate.of(year, month, 1).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		DateRange historicalDateRange = new DateRange(null, expiryDate.minusWeeks(2));
		DateRange futureDateRange = new DateRange(expiryDate.minusWeeks(2).plusDays(1), expiryDate);

		testExpiryDateIsAtWhichStandardDeviationBasedOnDataPrior(historicalDateRange, futureDateRange, standardDeviationMultipleOfIndex);
	}
}
