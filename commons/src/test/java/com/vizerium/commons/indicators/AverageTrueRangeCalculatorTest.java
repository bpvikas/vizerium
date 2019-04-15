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
public class AverageTrueRangeCalculatorTest {

	private static final float delta = 0.0007f;

	private AverageTrueRangeCalculator unit;

	private List<UnitPrice> unitPrices;

	@Before
	public void setUp() throws Exception {
		unit = new AverageTrueRangeCalculator();
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
		AverageTrueRange atr = unit.calculate(unitPrices);
		assertArrays(expectedSmoothedAtrValues, atr.getValues(), count);
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
				smoothedAtr[i] = Float.parseFloat(dataLineDetails[9]);
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
