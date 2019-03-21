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

import com.vizerium.commons.dao.UnitPrice;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DirectionalSystemCalculatorTest {

	private float delta = 0.0001f;

	private DirectionalSystemCalculator unit;

	@Before
	public void setUp() throws Exception {
		unit = new DirectionalSystemCalculator();
	}

	@Test
	public void testCalculate01_Success() {
		List<UnitPrice> ohlcData = getOHLCData(21);
		DirectionalSystem dms = unit.calculate(ohlcData);
		System.out.println(dms);

		Assert.assertEquals(58.1289f, dms.getPlusDI(), delta);
		Assert.assertEquals(76.72547778f, dms.getMinusDI(), delta);
		Assert.assertEquals(-18.59657778f, dms.getSmoothedPlusDI(), delta);
		Assert.assertEquals(58.1289f, dms.getSmoothedMinusDI(), delta);
		Assert.assertEquals(76.72547778f, dms.getAdx(), delta);
	}

	private List<UnitPrice> getOHLCData(int count) {

		List<UnitPrice> ohlcData2 = new ArrayList<UnitPrice>();
		try {
			File testDataFile = new File("src/test/resources/testData_dms_adx_calculation.csv");

			BufferedReader br = new BufferedReader(new FileReader(testDataFile));
			String dataLine;
			int i = 0;
			while ((dataLine = br.readLine()) != null && i++ < count) {
				String[] dataLineDetails = dataLine.split(",");
				ohlcData2.add(new UnitPrice(Float.parseFloat(dataLineDetails[3]), Float.parseFloat(dataLineDetails[4]), Float.parseFloat(dataLineDetails[5]), Float
						.parseFloat(dataLineDetails[6])));

			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Assert.fail(ioe.getMessage());
		}
		return ohlcData2;
	}
}
