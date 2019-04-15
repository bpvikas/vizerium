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

public class RSI implements Indicator<RSI> {

	private int lookbackPeriod;

	private MovingAverageType maType;

	private float[] values;

	private static final int DEFAULT_LOOKBACK_PERIOD = 14;

	private static final MovingAverageType DEFAULT_MOVING_AVERAGE_TYPE = MovingAverageType.WELLESWILDER;

	public RSI() {
		this.lookbackPeriod = DEFAULT_LOOKBACK_PERIOD;
		this.maType = DEFAULT_MOVING_AVERAGE_TYPE;
	}

	public RSI(int lookbackPeriod, MovingAverageType maType) {
		this.lookbackPeriod = lookbackPeriod;
		this.maType = maType;
	}

	public int getLookbackPeriod() {
		return lookbackPeriod;
	}

	public MovingAverageType getMaType() {
		return maType;
	}

	public float[] getValues() {
		return values;
	}

	public void setValues(float[] values) {
		this.values = values;
	}

	@Override
	public float[] getUnitPriceIndicator(int position) {
		return new float[] { lookbackPeriod, values[position] };
	}

	@Override
	public int getUnitPriceIndicatorValuesLength() {
		return 2;
	}

	@Override
	public int getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues() {
		// The value for the lookbackPeriod needs to be a sum of the start point of the gain loss calculation and the lookback period.
		return RSICalculator.AVERAGE_GAIN_LOSS_CALCULATION_START + lookbackPeriod;
	}

	@Override
	public RSI calculate(List<? extends UnitPrice> unitPrices) {
		RSICalculator rsiCalculator = new RSICalculator();
		return rsiCalculator.calculate(unitPrices, this);

	}

	@Override
	public String getName() {
		return getClass().getSimpleName() + "[" + lookbackPeriod + "]";
	}
}
