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

public class AverageTrueRangeCalculator implements IndicatorCalculator<AverageTrueRange> {

	static final int TRUE_RANGE_CALCULATION_START = 1;

	public AverageTrueRange calculate(List<? extends UnitPrice> unitPrices) {
		return calculate(unitPrices, new AverageTrueRange());
	}

	@Override
	public AverageTrueRange calculate(List<? extends UnitPrice> unitPrices, AverageTrueRange atr) {
		int smoothingPeriod = atr.getSmoothingPeriod();
		int size = unitPrices.size();
		if (size < smoothingPeriod) {
			atr.setValues(new float[size]);
			return atr;
		} else {
			float[] trueRangeArr = calculateTrueRange(unitPrices);
			float[] calculableTrueRangeArr = Arrays.copyOfRange(trueRangeArr, TRUE_RANGE_CALCULATION_START, size);
			float[] smoothAtrArr = MovingAverageCalculator.calculateArrayMA(atr.getSmoothingMAType(), calculableTrueRangeArr, smoothingPeriod);
			float[] smoothAtrArrShifted = new float[size];
			System.arraycopy(smoothAtrArr, 0, smoothAtrArrShifted, TRUE_RANGE_CALCULATION_START, smoothAtrArr.length);
			atr.setValues(smoothAtrArrShifted);
			return atr;
		}
	}

	private float[] calculateTrueRange(List<? extends UnitPrice> unitPrices) {
		float[] trueRange = new float[unitPrices.size()];
		// This starts from i=1 as trueRange[0] defaults to 0.0f
		for (int i = TRUE_RANGE_CALCULATION_START; i < unitPrices.size(); i++) {
			trueRange[i] = getTrueRange(unitPrices.get(i).getHigh(), unitPrices.get(i).getLow(), unitPrices.get(i - 1).getClose());
		}
		return trueRange;
	}

	private float getTrueRange(float todayHigh, float todayLow, float yesterdayClose) {
		float th_tl = Math.abs(todayHigh - todayLow);
		float th_yc = Math.abs(todayHigh - yesterdayClose);
		float tl_yc = Math.abs(todayLow - yesterdayClose);

		return th_tl > th_yc ? (th_tl > tl_yc ? th_tl : tl_yc) : (th_yc > tl_yc ? th_yc : tl_yc);
	}
}
