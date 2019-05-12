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
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventCandleIdentificationTest {

	private HistoricalDataReader historicalDataReader;

	private static final Logger logger = Logger.getLogger(EventCandleIdentificationTest.class);

	@Before
	public void setUp() throws Exception {
		historicalDataReader = new HistoricalDataReader();
	}

	@Test
	public void test01_EventCandleOnBankNifty1MinChart() {
		for (int year = 2010; year <= 2018; year++) {
			identifyEventCandle("BANKNIFTY", LocalDateTime.of(year, 4, 1, 6, 0), LocalDateTime.of(year + 1, 3, 31, 21, 0), TimeFormat._1MIN);
		}
	}

	@Test
	public void test02_EventCandleOnBankNifty5MinChart() {
		for (int year = 2010; year <= 2018; year++) {
			identifyEventCandle("BANKNIFTY", LocalDateTime.of(year, 4, 1, 6, 0), LocalDateTime.of(year + 1, 3, 31, 21, 0), TimeFormat._5MIN);
		}
	}

	@Test
	public void test03_EventCandleOnBankNifty1HourChart() {
		for (int year = 2010; year <= 2018; year++) {
			identifyEventCandle("BANKNIFTY", LocalDateTime.of(year, 4, 1, 6, 0), LocalDateTime.of(year + 1, 3, 31, 21, 0), TimeFormat._1HOUR);
		}
	}

	@Test
	public void test04_EventCandleOnBankNifty1DayChart() {
		for (int year = 2010; year <= 2018; year++) {
			identifyEventCandle("BANKNIFTY", LocalDateTime.of(year, 4, 1, 6, 0), LocalDateTime.of(year + 1, 3, 31, 21, 0), TimeFormat._1DAY);
		}
	}

	@Test
	public void test11_EventCandleOnNifty1MinChart() {
		for (int year = 2010; year <= 2018; year++) {
			identifyEventCandle("NIFTY", LocalDateTime.of(year, 4, 1, 6, 0), LocalDateTime.of(year + 1, 3, 31, 21, 0), TimeFormat._1MIN);
		}
	}

	@Test
	public void test12_EventCandleOnNifty5MinChart() {
		for (int year = 2010; year <= 2018; year++) {
			identifyEventCandle("NIFTY", LocalDateTime.of(year, 4, 1, 6, 0), LocalDateTime.of(year + 1, 3, 31, 21, 0), TimeFormat._5MIN);
		}
	}

	@Test
	public void test13_EventCandleOnNifty1HourChart() {
		for (int year = 2010; year <= 2018; year++) {
			identifyEventCandle("NIFTY", LocalDateTime.of(year, 4, 1, 6, 0), LocalDateTime.of(year + 1, 3, 31, 21, 0), TimeFormat._1HOUR);
		}
	}

	@Test
	public void test14_EventCandleOnNifty1DayChart() {
		for (int year = 2010; year <= 2018; year++) {
			identifyEventCandle("NIFTY", LocalDateTime.of(year, 4, 1, 6, 0), LocalDateTime.of(year + 1, 3, 31, 21, 0), TimeFormat._1DAY);
		}
	}

	private void identifyEventCandle(String scripName, LocalDateTime startDateTime, LocalDateTime endDateTime, TimeFormat timeFormat) {
		logger.info(System.lineSeparator() + "Checking for event candles in " + scripName + " from " + startDateTime + " to " + endDateTime + " in " + timeFormat.toString()
				+ " format.");
		List<UnitPriceData> unitPriceDataList = historicalDataReader.getUnitPriceDataForRange(scripName, startDateTime, endDateTime, timeFormat);
		if (unitPriceDataList.size() > 0) {
			float maxSize = 0;
			float minSize = 3000;

			for (UnitPriceData unitPriceData : unitPriceDataList) {
				float candleSize = Math.abs(unitPriceData.getHigh() - unitPriceData.getLow());
				if (candleSize < minSize) {
					minSize = candleSize;
				}
				if (candleSize > maxSize) {
					maxSize = candleSize;
				}
			}

			logger.info("Max candle size is : " + maxSize);
			logger.info("Min candle size is : " + minSize);

			float[] candleSizes = new float[unitPriceDataList.size()];
			int i = 0;
			for (UnitPriceData unitPriceData : unitPriceDataList) {
				candleSizes[i++] = Math.abs(unitPriceData.getHigh() - unitPriceData.getLow());
			}
			Arrays.sort(candleSizes);
			if (candleSizes.length % 2 == 0) {
				logger.info("Median is " + candleSizes[candleSizes.length / 2 - 1] + " and " + candleSizes[candleSizes.length / 2]);
			} else {
				logger.info("Median is " + candleSizes[candleSizes.length / 2 - 1]);
			}

			logger.info("3rd quartile is " + candleSizes[3 * candleSizes.length / 4]);
			logger.info("4rd quintile is " + candleSizes[4 * candleSizes.length / 5]);
			logger.info("9th decile is " + candleSizes[9 * candleSizes.length / 10]);
			logger.info("19th vigintile is " + candleSizes[19 * candleSizes.length / 20]);
			logger.info("96th centile is " + candleSizes[96 * candleSizes.length / 100]);
			logger.info("99th centile is " + candleSizes[99 * candleSizes.length / 100]);
		} else {
			Assert.fail("Unable to get unit prices for " + scripName + " from " + startDateTime + " to " + endDateTime + " in " + timeFormat.toString() + " format.");
		}
	}
}
