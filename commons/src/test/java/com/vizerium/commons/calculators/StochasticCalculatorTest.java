package com.vizerium.commons.calculators;

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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StochasticCalculatorTest {

	private StochasticCalculator unit;

	@Before
	public void setUp() throws Exception {
		unit = new StochasticCalculator();
	}

	@Test
	public void testCalculate01_Success() {
		List<UnitPrice> ohlcData = createOHLCData(21);
		Stochastic sc = unit.calculate(ohlcData, 14);
		System.out.println(sc);

		Assert.assertEquals(79.41176f, sc.getFastPercentK(), 0.0001f);
		Assert.assertEquals(79.80603f, sc.getFastPercentD(), 0.0001f);
		Assert.assertEquals(79.80603f, sc.getSlowPercentK(), 0.0001f);
		Assert.assertEquals(80.22602f, sc.getSlowPercentD(), 0.0001f);
	}

	@Test
	public void testCalculate02_SizeLessThanLookback() {
		List<UnitPrice> ohlcData = createOHLCData(10);
		Stochastic sc = unit.calculate(ohlcData, 14);
		System.out.println(sc);

		Assert.assertEquals(0.0f, sc.getFastPercentK(), 0.0f);
		Assert.assertEquals(0.0f, sc.getFastPercentD(), 0.0f);
		Assert.assertEquals(0.0f, sc.getSlowPercentK(), 0.0f);
		Assert.assertEquals(0.0f, sc.getSlowPercentD(), 0.0f);
	}

	@Test
	public void testCalculate03_SizeBetweenLookbackAndFirstMA() {
		List<UnitPrice> ohlcData = createOHLCData(15);
		Stochastic sc = unit.calculate(ohlcData, 14);
		System.out.println(sc);

		Assert.assertEquals(82.14286f, sc.getFastPercentK(), 0.0001f);
		Assert.assertEquals(0.0f, sc.getFastPercentD(), 0.0f);
		Assert.assertEquals(0.0f, sc.getSlowPercentK(), 0.0f);
		Assert.assertEquals(0.0f, sc.getSlowPercentD(), 0.0f);
	}

	@Test
	public void testCalculate04_SizeBetweenFirstMAAndSecondMA() {
		List<UnitPrice> ohlcData = createOHLCData(17);
		Stochastic sc = unit.calculate(ohlcData, 14);
		System.out.println(sc);

		Assert.assertEquals(81.11111f, sc.getFastPercentK(), 0.0001f);
		Assert.assertEquals(81.62105f, sc.getFastPercentD(), 0.0001f);
		Assert.assertEquals(81.62105f, sc.getSlowPercentK(), 0.0001f);
		Assert.assertEquals(0.0f, sc.getSlowPercentD(), 0.0f);
	}

	@Test
	public void testCalculate05_SizeGreaterThanSecondMA() {
		List<UnitPrice> ohlcData = createOHLCData(19);
		Stochastic sc = unit.calculate(ohlcData, 14);
		System.out.println(sc);

		Assert.assertEquals(80.20833f, sc.getFastPercentK(), 0.0001f);
		Assert.assertEquals(80.65487f, sc.getFastPercentD(), 0.0001f);
		Assert.assertEquals(80.65487f, sc.getSlowPercentK(), 0.0001f);
		Assert.assertEquals(81.13258f, sc.getSlowPercentD(), 0.0001f);
	}

	@Test
	public void testCalculate11_Success() {
		List<UnitPrice> ohlcData = createOHLCData2(24);
		Stochastic sc = unit.calculate(ohlcData, 14);
		System.out.println(sc);

		Assert.assertEquals(88.87155f, sc.getFastPercentK(), 0.0001f);
		Assert.assertEquals(90.30913f, sc.getFastPercentD(), 0.0001f);
		Assert.assertEquals(90.30913f, sc.getSlowPercentK(), 0.0001f);
		Assert.assertEquals(79.31948f, sc.getSlowPercentD(), 0.0001f);
	}

	@Test
	public void testCalculate12_SizeLessThanLookback() {
		List<UnitPrice> ohlcData = createOHLCData2(10);
		Stochastic sc = unit.calculate(ohlcData, 14);
		System.out.println(sc);

		Assert.assertEquals(0.0f, sc.getFastPercentK(), 0.0f);
		Assert.assertEquals(0.0f, sc.getFastPercentD(), 0.0f);
		Assert.assertEquals(0.0f, sc.getSlowPercentK(), 0.0f);
		Assert.assertEquals(0.0f, sc.getSlowPercentD(), 0.0f);
	}

	@Test
	public void testCalculate13_SizeBetweenLookbackAndFirstMA() {
		List<UnitPrice> ohlcData = createOHLCData2(15);
		Stochastic sc = unit.calculate(ohlcData, 14);
		System.out.println(sc);

		Assert.assertEquals(67.19764f, sc.getFastPercentK(), 0.0001f);
		Assert.assertEquals(0.0f, sc.getFastPercentD(), 0.0f);
		Assert.assertEquals(0.0f, sc.getSlowPercentK(), 0.0f);
		Assert.assertEquals(0.0f, sc.getSlowPercentD(), 0.0f);
	}

	@Test
	public void testCalculate14_SizeBetweenFirstMAAndSecondMA() {
		List<UnitPrice> ohlcData = createOHLCData2(17);
		Stochastic sc = unit.calculate(ohlcData, 14);
		System.out.println(sc);

		Assert.assertEquals(82.50781f, sc.getFastPercentK(), 0.0001f);
		Assert.assertEquals(77.04576f, sc.getFastPercentD(), 0.0001f);
		Assert.assertEquals(77.04576f, sc.getSlowPercentK(), 0.0001f);
		Assert.assertEquals(0.0f, sc.getSlowPercentD(), 0.0f);
	}

	@Test
	public void testCalculate15_SizeGreaterThanSecondMA() {
		List<UnitPrice> ohlcData = createOHLCData2(19);
		Stochastic sc = unit.calculate(ohlcData, 14);
		System.out.println(sc);

		Assert.assertEquals(79.89300f, sc.getFastPercentK(), 0.0001f);
		Assert.assertEquals(78.38823f, sc.getFastPercentD(), 0.0001f);
		Assert.assertEquals(78.38823f, sc.getSlowPercentK(), 0.0001f);
		Assert.assertEquals(78.11173f, sc.getSlowPercentD(), 0.0001f);
	}

	private List<UnitPrice> createOHLCData(int count) {

		List<UnitPrice> ohlcData = new ArrayList<UnitPrice>();
		for (float i = 1; i <= count; i++) {
			float j = 100 * i;
			ohlcData.add(new UnitPrice(j * 0.8f, j * 1.2f, j * 0.6f, j * 1.0f));
		}
		return ohlcData;

		// @formatter:off
		//				O		H		L		C		HH			LL			fast %K		fast %D
		//																					slow %K"	slow %D
		//		100		80		120		60		100					
		//		200		160		240		120		200					
		//		300		240		360		180		300					
		//		400		320		480		240		400					
		//		500		400		600		300		500					
		//		600		480		720		360		600					
		//		700		560		840		420		700					
		//		800		640		960		480		800					
		//		900		720		1080	540		900					
		//		1000	800		1200	600		1000					
		//		1100	880		1320	660		1100					
		//		1200	960		1440	720		1200					
		//		1300	1040	1560	780		1300					
		//		1400	1120	1680	840		1400	1680.00000	60.00000	82.71605		
		//		1500	1200	1800	900		1500	1800.00000	120.00000	82.14286		
		//		1600	1280	1920	960		1600	1920.00000	180.00000	81.60920	82.15603	
		//		1700	1360	2040	1020	1700	2040.00000	240.00000	81.11111	81.62105	
		//		1800	1440	2160	1080	1800	2160.00000	300.00000	80.64516	81.12182	81.63297
		//		1900	1520	2280	1140	1900	2280.00000	360.00000	80.20833	80.65487	81.13258
		//		2000	1600	2400	1200	2000	2400.00000	420.00000	79.79798	80.21716	80.66462
		//		2100	1680	2520	1260	2100	2520.00000	480.00000	79.41176	79.80603	80.22602
		// @formatter:on
	}

	private List<UnitPrice> createOHLCData2(int count) {

		List<UnitPrice> ohlcData2 = new ArrayList<UnitPrice>();
		try {
			File testDataFile = new File("src/test/resources/testData_stochastic_calculation.csv");

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

	// @formatter:off
		//		o			h			l			c			hh				ll			c -ll 		hh-ll			fast %K		fast %D
		//																															slow %K"	slow %D
		//	26878.00	26934.85	26755.95	26787.85							
		//	26787.60	26818.70	26729.20	26751.05							
		//	26750.75	26777.25	26723.65	26753.85							
		//	26754.85	26881.55	26740.05	26817.00							
		//	26817.25	26875.90	26783.25	26827.15							
		//	26827.80	26838.25	26676.95	26728.30							
		//	26727.05	26734.75	26703.05	26714.75							
		//	26596.65	26643.35	26414.45	26430.40							
		//	26429.50	26512.15	26408.15	26494.05							
		//	26495.15	26539.00	26458.20	26529.15							
		//	26528.20	26571.40	26503.10	26551.25							
		//	26553.30	26905.30	26533.20	26836.55							
		//	26836.90	27008.55	26836.90	26964.35							
		//	26969.45	27029.15	26965.10	26986.80	27029.15000		26408.15000		578.65000	621.00000		93.18035		
		//	27206.90	27239.95	26945.90	26967.10	27239.95000		26408.15000		558.95000	831.80000		67.19764		
		//	26968.50	27158.45	26967.95	27085.50	27239.95000		26408.15000		677.35000	831.80000		81.43183	80.60328	
		//	27086.35	27105.50	27045.40	27094.45	27239.95000		26408.15000		686.30000	831.80000		82.50781	77.04576	
		//	27092.80	27110.00	26939.15	27013.40	27239.95000		26408.15000		605.25000	831.80000		72.76389	78.90118	78.85007
		//	27010.45	27113.25	26972.15	27072.70	27239.95000		26408.15000		664.55000	831.80000		79.89300	78.38823	78.11173
		//	27073.85	27079.10	26845.10	26867.50	27239.95000		26408.15000		459.35000	831.80000		55.22361	69.29350	75.52764
		//	26861.45	26918.95	26848.30	26878.55	27239.95000		26408.15000		470.40000	831.80000		56.55206	63.88956	70.52376
		//	27071.15	27197.95	27039.60	27178.10	27239.95000		26408.15000		769.95000	831.80000		92.56432	68.11333	67.09880
		//	27179.75	27213.45	27136.25	27157.80	27239.95000		26458.20000		699.60000	781.75000		89.49153	79.53597	70.51295
		//	27156.80	27183.45	27092.40	27157.95	27239.95000		26503.10000		654.85000	736.85000		88.87155	90.30913	79.31948
		//	27154.95	27181.05	27131.80	27160.85	27239.95000		26533.20000		627.65000	706.75000		88.80792	89.05700	86.30070
		//	27162.70	27208.80	27127.60	27141.55	27239.95000		26836.90000		304.65000	403.05000		75.58616	84.42188	87.92934
		//	27138.90	27180.90	27112.60	27115.75	27239.95000		26845.10000		270.65000	394.85000		68.54502	77.64637	83.70841
		//	27116.55	27138.60	27098.40	27125.25	27239.95000		26845.10000		280.15000	394.85000		70.95099	71.69406	77.92077
		//	27275.45	27286.50	27141.95	27189.60	27286.50000		26845.10000		344.50000	441.40000		78.04712	72.51438	73.95160
		//	27189.10	27218.55	27163.45	27177.10	27286.50000		26845.10000		332.00000	441.40000		75.21522	74.73778	72.98207
		//	27177.55	27192.50	27139.90	27147.50	27286.50000		26845.10000		302.40000	441.40000		68.50929	73.92388	73.72535
		//	27148.45	27162.25	27105.00	27138.60	27286.50000		26845.10000		293.50000	441.40000		66.49298	70.07250	72.91139
		//	27139.05	27198.40	27131.65	27168.80	27286.50000		26845.10000		323.70000	441.40000		73.33484	69.44570	71.14736
		//	27168.00	27197.40	27141.60	27159.05	27286.50000		26848.30000		310.75000	438.20000		70.91511	70.24764	69.92195
		//	27165.40	27181.00	27147.10	27160.20	27286.50000		27039.60000		120.60000	246.90000		48.84569	64.36521	68.01952
		// @formatter:on

}
