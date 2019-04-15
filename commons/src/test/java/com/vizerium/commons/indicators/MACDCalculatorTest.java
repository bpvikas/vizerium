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
public class MACDCalculatorTest {

	private static final float delta = 0.01f;

	private MACDCalculator unit;

	private List<UnitPrice> unitPrices;

	@Before
	public void setUp() throws Exception {
		unit = new MACDCalculator();
		unitPrices = new ArrayList<UnitPrice>();
	}

	@After
	public void tearDown() throws Exception {
		unit = null;
		unitPrices = null;
	}

	@Test
	public void testCalculate01_Success() {
		testMACDCalculations(108);
	}

	@Test
	public void testCalculate02_SizeLessThanFastMA() {
		testMACDCalculations(8);
	}

	@Test
	public void testCalculate03_SizeGreaterThanFastMALessThanSlowMA() {
		testMACDCalculations(18);
	}

	@Test
	public void testCalculate04_SizeGreaterThanSlowMALessThanSignal() {
		testMACDCalculations(28);
	}

	@Test
	public void testCalculate05_SizeGreaterThanSignal() {
		testMACDCalculations(58);
	}

	private void testMACDCalculations(int count) {
		float[][] macdValues = getOHLCDataAndMACDValues(count);
		MACD macd = unit.calculate(unitPrices);
		assertArrays(macd, macdValues, count);
	}

	private void assertArrays(MACD macd, float[][] macdValues, int count) {

		float[] fastMA = macd.getFastMAValues();
		float[] slowMA = macd.getSlowMAValues();
		float[] differenceMA = macd.getDifferenceMAValues();
		float[] signalMA = macd.getSignalValues();
		float[] histogramMA = macd.getHistogramValues();

		for (int i = 0; i < count; i++) {
			Assert.assertEquals(macdValues[1][i], fastMA[i], delta);
			Assert.assertEquals(macdValues[2][i], slowMA[i], delta);
			Assert.assertEquals(macdValues[3][i], differenceMA[i], delta);
			Assert.assertEquals(macdValues[4][i], signalMA[i], delta);
			Assert.assertEquals(macdValues[5][i], histogramMA[i], delta);
		}
	}

	private float[][] getOHLCDataAndMACDValues(int count) {

		float[] closingPrices = new float[count];
		float[] fastMA = new float[count];
		float[] slowMA = new float[count];
		float[] differenceMA = new float[count];
		float[] signalMA = new float[count];
		float[] histogramMA = new float[count];

		try {
			File testDataFile = new File("src/test/resources/testData_macd_calculation.csv");

			BufferedReader br = new BufferedReader(new FileReader(testDataFile));
			String dataLine = br.readLine(); // The br.readLine() here is to read off the header line.
			int i = 0;
			while ((dataLine = br.readLine()) != null && i < count) {
				String[] dataLineDetails = dataLine.split(",");
				unitPrices.add(new UnitPrice(Float.parseFloat(dataLineDetails[3]), Float.parseFloat(dataLineDetails[4]), Float.parseFloat(dataLineDetails[5]), Float
						.parseFloat(dataLineDetails[6])));
				closingPrices[i] = Float.parseFloat(dataLineDetails[6]);
				fastMA[i] = Float.parseFloat(dataLineDetails[7]);
				slowMA[i] = Float.parseFloat(dataLineDetails[8]);
				differenceMA[i] = Float.parseFloat(dataLineDetails[9]);
				signalMA[i] = Float.parseFloat(dataLineDetails[10]);
				histogramMA[i] = Float.parseFloat(dataLineDetails[11]);
				i++;
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Assert.fail(ioe.getMessage());
		}
		return new float[][] { closingPrices, fastMA, slowMA, differenceMA, signalMA, histogramMA };
	}
}
