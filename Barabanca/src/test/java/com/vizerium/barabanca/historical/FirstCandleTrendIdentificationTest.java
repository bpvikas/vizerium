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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.barabanca.trend.PeriodTrend;
import com.vizerium.barabanca.trend.Trend;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;

/*
 * The purpose of this JUnit test case is to identify how much influence does the first 1 min or 5 min candle have on the overall day.
 * e.g. How many times if the first 1min/5min candle is green, then the day candle will also be green and
 * similarly, how many times if the first 1min/5min candle is red, then the day candle will also be red.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FirstCandleTrendIdentificationTest {

	private HistoricalDataReader historicalDataReader;

	private static final Logger logger = Logger.getLogger(FirstCandleTrendIdentificationTest.class);

	@Before
	public void setUp() throws Exception {
		historicalDataReader = new HistoricalDataReader();
	}

	// @formatter:off
	/*
	 * Checks are
	 * 	-	unitPrice1Day.getOpen() > unitPrice1Day.getClose() * 1
	 *  - 	unitPrice1Day.getClose() > unitPrice1Day.getOpen() * 1
	 * 
	 * Identifying trend based on 1st candle for BANKNIFTY from 2010-04-01T06:00 to 2019-03-31T21:00 in 1min format.
	 * %age days where 1st candle trend matched the entire day trend : 58.25112%
	 * Identifying trend based on 1st candle for BANKNIFTY from 2010-04-01T06:00 to 2019-03-31T21:00 in 5min format.
	 * %age days where 1st candle trend matched the entire day trend : 60.941704%
	 * Identifying trend based on 1st candle for NIFTY from 2010-04-01T06:00 to 2019-03-31T21:00 in 1min format.
	 * %age days where 1st candle trend matched the entire day trend : 62.06278%
	 * Identifying trend based on 1st candle for NIFTY from 2010-04-01T06:00 to 2019-03-31T21:00 in 5min format.
	 * %age days where 1st candle trend matched the entire day trend : 63.542603%
	 * 
	 * 
	 * If you add more stringent checks, then the test results deteriorate
	 * 
	 * e.g.
	 * unitPrice1Day.getOpen() > unitPrice1Day.getClose() * 1.001
	 * unitPrice1Day.getClose() > unitPrice1Day.getOpen() * 1.001
	 * 
	 * Identifying trend based on 1st candle for BANKNIFTY from 2010-04-01T06:00 to 2019-03-31T21:00 in 1min format.
	 * %age days where 1st candle trend matched the entire day trend : 42.017937%
	 * Identifying trend based on 1st candle for BANKNIFTY from 2010-04-01T06:00 to 2019-03-31T21:00 in 5min format.
	 * %age days where 1st candle trend matched the entire day trend : 47.264572%
	 * Identifying trend based on 1st candle for NIFTY from 2010-04-01T06:00 to 2019-03-31T21:00 in 1min format.
	 * %age days where 1st candle trend matched the entire day trend : 41.524662%
	 * Identifying trend based on 1st candle for NIFTY from 2010-04-01T06:00 to 2019-03-31T21:00 in 5min format.
	 * %age days where 1st candle trend matched the entire day trend : 44.932735%
	 * 
	 * 
	 * e.g.
	 * unitPrice1Day.getOpen() > unitPrice1Day.getClose() * 1.0005
	 * unitPrice1Day.getClose() > unitPrice1Day.getOpen() * 1.0005
	 * 
	 * Identifying trend based on 1st candle for BANKNIFTY from 2010-04-01T06:00 to 2019-03-31T21:00 in 1min format.
	 * %age days where 1st candle trend matched the entire day trend : 48.69955%
	 * Identifying trend based on 1st candle for BANKNIFTY from 2010-04-01T06:00 to 2019-03-31T21:00 in 5min format.
	 * %age days where 1st candle trend matched the entire day trend : 54.08072%
	 * Identifying trend based on 1st candle for NIFTY from 2010-04-01T06:00 to 2019-03-31T21:00 in 1min format.
	 * %age days where 1st candle trend matched the entire day trend : 49.55157%
	 * Identifying trend based on 1st candle for NIFTY from 2010-04-01T06:00 to 2019-03-31T21:00 in 5min format.
	 * %age days where 1st candle trend matched the entire day trend : 52.556053%
	 * 
	 * 
	 */
	// @formatter:on

	@Test
	public void test01_TrendIdentificationOnFirstCandleOnBankNifty1MinChart() {
		identifyTrendOnFirstCandle("BANKNIFTY", LocalDateTime.of(2010, 4, 1, 6, 0), LocalDateTime.of(2019, 3, 31, 21, 0), TimeFormat._1MIN);
	}

	@Test
	public void test02_TrendIdentificationOnFirstCandleOnBankNifty5MinChart() {
		identifyTrendOnFirstCandle("BANKNIFTY", LocalDateTime.of(2010, 4, 1, 6, 0), LocalDateTime.of(2019, 3, 31, 21, 0), TimeFormat._5MIN);
	}

	@Test
	public void test11_TrendIdentificationOnFirstCandleOnNifty1MinChart() {
		identifyTrendOnFirstCandle("NIFTY", LocalDateTime.of(2010, 4, 1, 6, 0), LocalDateTime.of(2019, 3, 31, 21, 0), TimeFormat._1MIN);
	}

	@Test
	public void test12_TrendIdentificationOnFirstCandleOnNifty5MinChart() {
		identifyTrendOnFirstCandle("NIFTY", LocalDateTime.of(2010, 4, 1, 6, 0), LocalDateTime.of(2019, 3, 31, 21, 0), TimeFormat._5MIN);
	}

	private void identifyTrendOnFirstCandle(String scripName, LocalDateTime startDateTime, LocalDateTime endDateTime, TimeFormat timeFormat) {
		logger.info("Identifying trend based on 1st candle for " + scripName + " from " + startDateTime + " to " + endDateTime + " in " + timeFormat.toString() + " format.");
		List<UnitPriceData> unitPriceDataListTestTimeFormat = historicalDataReader.getUnitPriceDataForRange(scripName, startDateTime, endDateTime, timeFormat);
		List<UnitPriceData> unitPriceDataList1DayFormat = historicalDataReader.getUnitPriceDataForRange(scripName, startDateTime, endDateTime, TimeFormat._1DAY);
		if (unitPriceDataListTestTimeFormat.size() > 0 && unitPriceDataList1DayFormat.size() > 0) {

			List<PeriodTrend> testTimeFormatTrend = new ArrayList<PeriodTrend>();
			List<PeriodTrend> _1DayTimeFormatTrend = new ArrayList<PeriodTrend>();

			for (UnitPriceData unitPrice1Day : unitPriceDataList1DayFormat) {
				if (unitPrice1Day.getOpen() > unitPrice1Day.getClose() * 1) {
					_1DayTimeFormatTrend.add(new PeriodTrend(unitPrice1Day.getDateTime(), unitPrice1Day.getTimeFormat(), Trend.DOWN));
				} else if (unitPrice1Day.getClose() > unitPrice1Day.getOpen() * 1) {
					_1DayTimeFormatTrend.add(new PeriodTrend(unitPrice1Day.getDateTime(), unitPrice1Day.getTimeFormat(), Trend.UP));
				} else {
					_1DayTimeFormatTrend.add(new PeriodTrend(unitPrice1Day.getDateTime(), unitPrice1Day.getTimeFormat(), Trend.CHOPPY));
				}
			}

			for (int i = 0; i < unitPriceDataListTestTimeFormat.size(); i++) {
				if (i == 0 || unitPriceDataListTestTimeFormat.get(i).getDate().isAfter(unitPriceDataListTestTimeFormat.get(i - 1).getDate())) {
					UnitPriceData currentUnitPrice = unitPriceDataListTestTimeFormat.get(i);
					if (currentUnitPrice.getOpen() > currentUnitPrice.getClose() * 1) {
						testTimeFormatTrend.add(new PeriodTrend(currentUnitPrice.getDateTime(), currentUnitPrice.getTimeFormat(), Trend.DOWN));
					} else if (currentUnitPrice.getClose() > currentUnitPrice.getOpen() * 1) {
						testTimeFormatTrend.add(new PeriodTrend(currentUnitPrice.getDateTime(), currentUnitPrice.getTimeFormat(), Trend.UP));
					} else {
						testTimeFormatTrend.add(new PeriodTrend(currentUnitPrice.getDateTime(), currentUnitPrice.getTimeFormat(), Trend.CHOPPY));
					}
				}
			}

			Assert.assertEquals(testTimeFormatTrend.size(), _1DayTimeFormatTrend.size());
			for (int i = 0; i < testTimeFormatTrend.size(); i++) {
				Assert.assertEquals(testTimeFormatTrend.get(i).getStartDateTime(), _1DayTimeFormatTrend.get(i).getStartDateTime());
			}

			int trendMatchedCount = 0;
			for (int i = 0; i < testTimeFormatTrend.size(); i++) {
				if (testTimeFormatTrend.get(i).getTrend().equals(_1DayTimeFormatTrend.get(i).getTrend())) {
					++trendMatchedCount;
				}
			}
			logger.info("%age days where 1st candle trend matched the entire day trend : " + (100 * (float) trendMatchedCount / testTimeFormatTrend.size()) + "%");

		} else {
			Assert.fail("Unable to get data for " + scripName + " from " + startDateTime + " to " + endDateTime + " in " + timeFormat.toString() + " format.");
		}
	}
}
