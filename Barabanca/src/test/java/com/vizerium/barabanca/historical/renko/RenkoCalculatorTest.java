package com.vizerium.barabanca.historical.renko;

import java.time.LocalDateTime;
import java.util.List;

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
		calculateRenko("BANKNIFTY", TimeFormat._1HOUR, 2011, 2019);
	}

	@Test
	public void test02_BankNiftyDailyChart() {
		calculateRenko("BANKNIFTY", TimeFormat._1DAY, 2011, 2019);
	}

	@Test
	public void test03_NiftyHourlyChart() {
		calculateRenko("NIFTY", TimeFormat._1HOUR, 2011, 2019);
	}

	@Test
	public void test04_NiftyDailyChart() {
		calculateRenko("NIFTY", TimeFormat._1DAY, 2011, 2019);
	}

	private Renko[] calculateRenko(String scripName, TimeFormat timeFormat, int startYear, int endYear) {
		Renko renko = new Renko();
		renko.setScripName(scripName);
		renko.setTimeFormat(timeFormat);
		HistoricalDataReader historicalDataReader = new HistoricalDataReader();

		List<UnitPriceData> unitPriceDataList = historicalDataReader.getUnitPriceDataForRange(scripName, LocalDateTime.of(startYear, 1, 1, 6, 0),
				LocalDateTime.of(endYear, 12, 31, 21, 00), timeFormat);

		return unit.calculate(unitPriceDataList, renko);
	}
}
