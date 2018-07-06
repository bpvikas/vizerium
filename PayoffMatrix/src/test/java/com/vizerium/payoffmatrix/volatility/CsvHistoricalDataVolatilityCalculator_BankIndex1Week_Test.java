package com.vizerium.payoffmatrix.volatility;

import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.payoffmatrix.volatility.CsvHistoricalDataVolatilityCalculator;
import com.vizerium.payoffmatrix.volatility.DateRange;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CsvHistoricalDataVolatilityCalculator_BankIndex1Week_Test extends CsvHistoricalDataVolatilityCalculatorTest {

	private CsvHistoricalDataVolatilityCalculator unit;

	private float standardDeviationMultipleOfBankIndex = 1.099f;

	private NumberFormat nf = NumberFormat.getInstance();

	@Before
	public void setup() {
		unit = new CsvHistoricalDataVolatilityCalculator("BANKINDEX");

		nf.setMinimumIntegerDigits(2);
	}

	@Test
	public void testCalculateVolatility() {
		unit.calculateVolatility(null);
	}

	@Test
	public void test201701ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 1);
	}

	@Test
	public void test201702ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 2);
	}

	@Test
	public void test201703ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 3);
	}

	@Test
	public void test201704ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 4);
	}

	@Test
	public void test201705ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 5);
	}

	@Test
	public void test201706ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 6);
	}

	@Test
	public void test201707ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 7);
	}

	@Test
	public void test201708ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 8);
	}

	@Test
	public void test201709ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 9);
	}

	@Test
	public void test201710ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 10);
	}

	@Test
	public void test201711ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 11);
	}

	@Test
	public void test201712ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 12);
	}

	@Test
	public void test201713ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 13);
	}

	@Test
	public void test201714ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 14);
	}

	@Test
	public void test201715ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 15);
	}

	@Test
	public void test201716ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 16);
	}

	@Test
	public void test201717ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 17);
	}

	@Test
	public void test201718ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 18);
	}

	@Test
	public void test201719ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 19);
	}

	@Test
	public void test201720ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 20);
	}

	@Test
	public void test201721ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 21);
	}

	@Test
	public void test201722ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 22);
	}

	@Test
	public void test201723ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 23);
	}

	@Test
	public void test201724ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 24);
	}

	@Test
	public void test201725ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 25);
	}

	@Test
	public void test201726ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 26);
	}

	@Test
	public void test201727ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 27);
	}

	@Test
	public void test201728ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 28);
	}

	@Test
	public void test201729ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 29);
	}

	@Test
	public void test201730ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 30);
	}

	@Test
	public void test201731ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 31);
	}

	@Test
	public void test201732ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 32);
	}

	@Test
	public void test201733ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 33);
	}

	@Test
	public void test201734ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 34);
	}

	@Test
	public void test201735ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 35);
	}

	@Test
	public void test201736ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 36);
	}

	@Test
	public void test201737ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 37);
	}

	@Test
	public void test201738ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 38);
	}

	@Test
	public void test201739ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 39);
	}

	@Test
	public void test201740ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 40);
	}

	@Test
	public void test201741ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 41);
	}

	@Test
	public void test201742ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 42);
	}

	@Test
	public void test201743ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 43);
	}

	@Test
	public void test201744ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 44);
	}

	@Test
	public void test201745ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 45);
	}

	@Test
	public void test201746ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 46);
	}

	@Test
	public void test201747ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 47);
	}

	@Test
	public void test201748ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 48);
	}

	@Test
	public void test201749ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 49);
	}

	@Test
	public void test201750ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 50);
	}

	@Test
	public void test201751ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 51);
	}

	@Test
	public void test201752ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2017, 52);
	}

	@Test
	public void test201801ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 1);
	}

	@Test
	public void test201802ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 2);
	}

	@Test
	public void test201803ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 3);
	}

	@Test
	public void test201804ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 4);
	}

	@Test
	public void test201805ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 5);
	}

	@Test
	public void test201806ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 6);
	}

	@Test
	public void test201807ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 7);
	}

	@Test
	public void test201808ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 8);
	}

	@Test
	public void test201809ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 9);
	}

	@Test
	public void test201810ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 10);
	}

	@Test
	public void test201811ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 11);
	}

	@Test
	public void test201812ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 12);
	}

	@Test
	public void test201813ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 13);
	}

	@Test
	public void test201814ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 14);
	}

	@Test
	public void test201815ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 15);
	}

	@Test
	public void test201816ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 16);
	}

	@Test
	public void test201817ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 17);
	}

	@Test
	public void test201818ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 18);
	}

	@Test
	public void test201819ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 19);
	}

	@Test
	public void test201820ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 20);
	}

	@Test
	public void test201821ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 21);
	}

	@Test
	public void test201822ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 22);
	}

	@Test
	public void test201823ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 23);
	}

	@Test
	public void test201824ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 24);
	}

	@Test
	public void test201825ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 25);
	}

	@Test
	public void test201826ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 26);
	}

	@Test
	public void test201827ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 27);
	}

	@Test
	public void test201828ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(2018, 28);
	}

	private void testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill1WeekPrior(int year, int week) {

		LocalDate expiryDate = LocalDate.parse(year + "-W" + nf.format(week) + "-1", DateTimeFormatter.ISO_WEEK_DATE).with(DayOfWeek.THURSDAY);

		DateRange historicalDateRange = new DateRange(null, expiryDate.minusWeeks(1));
		DateRange futureDateRange = new DateRange(expiryDate.minusWeeks(1).plusDays(1), expiryDate);

		testExpiryDateIsAtWhichStandardDeviationBasedOnDataPrior(historicalDateRange, futureDateRange, standardDeviationMultipleOfBankIndex);
	}

	@Override
	public CsvHistoricalDataVolatilityCalculator getUnit() {
		return unit;
	}
}
