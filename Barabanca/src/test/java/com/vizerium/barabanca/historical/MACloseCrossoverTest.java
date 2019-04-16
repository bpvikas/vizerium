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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MACloseCrossoverTest {

	private HistoricalDataReader historicalDataReader;

	private static final Logger logger = Logger.getLogger(MACloseCrossoverTest.class);

	@Before
	public void setUp() throws Exception {
		historicalDataReader = new HistoricalDataReader();
	}

	// @formatter:off
	/*
	 * 
	 * Checking for 200MA crossover in BANKNIFTY from 2010-04-01T06:00 to 2019-03-31T21:00 in 1min format.
	 * Total number of days : 2230
	 * Total number of days where close crossed over 200MA on 1min chart  : 2122
	 * %age days where 200MA crossed over Close : 95.15695%
	 * 
	 * Checking for 200MA crossover in NIFTY from 2010-04-01T06:00 to 2019-03-31T21:00 in 1min format.
	 * Total number of days : 2230
	 * Total number of days where close crossed over 200MA on 1min chart  : 2116
	 * %age days where 200MA crossed over Close : 94.88789%
	 * 
	 * Checking for 100MA crossover in BANKNIFTY from 2010-04-01T06:00 to 2019-03-31T21:00 in 1min format.
	 * Total number of days : 2230
	 * Total number of days where close crossed over 100MA on 1min chart  : 2222
	 * %age days where 100MA crossed over Close : 99.64126%
	 * 
	 * Checking for 100MA crossover in NIFTY from 2010-04-01T06:00 to 2019-03-31T21:00 in 1min format.
	 * Total number of days : 2230
	 * Total number of days where close crossed over 100MA on 1min chart  : 2216
	 * %age days where 100MA crossed over Close : 99.3722%
	 * 
	 */
	// @formatter:on

	@Test
	public void test01_200MACloseCrossoverOnBankNifty1MinChart() {
		getNumberOfCloseCrossoverDays("BANKNIFTY", LocalDateTime.of(2010, 4, 1, 6, 0), LocalDateTime.of(2019, 3, 31, 21, 0), TimeFormat._1MIN, 200);
	}

	@Test
	public void test02_200MACloseCrossoverOnNifty1MinChart() {
		getNumberOfCloseCrossoverDays("NIFTY", LocalDateTime.of(2010, 4, 1, 6, 0), LocalDateTime.of(2019, 3, 31, 21, 0), TimeFormat._1MIN, 200);
	}

	@Test
	public void test03_100MACloseCrossoverOnBankNifty1MinChart() {
		getNumberOfCloseCrossoverDays("BANKNIFTY", LocalDateTime.of(2010, 4, 1, 6, 0), LocalDateTime.of(2019, 3, 31, 21, 0), TimeFormat._1MIN, 100);
	}

	@Test
	public void test04_100MACloseCrossoverOnNifty1MinChart() {
		getNumberOfCloseCrossoverDays("NIFTY", LocalDateTime.of(2010, 4, 1, 6, 0), LocalDateTime.of(2019, 3, 31, 21, 0), TimeFormat._1MIN, 100);
	}

	private int[] getNumberOfCloseCrossoverDays(String scripName, LocalDateTime startDateTime, LocalDateTime endDateTime, TimeFormat timeFormat, int crossoverMA) {
		logger.info("Checking for " + crossoverMA + "MA crossover in " + scripName + " from " + startDateTime + " to " + endDateTime + " in " + timeFormat.toString() + " format.");
		List<UnitPriceData> unitPriceDataList = historicalDataReader.getUnitPriceDataForRange(scripName, startDateTime, endDateTime, timeFormat);
		if (unitPriceDataList.size() > 0) {
			int totalNumberOfDays = 1;
			int numberOfDaysOfCloseMACrossover = 0;

			LocalDate currentDate = unitPriceDataList.get(0).getDate();
			LocalDate latestCrossoverDate = null;
			for (int i = 1; i < unitPriceDataList.size(); i++) {
				if (!unitPriceDataList.get(i).getDate().equals(currentDate)) {
					++totalNumberOfDays;
					currentDate = unitPriceDataList.get(i).getDate();
				}
				if (isCrossover(unitPriceDataList.get(i), unitPriceDataList.get(i - 1), crossoverMA) && !unitPriceDataList.get(i).getDate().equals(latestCrossoverDate)) {
					++numberOfDaysOfCloseMACrossover;
					latestCrossoverDate = unitPriceDataList.get(i).getDate();
				}
			}

			logger.info("Total number of days : " + totalNumberOfDays);
			logger.info("Total number of days where close crossed over " + crossoverMA + "MA on " + timeFormat.toString() + " chart  : " + numberOfDaysOfCloseMACrossover);
			logger.info("%age days where " + crossoverMA + "MA crossed over Close : " + 100 * (float) numberOfDaysOfCloseMACrossover / totalNumberOfDays + "%"
					+ System.lineSeparator());

			return new int[] { totalNumberOfDays, numberOfDaysOfCloseMACrossover };

		} else {
			throw new RuntimeException("Unable to get unit prices.");
		}
	}

	private boolean isCrossover(UnitPriceData current, UnitPriceData previous, int ma) {
		return isPositiveCrossover(current, previous, ma) || isNegativeCrossover(current, previous, ma);
	}

	private boolean isPositiveCrossover(UnitPriceData current, UnitPriceData previous, int ma) {
		return current.getClose() > current.getMovingAverage(ma) && previous.getClose() < previous.getMovingAverage(ma);
	}

	private boolean isNegativeCrossover(UnitPriceData current, UnitPriceData previous, int ma) {
		return current.getClose() < current.getMovingAverage(ma) && previous.getClose() > previous.getMovingAverage(ma);
	}
}
