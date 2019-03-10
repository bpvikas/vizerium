package com.vizerium.barabanca.historical;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HistoricalDataDateRangeTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test01A_GetStartDateTimeForBankNifty() {
		String scripName = "BANKNIFTY";
		Assert.assertEquals(LocalDateTime.of(2010, 3, 2, 9, 1), HistoricalDataDateRange.getStartDateTime(scripName, TimeFormat._1MIN));
		Assert.assertEquals(LocalDateTime.of(2010, 3, 2, 9, 1), HistoricalDataDateRange.getStartDateTime(scripName, TimeFormat._5MIN));
		Assert.assertEquals(LocalDateTime.of(2010, 3, 2, 9, 1), HistoricalDataDateRange.getStartDateTime(scripName, TimeFormat._1HOUR));
		Assert.assertEquals(LocalDateTime.of(2010, 3, 2, 9, 1), HistoricalDataDateRange.getStartDateTime(scripName, TimeFormat._1DAY));
		Assert.assertEquals(LocalDateTime.of(2010, 3, 2, 9, 1), HistoricalDataDateRange.getStartDateTime(scripName, TimeFormat._1WEEK));
		Assert.assertEquals(LocalDateTime.of(2010, 3, 2, 9, 1), HistoricalDataDateRange.getStartDateTime(scripName, TimeFormat._1MONTH));
	}

	@Test
	public void test01B_GetEndDateTimeForBankNifty() {
		String scripName = "BANKNIFTY";
		Assert.assertEquals(LocalDateTime.of(2019, 2, 28, 15, 32), HistoricalDataDateRange.getEndDateTime(scripName, TimeFormat._1MIN));
		Assert.assertEquals(LocalDateTime.of(2019, 2, 28, 15, 31), HistoricalDataDateRange.getEndDateTime(scripName, TimeFormat._5MIN));
		Assert.assertEquals(LocalDateTime.of(2019, 2, 28, 15, 16), HistoricalDataDateRange.getEndDateTime(scripName, TimeFormat._1HOUR));
		Assert.assertEquals(LocalDateTime.of(2019, 2, 28, 9, 16), HistoricalDataDateRange.getEndDateTime(scripName, TimeFormat._1DAY));
		Assert.assertEquals(LocalDateTime.of(2019, 2, 25, 9, 16), HistoricalDataDateRange.getEndDateTime(scripName, TimeFormat._1WEEK));
		Assert.assertEquals(LocalDateTime.of(2019, 2, 1, 9, 16), HistoricalDataDateRange.getEndDateTime(scripName, TimeFormat._1MONTH));
	}

	@Test
	public void test02A_GetStartDateTimeForNifty() {
		String scripName = "NIFTY";
		Assert.assertEquals(LocalDateTime.of(2008, 1, 1, 9, 55), HistoricalDataDateRange.getStartDateTime(scripName, TimeFormat._1MIN));
		Assert.assertEquals(LocalDateTime.of(2008, 1, 1, 9, 55), HistoricalDataDateRange.getStartDateTime(scripName, TimeFormat._5MIN));
		Assert.assertEquals(LocalDateTime.of(2008, 1, 1, 9, 55), HistoricalDataDateRange.getStartDateTime(scripName, TimeFormat._1HOUR));
		Assert.assertEquals(LocalDateTime.of(2008, 1, 1, 9, 55), HistoricalDataDateRange.getStartDateTime(scripName, TimeFormat._1DAY));
		Assert.assertEquals(LocalDateTime.of(2008, 1, 1, 9, 55), HistoricalDataDateRange.getStartDateTime(scripName, TimeFormat._1WEEK));
		Assert.assertEquals(LocalDateTime.of(2008, 1, 1, 9, 55), HistoricalDataDateRange.getStartDateTime(scripName, TimeFormat._1MONTH));
	}

	@Test
	public void test02B_GetEndDateTimeForNifty() {
		String scripName = "NIFTY";
		Assert.assertEquals(LocalDateTime.of(2019, 2, 28, 15, 32), HistoricalDataDateRange.getEndDateTime(scripName, TimeFormat._1MIN));
		Assert.assertEquals(LocalDateTime.of(2019, 2, 28, 15, 31), HistoricalDataDateRange.getEndDateTime(scripName, TimeFormat._5MIN));
		Assert.assertEquals(LocalDateTime.of(2019, 2, 28, 15, 16), HistoricalDataDateRange.getEndDateTime(scripName, TimeFormat._1HOUR));
		Assert.assertEquals(LocalDateTime.of(2019, 2, 28, 9, 16), HistoricalDataDateRange.getEndDateTime(scripName, TimeFormat._1DAY));
		Assert.assertEquals(LocalDateTime.of(2019, 2, 25, 9, 16), HistoricalDataDateRange.getEndDateTime(scripName, TimeFormat._1WEEK));
		Assert.assertEquals(LocalDateTime.of(2019, 2, 1, 9, 16), HistoricalDataDateRange.getEndDateTime(scripName, TimeFormat._1MONTH));
	}
}
