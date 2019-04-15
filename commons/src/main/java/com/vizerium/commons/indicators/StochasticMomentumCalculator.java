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

import java.util.Arrays;
import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public class StochasticMomentumCalculator implements StochasticCalculatorBase, IndicatorCalculator<StochasticMomentum> {

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

	public StochasticMomentum calculate(List<? extends UnitPrice> unitPrices) {
		return calculate(unitPrices, new StochasticMomentum());
	}

	@Override
	public StochasticMomentum calculate(List<? extends UnitPrice> unitPrices, StochasticMomentum sm) {
		int lbpk = sm.getPercentKLookbackPeriod();
		int ma1 = sm.getMaPeriodCountForFirstSmoothingK();
		int ma2 = sm.getMaPeriodCountForDoubleSmoothingK();
		MovingAverageType maType = sm.getMaTypeForCalculatingDFromK();
		int lbpd = sm.getPercentDLookbackPeriod();

		int size = unitPrices.size();
		if (size < lbpk) {
			sm.setSmiArray(new float[unitPrices.size()]);
			sm.setSignalArray(new float[unitPrices.size()]);
		} else {
			float[] closeArray = new float[size];
			for (int i = 0; i < size; i++) {
				closeArray[i] = unitPrices.get(i).getClose();
			}

			float[] hhArray = new float[size - lbpk + 1];
			float[] llArray = new float[size - lbpk + 1];
			float[] medianArray = new float[size - lbpk + 1];
			float[] cmdArray = new float[size - lbpk + 1]; // cmd -> close median difference
			float[] hldArray = new float[size - lbpk + 1]; // hld -> high low difference
			for (int i = lbpk - 1; i < size; i++) {
				hhArray[i - lbpk + 1] = hh(unitPrices, i - lbpk + 1, i);
				llArray[i - lbpk + 1] = ll(unitPrices, i - lbpk + 1, i);
				medianArray[i - lbpk + 1] = (hhArray[i - lbpk + 1] + llArray[i - lbpk + 1]) / 2.0f;
				cmdArray[i - lbpk + 1] = closeArray[i] - medianArray[i - lbpk + 1];
				hldArray[i - lbpk + 1] = hhArray[i - lbpk + 1] - llArray[i - lbpk + 1];
			}

			float[] smiArrayShifted = new float[size];
			float[] signalArrayShifted = new float[size];

			if (size > lbpk - 1 + ma1 - 1) {
				float[] smoothCmdArray = MovingAverageCalculator.calculateArrayMA(maType, cmdArray, ma1);
				float[] smoothHldArray = MovingAverageCalculator.calculateArrayMA(maType, hldArray, ma1);

				float[] calculableSmoothCmdArray = Arrays.copyOfRange(smoothCmdArray, ma1 - 1, smoothCmdArray.length);
				float[] doubleSmoothCmdArray = MovingAverageCalculator.calculateArrayMA(maType, calculableSmoothCmdArray, ma2);

				float[] calculableSmoothHldArray = Arrays.copyOfRange(smoothHldArray, ma1 - 1, smoothHldArray.length);
				float[] doubleSmoothHldArray = MovingAverageCalculator.calculateArrayMA(maType, calculableSmoothHldArray, ma2);

				float[] doubleSmoothHld2Array = new float[doubleSmoothHldArray.length];
				for (int i = 0; i < doubleSmoothHldArray.length; i++) {
					doubleSmoothHld2Array[i] = doubleSmoothHldArray[i] / 2.0f;
				}

				float[] smiArray = new float[doubleSmoothHld2Array.length - ma2 + 1];
				for (int i = ma2 - 1; i < doubleSmoothHld2Array.length; i++) {
					smiArray[i - ma2 + 1] = 100.0f * doubleSmoothCmdArray[i] / doubleSmoothHld2Array[i];
				}

				float[] signalArray = MovingAverageCalculator.calculateArrayMA(maType, smiArray, lbpd);
				int destPos = lbpk - 1 + ma1 - 1 + ma1 - 1;
				smiArrayShifted = new float[smiArray.length + destPos];
				System.arraycopy(smiArray, 0, smiArrayShifted, destPos, smiArray.length);
				signalArrayShifted = new float[signalArray.length + destPos];
				System.arraycopy(signalArray, 0, signalArrayShifted, destPos, signalArray.length);
			}
			sm.setSmiArray(smiArrayShifted);
			sm.setSignalArray(signalArrayShifted);
		}
		return sm;
	}
}
