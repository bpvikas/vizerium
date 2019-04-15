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
public class StochasticMomentumCalculatorTest {

	private static final float delta = 0.0009f;

	private StochasticMomentumCalculator unit;

	private List<UnitPrice> unitPrices;

	@Before
	public void setUp() throws Exception {
		unit = new StochasticMomentumCalculator();
		unitPrices = new ArrayList<UnitPrice>();
	}

	@After
	public void tearDown() throws Exception {
		unit = null;
		unitPrices = null;
	}

	@Test
	public void testCalculate11_Success() {
		testStochasticCalculations(128);
	}

	@Test
	public void testCalculate12_SizeLessThanLookback() {
		testStochasticCalculations(8);
	}

	@Test
	public void testCalculate12_SizeLessThanLookbackPlusSmoothMA1() {
		testStochasticCalculations(11);
	}

	@Test
	public void testCalculate14_SizeLessThanLookbackPlusSmoothMA1PlusDoubleSmoothMA() {
		testStochasticCalculations(13);
	}

	@Test
	public void testCalculate15_SizeGreaterThanLookbackPlusSmoothMA1PlusDoubleSmoothMAAndLessThanSignalMA() {
		testStochasticCalculations(19);
	}

	@Test
	public void testCalculate16_SizeGreaterThanLookbackPlusSmoothMA1PlusDoubleSmoothMAAndSignalMA() {
		testStochasticCalculations(85);
	}

	private void testStochasticCalculations(int count) {
		float[][] stochasticValues = getOHLCDataAndStochasticMomentumValues(count);
		StochasticMomentum sm = unit.calculate(unitPrices);
		assertArrays(sm, stochasticValues, count);
	}

	private void assertArrays(StochasticMomentum sm, float[][] stochasticMomentumValues, int count) {
		float[] smi = sm.getSmiArray();
		float[] signal = sm.getSignalArray();

		for (int i = 0; i < count; i++) {
			Assert.assertEquals(stochasticMomentumValues[0][i], smi[i], delta);
			Assert.assertEquals(stochasticMomentumValues[1][i], signal[i], delta);
		}
	}

	private float[][] getOHLCDataAndStochasticMomentumValues(int count) {
		float[] smiArray = new float[count];
		float[] signalArray = new float[count];
		try {
			File testDataFile = new File("src/test/resources/testData_stochastic_mtm_calculation.csv");

			BufferedReader br = new BufferedReader(new FileReader(testDataFile));
			String dataLine = br.readLine(); // This is to read off the header line.
			int i = 0;
			while ((dataLine = br.readLine()) != null && i < count) {
				String[] dataLineDetails = dataLine.split(",");
				unitPrices.add(new UnitPrice(Float.parseFloat(dataLineDetails[3]), Float.parseFloat(dataLineDetails[4]), Float.parseFloat(dataLineDetails[5]), Float
						.parseFloat(dataLineDetails[6])));
				smiArray[i] = Float.parseFloat(dataLineDetails[17]);
				signalArray[i] = Float.parseFloat(dataLineDetails[18]);
				i++;
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Assert.fail(ioe.getMessage());
		}
		return new float[][] { smiArray, signalArray };
	}
}
