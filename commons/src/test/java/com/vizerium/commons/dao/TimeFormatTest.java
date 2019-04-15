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

package com.vizerium.commons.dao;

import org.junit.Assert;
import org.junit.Test;

import com.vizerium.commons.dao.TimeFormat;

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