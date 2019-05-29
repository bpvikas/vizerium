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

import com.vizerium.payoffmatrix.volatility.CsvHistoricalDataVolatilityCalculator;
import com.vizerium.payoffmatrix.volatility.DateRange;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CsvHistoricalDataVolatilityCalculator_Index1Week_Test extends CsvHistoricalDataVolatilityCalculatorTest {

	private CsvHistoricalDataVolatilityCalculator unit;

	private float standardDeviationMultipleOfIndex = 1.477f;

	@Before
	public void setup() {
		unit = new CsvHistoricalDataVolatilityCalculator("INDEX");
	}

	@Test
	public void testCalculateVolatility() {
		unit.calculateVolatility(null);
	}

	@Test
	public void test201201ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 1);
	}

	@Test
	public void test201202ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 2);
	}

	@Test
	public void test201203ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 3);
	}

	@Test
	public void test201204ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 4);
	}

	@Test
	public void test201205ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 5);
	}

	@Test
	public void test201206ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 6);
	}

	@Test
	public void test201207ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 7);
	}

	@Test
	public void test201208ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 8);
	}

	@Test
	public void test201209ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 9);
	}

	@Test
	public void test201210ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 10);
	}

	@Test
	public void test201211ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 11);
	}

	@Test
	public void test201212ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2012, 12);
	}

	@Test
	public void test201301ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 1);
	}

	@Test
	public void test201302ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 2);
	}

	@Test
	public void test201303ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 3);
	}

	@Test
	public void test201304ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 4);
	}

	@Test
	public void test201305ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 5);
	}

	@Test
	public void test201306ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 6);
	}

	@Test
	public void test201307ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 7);
	}

	@Test
	public void test201308ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 8);
	}

	@Test
	public void test201309ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 9);
	}

	@Test
	public void test201310ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 10);
	}

	@Test
	public void test201311ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 11);
	}

	@Test
	public void test201312ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2013, 12);
	}

	@Test
	public void test201401ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 1);
	}

	@Test
	public void test201402ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 2);
	}

	@Test
	public void test201403ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 3);
	}

	@Test
	public void test201404ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 4);
	}

	@Test
	public void test201405ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 5);
	}

	@Test
	public void test201406ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 6);
	}

	@Test
	public void test201407ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 7);
	}

	@Test
	public void test201408ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 8);
	}

	@Test
	public void test201409ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 9);
	}

	@Test
	public void test201410ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 10);
	}

	@Test
	public void test201411ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 11);
	}

	@Test
	public void test201412ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2014, 12);
	}

	@Test
	public void test201501ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 1);
	}

	@Test
	public void test201502ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 2);
	}

	@Test
	public void test201503ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 3);
	}

	@Test
	public void test201504ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 4);
	}

	@Test
	public void test201505ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 5);
	}

	@Test
	public void test201506ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 6);
	}

	@Test
	public void test201507ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 7);
	}

	@Test
	public void test201508ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 8);
	}

	@Test
	public void test201509ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 9);
	}

	@Test
	public void test201510ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 10);
	}

	@Test
	public void test201511ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 11);
	}

	@Test
	public void test201512ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2015, 12);
	}

	@Test
	public void test201601ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 1);
	}

	@Test
	public void test201602ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 2);
	}

	@Test
	public void test201603ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 3);
	}

	@Test
	public void test201604ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 4);
	}

	@Test
	public void test201605ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 5);
	}

	@Test
	public void test201606ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 6);
	}

	@Test
	public void test201607ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 7);
	}

	@Test
	public void test201608ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 8);
	}

	@Test
	public void test201609ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 9);
	}

	@Test
	public void test201610ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 10);
	}

	@Test
	public void test201611ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 11);
	}

	@Test
	public void test201612ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2016, 12);
	}

	@Test
	public void test201701ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 1);
	}

	@Test
	public void test201702ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 2);
	}

	@Test
	public void test201703ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 3);
	}

	@Test
	public void test201704ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 4);
	}

	@Test
	public void test201705ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 5);
	}

	@Test
	public void test201706ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 6);
	}

	@Test
	public void test201707ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 7);
	}

	@Test
	public void test201708ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 8);
	}

	@Test
	public void test201709ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 9);
	}

	@Test
	public void test201710ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 10);
	}

	@Test
	public void test201711ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 11);
	}

	@Test
	public void test201712ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 12);
	}

	@Test
	public void test201801ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 1);
	}

	@Test
	public void test201802ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 2);
	}

	@Test
	public void test201803ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 3);
	}

	@Test
	public void test201804ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 4);
	}

	@Test
	public void test201805ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 5);
	}

	@Test
	public void test201806ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 6);
	}

	@Test
	public void test201807ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 7);
	}

	@Test
	public void test201808ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 8);
	}

	@Test
	public void test201809ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 9);
	}

	@Test
	public void test201810ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 10);
	}

	@Test
	public void test201811ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 11);
	}

	@Test
	public void test201812ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 12);
	}

	@Test
	public void test201901ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 1);
	}

	@Test
	public void test201902ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 2);
	}

	@Test
	public void test201903ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 3);
	}

	@Test
	public void test201904ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2019, 4);
	}

	private void testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(int year, int month) {

		LocalDate expiryDate = LocalDate.of(year, month, 1).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		DateRange historicalDateRange = new DateRange(null, expiryDate.minusWeeks(1));
		DateRange futureDateRange = new DateRange(expiryDate.minusWeeks(1).plusDays(1), expiryDate);

		testExpiryDateIsAtWhichStandardDeviationBasedOnDataPrior(historicalDateRange, futureDateRange, standardDeviationMultipleOfIndex);
	}

	@Override
	public CsvHistoricalDataVolatilityCalculator getUnit() {
		return unit;
	}
}
