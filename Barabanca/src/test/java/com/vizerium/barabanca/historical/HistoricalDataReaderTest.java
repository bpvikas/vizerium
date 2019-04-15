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

package com.vizerium.barabanca.historical;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HistoricalDataReaderTest {

	private HistoricalDataReader unit;

	@Before
	public void setUp() {
		unit = new HistoricalDataReader();
	}

	@Test
	public void test01_GetUnitPriceDataForRangeForWeekends() {
		List<UnitPriceData> weekendUnitPriceData = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2019, 1, 1, 6, 0).with(DayOfWeek.SATURDAY),
				LocalDateTime.of(2019, 1, 1, 6, 0).with(DayOfWeek.SUNDAY), TimeFormat._1DAY);
		Assert.assertEquals(0, weekendUnitPriceData.size());
	}

	@Test
	public void test11_getPrevious1min() {
		List<UnitPriceData> previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._1MIN, LocalDateTime.of(2019, 1, 1, 6, 0), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2018, 12, 31, 15, 32), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._1MIN, LocalDateTime.of(2019, 1, 1, 12, 31), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 12, 30), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._1MIN, LocalDateTime.of(2019, 1, 1, 12, 33), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 12, 32), previousUnitPrice.get(0).getDateTime());
	}

	@Test
	public void test12_getPrevious5min() {
		List<UnitPriceData> previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._5MIN, LocalDateTime.of(2019, 1, 1, 6, 0), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2018, 12, 31, 15, 31), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._5MIN, LocalDateTime.of(2019, 1, 1, 12, 31), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 12, 26), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._5MIN, LocalDateTime.of(2019, 1, 1, 12, 33), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 12, 26), previousUnitPrice.get(0).getDateTime());
	}

	@Test
	public void test13_getPreviousHour() {
		List<UnitPriceData> previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._1HOUR, LocalDateTime.of(2019, 1, 1, 6, 0), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2018, 12, 31, 15, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._1HOUR, LocalDateTime.of(2019, 1, 1, 10, 16), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 9, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._1HOUR, LocalDateTime.of(2019, 1, 1, 10, 30), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 9, 16), previousUnitPrice.get(0).getDateTime());
	}

	@Test
	public void test14_getPreviousDay() {
		List<UnitPriceData> previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._1DAY, LocalDateTime.of(2019, 1, 1, 6, 0), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2018, 12, 31, 9, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._1DAY, LocalDateTime.of(2019, 1, 1, 9, 16), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2018, 12, 31, 9, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._1DAY, LocalDateTime.of(2019, 1, 1, 10, 20), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2018, 12, 31, 9, 16), previousUnitPrice.get(0).getDateTime());
	}

	@Test
	public void test15_getPreviousWeek() {
		List<UnitPriceData> previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._1WEEK, LocalDateTime.of(2019, 1, 1, 6, 0), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2018, 12, 24, 9, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._1WEEK, LocalDateTime.of(2018, 10, 15, 9, 16), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2018, 10, 8, 9, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._1WEEK, LocalDateTime.of(2018, 10, 17, 9, 16), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2018, 10, 8, 9, 16), previousUnitPrice.get(0).getDateTime());
	}

	@Test
	public void test16_getPreviousMonth() {
		List<UnitPriceData> previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._1MONTH, LocalDateTime.of(2019, 1, 1, 6, 0), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2018, 12, 3, 9, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._1MONTH, LocalDateTime.of(2019, 1, 1, 9, 16), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2018, 12, 3, 9, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getPreviousN("BANKNIFTY", TimeFormat._1MONTH, LocalDateTime.of(2019, 1, 5, 6, 0), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2018, 12, 3, 9, 16), previousUnitPrice.get(0).getDateTime());
	}

	@Test
	public void test21_getNext1min() {
		List<UnitPriceData> previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._1MIN, LocalDateTime.of(2018, 12, 31, 21, 0), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 9, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._1MIN, LocalDateTime.of(2019, 1, 1, 12, 31), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 12, 32), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._1MIN, LocalDateTime.of(2019, 1, 1, 12, 33), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 12, 34), previousUnitPrice.get(0).getDateTime());
	}

	@Test
	public void test22_getNext5min() {
		List<UnitPriceData> previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._5MIN, LocalDateTime.of(2018, 12, 31, 21, 0), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 9, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._5MIN, LocalDateTime.of(2019, 1, 1, 12, 31), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 12, 36), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._5MIN, LocalDateTime.of(2019, 1, 1, 12, 33), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 12, 36), previousUnitPrice.get(0).getDateTime());
	}

	@Test
	public void test23_getNextHour() {
		List<UnitPriceData> previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._1HOUR, LocalDateTime.of(2018, 12, 31, 21, 0), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 9, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._1HOUR, LocalDateTime.of(2019, 1, 1, 10, 16), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 11, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._1HOUR, LocalDateTime.of(2019, 1, 1, 10, 30), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 11, 16), previousUnitPrice.get(0).getDateTime());
	}

	@Test
	public void test24_getNextDay() {
		List<UnitPriceData> previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._1DAY, LocalDateTime.of(2018, 12, 31, 21, 0), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 9, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._1DAY, LocalDateTime.of(2019, 1, 1, 9, 16), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 2, 9, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._1DAY, LocalDateTime.of(2019, 1, 1, 10, 20), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 2, 9, 16), previousUnitPrice.get(0).getDateTime());
	}

	@Test
	public void test25_getNextWeek() {
		List<UnitPriceData> previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._1WEEK, LocalDateTime.of(2018, 12, 31, 21, 0), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 7, 9, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._1WEEK, LocalDateTime.of(2018, 10, 15, 9, 16), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2018, 10, 22, 9, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._1WEEK, LocalDateTime.of(2018, 10, 17, 9, 16), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2018, 10, 22, 9, 16), previousUnitPrice.get(0).getDateTime());
	}

	@Test
	public void test26_getNextMonth() {
		List<UnitPriceData> previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._1MONTH, LocalDateTime.of(2018, 12, 31, 21, 0), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 9, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._1MONTH, LocalDateTime.of(2019, 1, 1, 9, 16), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 2, 1, 9, 16), previousUnitPrice.get(0).getDateTime());

		previousUnitPrice = unit.getNextN("BANKNIFTY", TimeFormat._1MONTH, LocalDateTime.of(2019, 1, 5, 6, 0), 1);
		Assert.assertEquals(previousUnitPrice.size(), 1);
		Assert.assertEquals(LocalDateTime.of(2019, 2, 1, 9, 16), previousUnitPrice.get(0).getDateTime());
	}

	@Test
	public void test31_isLastCandle1min() {
		List<UnitPriceData> unitPrice = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2019, 1, 1, 14, 15), LocalDateTime.of(2019, 1, 1, 14, 15), TimeFormat._1MIN);

		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), unitPrice.get(0).getTimeFormat()));
		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._5MIN));
		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1HOUR));
		Assert.assertFalse(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1DAY));

		unitPrice = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2019, 1, 1, 15, 25), LocalDateTime.of(2019, 1, 1, 15, 25), TimeFormat._1MIN);

		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), unitPrice.get(0).getTimeFormat()));
		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._5MIN));
		Assert.assertFalse(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1HOUR));
		Assert.assertFalse(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1DAY));

		unitPrice = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2019, 1, 1, 15, 27), LocalDateTime.of(2019, 1, 1, 15, 27), TimeFormat._1MIN);

		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), unitPrice.get(0).getTimeFormat()));
		Assert.assertFalse(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._5MIN));
		Assert.assertFalse(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1HOUR));
		Assert.assertFalse(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1DAY));

		unitPrice = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2019, 1, 1, 15, 32), LocalDateTime.of(2019, 1, 1, 15, 32), TimeFormat._1MIN);

		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), unitPrice.get(0).getTimeFormat()));
		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._5MIN));
		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1HOUR));
		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1DAY));
	}

	@Test
	public void test32_isLastCandle5min() {
		List<UnitPriceData> unitPrice = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2019, 1, 9, 13, 11), LocalDateTime.of(2019, 1, 9, 13, 11), TimeFormat._5MIN);

		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), unitPrice.get(0).getTimeFormat()));
		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1HOUR));
		Assert.assertFalse(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1DAY));

		unitPrice = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2019, 1, 9, 14, 36), LocalDateTime.of(2019, 1, 9, 14, 36), TimeFormat._5MIN);

		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), unitPrice.get(0).getTimeFormat()));
		Assert.assertFalse(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1HOUR));
		Assert.assertFalse(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1DAY));

		unitPrice = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2019, 1, 9, 15, 31), LocalDateTime.of(2019, 1, 9, 15, 31), TimeFormat._5MIN);

		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), unitPrice.get(0).getTimeFormat()));
		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1HOUR));
		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1DAY));
	}

	@Test
	public void test33_isLastCandleHour() {
		List<UnitPriceData> unitPrice = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2019, 1, 23, 12, 16), LocalDateTime.of(2019, 1, 23, 12, 16), TimeFormat._1HOUR);

		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), unitPrice.get(0).getTimeFormat()));
		Assert.assertFalse(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1DAY));

		unitPrice = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2019, 1, 23, 15, 16), LocalDateTime.of(2019, 1, 23, 15, 16), TimeFormat._1HOUR);

		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), unitPrice.get(0).getTimeFormat()));
		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1DAY));
	}

	@Test
	public void test34_isLastCandleDay() {
		List<UnitPriceData> unitPrice = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2019, 1, 22, 9, 16), LocalDateTime.of(2019, 1, 22, 9, 16), TimeFormat._1DAY);

		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), unitPrice.get(0).getTimeFormat()));
		Assert.assertFalse(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1WEEK));
		Assert.assertFalse(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1MONTH));

		unitPrice = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2019, 1, 25, 9, 16), LocalDateTime.of(2019, 1, 25, 9, 16), TimeFormat._1DAY);

		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), unitPrice.get(0).getTimeFormat()));
		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1WEEK));
		Assert.assertFalse(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1MONTH));

		unitPrice = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2019, 1, 31, 9, 16), LocalDateTime.of(2019, 1, 31, 9, 16), TimeFormat._1DAY);

		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), unitPrice.get(0).getTimeFormat()));
		Assert.assertFalse(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1WEEK));
		Assert.assertTrue(unit.isLastCandleOfTimePeriod(unitPrice.get(0), TimeFormat._1MONTH));
	}
}