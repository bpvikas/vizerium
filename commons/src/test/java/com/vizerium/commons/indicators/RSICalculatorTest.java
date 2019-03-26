package com.vizerium.commons.indicators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RSICalculatorTest {

	private static final float delta = 0.0007f;

	private RSICalculator unit;

	@Before
	public void setUp() throws Exception {
		unit = new RSICalculator();
	}

	@Test
	public void testCalculate01_Success() {
		testRSICalculations(1008);
	}

	@Test
	public void testCalculate02_SizeLessThanLookbackPeriod() {
		testRSICalculations(8);
	}

	@Test
	public void testCalculate03_SizeGreaterThanLookbackPeriod() {
		testRSICalculations(208);
	}

	private void testRSICalculations(int count) {
		float[][] closingPricesAndExpectedRSIValues = getClosingPricesAndRSIValues(count);
		RSI rsi = unit.calculate(closingPricesAndExpectedRSIValues[0]);
		assertArrays(closingPricesAndExpectedRSIValues[1], rsi.getValues(), count);
	}

	private void assertArrays(float[] rsiExpectedValues, float[] rsiActualValues, int count) {
		for (int i = 0; i < count; i++) {
			Assert.assertEquals(rsiExpectedValues[i], rsiActualValues[i], delta);
		}
	}

	private float[][] getClosingPricesAndRSIValues(int count) {
		float[] closingPrices = new float[count];
		float[] rsiExpectedValues = new float[count];

		try {
			File testDataFile = new File("src/test/resources/testData_rsi_calculation.csv");

			BufferedReader br = new BufferedReader(new FileReader(testDataFile));
			String dataLine = br.readLine(); // The br.readLine() here is to read off the header line.
			int i = 0;
			while ((dataLine = br.readLine()) != null && i < count) {
				String[] dataLineDetails = dataLine.split(",");
				closingPrices[i] = Float.parseFloat(dataLineDetails[6]);
				rsiExpectedValues[i] = Float.parseFloat(dataLineDetails[11]);
				i++;
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Assert.fail(ioe.getMessage());
		}
		return new float[][] { closingPrices, rsiExpectedValues };
	}
}
