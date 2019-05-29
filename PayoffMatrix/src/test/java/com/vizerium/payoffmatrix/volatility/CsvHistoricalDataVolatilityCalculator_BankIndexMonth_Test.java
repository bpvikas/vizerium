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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CsvHistoricalDataVolatilityCalculator_BankIndexMonth_Test extends CsvHistoricalDataVolatilityCalculatorTest {

	private CsvHistoricalDataVolatilityCalculator unit;

	private float standardDeviationMultipleOfIndex = 1.801f;

	@Before
	public void setup() {
		unit = new CsvHistoricalDataVolatilityCalculator("BANKINDEX");
	}

	@Test
	public void testCalculateVolatility() {
		unit.calculateVolatility(null);
	}

	@Test
	public void test201202ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2012, 1);
	}

	@Test
	public void test201203ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2012, 2);
	}

	@Test
	public void test201204ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2012, 3);
	}

	@Test
	public void test201205ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2012, 4);
	}

	@Test
	public void test201206ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2012, 5);
	}

	@Test
	public void test201207ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2012, 6);
	}

	@Test
	public void test201208ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2012, 7);
	}

	@Test
	public void test201209ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2012, 8);
	}

	@Test
	public void test201210ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2012, 9);
	}

	@Test
	public void test201211ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2012, 10);
	}

	@Test
	public void test201212ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2012, 11);
	}

	@Test
	public void test201301ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2012, 12);
	}

	@Test
	public void test201302ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2013, 1);
	}

	@Test
	public void test201303ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2013, 2);
	}

	@Test
	public void test201304ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2013, 3);
	}

	@Test
	public void test201305ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2013, 4);
	}

	@Test
	public void test201306ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2013, 5);
	}

	@Test
	public void test201307ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2013, 6);
	}

	@Test
	public void test201308ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2013, 7);
	}

	@Test
	public void test201309ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2013, 8);
	}

	@Test
	public void test201310ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2013, 9);
	}

	@Test
	public void test201311ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2013, 10);
	}

	@Test
	public void test201312ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2013, 11);
	}

	@Test
	public void test201401ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2013, 12);
	}

	@Test
	public void test201402ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2014, 1);
	}

	@Test
	public void test201403ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2014, 2);
	}

	@Test
	public void test201404ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2014, 3);
	}

	@Test
	public void test201405ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2014, 4);
	}

	@Test
	public void test201406ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2014, 5);
	}

	@Test
	public void test201407ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2014, 6);
	}

	@Test
	public void test201408ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2014, 7);
	}

	@Test
	public void test201409ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2014, 8);
	}

	@Test
	public void test201410ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2014, 9);
	}

	@Test
	public void test201411ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2014, 10);
	}

	@Test
	public void test201412ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2014, 11);
	}

	@Test
	public void test201501ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2014, 12);
	}

	@Test
	public void test201502ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 1);
	}

	@Test
	public void test201503ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 2);
	}

	@Test
	public void test201504ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 3);
	}

	@Test
	public void test201505ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 4);
	}

	@Test
	public void test201506ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 5);
	}

	@Test
	public void test201507ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 6);
	}

	@Test
	public void test201508ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 7);
	}

	@Test
	public void test201509ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 8);
	}

	@Test
	public void test201510ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 9);
	}

	@Test
	public void test201511ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 10);
	}

	@Test
	public void test201512ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 11);
	}

	@Test
	public void test201601ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 12);
	}

	@Test
	public void test201602ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 1);
	}

	@Test
	public void test201603ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 2);
	}

	@Test
	public void test201604ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 3);
	}

	@Test
	public void test201605ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 4);
	}

	@Test
	public void test201606ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 5);
	}

	@Test
	public void test201607ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 6);
	}

	@Test
	public void test201608ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 7);
	}

	@Test
	public void test201609ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 8);
	}

	@Test
	public void test201610ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 9);
	}

	@Test
	public void test201611ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 10);
	}

	@Test
	public void test201612ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 11);
	}

	@Test
	public void test201701ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 12);
	}

	@Test
	public void test201702ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 1);
	}

	@Test
	public void test201703ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 2);
	}

	@Test
	public void test201704ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 3);
	}

	@Test
	public void test201705ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 4);
	}

	@Test
	public void test201706ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 5);
	}

	@Test
	public void test201707ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 6);
	}

	@Test
	public void test201708ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 7);
	}

	@Test
	public void test201709ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 8);
	}

	@Test
	public void test201710ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 9);
	}

	@Test
	public void test201711ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 10);
	}

	@Test
	public void test201712ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 11);
	}

	@Test
	public void test201801ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 12);
	}

	@Test
	public void test201802ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 1);
	}

	@Test
	public void test201803ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 2);
	}

	@Test
	public void test201804ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 3);
	}

	@Test
	public void test201805ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 4);
	}

	@Test
	public void test201806ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 5);
	}

	@Test
	public void test201807ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 6);
	}

	@Test
	public void test201808ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 7);
	}

	@Test
	public void test201809ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 8);
	}

	@Test
	public void test201810ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 9);
	}

	@Test
	public void test201811ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 10);
	}

	@Test
	public void test201812ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 11);
	}

	@Test
	public void test201901ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 12);
	}

	@Test
	public void test201902ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2019, 1);
	}

	@Test
	public void test201903ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2019, 2);
	}

	@Test
	public void test201904ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2019, 3);
	}

	@Test
	public void test201905ExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2019, 4);
	}

	private void testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(int year, int month) {

		LocalDate expiryDatePreviousMonth = LocalDate.of(year, month, 1).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		DateRange historicalDateRange = new DateRange(null, expiryDatePreviousMonth);
		DateRange futureDateRange = new DateRange(expiryDatePreviousMonth.plusDays(1),
				LocalDate.of(year, month, 1).plusMonths(1).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)));

		testExpiryDateIsAtWhichStandardDeviationBasedOnDataPrior(historicalDateRange, futureDateRange, standardDeviationMultipleOfIndex);
	}

	@Override
	public CsvHistoricalDataVolatilityCalculator getUnit() {
		return unit;
	}
}
