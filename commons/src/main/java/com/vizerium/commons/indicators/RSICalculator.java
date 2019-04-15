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

public class RSICalculator implements IndicatorCalculator<RSI> {

	static final int AVERAGE_GAIN_LOSS_CALCULATION_START = 1;

	public RSI calculate(float[] closingPrices) {
		return calculate(closingPrices, new RSI());
	}

	public RSI calculate(float[] closingPrices, RSI rsi) {
		int lookbackPeriod = rsi.getLookbackPeriod();
		if (closingPrices.length <= lookbackPeriod) {
			rsi.setValues(new float[closingPrices.length]);
			return rsi;
		} else {

			float[] currentGain = calculateCurrentGain(closingPrices);
			float[] currentLoss = calculateCurrentLoss(closingPrices);

			float[] averageGain = calculateAverageGainLoss(currentGain, rsi);
			float[] averageLoss = calculateAverageGainLoss(currentLoss, rsi);

			float[] rsiValues = calculateRSIValues(averageGain, averageLoss);
			rsi.setValues(rsiValues);
			return rsi;
		}
	}

	private float[] calculateCurrentGain(float[] closingPrices) {
		float[] currentGain = new float[closingPrices.length];
		for (int i = AVERAGE_GAIN_LOSS_CALCULATION_START; i < closingPrices.length; i++) {
			float diff = closingPrices[i] - closingPrices[i - 1];
			currentGain[i] = (diff > 0) ? diff : 0;
		}
		return currentGain;
	}

	private float[] calculateCurrentLoss(float[] closingPrices) {
		float[] currentLoss = new float[closingPrices.length];
		for (int i = AVERAGE_GAIN_LOSS_CALCULATION_START; i < closingPrices.length; i++) {
			float diff = closingPrices[i - 1] - closingPrices[i];
			currentLoss[i] = (diff > 0) ? diff : 0;
		}
		return currentLoss;
	}

	private float[] calculateAverageGainLoss(float[] currentGainLoss, RSI rsi) {
		float[] calculableCurrentGainLoss = Arrays.copyOfRange(currentGainLoss, AVERAGE_GAIN_LOSS_CALCULATION_START, currentGainLoss.length);
		float[] averageGainLoss = MovingAverageCalculator.calculateArrayMA(rsi.getMaType(), calculableCurrentGainLoss, rsi.getLookbackPeriod());

		float[] averageGainLossShifted = new float[currentGainLoss.length];
		System.arraycopy(averageGainLoss, 0, averageGainLossShifted, AVERAGE_GAIN_LOSS_CALCULATION_START, averageGainLoss.length);
		return averageGainLossShifted;
	}

	private float[] calculateRSIValues(float[] averageGain, float[] averageLoss) {
		float[] rsiValues = new float[averageGain.length];
		for (int i = 0; i < averageGain.length; i++) {
			if (averageLoss[i] == 0.0f) {
				rsiValues[i] = 0.0f;
			} else {
				float rs = averageGain[i] / averageLoss[i];
				rsiValues[i] = 100.0f - (100.0f / (1.0f + rs));
			}
		}
		return rsiValues;
	}

	@Override
	public RSI calculate(List<? extends UnitPrice> unitPrices, RSI rsi) {
		// I really tried to use lambda expressions here.
		// return calculate(unitPrices.stream().mapToFloat(UnitPrice::getClose).toArray(), rsi);
		// but it does not have a mapToFloat.. So, I am looping over it myself
		// https://stackoverflow.com/questions/4837568/java-convert-arraylistfloat-to-float

		float[] closingPrices = new float[unitPrices.size()];
		int i = 0;
		for (UnitPrice unitPrice : unitPrices) {
			closingPrices[i++] = unitPrice.getClose();
		}
		return calculate(closingPrices, rsi);
	}
}