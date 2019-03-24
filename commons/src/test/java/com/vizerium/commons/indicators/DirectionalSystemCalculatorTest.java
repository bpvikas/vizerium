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
public class DirectionalSystemCalculatorTest {

	private static final float delta = 0.001f;

	private DirectionalSystemCalculator unit;

	private List<UnitPrice> unitPrices;

	@Before
	public void setUp() throws Exception {
		unit = new DirectionalSystemCalculator();
		unitPrices = new ArrayList<UnitPrice>();
	}

	@After
	public void tearDown() throws Exception {
		unitPrices = null;
		unit = null;
	}

	@Test
	public void testCalculate01_Success() {
		testDSCalculations(301);
	}

	@Test
	public void testCalculate02_SizeLessThan1Smooth() {
		testDSCalculations(11);
	}

	@Test
	public void testCalculate03_SizeGreaterThan1SmoothLessThan2Smooth() {
		testDSCalculations(28);
	}

	@Test
	public void testCalculate04_SizeGreaterThan2Smooth() {
		testDSCalculations(151);
	}

	private void testDSCalculations(int count) {
		float[][] dsValues = getOHLCDataAndDSValues(count);
		DirectionalSystem ds = unit.calculate(unitPrices);
		assertArrays(ds, dsValues, count);
	}

	private void assertArrays(DirectionalSystem ds, float[][] dsValues, int count) {

		float[] smoothedPlusDI = ds.getSmoothedPlusDI();
		float[] smoothedMinusDI = ds.getSmoothedMinusDI();
		float[] adx = ds.getAdx();

		for (int i = 0; i < count; i++) {
			Assert.assertEquals(dsValues[0][i], smoothedPlusDI[i], delta);
			Assert.assertEquals(dsValues[1][i], smoothedMinusDI[i], delta);
			Assert.assertEquals(dsValues[2][i], adx[i], delta);
		}
	}

	private float[][] getOHLCDataAndDSValues(int count) {

		float[] smoothedPlusDI = new float[count];
		float[] smoothedMinusDI = new float[count];
		float[] adx = new float[count];

		try {
			File testDataFile = new File("src/test/resources/testData_dms_calculation_cs.csv");

			BufferedReader br = new BufferedReader(new FileReader(testDataFile));
			String dataLine = br.readLine(); // Read off the header line
			br.readLine(); // Read off the first formula line (kept for self reference)
			br.readLine(); // Read off the second formula line (kept for self reference)

			int i = 0;
			while ((dataLine = br.readLine()) != null && i < count) {
				String[] dataLineDetails = dataLine.split(",");
				unitPrices.add(new UnitPrice(Float.parseFloat(dataLineDetails[3]), Float.parseFloat(dataLineDetails[4]), Float.parseFloat(dataLineDetails[5]), Float
						.parseFloat(dataLineDetails[6])));
				smoothedPlusDI[i] = Float.parseFloat(dataLineDetails[13]);
				smoothedMinusDI[i] = Float.parseFloat(dataLineDetails[14]);
				adx[i] = Float.parseFloat(dataLineDetails[18]);
				i++;
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Assert.fail(ioe.getMessage());
		}
		return new float[][] { smoothedPlusDI, smoothedMinusDI, adx };
	}
}
