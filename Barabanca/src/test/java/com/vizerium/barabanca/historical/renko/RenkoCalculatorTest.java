package com.vizerium.barabanca.historical.renko;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.barabanca.historical.HistoricalDataReader;
import com.vizerium.barabanca.historical.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RenkoCalculatorTest {

	private RenkoCalculator unit;

	@Before
	public void setUp() throws Exception {
		unit = new RenkoCalculator();
	}

	@Test
	public void test01_BankNiftyHourlyChart() {
		calculateRenko("BANKNIFTY", TimeFormat._1HOUR, 2011, 2019, 100, true);
	}

	@Test
	public void test02_BankNiftyDailyChart() {
		calculateRenko("BANKNIFTY", TimeFormat._1DAY, 2011, 2019, 100, true);
	}

	@Test
	public void test03_NiftyHourlyChart() {
		calculateRenko("NIFTY", TimeFormat._1HOUR, 2011, 2019, 50, true);
	}

	@Test
	public void test04_NiftyDailyChart() {
		calculateRenko("NIFTY", TimeFormat._1DAY, 2011, 2019, 50, true);
	}

	private Renko[] calculateRenko(String scripName, TimeFormat timeFormat, int startYear, int endYear, int brickSize, boolean smoothPriceRange) {
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

		Renko[] renkoRange = unit.calculate(unitPriceDataList, renko);

		for (int i = 0; i < renkoRange.length; i++) {
			Assert.assertNotNull("Start date time cannot be null. " + renkoRange[i], renkoRange[i].getStartDateTime());
			if (i != renkoRange.length - 1) { // The end date for the last Renko is typically null.
				Assert.assertNotNull("End date time cannot be null. " + renkoRange[i], renkoRange[i].getEndDateTime());
			}
			Assert.assertNotEquals(0.0f, renkoRange[i].getStartPrice());
			Assert.assertNotEquals(0.0f, renkoRange[i].getEndPrice());

			if (i > 0) {
				Assert.assertTrue(
						"The adjacent renkos should have adjacent price ranges. " + renkoRange[i - 1] + " " + renkoRange[i],
						(renkoRange[i].getStartPrice() == renkoRange[i - 1].getEndPrice()) || (renkoRange[i].getEndPrice() == renkoRange[i - 1].getStartPrice())
								|| (renkoRange[i].getStartPrice() == renkoRange[i - 1].getStartPrice()) || (renkoRange[i].getEndPrice() == renkoRange[i - 1].getEndPrice()));
			}
		}
		return renkoRange;
	}
}
