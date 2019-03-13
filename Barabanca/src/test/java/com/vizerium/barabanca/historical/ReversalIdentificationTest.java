package com.vizerium.barabanca.historical;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.commons.dao.UnitPriceData;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReversalIdentificationTest {

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
				System.out.println("Bottom @ " + unitPriceDataList.get(i + 1).getDateTime() + " @ " + unitPriceDataList.get(i + 1).getClose() + " after "
						+ (i + 1 - previousPeakOrTrough) + " " + timeFormat.getProperty().substring(1) + "s.");
				previousPeakOrTrough = i + 1;
			}

			if (isTop(unitPriceDataList, i) && !isBottom(unitPriceDataList, i - 1)) {
				System.out.println("Top    @ " + unitPriceDataList.get(i + 1).getDateTime() + " @ " + unitPriceDataList.get(i + 1).getClose() + " after "
						+ (i + 1 - previousPeakOrTrough) + " " + timeFormat.getProperty().substring(1) + "s.");
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
