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

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MovingAverageCalculatorTest {

	private static final float delta = 0.006f;

	@Test
	public void test01_CalculateSMA() {
		int count = 30;
		float[][] closingPricesAndMovingAverages = createClosingPricesAndMovingAveragesData(count);
		float sma = MovingAverageCalculator.calculateSMA(closingPricesAndMovingAverages[0], 14);
		Assert.assertEquals(closingPricesAndMovingAverages[1][count - 1], sma, delta);
	}

	@Test
	public void test02_CalculateEMA() {
		int count = 40;
		float[][] closingPricesAndMovingAverages = createClosingPricesAndMovingAveragesData(count);
		float ema = MovingAverageCalculator.calculateEMA(closingPricesAndMovingAverages[0], 14);
		Assert.assertEquals(closingPricesAndMovingAverages[2][count - 1], ema, delta);
	}

	@Test
	public void test03_CalculateWMA() {
		int count = 50;
		float[][] closingPricesAndMovingAverages = createClosingPricesAndMovingAveragesData(count);
		float wma = MovingAverageCalculator.calculateWMA(closingPricesAndMovingAverages[0], 14);
		Assert.assertEquals(closingPricesAndMovingAverages[3][count - 1], wma, delta);
	}

	@Test
	public void test04_CalculateMA() {
		int count = 60;
		float[][] closingPricesAndMovingAverages = createClosingPricesAndMovingAveragesData(count);
		float sma = MovingAverageCalculator.calculateMA(MovingAverageType.SIMPLE, closingPricesAndMovingAverages[0], 14);
		Assert.assertEquals(closingPricesAndMovingAverages[1][count - 1], sma, delta);
		float ema = MovingAverageCalculator.calculateMA(MovingAverageType.EXPONENTIAL, closingPricesAndMovingAverages[0], 14);
		Assert.assertEquals(closingPricesAndMovingAverages[2][count - 1], ema, delta);
		float wma = MovingAverageCalculator.calculateMA(MovingAverageType.WELLESWILDER, closingPricesAndMovingAverages[0], 14);
		Assert.assertEquals(closingPricesAndMovingAverages[3][count - 1], wma, delta);
	}

	@Test
	public void test11_CalculateArraySMA() {
		int count = 70;
		float[][] closingPricesAndMovingAverages = createClosingPricesAndMovingAveragesData(count);
		float[] smaArray = MovingAverageCalculator.calculateArraySMA(closingPricesAndMovingAverages[0], 14);
		Assert.assertEquals(closingPricesAndMovingAverages[0].length, smaArray.length);
		for (int i = 0; i < closingPricesAndMovingAverages[0].length; i++) {
			Assert.assertEquals(closingPricesAndMovingAverages[1][i], smaArray[i], delta);
		}
	}

	@Test
	public void test12_CalculateArrayEMA() {
		int count = 80;
		float[][] closingPricesAndMovingAverages = createClosingPricesAndMovingAveragesData(count);
		float[] emaArray = MovingAverageCalculator.calculateArrayEMA(closingPricesAndMovingAverages[0], 14);
		Assert.assertEquals(closingPricesAndMovingAverages[0].length, emaArray.length);
		for (int i = 0; i < closingPricesAndMovingAverages[0].length; i++) {
			Assert.assertEquals(closingPricesAndMovingAverages[2][i], emaArray[i], delta);
		}
	}

	@Test
	public void test13_CalculateArrayWMA() {
		int count = 90;
		float[][] closingPricesAndMovingAverages = createClosingPricesAndMovingAveragesData(count);
		float[] wmaArray = MovingAverageCalculator.calculateArrayWMA(closingPricesAndMovingAverages[0], 14);
		Assert.assertEquals(closingPricesAndMovingAverages[0].length, wmaArray.length);
		for (int i = 0; i < closingPricesAndMovingAverages[0].length; i++) {
			Assert.assertEquals(closingPricesAndMovingAverages[3][i], wmaArray[i], delta);
		}
	}

	@Test
	public void test14_CalculateArrayMA() {
		int count = 100;
		float[][] closingPricesAndMovingAverages = createClosingPricesAndMovingAveragesData(count);
		float[] smaArray = MovingAverageCalculator.calculateArrayMA(MovingAverageType.SIMPLE, closingPricesAndMovingAverages[0], 14);
		float[] emaArray = MovingAverageCalculator.calculateArrayMA(MovingAverageType.EXPONENTIAL, closingPricesAndMovingAverages[0], 14);
		float[] wmaArray = MovingAverageCalculator.calculateArrayMA(MovingAverageType.WELLESWILDER, closingPricesAndMovingAverages[0], 14);
		Assert.assertEquals(closingPricesAndMovingAverages[0].length, smaArray.length);
		Assert.assertEquals(closingPricesAndMovingAverages[0].length, emaArray.length);
		Assert.assertEquals(closingPricesAndMovingAverages[0].length, wmaArray.length);
		for (int i = 0; i < closingPricesAndMovingAverages[0].length; i++) {
			Assert.assertEquals(closingPricesAndMovingAverages[1][i], smaArray[i], delta);
			Assert.assertEquals(closingPricesAndMovingAverages[2][i], emaArray[i], delta);
			Assert.assertEquals(closingPricesAndMovingAverages[3][i], wmaArray[i], delta);
		}
	}

	private float[][] createClosingPricesAndMovingAveragesData(int count) {
		float[] closingPrices = new float[count];
		float[] smaArray = new float[count];
		float[] emaArray = new float[count];
		float[] wmaArray = new float[count];
		try {
			File testDataFile = new File("src/test/resources/testData_movingaverage_calculation.csv");

			BufferedReader br = new BufferedReader(new FileReader(testDataFile));
			String dataLine = br.readLine(); // For reading off the header line
			int i = 0;
			while ((dataLine = br.readLine()) != null && i < count) {
				String[] dataLineDetails = dataLine.split(",");
				closingPrices[i] = Float.parseFloat(dataLineDetails[3]);
				smaArray[i] = Float.parseFloat(dataLineDetails[4]);
				emaArray[i] = Float.parseFloat(dataLineDetails[5]);
				wmaArray[i] = Float.parseFloat(dataLineDetails[6]);
				i++;
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Assert.fail(ioe.getMessage());
		}
		return new float[][] { closingPrices, smaArray, emaArray, wmaArray };
	}
}
