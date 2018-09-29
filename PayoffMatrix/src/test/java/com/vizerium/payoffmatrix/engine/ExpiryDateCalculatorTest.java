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

package com.vizerium.payoffmatrix.engine;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import com.vizerium.payoffmatrix.engine.ExpiryDateCalculator;

public class ExpiryDateCalculatorTest {

	@Test
	public void testMonthExpiry() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "NEAR", LocalDate.of(2018, 6, 5)), LocalDate.of(2018, 6, 28));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "MID", LocalDate.of(2018, 6, 5)), LocalDate.of(2018, 7, 26));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "FAR", LocalDate.of(2018, 6, 5)), LocalDate.of(2018, 8, 30));
	}

	@Test
	public void testWeekExpiry() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "NEAR", LocalDate.of(2018, 4, 11)), LocalDate.of(2018, 4, 12));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "MID", LocalDate.of(2018, 4, 11)), LocalDate.of(2018, 4, 19));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "FAR", LocalDate.of(2018, 4, 11)), LocalDate.of(2018, 4, 26));
	}

	@Test
	public void testMonthExpiryForMondays() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "NEAR", LocalDate.of(2018, 2, 5)), LocalDate.of(2018, 2, 22));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "MID", LocalDate.of(2018, 2, 5)), LocalDate.of(2018, 3, 29));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "FAR", LocalDate.of(2018, 2, 5)), LocalDate.of(2018, 4, 26));
	}

	@Test
	public void testWeekExpiryForMondays() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "NEAR", LocalDate.of(2018, 3, 12)), LocalDate.of(2018, 3, 15));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "MID", LocalDate.of(2018, 3, 12)), LocalDate.of(2018, 3, 22));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "FAR", LocalDate.of(2018, 3, 12)), LocalDate.of(2018, 3, 29));
	}

	@Test
	public void testMonthExpiryForThursdays() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "NEAR", LocalDate.of(2018, 1, 11)), LocalDate.of(2018, 1, 25));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "MID", LocalDate.of(2018, 1, 11)), LocalDate.of(2018, 2, 22));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "FAR", LocalDate.of(2018, 1, 11)), LocalDate.of(2018, 3, 29));
	}

	@Test
	public void testWeekExpiryForThursdays() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "NEAR", LocalDate.of(2018, 7, 5)), LocalDate.of(2018, 7, 5));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "MID", LocalDate.of(2018, 7, 5)), LocalDate.of(2018, 7, 12));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "FAR", LocalDate.of(2018, 7, 5)), LocalDate.of(2018, 7, 19));
	}

	@Test
	public void testMonthExpiryForFridays() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "NEAR", LocalDate.of(2018, 9, 14)), LocalDate.of(2018, 9, 27));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "MID", LocalDate.of(2018, 9, 14)), LocalDate.of(2018, 10, 25));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "FAR", LocalDate.of(2018, 9, 14)), LocalDate.of(2018, 11, 29));
	}

	@Test
	public void testWeekExpiryForFridays() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "NEAR", LocalDate.of(2018, 10, 19)), LocalDate.of(2018, 10, 25));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "MID", LocalDate.of(2018, 10, 19)), LocalDate.of(2018, 11, 1));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "FAR", LocalDate.of(2018, 10, 19)), LocalDate.of(2018, 11, 8));
	}

	@Test
	public void testMonthExpiryForLastMondaysOfMonth() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "NEAR", LocalDate.of(2018, 1, 29)), LocalDate.of(2018, 2, 22));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "MID", LocalDate.of(2018, 1, 29)), LocalDate.of(2018, 3, 29));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "FAR", LocalDate.of(2018, 1, 29)), LocalDate.of(2018, 4, 26));
	}

	@Test
	public void testWeekExpiryForLastMondaysOfMonth() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "NEAR", LocalDate.of(2018, 10, 29)), LocalDate.of(2018, 11, 1));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "MID", LocalDate.of(2018, 10, 29)), LocalDate.of(2018, 11, 8));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "FAR", LocalDate.of(2018, 10, 29)), LocalDate.of(2018, 11, 15));
	}

	@Test
	public void testMonthExpiryForLastThursdaysOfMonth() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "NEAR", LocalDate.of(2018, 7, 26)), LocalDate.of(2018, 7, 26));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "MID", LocalDate.of(2018, 7, 26)), LocalDate.of(2018, 8, 30));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "FAR", LocalDate.of(2018, 7, 26)), LocalDate.of(2018, 9, 27));
	}

	@Test
	public void testWeekExpiryForLastThursdaysOfMonth() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "NEAR", LocalDate.of(2018, 11, 29)), LocalDate.of(2018, 11, 29));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "MID", LocalDate.of(2018, 11, 29)), LocalDate.of(2018, 12, 6));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "FAR", LocalDate.of(2018, 11, 29)), LocalDate.of(2018, 12, 13));
	}

	@Test
	public void testMonthExpiryForLastFridaysOfMonth() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "NEAR", LocalDate.of(2018, 5, 25)), LocalDate.of(2018, 5, 31));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "MID", LocalDate.of(2018, 5, 25)), LocalDate.of(2018, 6, 28));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "FAR", LocalDate.of(2018, 5, 25)), LocalDate.of(2018, 7, 26));
	}

	@Test
	public void testWeekExpiryForLastFridaysOfMonth() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "NEAR", LocalDate.of(2018, 4, 27)), LocalDate.of(2018, 5, 3));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "MID", LocalDate.of(2018, 4, 27)), LocalDate.of(2018, 5, 10));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "FAR", LocalDate.of(2018, 4, 27)), LocalDate.of(2018, 5, 17));
	}

	@Test
	public void testMonthExpiryForLastMondaysOfYear() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "NEAR", LocalDate.of(2018, 12, 31)), LocalDate.of(2019, 1, 31));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "MID", LocalDate.of(2018, 12, 31)), LocalDate.of(2019, 2, 28));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "FAR", LocalDate.of(2018, 12, 31)), LocalDate.of(2019, 3, 28));
	}

	@Test
	public void testWeekExpiryForLastMondaysOfYear() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "NEAR", LocalDate.of(2018, 12, 31)), LocalDate.of(2019, 1, 3));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "MID", LocalDate.of(2018, 12, 31)), LocalDate.of(2019, 1, 10));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "FAR", LocalDate.of(2018, 12, 31)), LocalDate.of(2019, 1, 17));
	}

	@Test
	public void testMonthExpiryForLastThursdaysOfYear() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "NEAR", LocalDate.of(2018, 12, 27)), LocalDate.of(2018, 12, 27));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "MID", LocalDate.of(2018, 12, 27)), LocalDate.of(2019, 1, 31));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "FAR", LocalDate.of(2018, 12, 27)), LocalDate.of(2019, 2, 28));
	}

	@Test
	public void testWeekExpiryForLastThursdaysOfYear() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "NEAR", LocalDate.of(2018, 12, 27)), LocalDate.of(2018, 12, 27));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "MID", LocalDate.of(2018, 12, 27)), LocalDate.of(2019, 1, 3));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "FAR", LocalDate.of(2018, 12, 27)), LocalDate.of(2019, 1, 10));
	}

	@Test
	public void testMonthExpiryForLastFridaysOfYear() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "NEAR", LocalDate.of(2018, 12, 28)), LocalDate.of(2019, 1, 31));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "MID", LocalDate.of(2018, 12, 28)), LocalDate.of(2019, 2, 28));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("MONTH", "FAR", LocalDate.of(2018, 12, 28)), LocalDate.of(2019, 3, 28));
	}

	@Test
	public void testWeekExpiryForLastFridaysOfYear() {
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "NEAR", LocalDate.of(2018, 12, 28)), LocalDate.of(2019, 1, 3));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "MID", LocalDate.of(2018, 12, 28)), LocalDate.of(2019, 1, 10));
		Assert.assertEquals(ExpiryDateCalculator.convertToExpiryDate("WEEK", "FAR", LocalDate.of(2018, 12, 28)), LocalDate.of(2019, 1, 17));
	}
}
