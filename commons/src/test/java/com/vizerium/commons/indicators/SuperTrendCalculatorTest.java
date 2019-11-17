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
public class SuperTrendCalculatorTest {

	private static final float delta = 0.002f;

	private SuperTrendCalculator unit;

	private List<UnitPrice> unitPrices;

	@Before
	public void setUp() throws Exception {
		unit = new SuperTrendCalculator();
		unitPrices = new ArrayList<UnitPrice>();
	}

	@After
	public void tearDown() throws Exception {
		unitPrices = null;
	}

	@Test
	public void testCalculate01_Success() {
		testSuperTrendCalculations(1292);
	}

	@Test
	public void testCalculate02_SizeLessThanAtrPeriod() {
		testSuperTrendCalculations(8);
	}

	@Test
	public void testCalculate05_SizeGreaterThanAtrPeriod() {
		testSuperTrendCalculations(152);
	}

	private void testSuperTrendCalculations(int count) {
		float[][] expectedSuperTrendValues = getOHLCDataAndSuperTrendValues(count);
		SuperTrend st = unit.calculate(unitPrices, new SuperTrend(10, 3.0f, MovingAverageType.EXPONENTIAL));
		assertArrays(st, expectedSuperTrendValues, count);
	}

	private void assertArrays(SuperTrend st, float[][] expectedSuperTrendValues, int count) {

		float[] superTrendValues = st.getSuperTrendValues();
		float[] trendValues = st.getTrendValues();
		for (int i = 0; i < count; i++) {
			Assert.assertEquals(expectedSuperTrendValues[0][i], superTrendValues[i], delta);
			Assert.assertEquals(expectedSuperTrendValues[1][i], trendValues[i], delta);
		}
	}

	private float[][] getOHLCDataAndSuperTrendValues(int count) {
		float[] superTrendValues = new float[count];
		float[] trendValues = new float[count];

		try {
			File testDataFile = new File("src/test/resources/testData_supertrend_calculation.csv");

			BufferedReader br = new BufferedReader(new FileReader(testDataFile));
			String dataLine = br.readLine(); // The br.readLine() here is to read off the header line.
			int i = 0;
			while ((dataLine = br.readLine()) != null && i < count) {
				String[] dataLineDetails = dataLine.split(",");
				unitPrices.add(new UnitPrice(Float.parseFloat(dataLineDetails[3]), Float.parseFloat(dataLineDetails[4]), Float.parseFloat(dataLineDetails[5]),
						Float.parseFloat(dataLineDetails[6])));
				superTrendValues[i] = Float.parseFloat(dataLineDetails[23]);
				trendValues[i] = Float.parseFloat(dataLineDetails[25]);
				i++;
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Assert.fail(ioe.getMessage());
		}
		return new float[][] { superTrendValues, trendValues };
	}
}
