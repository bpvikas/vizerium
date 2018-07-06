package com.vizerium.payoffmatrix.volatility;

import org.junit.Assert;

import com.vizerium.payoffmatrix.exchange.Exchanges;
import com.vizerium.payoffmatrix.volatility.CsvHistoricalDataVolatilityCalculator;
import com.vizerium.payoffmatrix.volatility.DateRange;
import com.vizerium.payoffmatrix.volatility.Volatility;

public abstract class CsvHistoricalDataVolatilityCalculatorTest {

	protected void testExpiryDateIsAtWhichStandardDeviationBasedOnDataPrior(DateRange historicalDateRange, DateRange futureDateRange, float standardDeviationMultiple) {

		System.out.println("historicalDateRange : " + historicalDateRange + ", futureDateRange" + futureDateRange);
		Volatility volatility = getUnit().calculateVolatility(historicalDateRange);
		volatility.setStandardDeviationMultiple(standardDeviationMultiple);
		volatility.calculateUnderlyingRange(historicalDateRange.getEndDate(), futureDateRange.getEndDate(), Exchanges.get("TEI"), 25.0f);
		System.out.println(volatility.getUnderlyingRange());

		float[] closingPrices = getUnit().readClosingPrices(futureDateRange);
		int upperEndViolations = 0, lowerEndViolations = 0;
		for (float closingPrice : closingPrices) {
			if (closingPrice > volatility.getUnderlyingRange().getHigh()) {
				System.out.println("The closing price is greater than the top end of the range. " + closingPrice);
				++upperEndViolations;
			}
			if (closingPrice < volatility.getUnderlyingRange().getLow()) {
				System.out.println("The closing price is lesser than the bottom end of the range. " + closingPrice);
				++lowerEndViolations;
			}
		}
		if (closingPrices[closingPrices.length - 1] > volatility.getUnderlyingRange().getHigh()) {
			Assert.fail("The expiry date closing price is greater than the top end of the range. " + closingPrices[closingPrices.length - 1] + " "
					+ printUpperAndLowerEndViolations(upperEndViolations, lowerEndViolations));
		} else if (closingPrices[closingPrices.length - 1] < volatility.getUnderlyingRange().getLow()) {
			Assert.fail("The expiry date closing price is lesser than the bottom end of the range. " + closingPrices[closingPrices.length - 1] + " "
					+ printUpperAndLowerEndViolations(upperEndViolations, lowerEndViolations));
		} else if (upperEndViolations > 0 || lowerEndViolations > 0) {
			Assert.fail(printUpperAndLowerEndViolations(upperEndViolations, lowerEndViolations));
		}
	}

	public abstract CsvHistoricalDataVolatilityCalculator getUnit();

	private String printUpperAndLowerEndViolations(int upperEndViolations, int lowerEndViolations) {
		return "upperEndViolations : " + upperEndViolations + ", lowerEndViolations : " + lowerEndViolations;
	}

}
