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

import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public class SuperTrendCalculator implements IndicatorCalculator<SuperTrend> {

	public SuperTrend calculate(List<? extends UnitPrice> unitPrices) {
		return calculate(unitPrices, new SuperTrend());
	}

	@Override
	public SuperTrend calculate(List<? extends UnitPrice> unitPrices, SuperTrend superTrend) {

		AverageTrueRange atr = new AverageTrueRange(superTrend.getPeriod(), superTrend.getAtrMAType());
		AverageTrueRangeCalculator atrCalculator = new AverageTrueRangeCalculator();
		float[] atrValues = atrCalculator.calculate(unitPrices, atr).getValues();

		superTrend.setAtrValues(atrValues);

		calculateBasicBandValues(unitPrices, superTrend);
		calculateFinalBandAndSuperTrendValues(unitPrices, superTrend);

		return superTrend;
	}

	private void calculateBasicBandValues(List<? extends UnitPrice> unitPrices, SuperTrend superTrend) {
		float[] basicUpperBandValues = new float[unitPrices.size()];
		float[] basicLowerBandValues = new float[unitPrices.size()];

		float[] atrValues = superTrend.getAtrValues();

		for (int i = superTrend.getPeriod(); i < unitPrices.size(); i++) {
			float hiLoAverage = (unitPrices.get(i).getHigh() + unitPrices.get(i).getLow()) / 2.0f;
			float multiplierAtr = atrValues[i] * superTrend.getMultiplier();

			basicUpperBandValues[i] = hiLoAverage + multiplierAtr;
			basicLowerBandValues[i] = hiLoAverage - multiplierAtr;
		}

		superTrend.setBasicUpperBandValues(basicUpperBandValues);
		superTrend.setBasicLowerBandValues(basicLowerBandValues);
	}

	private void calculateFinalBandAndSuperTrendValues(List<? extends UnitPrice> unitPrices, SuperTrend superTrend) {

		float[] basicUpperBandValues = superTrend.getBasicUpperBandValues();
		float[] basicLowerBandValues = superTrend.getBasicLowerBandValues();

		float[] finalUpperBandValues = new float[unitPrices.size()];
		float[] finalLowerBandValues = new float[unitPrices.size()];

		float[] superTrendValues = new float[unitPrices.size()];
		float[] trendValues = new float[unitPrices.size()];

		for (int i = superTrend.getPeriod(); i < unitPrices.size(); i++) {

			finalUpperBandValues[i] = (basicUpperBandValues[i] < finalUpperBandValues[i - 1] || unitPrices.get(i - 1).getClose() > finalUpperBandValues[i - 1])
					? basicUpperBandValues[i]
					: finalUpperBandValues[i - 1];

			finalLowerBandValues[i] = (basicLowerBandValues[i] > finalLowerBandValues[i - 1] || unitPrices.get(i - 1).getClose() < finalLowerBandValues[i - 1])
					? basicLowerBandValues[i]
					: finalLowerBandValues[i - 1];

			if (superTrendValues[i - 1] == finalUpperBandValues[i - 1] && unitPrices.get(i).getClose() <= finalUpperBandValues[i]) {
				superTrendValues[i] = finalUpperBandValues[i];
			} else if (superTrendValues[i - 1] == finalUpperBandValues[i - 1] && unitPrices.get(i).getClose() >= finalUpperBandValues[i]) {
				superTrendValues[i] = finalLowerBandValues[i];
			} else if (superTrendValues[i - 1] == finalLowerBandValues[i - 1] && unitPrices.get(i).getClose() >= finalLowerBandValues[i]) {
				superTrendValues[i] = finalLowerBandValues[i];
			} else if (superTrendValues[i - 1] == finalLowerBandValues[i - 1] && unitPrices.get(i).getClose() <= finalLowerBandValues[i]) {
				superTrendValues[i] = finalUpperBandValues[i];
			}

			trendValues[i] = (unitPrices.get(i).getClose() > superTrendValues[i]) ? 1 : -1;
		}

		superTrend.setFinalUpperBandValues(finalUpperBandValues);
		superTrend.setFinalLowerBandValues(finalLowerBandValues);
		superTrend.setSuperTrendValues(superTrendValues);
		superTrend.setTrendValues(trendValues);
	}
}
