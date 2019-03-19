package com.vizerium.commons.indicators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.commons.dao.UnitPriceData;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MACDCalculatorTest {

	private float delta = 0.0001f;

	private MACDCalculator unit;

	@Before
	public void setUp() throws Exception {
		unit = new MACDCalculator();
	}

	@Test
	public void testCalculate01_Success() {
		List<UnitPriceData> ohlcData = getOHLCData(40);
		MACD macd = unit.calculate(ohlcData);
		System.out.println(macd);

		Assert.assertEquals(58.1289f, macd.getValue(), delta);
		Assert.assertEquals(76.72547778f, macd.getSignalValue(), delta);
		Assert.assertEquals(-18.59657778f, macd.getHistogramLength(), delta);
	}

	@Test
	public void testCalculate12_SizeLessThanSmoothingPeriod() {

		List<UnitPriceData> ohlcData = getOHLCData(5);
		MACD macd = unit.calculate(ohlcData);
		System.out.println(macd);

		Assert.assertEquals(104.7676f, macd.getValue(), delta);
		Assert.assertEquals(Float.NaN, macd.getSignalValue(), delta);
		Assert.assertEquals(Float.NaN, macd.getHistogramLength(), delta);
	}

	@Test
	public void testCalculate12_SizeMoreThanSmoothingPeriod() {

		List<UnitPriceData> ohlcData = getOHLCData(51);
		MACD macd = unit.calculate(ohlcData);
		System.out.println(macd);

		Assert.assertEquals(10.916f, macd.getValue(), delta);
		Assert.assertEquals(-2.477022222f, macd.getSignalValue(), delta);
		Assert.assertEquals(13.39302222f, macd.getHistogramLength(), delta);
	}

	private List<UnitPriceData> getOHLCData(int count) {

		List<UnitPriceData> ohlcData = new ArrayList<UnitPriceData>();
		try {
			File testDataFile = new File("src/test/resources/testData_macd_calculation.csv");

			BufferedReader br = new BufferedReader(new FileReader(testDataFile));
			String dataLine;
			int i = 0;
			while ((dataLine = br.readLine()) != null && i++ < count) {
				String[] dataLineDetails = dataLine.split(",");
				ohlcData.add(new UnitPriceData(dataLineDetails));
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Assert.fail(ioe.getMessage());
		}
		return ohlcData;
	}
}
