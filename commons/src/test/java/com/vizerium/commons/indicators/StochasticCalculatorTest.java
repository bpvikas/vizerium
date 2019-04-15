/*
 * Copyright 2019 Vizerium, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
public class StochasticCalculatorTest {

	private static final float delta = 0.0005f;

	private StochasticCalculator unit;

	private List<UnitPrice> unitPrices;

	@Before
	public void setUp() throws Exception {
		unit = new StochasticCalculator();
		unitPrices = new ArrayList<UnitPrice>();
	}

	@After
	public void tearDown() throws Exception {
		unit = null;
		unitPrices = null;
	}

	@Test
	public void testCalculate11_Success() {
		testStochasticCalculations(94);
	}

	@Test
	public void testCalculate12_SizeLessThanLookback() {
		testStochasticCalculations(10);
	}

	@Test
	public void testCalculate13_SizeBetweenLookbackAndFirstMA() {
		testStochasticCalculations(15);
	}

	@Test
	public void testCalculate14_SizeBetweenFirstMAAndSecondMA() {
		testStochasticCalculations(17);
	}

	@Test
	public void testCalculate15_SizeGreaterThanSecondMA() {
		testStochasticCalculations(59);
	}

	private void testStochasticCalculations(int count) {
		float[][] stochasticValues = getOHLCDataAndStochasticValues(count);
		Stochastic sc = unit.calculate(unitPrices);
		assertArrays(sc, stochasticValues, count);

		sc = new Stochastic(14, 3, MovingAverageType.SIMPLE);
		sc = unit.calculate(unitPrices, sc);
		assertArrays(sc, stochasticValues, count);
	}

	private void assertArrays(Stochastic sc, float[][] stochasticValues, int count) {
		float[] fastPercentK = sc.getFastPercentKArray();
		float[] slowPercentK = sc.getSlowPercentKArray();
		float[] fastPercentD = sc.getFastPercentDArray();
		float[] slowPercentD = sc.getSlowPercentDArray();

		int start = 1;
		if (sc.getMaTypeForCalculatingSlowFromFast() == MovingAverageType.SIMPLE) {
			start = start + 3;
		}

		for (int i = 0; i < count; i++) {
			Assert.assertEquals(stochasticValues[0][i], fastPercentK[i], delta);
			Assert.assertEquals(stochasticValues[start][i], slowPercentK[i], delta);
			Assert.assertEquals(stochasticValues[start + 1][i], fastPercentD[i], delta);
			Assert.assertEquals(stochasticValues[start + 2][i], slowPercentD[i], delta);
		}
	}

	private float[][] getOHLCDataAndStochasticValues(int count) {

		float[] fastPercentK = new float[count];
		float[] slowPercentKEma = new float[count];
		float[] slowPercentDEma = new float[count];
		float[] slowPercentKSma = new float[count];
		float[] slowPercentDSma = new float[count];

		try {
			File testDataFile = new File("src/test/resources/testData_stochastic_calculation.csv");

			BufferedReader br = new BufferedReader(new FileReader(testDataFile));
			String dataLine = br.readLine(); // This is to read off the header line.
			int i = 0;
			while ((dataLine = br.readLine()) != null && i < count) {
				String[] dataLineDetails = dataLine.split(",");
				unitPrices.add(new UnitPrice(Float.parseFloat(dataLineDetails[3]), Float.parseFloat(dataLineDetails[4]), Float.parseFloat(dataLineDetails[5]), Float
						.parseFloat(dataLineDetails[6])));
				fastPercentK[i] = Float.parseFloat(dataLineDetails[11]);
				slowPercentKEma[i] = Float.parseFloat(dataLineDetails[12]);
				slowPercentDEma[i] = Float.parseFloat(dataLineDetails[13]);
				slowPercentKSma[i] = Float.parseFloat(dataLineDetails[14]);
				slowPercentDSma[i] = Float.parseFloat(dataLineDetails[15]);
				i++;
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Assert.fail(ioe.getMessage());
		}
		return new float[][] { fastPercentK, slowPercentKEma, slowPercentKEma, slowPercentDEma, slowPercentKSma, slowPercentKSma, slowPercentDSma };
	}
}
