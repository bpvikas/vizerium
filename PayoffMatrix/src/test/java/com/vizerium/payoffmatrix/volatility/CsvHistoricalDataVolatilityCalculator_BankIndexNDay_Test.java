package com.vizerium.payoffmatrix.volatility;

import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.vizerium.payoffmatrix.dao.HistoricalDataStore;
import com.vizerium.payoffmatrix.historical.DayPriceData;

public class CsvHistoricalDataVolatilityCalculator_BankIndexNDay_Test {

	private CsvHistoricalDataVolatilityCalculator unit;

	private NumberFormat nf = NumberFormat.getInstance();

	@Before
	public void setup() {
		unit = new CsvHistoricalDataVolatilityCalculator("BANKINDEX");

		nf.setMinimumIntegerDigits(2);
	}

	@Test
	public void testCalculateNDayVolatility() {

		int differenceNDays = 2;

		List<Float> differenceArray = new ArrayList<Float>();
		for (int year = 2015; year < 2019; year++) {
			for (int week = 1; week <= 52; week++) {

				LocalDate expiryDate = LocalDate.parse(year + "-W" + nf.format(week) + "-1", DateTimeFormatter.ISO_WEEK_DATE).with(DayOfWeek.THURSDAY);

				DateRange historicalDateRange = new DateRange(expiryDate.minusWeeks(2).plusDays(1), expiryDate.minusWeeks(1));

				HistoricalDataStore historicalDataStore = unit.getHistoricalDataStore();
				DayPriceData[] historicalData = historicalDataStore.readHistoricalData(historicalDateRange);
				if (historicalData.length > differenceNDays) {
					float difference = historicalData[historicalData.length - 1].getOpen() - historicalData[historicalData.length - 1 - differenceNDays].getOpen();

					if (difference < -500.0 || difference >= 500.0) {
						System.out.println("For year: " + year + " and week: " + week + ", the data is...");
						for (DayPriceData dayPriceData : historicalData) {
							System.out.println(dayPriceData.getDate().getDayOfWeek().name().substring(0, 3) + "\t" + dayPriceData.getDate() + "\t" + dayPriceData.getOpen());
						}
						System.out.println(difference);
					}
					differenceArray.add(difference);
				}
			}
		}

		System.out.println("Difference array length: " + differenceArray.size());
		int diffMinNegative = 0, diff_500to_400 = 0, diff_400to_300 = 0, diff_300to_200 = 0, diff_200to_100 = 0, diff_100to0 = 0, diff0to100 = 0, diff100to200 = 0, diff200to300 = 0, diff300to400 = 0, diff400to500 = 0, diffMaxPositive = 0;
		for (float difference : differenceArray) {
			if (difference < -500.0) {
				diffMinNegative++;
			} else if (difference >= -500.0 && difference < -400.0) {
				diff_500to_400++;
			} else if (difference >= -400.0 && difference < -300.0) {
				diff_400to_300++;
			} else if (difference >= -300.0 && difference < -200.0) {
				diff_300to_200++;
			} else if (difference >= -200.0 && difference < -100.0) {
				diff_200to_100++;
			} else if (difference >= -100.0 && difference < 0.0) {
				diff_100to0++;
			} else if (difference >= 0.0 && difference < 100.0) {
				diff0to100++;
			} else if (difference >= 100.0 && difference < 200.0) {
				diff100to200++;
			} else if (difference >= 200.0 && difference < 300.0) {
				diff200to300++;
			} else if (difference >= 300.0 && difference < 400.0) {
				diff300to400++;
			} else if (difference >= 400.0 && difference < 500.0) {
				diff400to500++;
			} else if (difference >= -500.0) {
				diffMaxPositive++;
			}
		}

		System.out.println("diffMinNegative: " + diffMinNegative + "\ndiff_500to_400: " + diff_500to_400 + "\ndiff_400to_300: " + diff_400to_300 + "\ndiff_300to_200: "
				+ diff_300to_200 + "\ndiff_200to_100: " + diff_200to_100 + "\ndiff_100to0: " + diff_100to0 + "\ndiff0to100: " + diff0to100 + "\ndiff100to200: " + diff100to200
				+ "\ndiff200to300: " + diff200to300 + "\ndiff300to400: " + diff300to400 + "\ndiff400to500: " + diff400to500 + "\ndiffMaxPositive: " + diffMaxPositive);

		System.out.println("Failure rate : " + (float) (diffMinNegative + diffMaxPositive) / differenceArray.size() * 100);

	}
}
