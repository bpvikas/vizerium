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
	public void test11_GetUnitPriceDataForRangeForWeekends() {
		List<UnitPriceData> weekendUnitPriceData = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2019, 1, 1, 6, 0).with(DayOfWeek.SATURDAY),
				LocalDateTime.of(2019, 1, 1, 6, 0).with(DayOfWeek.SUNDAY), TimeFormat._1DAY);

		Assert.assertEquals(0, weekendUnitPriceData.size());
	}
}
