package com.vizerium.commons.indicators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.commons.dao.UnitPrice;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AverageTrueRangeCalculatorTest {

	private float delta = 0.0007f;

	private List<UnitPrice> unitPrices;

	@Before
	public void setUp() throws Exception {
		unitPrices = new ArrayList<UnitPrice>();
	}

	@After
	public void tearDown() throws Exception {
		unitPrices = null;
	}

	@Test
	public void testCalculate01_Success() {
		testAtrCalculations(76);
	}

	@Test
	public void testCalculate02_SizeLessThanSmoothMA() {
		testAtrCalculations(6);
	}

	@Test
	public void testCalculate05_SizeGreaterThanSmoothMA() {
		testAtrCalculations(46);
	}

	private void testAtrCalculations(int count) {
		float[] expectedSmoothedAtrValues = getOHLCDataAndATRValues(count);
		float[] actualSmoothedAtrValues = AverageTrueRangeCalculator.calculateArray(unitPrices);
		assertArrays(expectedSmoothedAtrValues, actualSmoothedAtrValues, count);
	}

	private void assertArrays(float[] expectedSmoothedAtrValues, float[] actualSmoothedAtrValues, int count) {

		for (int i = 0; i < count; i++) {
			Assert.assertEquals(expectedSmoothedAtrValues[i], actualSmoothedAtrValues[i], delta);
		}
	}

	private float[] getOHLCDataAndATRValues(int count) {
		float[] smoothedAtr = new float[count];

		try {
			File testDataFile = new File("src/test/resources/testData_atr_calculation.csv");

			BufferedReader br = new BufferedReader(new FileReader(testDataFile));
			String dataLine = br.readLine(); // The br.readLine() here is to read off the header line.
			int i = 0;
			while ((dataLine = br.readLine()) != null && i < count) {
				String[] dataLineDetails = dataLine.split(",");
				unitPrices.add(new UnitPrice(Float.parseFloat(dataLineDetails[3]), Float.parseFloat(dataLineDetails[4]), Float.parseFloat(dataLineDetails[5]), Float
						.parseFloat(dataLineDetails[6])));
				smoothedAtr[i] = Float.parseFloat(dataLineDetails[8]);
				i++;
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Assert.fail(ioe.getMessage());
		}
		return smoothedAtr;
	}
}
