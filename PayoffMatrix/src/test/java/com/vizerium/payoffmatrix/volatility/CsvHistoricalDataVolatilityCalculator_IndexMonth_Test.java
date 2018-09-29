/*
 * Copyright 2018 Vizerium, Inc.
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
public class CsvHistoricalDataVolatilityCalculator_IndexMonth_Test extends CsvHistoricalDataVolatilityCalculatorTest {

	private CsvHistoricalDataVolatilityCalculator unit;

	private float standardDeviationMultipleOfIndex = 1.416f;

	@Before
	public void setup() {
		unit = new CsvHistoricalDataVolatilityCalculator("INDEX");
	}

	@Test
	public void testCalculateVolatility() {
		unit.calculateVolatility(null);
	}

	@Test
	public void test201502ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201501() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 1);
	}

	@Test
	public void test201503ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201502() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 2);
	}

	@Test
	public void test201504ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201503() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 3);
	}

	@Test
	public void test201505ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201504() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 4);
	}

	@Test
	public void test201506ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201505() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 5);
	}

	@Test
	public void test201507ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201506() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 6);
	}

	@Test
	public void test201508ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201507() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 7);
	}

	@Test
	public void test201509ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201508() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 8);
	}

	@Test
	public void test201510ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201509() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 9);
	}

	@Test
	public void test201511ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201510() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 10);
	}

	@Test
	public void test201512ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201511() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 11);
	}

	@Test
	public void test201601ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201512() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2015, 12);
	}

	@Test
	public void test201602ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201601() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 1);
	}

	@Test
	public void test201603ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201602() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 2);
	}

	@Test
	public void test201604ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201603() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 3);
	}

	@Test
	public void test201605ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201604() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 4);
	}

	@Test
	public void test201606ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201605() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 5);
	}

	@Test
	public void test201607ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201606() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 6);
	}

	@Test
	public void test201608ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201607() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 7);
	}

	@Test
	public void test201609ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201608() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 8);
	}

	@Test
	public void test201610ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201609() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 9);
	}

	@Test
	public void test201611ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201610() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 10);
	}

	@Test
	public void test201612ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201611() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 11);
	}

	@Test
	public void test201701ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201612() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2016, 12);
	}

	@Test
	public void test201702ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201701() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 1);
	}

	@Test
	public void test201703ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201702() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 2);
	}

	@Test
	public void test201704ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201703() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 3);
	}

	@Test
	public void test201705ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201704() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 4);
	}

	@Test
	public void test201706ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201705() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 5);
	}

	@Test
	public void test201707ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201706() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 6);
	}

	@Test
	public void test201708ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201707() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 7);
	}

	@Test
	public void test201709ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201708() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 8);
	}

	@Test
	public void test201710ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201709() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 9);
	}

	@Test
	public void test201711ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201710() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 10);
	}

	@Test
	public void test201712ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201711() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 11);
	}

	@Test
	public void test201801ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201712() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2017, 12);
	}

	@Test
	public void test201802ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201801() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 1);
	}

	@Test
	public void test201803ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201802() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 2);
	}

	@Test
	public void test201804ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201803() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 3);
	}

	@Test
	public void test201805ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201804() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 4);
	}

	@Test
	public void test201806ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill201805() {
		testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(2018, 5);
	}

	private void testNextMonthExpiryDateIsAtWhichStandardDeviationBasedOnDataTillPreviousMonth(int year, int month) {

		LocalDate expiryDatePreviousMonth = LocalDate.of(year, month, 1).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		DateRange historicalDateRange = new DateRange(null, expiryDatePreviousMonth);
		DateRange futureDateRange = new DateRange(expiryDatePreviousMonth.plusDays(1), LocalDate.of(year, month, 1).plusMonths(1)
				.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY)));

		testExpiryDateIsAtWhichStandardDeviationBasedOnDataPrior(historicalDateRange, futureDateRange, standardDeviationMultipleOfIndex);
	}

	@Override
	public CsvHistoricalDataVolatilityCalculator getUnit() {
		return unit;
	}
}
