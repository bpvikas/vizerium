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

import com.vizerium.commons.dao.UnitPrice;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StochasticMomentumCalculatorTest {

	private StochasticMomentumCalculator unit;

	@Before
	public void setUp() throws Exception {
		unit = new StochasticMomentumCalculator();
	}

	@Test
	public void testCalculate01_Success() {
		List<UnitPrice> ohlcData = createOHLCData(24);
		StochasticMomentum sm = unit.calculate(ohlcData);
		System.out.println(sm);
		Assert.assertEquals(52.68817f, sm.getSmi(), 0.0001f);
		Assert.assertEquals(56.24652277f, sm.getSignal(), 0.0001f);
	}

	@Test
	public void testCalculate02_SizeLessThanLookbackPlusSmoothMA1PlusDoubleSmoothMA() {
		List<UnitPrice> ohlcData = createOHLCData(13);
		StochasticMomentum sm = unit.calculate(ohlcData);
		System.out.println(sm);
		Assert.assertEquals(0.0f, sm.getSmi(), 0.0f);
		Assert.assertEquals(0.0f, sm.getSignal(), 0.0f);
	}

	@Test
	public void testCalculate03_SizeGreaterThanLookbackPlusSmoothMA1PlusDoubleSmoothMAAndLessThanSignalMA() {
		List<UnitPrice> ohlcData = createOHLCData(19);
		StochasticMomentum sm = unit.calculate(ohlcData);
		System.out.println(sm);
		Assert.assertEquals(56.41026f, sm.getSmi(), 0.0001f);
		Assert.assertEquals(0.0f, sm.getSignal(), 0.0f);
	}

	@Test
	public void testCalculate04_SizeGreaterThanLookbackPlusSmoothMA1PlusDoubleSmoothMAAndSignalMA() {
		List<UnitPrice> ohlcData = createOHLCData(25);
		StochasticMomentum sm = unit.calculate(ohlcData);
		System.out.println(sm);
		Assert.assertEquals(52.08333f, sm.getSmi(), 0.0001f);
		Assert.assertEquals(55.39425005f, sm.getSignal(), 0.0001f);
	}

	@Test
	public void testCalculate11_Success() {
		List<UnitPrice> ohlcData = createOHLCData2(28);
		StochasticMomentum sm = unit.calculate(ohlcData);
		System.out.println(sm);
		Assert.assertEquals(58.91251f, sm.getSmi(), 0.001f);
		Assert.assertEquals(45.43347904f, sm.getSignal(), 0.001f);
	}

	@Test
	public void testCalculate12_SizeLessThanLookbackPlusSmoothMA1PlusDoubleSmoothMA() {
		List<UnitPrice> ohlcData = createOHLCData2(13);
		StochasticMomentum sm = unit.calculate(ohlcData);
		System.out.println(sm);
		Assert.assertEquals(0.0f, sm.getSmi(), 0.0f);
		Assert.assertEquals(0.0f, sm.getSignal(), 0.0f);
	}

	@Test
	public void testCalculate13_SizeGreaterThanLookbackPlusSmoothMA1PlusDoubleSmoothMAAndLessThanSignalMA() {
		List<UnitPrice> ohlcData = createOHLCData2(19);
		StochasticMomentum sm = unit.calculate(ohlcData);
		System.out.println(sm);
		Assert.assertEquals(55.92881f, sm.getSmi(), 0.001f);
		Assert.assertEquals(0.0f, sm.getSignal(), 0.0f);
	}

	@Test
	public void testCalculate14_SizeGreaterThanLookbackPlusSmoothMA1PlusDoubleSmoothMAAndSignalMA() {
		List<UnitPrice> ohlcData = createOHLCData2(25);
		StochasticMomentum sm = unit.calculate(ohlcData);
		System.out.println(sm);
		Assert.assertEquals(50.81508f, sm.getSmi(), 0.001f);
		Assert.assertEquals(45.40705908f, sm.getSignal(), 0.001f);
	}

	private List<UnitPrice> createOHLCData(int count) {
		List<UnitPrice> ohlcData = new ArrayList<UnitPrice>();
		for (float i = 1; i <= count; i++) {
			float j = 100 * i;
			ohlcData.add(new UnitPrice(j * 0.8f, j * 1.2f, j * 0.6f, j * 1.0f));
		}
		return ohlcData;

		// @formatter:off
		//			O		H		L		C			HH		LL			M			C-M=D		HH-LL=HL	D MA		HL MA		D Smooth	HL smooth	HL2			SMI			Signal
		//	100		80		120		60		100												
		//	200		160		240		120		200												
		//	300		240		360		180		300												
		//	400		320		480		240		400												
		//	500		400		600		300		500												
		//	600		480		720		360		600												
		//	700		560		840		420		700												
		//	800		640		960		480		800												
		//	900		720		1080	540		900												
		//	1000	800		1200	600		1000	1200.00000	60.00000	630.00000	370.00000	1140.00000							
		//	1100	880		1320	660		1100	1320.00000	120.00000	720.00000	380.00000	1200.00000							
		//	1200	960		1440	720		1200	1440.00000	180.00000	810.00000	390.00000	1260.00000	380.00000	1200.00000					
		//	1300	1040	1560	780		1300	1560.00000	240.00000	900.00000	400.00000	1320.00000	390.00000	1260.00000					
		//	1400	1120	1680	840		1400	1680.00000	300.00000	990.00000	410.00000	1380.00000	400.00000	1320.00000	390.00000	1260.00000	630.00000	61.90476	
		//	1500	1200	1800	900		1500	1800.00000	360.00000	1080.00000	420.00000	1440.00000	410.00000	1380.00000	400.00000	1320.00000	660.00000	60.60606	
		//	1600	1280	1920	960		1600	1920.00000	420.00000	1170.00000	430.00000	1500.00000	420.00000	1440.00000	410.00000	1380.00000	690.00000	59.42029	
		//	1700	1360	2040	1020	1700	2040.00000	480.00000	1260.00000	440.00000	1560.00000	430.00000	1500.00000	420.00000	1440.00000	720.00000	58.33333	
		//	1800	1440	2160	1080	1800	2160.00000	540.00000	1350.00000	450.00000	1620.00000	440.00000	1560.00000	430.00000	1500.00000	750.00000	57.33333	
		//	1900	1520	2280	1140	1900	2280.00000	600.00000	1440.00000	460.00000	1680.00000	450.00000	1620.00000	440.00000	1560.00000	780.00000	56.41026	
		//	2000	1600	2400	1200	2000	2400.00000	660.00000	1530.00000	470.00000	1740.00000	460.00000	1680.00000	450.00000	1620.00000	810.00000	55.55556	
		//	2100	1680	2520	1260	2100	2520.00000	720.00000	1620.00000	480.00000	1800.00000	470.00000	1740.00000	460.00000	1680.00000	840.00000	54.76190	
		//	2200	1760	2640	1320	2200	2640.00000	780.00000	1710.00000	490.00000	1860.00000	480.00000	1800.00000	470.00000	1740.00000	870.00000	54.02299	
		//	2300	1840	2760	1380	2300	2760.00000	840.00000	1800.00000	500.00000	1920.00000	490.00000	1860.00000	480.00000	1800.00000	900.00000	53.33333	57.16818176
		//	2400	1920	2880	1440	2400	2880.00000	900.00000	1890.00000	510.00000	1980.00000	500.00000	1920.00000	490.00000	1860.00000	930.00000	52.68817	56.24652277
		//	2500	2000	3000	1500	2500	3000.00000	960.00000	1980.00000	520.00000	2040.00000	510.00000	1980.00000	500.00000	1920.00000	960.00000	52.08333	55.39425005
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

		// @formatter:off
		//			O			H			L			C			HH				LL				M			C-M=D		HH-LL=HL	D MA		HL MA		D Smooth	HL smooth	HL2			SMI			Signal
		//		26878.00	26934.85	26755.95	26787.85												
		//		26787.60	26818.70	26729.20	26751.05												
		//		26750.75	26777.25	26723.65	26753.85												
		//		26754.85	26881.55	26740.05	26817.00												
		//		26817.25	26875.90	26783.25	26827.15												
		//		26827.80	26838.25	26676.95	26728.30												
		//		26727.05	26734.75	26703.05	26714.75												
		//		26596.65	26643.35	26414.45	26430.40												
		//		26429.50	26512.15	26408.15	26494.05												
		//		26495.15	26539.00	26458.20	26529.15	26934.85000		26408.15000		26671.50000		-142.35000	526.70000							
		//		26528.20	26571.40	26503.10	26551.25	26881.55000		26408.15000		26644.85000		-93.60000	473.40000							
		//		26553.30	26905.30	26533.20	26836.55	26905.30000		26408.15000		26656.72500		179.82500	497.15000	-18.70833	499.08333					
		//		26836.90	27008.55	26836.90	26964.35	27008.55000		26408.15000		26708.35000		256.00000	600.40000	114.07500	523.65000					
		//		26969.45	27029.15	26965.10	26986.80	27029.15000		26408.15000		26718.65000		268.15000	621.00000	234.65833	572.85000	110.00833	531.86111	265.93056	41.36732	
		//		27206.90	27239.95	26945.90	26967.10	27239.95000		26408.15000		26824.05000		143.05000	831.80000	222.40000	684.40000	190.37778	593.63333	296.81667	64.13985	
		//		26968.50	27158.45	26967.95	27085.50	27239.95000		26408.15000		26824.05000		261.45000	831.80000	224.21667	761.53333	227.09167	672.92778	336.46389	67.49362	
		//		27086.35	27105.50	27045.40	27094.45	27239.95000		26408.15000		26824.05000		270.40000	831.80000	224.96667	831.80000	223.86111	759.24444	379.62222	58.96944	
		//		27092.80	27110.00	26939.15	27013.40	27239.95000		26408.15000		26824.05000		189.35000	831.80000	240.40000	831.80000	229.86111	808.37778	404.18889	56.86973	
		//		27010.45	27113.25	26972.15	27072.70	27239.95000		26458.20000		26849.07500		223.62500	781.75000	227.79167	815.11667	231.05278	826.23889	413.11944	55.92881	
		//		27073.85	27079.10	26845.10	26867.50	27239.95000		26503.10000		26871.52500		-4.02500	736.85000	136.31667	783.46667	201.50278	810.12778	405.06389	49.74592	
		//		26861.45	26918.95	26848.30	26878.55	27239.95000		26533.20000		26886.57500		-8.02500	706.75000	70.52500	741.78333	144.87778	780.12222	390.06111	37.14233	
		//		27071.15	27197.95	27039.60	27178.10	27239.95000		26836.90000		27038.42500		139.67500	403.05000	42.54167	615.55000	83.12778	713.60000	356.80000	23.29814	
		//		27179.75	27213.45	27136.25	27157.80	27239.95000		26845.10000		27042.52500		115.27500	394.85000	82.30833	501.55000	65.12500	619.62778	309.81389	21.02068	47.59758502
		//		27156.80	27183.45	27092.40	27157.95	27239.95000		26845.10000		27042.52500		115.42500	394.85000	123.45833	397.58333	82.76944	504.89444	252.44722	32.78683	46.73953653
		//		27154.95	27181.05	27131.80	27160.85	27213.45000		26845.10000		27029.27500		131.57500	368.35000	120.75833	386.01667	108.84167	428.38333	214.19167	50.81508	45.40705908
		//		27162.70	27208.80	27127.60	27141.55	27213.45000		26845.10000		27029.27500		112.27500	368.35000	119.75833	377.18333	121.32500	386.92778	193.46389	62.71196	44.92889302
		//		27138.90	27180.90	27112.60	27115.75	27213.45000		26845.10000		27029.27500		86.47500	368.35000	110.10833	368.35000	116.87500	377.18333	188.59167	61.97252	45.22920025
		//		27116.55	27138.60	27098.40	27125.25	27213.45000		26845.10000		27029.27500		95.97500	368.35000	98.24167	368.35000	109.36944	371.29444	185.64722	58.91251	45.43347904
		//		27275.45	27286.50	27141.95	27189.60	27286.50000		26845.10000		27065.80000		123.80000	441.40000	102.08333	392.70000	103.47778	376.46667	188.23333	54.97314	45.33791252
		//		27189.10	27218.55	27163.45	27177.10	27286.50000		26848.30000		27067.40000		109.70000	438.20000	109.82500	415.98333	103.38333	392.34444	196.17222	52.70029	45.6333492
		//		27177.55	27192.50	27139.90	27147.50	27286.50000		27039.60000		27163.05000		-15.55000	246.90000	72.65000	375.50000	94.85278	394.72778	197.36389	48.05984	46.72510083
		//		27148.45	27162.25	27105.00	27138.60	27286.50000		27092.40000		27189.45000		-50.85000	194.10000	14.43333	293.06667	65.63611	361.51667	180.75833	36.31153	48.02643913
		//		27139.05	27198.40	27131.65	27168.80	27286.50000		27092.40000		27189.45000		-20.65000	194.10000	-29.01667	211.70000	19.35556	293.42222	146.71111	13.19297	47.24366787
		//		27168.00	27197.40	27141.60	27159.05	27286.50000		27098.40000		27192.45000		-33.40000	188.10000	-34.96667	192.10000	-16.51667	232.28889	116.14444	-14.22080	42.54290497
		//		27165.40	27181.00	27147.10	27160.20	27286.50000		27098.40000		27192.45000		-32.25000	188.10000	-28.76667	190.10000	-30.91667	197.96667	98.98333	-31.23421	34.33797552
		// @formatter:on
	}
}
