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

public class DirectionalSystem implements Indicator<DirectionalSystem> {

	private float[] smoothedPlusDI;

	private float[] smoothedMinusDI;

	private float[] adx;

	private int smoothingPeriod;

	private MovingAverageType movingAverageType;

	private static final int DEFAULT_SMOOTHING_PERIOD_COUNT = 14;
	private static final MovingAverageType DEFAULT_SMOOTHING_MA_TYPE = MovingAverageType.WELLESWILDER;

	public DirectionalSystem() {
		this.smoothingPeriod = DEFAULT_SMOOTHING_PERIOD_COUNT;
		this.movingAverageType = DEFAULT_SMOOTHING_MA_TYPE;
	}

	public float[] getSmoothedPlusDI() {
		return smoothedPlusDI;
	}

	public void setSmoothedPlusDI(float[] smoothedPlusDIArray) {
		this.smoothedPlusDI = smoothedPlusDIArray;
	}

	public float[] getSmoothedMinusDI() {
		return smoothedMinusDI;
	}

	public void setSmoothedMinusDI(float[] smoothedMinusDI) {
		this.smoothedMinusDI = smoothedMinusDI;
	}

	public float[] getAdx() {
		return adx;
	}

	public void setAdx(float[] adx) {
		this.adx = adx;
	}

	public int getSmoothingPeriod() {
		return smoothingPeriod;
	}

	public void setSmoothingPeriod(int smoothingPeriod) {
		this.smoothingPeriod = smoothingPeriod;
	}

	public MovingAverageType getMovingAverageType() {
		return movingAverageType;
	}

	public void setMovingAverageType(MovingAverageType movingAverageType) {
		this.movingAverageType = movingAverageType;
	}

	@Override
	public float[] getUnitPriceIndicator(int position) {
		return new float[] { smoothingPeriod, smoothedPlusDI[position], smoothedMinusDI[position], adx[position] };
	}

	@Override
	public int getUnitPriceIndicatorValuesLength() {
		return 4;
	}

	@Override
	public int getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues() {
		// The value for the lookbackPeriod needs to be a sum of the first smoothing (to get +DI -DI) and the smoothing period (to get ADX) and 1 (to get the initial true
		// range) so that we can get the calculations correct.
		return DirectionalSystemCalculator.DIRECTIONAL_MOVEMENT_CALCULATION_START + getSmoothingPeriod() + getSmoothingPeriod();
	}

	@Override
	public DirectionalSystem calculate(List<? extends UnitPrice> unitPrices) {
		DirectionalSystemCalculator dsCalculator = new DirectionalSystemCalculator();
		return dsCalculator.calculate(unitPrices, this);
	}

	@Override
	public String getName() {
		return "ADX[" + smoothingPeriod + "," + movingAverageType.toString() + "]";
	}
}
