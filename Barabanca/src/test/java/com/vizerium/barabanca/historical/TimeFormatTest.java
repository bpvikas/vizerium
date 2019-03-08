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

	@Test
	public void testGetLowerTimeFormat() {
		Assert.assertEquals(null, TimeFormat._1MIN.getLowerTimeFormat());
		Assert.assertEquals(TimeFormat._1MIN, TimeFormat._5MIN.getLowerTimeFormat());
		Assert.assertEquals(TimeFormat._5MIN, TimeFormat._1HOUR.getLowerTimeFormat());
		Assert.assertEquals(TimeFormat._1HOUR, TimeFormat._1DAY.getLowerTimeFormat());
		Assert.assertEquals(TimeFormat._1DAY, TimeFormat._1WEEK.getLowerTimeFormat());
		Assert.assertEquals(TimeFormat._1WEEK, TimeFormat._1MONTH.getLowerTimeFormat());
	}

}