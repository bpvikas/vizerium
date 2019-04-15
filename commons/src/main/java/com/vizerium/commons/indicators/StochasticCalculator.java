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

public class StochasticCalculator implements StochasticCalculatorBase, IndicatorCalculator<Stochastic> {

	public Stochastic calculate(List<? extends UnitPrice> unitPrices) {
		return calculate(unitPrices, new Stochastic());
	}

	@Override
	public Stochastic calculate(List<? extends UnitPrice> unitPrices, Stochastic stochastic) {
		int size = unitPrices.size();
		int lookbackPeriod = stochastic.getLookbackPeriod();

		if (size < lookbackPeriod) {
			stochastic.setFastPercentKArray(new float[size]);
			stochastic.setSlowPercentKArray(new float[size]);
			stochastic.setSlowPercentDArray(new float[size]);
			return stochastic;
		} else {

			float[] closeArray = new float[size];
			for (int i = 0; i < size; i++) {
				closeArray[i] = unitPrices.get(i).getClose();
			}

			float[] hhArray = new float[size - lookbackPeriod + 1];
			float[] llArray = new float[size - lookbackPeriod + 1];
			for (int i = lookbackPeriod - 1; i < size; i++) {
				hhArray[i - lookbackPeriod + 1] = hh(unitPrices, i - lookbackPeriod + 1, i);
				llArray[i - lookbackPeriod + 1] = ll(unitPrices, i - lookbackPeriod + 1, i);
			}

			float[] fastPercentKArray = new float[hhArray.length];
			for (int i = 0; i < fastPercentKArray.length; i++) {
				fastPercentKArray[i] = 100 * (closeArray[i + lookbackPeriod - 1] - llArray[i]) / (hhArray[i] - llArray[i]);
			}

			float[] slowPercentKArray = MovingAverageCalculator.calculateArrayMA(stochastic.getMaTypeForCalculatingSlowFromFast(), fastPercentKArray,
					stochastic.getMaPeriodCountForCalculatingSlowFromFast());

			float[] calculableSlowPercentKArray = Arrays.copyOfRange(slowPercentKArray, stochastic.getMaPeriodCountForCalculatingSlowFromFast() - 1, slowPercentKArray.length);

			float[] slowPercentDArray = MovingAverageCalculator.calculateArrayMA(stochastic.getMaTypeForCalculatingDFromK(), calculableSlowPercentKArray,
					stochastic.getMaPeriodCountForCalculatingDFromK());

			float[] fastPercentKArrayShifted = new float[size];
			int destPos = lookbackPeriod - 1;
			System.arraycopy(fastPercentKArray, 0, fastPercentKArrayShifted, destPos, fastPercentKArray.length);

			float[] slowPercentKArrayShifted = new float[size];
			if (size > destPos) {
				System.arraycopy(slowPercentKArray, 0, slowPercentKArrayShifted, destPos, slowPercentKArray.length);
			}

			float[] slowPercentDArrayShifted = new float[size];
			destPos = destPos + stochastic.getMaPeriodCountForCalculatingDFromK() - 1;
			if (size > destPos) {
				System.arraycopy(slowPercentDArray, 0, slowPercentDArrayShifted, destPos, slowPercentDArray.length);
			}

			stochastic.setFastPercentKArray(fastPercentKArrayShifted);
			stochastic.setSlowPercentKArray(slowPercentKArrayShifted);
			stochastic.setSlowPercentDArray(slowPercentDArrayShifted);
		}
		return stochastic;
	}
}
