package com.vizerium.barabanca.historical;

import org.junit.Before;
import org.junit.Test;

public class ClosingPricesWithTrendCheckTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test02_BankNiftyDailyChartWithClosingPricesAndTrendCheck() {
		testScripWithClosingPricesAndTrendCheck("BANKNIFTY", TimeFormat._1DAY, 2018, 2018);
	}

	private void testScripWithClosingPricesAndTrendCheck(String string, TimeFormat timeFormat, int i, int j) {
		// TODO Auto-generated method stub

	}
}
