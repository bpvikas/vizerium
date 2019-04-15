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

package com.vizerium.barabanca.trend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.Indicator;

public abstract class IndicatorTrendCheckTest<I extends Indicator<I>> {

	private static final Logger logger = Logger.getLogger(IndicatorTrendCheckTest.class);

	protected IndicatorTrendCheck<I> unit;

	@Test
	public abstract void testGetIndicator();

	@Test
	public void testGetTrend() {
		List<UnitPriceData> unitPrices = getOHLCData(LocalDateTime.of(2018, 1, 1, 6, 0), LocalDateTime.of(2018, 12, 31, 21, 00), TimeFormat._1DAY);

		PeriodTrends periodTrends = unit.getTrend(TimeFormat._1WEEK, unitPrices);
		for (PeriodTrend periodTrend : periodTrends) {
			logger.info(periodTrend);
		}
	}

	public abstract IndicatorTrendCheck<I> getUnit();

	protected List<UnitPriceData> getOHLCData(LocalDateTime startDateTime, LocalDateTime endDateTime, TimeFormat timeFormat) {

		List<UnitPriceData> unitPrices = new ArrayList<UnitPriceData>();
		try {
			File testDataFile = new File("src/test/resources/testData_trendcheck_calculation_" + timeFormat.getProperty() + ".csv");

			BufferedReader br = new BufferedReader(new FileReader(testDataFile));
			String dataLine = br.readLine(); // This is to read off the header line.
			while ((dataLine = br.readLine()) != null) {
				String[] dataLineDetails = dataLine.split(",");
				UnitPriceData unitPriceData = new UnitPriceData(dataLineDetails);
				if (!unitPriceData.getDateTime().isBefore(startDateTime) && !unitPriceData.getDateTime().isAfter(endDateTime)) {
					unitPriceData.setTimeFormat(timeFormat);
					unitPrices.add(unitPriceData);
				}
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Assert.fail(ioe.getMessage());
		}
		return unitPrices;
	}
}
