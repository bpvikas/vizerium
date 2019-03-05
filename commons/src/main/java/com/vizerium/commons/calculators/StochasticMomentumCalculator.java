package com.vizerium.commons.calculators;

import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public class StochasticMomentumCalculator implements StochasticCalculatorBase {

	// https://www.motivewave.com/studies/stochastic_momentum_index.htm
	//
	// HH = highest(index, hlPeriod, HIGH);
	// LL = lowest(index, hlPeriod, LOW);
	// M = (HH + LL)/2;
	// D = getClose(index) - M;
	// HL = HH - LL;
	// D_MA = ma(method, index, maPeriod, D);
	// HL_MA = ma(method, index, maPeriod, HL);
	// D_SMOOTH = ma(method, index, smoothPeriod, D_MA);
	// HL_SMOOTH = ma(method, index, smoothPeriod, HL_MA);
	// HL2 = HL_SMOOTH/2;
	// SMI = 0;
	// SMI = 100 * (D_SMOOTH/HL2);
	// SIGNAL = ma(method, index, signalPeriod, SMI);

	// //Signals
	// buy = crossedAbove(SMI, SIGNAL);
	// sell = crossedBelow(SMI, SIGNAL);

	// https://tradingqna.com/t/need-formula-for-stochastic-momentum-index-indicator/43201
	// Above URL explains the same formula in text format

	public StochasticMomentum calculate(List<UnitPrice> unitPrices) {
		return calculate(unitPrices, new StochasticMomentum());
	}

	public StochasticMomentum calculate(List<UnitPrice> unitPrices, StochasticMomentum sm) {

		int lbpk = sm.getPercentKLookbackPeriod();
		int ma1 = sm.getMaPeriodCountForFirstSmoothingK();
		int ma2 = sm.getMaPeriodCountForDoubleSmoothingK();
		MovingAverageType matype = sm.getMaTypeForCalculatingDFromK();
		int lbpd = sm.getPercentDLookbackPeriod();

		int size = unitPrices.size();

		if (size < lbpk + ma1 + ma2 - 1 - 1) {
			return sm;
		} else if (size >= lbpk + ma1 + ma2 - 1 - 1 && size < lbpk + ma1 + ma2 - 1 - 1 + lbpd) {

			sm.setSmi(calculateSMI(unitPrices, size, lbpk, ma1, ma2, matype));
			return sm;

		} else if (size >= lbpk + ma1 + ma2 - 1 - 1 + lbpd) {
			float[] smiArr = new float[lbpd];
			for (int i = 0; i < lbpd; i++) {
				smiArr[i] = calculateSMI(unitPrices, size - lbpd + i + 1, lbpk, ma1, ma2, matype);
			}
			sm.setSmi(smiArr[lbpd - 1]);
			sm.setSignal(MovingAverageCalculator.calculateMA(matype, smiArr, lbpd));
			return sm;
		} else {
			throw new RuntimeException("Error while calculating stochastic momentum for unitPrices size : " + size);
		}
	}

	private float calculateSMI(List<UnitPrice> unitPrices, int size, int lbpk, int ma1, int ma2, MovingAverageType matype) {
		float[] smoothCmdArr = new float[ma2];
		float[] smoothHldArr = new float[ma2];
		for (int j = 0; j < ma2; j++) {
			float[] cmdArr = new float[ma1]; // cmd -> close median difference
			float[] hldArr = new float[ma1]; // hld -> high low difference
			for (int i = 0; i < ma1; i++) {
				float cc = unitPrices.get(size - ma1 - ma2 + i + j + 1).getClose();
				float hh = hh(unitPrices, size - ma1 - ma2 + i + j - lbpk + 1 + 1, size - ma1 - ma2 + i + j + 1);
				float ll = ll(unitPrices, size - ma1 - ma2 + i + j - lbpk + 1 + 1, size - ma1 - ma2 + i + j + 1);
				float median = (hh + ll) / 2.0f;
				cmdArr[i] = cc - median;
				hldArr[i] = hh - ll;
			}
			smoothCmdArr[j] = MovingAverageCalculator.calculateMA(matype, cmdArr, ma1);
			smoothHldArr[j] = MovingAverageCalculator.calculateMA(matype, hldArr, ma1);
		}
		float doubleSmoothCmd = MovingAverageCalculator.calculateMA(matype, smoothCmdArr, ma2);
		float doubleSmoothHld2 = MovingAverageCalculator.calculateMA(matype, smoothHldArr, ma2) / 2;

		float smi = 100 * (doubleSmoothCmd / doubleSmoothHld2);
		return smi;

	}
}
