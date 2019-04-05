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
}
