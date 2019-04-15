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
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;

/*
 * @deprecated 
 * @see com.vizerium.barabanca.historical.renko.RenkoCalculator#calculate, 
 * which smoothes the data in an industry-standard way, viz. Renko.
 */

@Deprecated
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReversalIdentificationTest {

	private static final Logger logger = Logger.getLogger(ReversalIdentificationTest.class);

	private HistoricalDataReader historicalDataReader;

	@Before
	public void setUp() throws Exception {
		historicalDataReader = new HistoricalDataReader();
	}

	@Test
	public void test01_BankNiftyHourlyChart() {
		identifyReversals("BANKNIFTY", TimeFormat._1HOUR, 2011, 2019);
	}

	@Test
	public void test02_BankNiftyDailyChart() {
		identifyReversals("BANKNIFTY", TimeFormat._1DAY, 2011, 2019);
	}

	@Test
	public void test03_NiftyHourlyChart() {
		identifyReversals("NIFTY", TimeFormat._1HOUR, 2011, 2019);
	}

	@Test
	public void test04_NiftyDailyChart() {
		identifyReversals("NIFTY", TimeFormat._1DAY, 2011, 2019);
	}

	private void identifyReversals(String scripName, TimeFormat timeFormat, int startYear, int endYear) {
		List<UnitPriceData> unitPriceDataList = historicalDataReader.getUnitPriceDataForRange(scripName, LocalDateTime.of(startYear, 1, 1, 6, 0),
				LocalDateTime.of(endYear, 12, 31, 21, 00), timeFormat);

		int previousPeakOrTrough = 0;
		for (int i = 0; i < unitPriceDataList.size() - 2; i++) {

			if (isBottom(unitPriceDataList, i) && !isTop(unitPriceDataList, i - 1)) {
				logger.info("Bottom @ " + unitPriceDataList.get(i + 1).getDateTime() + " @ " + unitPriceDataList.get(i + 1).getClose() + " after " + (i + 1 - previousPeakOrTrough)
						+ " " + timeFormat.getProperty().substring(1) + "s.");
				previousPeakOrTrough = i + 1;
			}

			if (isTop(unitPriceDataList, i) && !isBottom(unitPriceDataList, i - 1)) {
				logger.info("Top    @ " + unitPriceDataList.get(i + 1).getDateTime() + " @ " + unitPriceDataList.get(i + 1).getClose() + " after " + (i + 1 - previousPeakOrTrough)
						+ " " + timeFormat.getProperty().substring(1) + "s.");
				previousPeakOrTrough = i + 1;
			}
		}
	}

	private boolean isBottom(List<UnitPriceData> unitPriceDataList, int i) {
		return (unitPriceDataList.get(i).getClose() > unitPriceDataList.get(i + 1).getClose())
				&& (unitPriceDataList.get(i + 1).getClose() < unitPriceDataList.get(i + 2).getClose());
	}

	private boolean isTop(List<UnitPriceData> unitPriceDataList, int i) {
		return (unitPriceDataList.get(i).getClose() < unitPriceDataList.get(i + 1).getClose())
				&& (unitPriceDataList.get(i + 1).getClose() > unitPriceDataList.get(i + 2).getClose());
	}
}
