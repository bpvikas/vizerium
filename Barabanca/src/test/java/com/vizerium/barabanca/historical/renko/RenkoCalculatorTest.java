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

package com.vizerium.barabanca.historical.renko;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.barabanca.historical.HistoricalDataReader;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RenkoCalculatorTest {

	private RenkoCalculator unit;

	private static final float delta = 0.0f;

	private static final int BANKNIFTY_BRICK_SIZE = 100;

	private static final int NIFTY_BRICK_SIZE = 50;

	@Before
	public void setUp() throws Exception {
		unit = new RenkoCalculator();
	}

	@Test
	public void test01_BankNiftyHourlyChart() {
		calculateRenko("BANKNIFTY", TimeFormat._1HOUR, 2011, 2019, BANKNIFTY_BRICK_SIZE, true);
	}

	@Test
	public void test02_BankNiftyDailyChart() {
		calculateRenko("BANKNIFTY", TimeFormat._1DAY, 2011, 2019, BANKNIFTY_BRICK_SIZE, true);
	}

	@Test
	public void test03_NiftyHourlyChart() {
		calculateRenko("NIFTY", TimeFormat._1HOUR, 2011, 2019, NIFTY_BRICK_SIZE, true);
	}

	@Test
	public void test04_NiftyDailyChart() {
		calculateRenko("NIFTY", TimeFormat._1DAY, 2011, 2019, NIFTY_BRICK_SIZE, true);
	}

	@Test
	public void test11_BankNiftyHourlyChartHighLow() {
		RenkoRange renkoRangeBN2018 = calculateRenko("BANKNIFTY", TimeFormat._1HOUR, 2018, 2018, BANKNIFTY_BRICK_SIZE, true);
		Assert.assertEquals(27400.0f, renkoRangeBN2018.getPreviousHigh(), delta);
		Assert.assertEquals(26400.0f, renkoRangeBN2018.getPreviousLow(), delta);
		RenkoRange renkoRangeBN2019 = calculateRenko("BANKNIFTY", TimeFormat._1HOUR, 2019, 2019, BANKNIFTY_BRICK_SIZE, true);
		Assert.assertEquals(30600.0f, renkoRangeBN2019.getPreviousHigh(), delta);
		Assert.assertEquals(29200.0f, renkoRangeBN2019.getPreviousLow(), delta);
	}

	@Test
	public void test12_BankNiftyDailyChartHighLow() {
		RenkoRange renkoRangeBN2018 = calculateRenko("BANKNIFTY", TimeFormat._1DAY, 2018, 2018, BANKNIFTY_BRICK_SIZE, true);
		Assert.assertEquals(27300.0f, renkoRangeBN2018.getPreviousHigh(), delta);
		Assert.assertEquals(26700.0f, renkoRangeBN2018.getPreviousLow(), delta);
		RenkoRange renkoRangeBN2019 = calculateRenko("BANKNIFTY", TimeFormat._1DAY, 2019, 2019, BANKNIFTY_BRICK_SIZE, true);
		Assert.assertEquals(30600.0f, renkoRangeBN2019.getPreviousHigh(), delta);
		Assert.assertEquals(29200.0f, renkoRangeBN2019.getPreviousLow(), delta);
	}

	@Test
	public void test13_NiftyHourlyChartHighLow() {
		RenkoRange renkoRangeN2018 = calculateRenko("NIFTY", TimeFormat._1HOUR, 2018, 2018, NIFTY_BRICK_SIZE, true);
		Assert.assertEquals(11000.0f, renkoRangeN2018.getPreviousHigh(), delta);
		Assert.assertEquals(10500.0f, renkoRangeN2018.getPreviousLow(), delta);
		RenkoRange renkoRangeN2019 = calculateRenko("NIFTY", TimeFormat._1HOUR, 2019, 2019, NIFTY_BRICK_SIZE, true);
		Assert.assertEquals(11700.0f, renkoRangeN2019.getPreviousHigh(), delta);
		Assert.assertEquals(11300.0f, renkoRangeN2019.getPreviousLow(), delta);
	}

	@Test
	public void test14_NiftyDailyChartHighLow() {
		RenkoRange renkoRangeN2018 = calculateRenko("NIFTY", TimeFormat._1DAY, 2018, 2018, NIFTY_BRICK_SIZE, true);
		Assert.assertEquals(11000.0f, renkoRangeN2018.getPreviousHigh(), delta);
		Assert.assertEquals(10650.0f, renkoRangeN2018.getPreviousLow(), delta);
		RenkoRange renkoRangeN2019 = calculateRenko("NIFTY", TimeFormat._1DAY, 2019, 2019, NIFTY_BRICK_SIZE, true);
		Assert.assertEquals(11700.0f, renkoRangeN2019.getPreviousHigh(), delta);
		Assert.assertEquals(11350.0f, renkoRangeN2019.getPreviousLow(), delta);
	}

	@Test
	public void test21_BankNiftyHourlyChartReversals() {
		RenkoRange renkoRangeBN2018 = calculateRenko("BANKNIFTY", TimeFormat._1HOUR, 2018, 2018, BANKNIFTY_BRICK_SIZE, true);
		Assert.assertEquals(87, renkoRangeBN2018.getAllReversals().size());
		RenkoRange renkoRangeBN2019 = calculateRenko("BANKNIFTY", TimeFormat._1HOUR, 2019, 2019, BANKNIFTY_BRICK_SIZE, true);
		Assert.assertEquals(113, renkoRangeBN2019.getAllReversals().size());
	}

	@Test
	public void test22_BankNiftyDailyChartReversals() {
		RenkoRange renkoRangeBN2018 = calculateRenko("BANKNIFTY", TimeFormat._1DAY, 2018, 2018, BANKNIFTY_BRICK_SIZE, true);
		Assert.assertEquals(54, renkoRangeBN2018.getAllReversals().size());
		RenkoRange renkoRangeBN2019 = calculateRenko("BANKNIFTY", TimeFormat._1DAY, 2019, 2019, BANKNIFTY_BRICK_SIZE, true);
		Assert.assertEquals(53, renkoRangeBN2019.getAllReversals().size());
	}

	@Test
	public void test23_NiftyHourlyChartReversals() {
		RenkoRange renkoRangeN2018 = calculateRenko("NIFTY", TimeFormat._1HOUR, 2018, 2018, NIFTY_BRICK_SIZE, true);
		Assert.assertEquals(51, renkoRangeN2018.getAllReversals().size());
		RenkoRange renkoRangeN2019 = calculateRenko("NIFTY", TimeFormat._1HOUR, 2019, 2019, NIFTY_BRICK_SIZE, true);
		Assert.assertEquals(14, renkoRangeN2019.getAllReversals().size());
	}

	@Test
	public void test24_NiftyDailyChartReversals() {
		RenkoRange renkoRangeN2018 = calculateRenko("NIFTY", TimeFormat._1DAY, 2018, 2018, NIFTY_BRICK_SIZE, true);
		Assert.assertEquals(28, renkoRangeN2018.getAllReversals().size());
		RenkoRange renkoRangeN2019 = calculateRenko("NIFTY", TimeFormat._1DAY, 2019, 2019, NIFTY_BRICK_SIZE, true);
		Assert.assertEquals(39, renkoRangeN2019.getAllReversals().size());
	}

	private RenkoRange calculateRenko(String scripName, TimeFormat timeFormat, int startYear, int endYear, int brickSize, boolean smoothPriceRange) {
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

		for (int i = 0; i < renkoRange.size(); i++) {
			Assert.assertNotNull("Start date time cannot be null. " + renkoRange.get(i), renkoRange.get(i).getStartDateTime());
			if (i != renkoRange.size() - 1) { // The end date for the last Renko is typically null.
				Assert.assertNotNull("End date time cannot be null. " + renkoRange.get(i), renkoRange.get(i).getEndDateTime());
			} else {
				Assert.assertNull("End date time for last entry should be null. " + renkoRange.get(i), renkoRange.get(i).getEndDateTime());
			}
			Assert.assertNotEquals(0.0f, renkoRange.get(i).getStartPrice());
			Assert.assertNotEquals(0.0f, renkoRange.get(i).getEndPrice());

			if (i > 0) {
				Assert.assertTrue("The adjacent renkos should have adjacent price ranges. " + renkoRange.get(i - 1) + " " + renkoRange.get(i),
						(renkoRange.get(i).getStartPrice() == renkoRange.get(i - 1).getEndPrice()) || (renkoRange.get(i).getEndPrice() == renkoRange.get(i - 1).getStartPrice())
								|| (renkoRange.get(i).getStartPrice() == renkoRange.get(i - 1).getStartPrice())
								|| (renkoRange.get(i).getEndPrice() == renkoRange.get(i - 1).getEndPrice()));
			}
		}
		return renkoRange;
	}
}
