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

public class MovingAverage implements Indicator<MovingAverage> {
	private int ma;
	private MovingAverageType type;
	private float[] values;

	public MovingAverage(int ma, MovingAverageType type) {
		this.ma = ma;
		this.type = type;
	}

	public int getMA() {
		return ma;
	}

	public MovingAverageType getType() {
		return type;
	}

	public float[] getValues() {
		return values;
	}

	public void setValues(float[] values) {
		this.values = values;
	}

	@Override
	public float[] getUnitPriceIndicator(int position) {
		return new float[] { ma, values[position] };
	}

	@Override
	public int getUnitPriceIndicatorValuesLength() {
		return 2;
	}

	@Override
	public int getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues() {
		return ma + 2; // +2 to ensure that the slope trend check requires 2 prior trends to function correctly
	}

	@Override
	public MovingAverage calculate(List<? extends UnitPrice> unitPrices) {
		MovingAverageCalculator maCalculator = new MovingAverageCalculator();
		return maCalculator.calculate(unitPrices, this);
	}

	@Override
	public String getName() {
		return "[" + ma + type.toString() + "]";
	}
}
