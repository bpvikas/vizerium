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
public class CsvHistoricalDataVolatilityCalculator_BankIndex2Week_Test extends CsvHistoricalDataVolatilityCalculatorTest {

	private CsvHistoricalDataVolatilityCalculator unit;

	private float standardDeviationMultipleOfBankIndex = 1.176f;

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
	public void test201701ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 1);
	}

	@Test
	public void test201702ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 2);
	}

	@Test
	public void test201703ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 3);
	}

	@Test
	public void test201704ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 4);
	}

	@Test
	public void test201705ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 5);
	}

	@Test
	public void test201706ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 6);
	}

	@Test
	public void test201707ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 7);
	}

	@Test
	public void test201708ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 8);
	}

	@Test
	public void test201709ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 9);
	}

	@Test
	public void test201710ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 10);
	}

	@Test
	public void test201711ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 11);
	}

	@Test
	public void test201712ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 12);
	}

	@Test
	public void test201713ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 13);
	}

	@Test
	public void test201714ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 14);
	}

	@Test
	public void test201715ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 15);
	}

	@Test
	public void test201716ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 16);
	}

	@Test
	public void test201717ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 17);
	}

	@Test
	public void test201718ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 18);
	}

	@Test
	public void test201719ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 19);
	}

	@Test
	public void test201720ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 20);
	}

	@Test
	public void test201721ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 21);
	}

	@Test
	public void test201722ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 22);
	}

	@Test
	public void test201723ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 23);
	}

	@Test
	public void test201724ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 24);
	}

	@Test
	public void test201725ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 25);
	}

	@Test
	public void test201726ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 26);
	}

	@Test
	public void test201727ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 27);
	}

	@Test
	public void test201728ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 28);
	}

	@Test
	public void test201729ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 29);
	}

	@Test
	public void test201730ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 30);
	}

	@Test
	public void test201731ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 31);
	}

	@Test
	public void test201732ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 32);
	}

	@Test
	public void test201733ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 33);
	}

	@Test
	public void test201734ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 34);
	}

	@Test
	public void test201735ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 35);
	}

	@Test
	public void test201736ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 36);
	}

	@Test
	public void test201737ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 37);
	}

	@Test
	public void test201738ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 38);
	}

	@Test
	public void test201739ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 39);
	}

	@Test
	public void test201740ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 40);
	}

	@Test
	public void test201741ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 41);
	}

	@Test
	public void test201742ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 42);
	}

	@Test
	public void test201743ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 43);
	}

	@Test
	public void test201744ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 44);
	}

	@Test
	public void test201745ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 45);
	}

	@Test
	public void test201746ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 46);
	}

	@Test
	public void test201747ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 47);
	}

	@Test
	public void test201748ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 48);
	}

	@Test
	public void test201749ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 49);
	}

	@Test
	public void test201750ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 50);
	}

	@Test
	public void test201751ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 51);
	}

	@Test
	public void test201752ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2017, 52);
	}

	@Test
	public void test201801ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 1);
	}

	@Test
	public void test201802ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 2);
	}

	@Test
	public void test201803ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 3);
	}

	@Test
	public void test201804ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 4);
	}

	@Test
	public void test201805ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 5);
	}

	@Test
	public void test201806ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 6);
	}

	@Test
	public void test201807ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 7);
	}

	@Test
	public void test201808ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 8);
	}

	@Test
	public void test201809ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 9);
	}

	@Test
	public void test201810ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 10);
	}

	@Test
	public void test201811ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 11);
	}

	@Test
	public void test201812ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 12);
	}

	@Test
	public void test201813ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 13);
	}

	@Test
	public void test201814ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 14);
	}

	@Test
	public void test201815ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 15);
	}

	@Test
	public void test201816ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 16);
	}

	@Test
	public void test201817ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 17);
	}

	@Test
	public void test201818ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 18);
	}

	@Test
	public void test201819ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 19);
	}

	@Test
	public void test201820ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 20);
	}

	@Test
	public void test201821ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 21);
	}

	@Test
	public void test201822ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 22);
	}

	@Test
	public void test201823ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 23);
	}

	@Test
	public void test201824ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 24);
	}

	@Test
	public void test201825ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 25);
	}

	@Test
	public void test201826ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 26);
	}

	@Test
	public void test201827ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 27);
	}

	@Test
	public void test201828ExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior() {
		testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(2018, 28);
	}

	private void testNextWeekExpiryDateIsAtWhichStandardDeviationBasedOnDataTill2WeeksPrior(int year, int week) {

		LocalDate expiryDate = LocalDate.parse(year + "-W" + nf.format(week) + "-1", DateTimeFormatter.ISO_WEEK_DATE).with(DayOfWeek.THURSDAY);

		DateRange historicalDateRange = new DateRange(null, expiryDate.minusWeeks(2));
		DateRange futureDateRange = new DateRange(expiryDate.minusWeeks(2).plusDays(1), expiryDate);

		testExpiryDateIsAtWhichStandardDeviationBasedOnDataPrior(historicalDateRange, futureDateRange, standardDeviationMultipleOfBankIndex);
	}

	@Override
	public CsvHistoricalDataVolatilityCalculator getUnit() {
		return unit;
	}
}
