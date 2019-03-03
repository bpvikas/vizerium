package com.vizerium.commons.calculators;

import java.util.List;

import com.vizerium.barabanca.dao.UnitPrice;

public class StochasticCalculator implements StochasticCalculatorBase {

	public Stochastic calculate(List<UnitPrice> unitPrices, int lookbackPeriod) {

		// @formatter:off
		// No of elements required to calculate the 
		// 1. %K fast stochastic
		// 2. %D fast stochastic = %K slow stochastic
		// 3. %D slow stochastic
		
		// Given 14 period lookback period, 3 period SMA for calculating %D fast stochastic, 
		// 3 period SMA for calculating %D slow stochastic and size of list = 21 as an example 
		
		//		Elements	%K fast		%D fast		%D slow 
		//								%K slow
		// @formatter:off
		//				O		H		L		C		HH			LL			fast %K		fast %D
		//																					slow %K		slow %D
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

		Stochastic stochastic = new Stochastic(lookbackPeriod);

		int size = unitPrices.size();
		int lbp = lookbackPeriod;
		int ma1 = stochastic.getMaPeriodCountForCalculatingDFromK();
		int ma2 = stochastic.getMaPeriodCountForCalculatingSlowFromFast();

		if (size < lbp) {
			return stochastic;
		} else if (size >= lbp && size < lbp + ma1 - 1) {

			float cc = unitPrices.get(size - 1).getClose();
			float fpk = 100 * (cc - ll(unitPrices, size - lbp, size - 1)) / (hh(unitPrices, size - lbp, size - 1) - ll(unitPrices, size - lbp, size - 1));
			stochastic.setFastPercentK(fpk);
			return stochastic;
		} else if (size >= lbp + ma1 - 1 && size < lbp + ma1 + ma2 - 1 - 1) {

			float[] fpkArr = new float[ma1];
			for (int i = 0; i < ma1; i++) {
				float cc = unitPrices.get(size - ma1 + i).getClose();
				float fpk = 100 * (cc - ll(unitPrices, size - ma1 - lbp + i + 1, size - ma1 + i))
						/ (hh(unitPrices, size - ma1 - lbp + i + 1, size - ma1 + i) - ll(unitPrices, size - ma1 - lbp + i + 1, size - ma1 + i));

				fpkArr[i] = fpk;
			}

			stochastic.setFastPercentK(fpkArr[ma1 - 1]);
			stochastic.setFastPercentD(MovingAverageCalculator.calculateMA(stochastic.getMaTypeForCalculatingDFromK(), fpkArr, ma1));
			stochastic.setSlowPercentK(MovingAverageCalculator.calculateMA(stochastic.getMaTypeForCalculatingDFromK(), fpkArr, ma1));

			return stochastic;

		} else if (size >= lbp + ma1 + ma2 - 1 - 1) {

			float fpkLast = 0.0f;
			float fpdArr[] = new float[ma2];
			for (int j = 0; j < ma2; j++) {
				float fpkArr[] = new float[ma1];
				for (int i = 0; i < ma1; i++) {
					float cc = unitPrices.get(size - ma1 - ma2 + i + j + 1).getClose();
					float fpk = 100
							* (cc - ll(unitPrices, size - ma1 - ma2 + i + j - lbp + 1 + 1, size - ma1 - ma2 + i + j + 1))
							/ (hh(unitPrices, size - ma1 - ma2 + i + j - lbp + 1 + 1, size - ma1 - ma2 + i + j + 1) - ll(unitPrices, size - ma1 - ma2 + i + j - lbp + 1 + 1, size
									- ma1 - ma2 + i + j + 1));

					fpkArr[i] = fpk;

					if (i == ma1 - 1 && j == ma2 - 1) {
						fpkLast = fpk;
					}
				}
				fpdArr[j] = MovingAverageCalculator.calculateMA(stochastic.getMaTypeForCalculatingDFromK(), fpkArr, ma1);

			}

			stochastic.setFastPercentK(fpkLast);
			stochastic.setFastPercentD(fpdArr[ma1 - 1]);
			stochastic.setSlowPercentK(fpdArr[ma1 - 1]);
			stochastic.setSlowPercentD(MovingAverageCalculator.calculateMA(stochastic.getMaTypeForCalculatingSlowFromFast(), fpdArr, ma2));

			return stochastic;

		} else {
			throw new RuntimeException("Error while calculating stochastic for unitPrices size : " + size + " and lookback period : " + lbp);
		}
	}
}
