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
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.barabanca.historical.renko.Renko;
import com.vizerium.barabanca.historical.renko.RenkoCalculator;
import com.vizerium.barabanca.historical.renko.RenkoRange;
import com.vizerium.barabanca.historical.renko.Reversal;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DoubleBottomTest {

	private static final Logger logger = Logger.getLogger(DoubleBottomTest.class);

	private RenkoCalculator unit;

	private static final int BANKNIFTY_DAILY_BRICK_SIZE = 100;

	private static final int NIFTY_DAILY_BRICK_SIZE = 50;

	private static final int BANKNIFTY_HOURLY_BRICK_SIZE = 50;

	private static final int NIFTY_HOURLY_BRICK_SIZE = 20;

	@Before
	public void setUp() throws Exception {
		unit = new RenkoCalculator();
	}

	@Test
	public void test01_DoubleBottomBankNiftyHourlyChart() {
		getDoubleBottom("BANKNIFTY", TimeFormat._1HOUR, 2011, 2019, BANKNIFTY_HOURLY_BRICK_SIZE, true);
	}

	@Test
	public void test02_DoubleBottomBankNiftyDailyChart() {
		getDoubleBottom("BANKNIFTY", TimeFormat._1DAY, 2011, 2019, BANKNIFTY_DAILY_BRICK_SIZE, true);
	}

	@Test
	public void test03_DoubleBottomNiftyHourlyChart() {
		getDoubleBottom("NIFTY", TimeFormat._1HOUR, 2011, 2019, NIFTY_HOURLY_BRICK_SIZE, true);
	}

	@Test
	public void test04_DoubleBottomNiftyDailyChart() {
		getDoubleBottom("NIFTY", TimeFormat._1DAY, 2011, 2019, NIFTY_DAILY_BRICK_SIZE, true);
	}

	private void getDoubleBottom(String scripName, TimeFormat timeFormat, int startYear, int endYear, int brickSize, boolean smoothPriceRange) {
		logger.info(System.lineSeparator() + "Getting double bottoms for " + scripName + " " + timeFormat.toString() + " chart from " + startYear + " to " + endYear
				+ " for a brick size of " + brickSize);
		Renko renko = new Renko();
		renko.setScripName(scripName);
		renko.setTimeFormat(timeFormat);

		if (brickSize > 0) {
			renko.setBrickSize(brickSize);
		}
		renko.setSmoothPriceRange(smoothPriceRange);

		HistoricalDataReader historicalDataReader = new HistoricalDataReader();
		List<UnitPriceData> unitPriceDataList = historicalDataReader.getUnitPriceDataForRange(scripName, LocalDateTime.of(startYear, 1, 1, 6, 0),
				LocalDateTime.of(endYear, 12, 31, 21, 00), timeFormat);

		RenkoRange renkoRange = unit.calculate(unitPriceDataList, renko);

		List<Reversal> reversals = renkoRange.getAllReversals();
		List<Reversal> reversalsFromBottom = reversals.stream().filter(Reversal::isBottom).collect(Collectors.toList());

		for (int i = 0; i < reversalsFromBottom.size() - 1; i++) {
			if (reversalsFromBottom.get(i).getExtremePrice() == reversalsFromBottom.get(i + 1).getExtremePrice()) {
				logger.info("Double Bottom -> [" + reversalsFromBottom.get(i) + " and " + reversalsFromBottom.get(i + 1) + "]");
			}
		}
	}
}
