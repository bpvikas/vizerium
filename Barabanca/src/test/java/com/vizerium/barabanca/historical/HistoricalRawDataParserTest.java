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

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.commons.dao.TimeFormat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HistoricalRawDataParserTest {

	private HistoricalRawDataParser unit;

	@Before
	public void setUp() {
		unit = new HistoricalRawDataParser();
	}

	@Test
	public void test01_ExtractRawData() {
		unit.extractRawData();
	}

	@Test
	public void test02_ValidateData() {
		unit.validateData();

		// 2010-02-06 11:01 12:30
		// 2011-12-27 09:07 15:30
		// 2012-01-07 11:08 12:47
		// 2012-03-03 11:08 12:47
		// 2012-04-28 11:08 12:48
		// 2012-09-08 11:08 12:48
		// 2013-05-11 11:08 12:50
		// 2014-03-22 11:08 12:47
		// 2014-10-23 18:23 19:30
		// 2016-10-30 18:23 19:32
		// 2017-10-19 18:23 19:32
		// 2018-11-07 17:23 18:32
		// **********************************
		// 2009-05-18 09:55 11:55
		// 2010-02-06 11:01 12:30
		// 2010-09-20 09:01 15:07
		// 2012-01-07 11:08 12:47
		// 2012-03-03 11:08 12:47
		// 2012-04-28 11:08 12:48
		// 2012-09-08 11:08 12:48
		// 2013-05-11 11:08 12:50
		// 2014-03-22 11:08 12:47
		// 2014-10-23 18:23 19:30
		// 2016-10-30 18:23 19:32
		// 2017-10-19 18:23 19:32
		// 2018-11-07 17:23 18:32
		// **********************************
		// No of unique first four timings : 11
		// 09:01,09:02,09:03,09:04
		// 09:07,09:15,09:16,09:17
		// 09:08,09:16,09:17,09:18
		// 09:09,09:16,09:17,09:18
		// 09:16,09:17,09:18,09:19
		// 09:55,09:56,09:57,09:58
		// 09:56,09:57,09:58,09:59
		// 11:01,11:02,11:03,11:04
		// 11:08,11:16,11:17,11:18
		// 17:23,17:31,17:32,17:33
		// 18:23,18:31,18:32,18:33

	}

	@Test
	public void test03_AllTimeFormatsFileCreation() {
		test03A_1MinFileCreation();
		test03B_5MinFileCreation();
		test03C_15MinFileCreation();
		test03D_1HourFileCreation();
		test03E_1DayFileCreation();
		test03F_1WeekFileCreation();
		test03G_1MonthFileCreation();
	}

	private void test03A_1MinFileCreation() {
		unit.createTimeSeriesDataFiles(TimeFormat._1MIN);
	}

	private void test03B_5MinFileCreation() {
		unit.createTimeSeriesDataFiles(TimeFormat._5MIN);
	}

	private void test03C_15MinFileCreation() {
		unit.createTimeSeriesDataFiles(TimeFormat._15MIN);
	}

	private void test03D_1HourFileCreation() {
		unit.createTimeSeriesDataFiles(TimeFormat._1HOUR);
	}

	private void test03E_1DayFileCreation() {
		unit.createTimeSeriesDataFiles(TimeFormat._1DAY);
	}

	private void test03F_1WeekFileCreation() {
		unit.createMultiDayTimeSeriesDataFiles(TimeFormat._1WEEK);
	}

	private void test03G_1MonthFileCreation() {
		unit.createMultiDayTimeSeriesDataFiles(TimeFormat._1MONTH);
	}

	@Test
	public void test04_UpdateMovingAveragesInAllTimeFormatsDataFiles() {
		test04A_UpdateMovingAveragesIn1MinDataFiles();
		test04B_UpdateMovingAveragesIn5MinDataFiles();
		test04C_UpdateMovingAveragesIn15MinDataFiles();
		test04D_UpdateMovingAveragesIn1HourDataFiles();
		test04E_UpdateMovingAveragesIn1DayDataFiles();
		test04F_UpdateMovingAveragesIn1WeekDataFiles();
		test04G_UpdateMovingAveragesIn1MonthDataFiles();
	}

	private void test04A_UpdateMovingAveragesIn1MinDataFiles() {
		unit.updateStandardMovingAveragesInTimeSeriesDataFiles(TimeFormat._1MIN);
	}

	private void test04B_UpdateMovingAveragesIn5MinDataFiles() {
		unit.updateStandardMovingAveragesInTimeSeriesDataFiles(TimeFormat._5MIN);
	}

	private void test04C_UpdateMovingAveragesIn15MinDataFiles() {
		unit.updateStandardMovingAveragesInTimeSeriesDataFiles(TimeFormat._15MIN);
	}

	private void test04D_UpdateMovingAveragesIn1HourDataFiles() {
		unit.updateStandardMovingAveragesInTimeSeriesDataFiles(TimeFormat._1HOUR);
	}

	private void test04E_UpdateMovingAveragesIn1DayDataFiles() {
		unit.updateStandardMovingAveragesInTimeSeriesDataFiles(TimeFormat._1DAY);
	}

	private void test04F_UpdateMovingAveragesIn1WeekDataFiles() {
		unit.updateStandardMovingAveragesInTimeSeriesDataFiles(TimeFormat._1WEEK);
	}

	private void test04G_UpdateMovingAveragesIn1MonthDataFiles() {
		unit.updateStandardMovingAveragesInTimeSeriesDataFiles(TimeFormat._1MONTH);
	}
}
