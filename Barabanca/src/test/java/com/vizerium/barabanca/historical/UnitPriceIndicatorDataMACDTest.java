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

package com.vizerium.barabanca.historical;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MACD;

public class UnitPriceIndicatorDataMACDTest {

	private static final float delta = 0.0045f;

	private UnitPriceIndicatorData<MACD> unit;

	@Before
	public void setUp() throws Exception {
		unit = new UnitPriceIndicatorData<MACD>();
	}

	@Test
	public void testUpdateIndicatorDataInUnitPriceDataList() {
		MACD macd = new MACD();
		// The above startDateTime is set in such a way that it matches the MACD calculations in the MACD test data file.
		// Moving the startDateTime above or below results in correct calculations, but the test data has calculations only from a specific date,
		// which we get by subtracting the lookbackPeriod from the startDate below.
		LocalDateTime startDateTime = LocalDateTime.of(2018, 11, 10, 12, 16);
		LocalDateTime endDateTime = LocalDateTime.of(2018, 11, 30, 15, 16);
		List<UnitPriceData> expectedUnitPriceDataList = getUnitPriceDataWithMACDValues(startDateTime, endDateTime, macd);
		List<UnitPriceData> actualUnitPriceDataList = getUnitPriceDataWithMACDValues(startDateTime, endDateTime, null);
		actualUnitPriceDataList = unit.updateIndicatorDataInUnitPriceDataList(actualUnitPriceDataList, macd);
		assertIndicators(expectedUnitPriceDataList, actualUnitPriceDataList, macd);
	}

	private void assertIndicators(List<UnitPriceData> expectedUnitPriceDataList, List<UnitPriceData> actualUnitPriceDataList, MACD macd) {

		Assert.assertEquals(expectedUnitPriceDataList.size(), actualUnitPriceDataList.size());

		for (int i = 0; i < expectedUnitPriceDataList.size(); i++) {

			UnitPriceData expectedUnitPriceData = expectedUnitPriceDataList.get(i);
			UnitPriceData actualUnitPriceData = actualUnitPriceDataList.get(i);

			float[] expectedUnitPriceIndicatorData = expectedUnitPriceData.getIndicator(macd.getName()).getValues();
			float[] actualUnitPriceIndicatorData = actualUnitPriceData.getIndicator(macd.getName()).getValues();

			Assert.assertEquals(expectedUnitPriceIndicatorData.length, actualUnitPriceIndicatorData.length);
			for (int j = 0; j < expectedUnitPriceIndicatorData.length; j++) {
				Assert.assertEquals(expectedUnitPriceIndicatorData[j], actualUnitPriceIndicatorData[j], delta);
			}
		}
	}

	private List<UnitPriceData> getUnitPriceDataWithMACDValues(LocalDateTime startDateTime, LocalDateTime endDateTime, MACD macd) {
		List<UnitPriceData> unitPriceDataList = new ArrayList<UnitPriceData>();

		try {
			File testDataFile = new File("../commons/src/test/resources/testData_macd_calculation.csv");

			BufferedReader br = new BufferedReader(new FileReader(testDataFile));
			String dataLine = br.readLine(); // The br.readLine() here is to read off the header line.

			while ((dataLine = br.readLine()) != null) {
				String[] dataLineDetails = dataLine.split(",");

				UnitPriceData unitPriceData = new UnitPriceData(dataLineDetails[0], dataLineDetails[1], dataLineDetails[2], dataLineDetails[3], dataLineDetails[4],
						dataLineDetails[5], dataLineDetails[6]);
				unitPriceData.setTimeFormat(TimeFormat._1HOUR);
				if (!unitPriceData.getDateTime().isBefore(startDateTime) && !unitPriceData.getDateTime().isAfter(endDateTime)) {
					if (macd != null) {
						float[] indicatorValues = new float[macd.getUnitPriceIndicatorValuesLength()];
						indicatorValues[0] = macd.getFastMA();
						indicatorValues[1] = Float.parseFloat(dataLineDetails[7]);
						indicatorValues[2] = macd.getSlowMA();
						indicatorValues[3] = Float.parseFloat(dataLineDetails[8]);
						indicatorValues[4] = macd.getSmoothingPeriod();
						indicatorValues[5] = Float.parseFloat(dataLineDetails[9]);
						indicatorValues[6] = Float.parseFloat(dataLineDetails[10]);
						indicatorValues[7] = Float.parseFloat(dataLineDetails[11]);

						unitPriceData.addIndicator(macd.getName(), indicatorValues);
					}
					unitPriceDataList.add(unitPriceData);
				}
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Assert.fail(ioe.getMessage());
		}
		return unitPriceDataList;
	}
}
