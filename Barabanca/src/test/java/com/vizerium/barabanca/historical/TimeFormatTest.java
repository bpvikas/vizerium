package com.vizerium.barabanca.historical;

import org.junit.Assert;
import org.junit.Test;

public class TimeFormatTest {

	@Test
	public void testGetHigherTimeFormat() {
		Assert.assertEquals(TimeFormat._5MIN, TimeFormat._1MIN.getHigherTimeFormat());
		Assert.assertEquals(TimeFormat._1HOUR, TimeFormat._5MIN.getHigherTimeFormat());
		Assert.assertEquals(TimeFormat._1DAY, TimeFormat._1HOUR.getHigherTimeFormat());
		Assert.assertEquals(TimeFormat._1WEEK, TimeFormat._1DAY.getHigherTimeFormat());
		Assert.assertEquals(TimeFormat._1MONTH, TimeFormat._1WEEK.getHigherTimeFormat());
		Assert.assertEquals(null, TimeFormat._1MONTH.getHigherTimeFormat());
	}
}