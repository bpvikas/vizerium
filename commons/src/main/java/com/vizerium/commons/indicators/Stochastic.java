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

public class Stochastic implements Indicator<Stochastic> {

	// The default value for the look back period for the Stochastic indicator.
	public static final int DEFAULT_STOCHASTIC_LOOK_BACK_PERIOD = 14;
	private static final int DEFAULT_MA_PERIOD_COUNT = 3;
	private static final MovingAverageType DEFAULT_MA_TYPE = MovingAverageType.EXPONENTIAL;

	private int lookbackPeriod;

	private float[] fastPercentKArray;

	private float[] slowPercentKArray;

	private float[] slowPercentDArray;

	private int maPeriodCountForCalculatingDFromK;

	private MovingAverageType maTypeForCalculatingDFromK;

	private int maPeriodCountForCalculatingSlowFromFast;

	private MovingAverageType maTypeForCalculatingSlowFromFast;

	public Stochastic() {
		this.lookbackPeriod = DEFAULT_STOCHASTIC_LOOK_BACK_PERIOD;
		maPeriodCountForCalculatingDFromK = DEFAULT_MA_PERIOD_COUNT;
		maTypeForCalculatingDFromK = DEFAULT_MA_TYPE;
		maPeriodCountForCalculatingSlowFromFast = DEFAULT_MA_PERIOD_COUNT;
		maTypeForCalculatingSlowFromFast = DEFAULT_MA_TYPE;
	}

	public Stochastic(int lookbackPeriod) {
		this();
		this.lookbackPeriod = lookbackPeriod;
	}

	public Stochastic(int lookbackPeriod, int maPeriodCount, MovingAverageType maType) {
		this(lookbackPeriod);
		this.maPeriodCountForCalculatingDFromK = maPeriodCount;
		this.maTypeForCalculatingDFromK = maType;
		this.maPeriodCountForCalculatingSlowFromFast = maPeriodCount;
		this.maTypeForCalculatingSlowFromFast = maType;
	}

	public Stochastic(int lookbackPeriod, int maPeriodCountForCalculatingDFromK, MovingAverageType maTypeForCalculatingDFromK, int maPeriodCountForCalculatingSlowFromFast,
			MovingAverageType maTypeForCalculatingSlowFromFast) {
		this(lookbackPeriod);
		this.maPeriodCountForCalculatingDFromK = maPeriodCountForCalculatingDFromK;
		this.maTypeForCalculatingDFromK = maTypeForCalculatingDFromK;
		this.maPeriodCountForCalculatingSlowFromFast = maPeriodCountForCalculatingSlowFromFast;
		this.maTypeForCalculatingSlowFromFast = maTypeForCalculatingSlowFromFast;
	}

	public int getLookbackPeriod() {
		return lookbackPeriod;
	}

	public float[] getFastPercentKArray() {
		return fastPercentKArray;
	}

	public void setFastPercentKArray(float[] fastPercentKArray) {
		this.fastPercentKArray = fastPercentKArray;
	}

	public float[] getSlowPercentKArray() {
		return slowPercentKArray;
	}

	public void setSlowPercentKArray(float[] slowPercentKArray) {
		this.slowPercentKArray = slowPercentKArray;
	}

	/*
	 * As per the Stochastic calculations, the slow %K is the fast%D array while double smoothing.
	 */
	public float[] getFastPercentDArray() {
		return getSlowPercentKArray();
	}

	/*
	 * As per the Stochastic calculations, the slow %K is the fast%D array while double smoothing.
	 */
	public void setFastPercentDArray(float[] slowPercentKArray) {
		setSlowPercentKArray(slowPercentKArray);
	}

	public float[] getSlowPercentDArray() {
		return slowPercentDArray;
	}

	public void setSlowPercentDArray(float[] slowPercentDArray) {
		this.slowPercentDArray = slowPercentDArray;
	}

	public int getMaPeriodCountForCalculatingDFromK() {
		return maPeriodCountForCalculatingDFromK;
	}

	public MovingAverageType getMaTypeForCalculatingDFromK() {
		return maTypeForCalculatingDFromK;
	}

	public int getMaPeriodCountForCalculatingSlowFromFast() {
		return maPeriodCountForCalculatingSlowFromFast;
	}

	public MovingAverageType getMaTypeForCalculatingSlowFromFast() {
		return maTypeForCalculatingSlowFromFast;
	}

	@Override
	public float[] getUnitPriceIndicator(int position) {
		return new float[] { lookbackPeriod, maPeriodCountForCalculatingSlowFromFast, maPeriodCountForCalculatingDFromK, fastPercentKArray[position], slowPercentKArray[position],
				slowPercentKArray[position], slowPercentDArray[position] };
	}

	@Override
	public int getUnitPriceIndicatorValuesLength() {
		return 7;
	}

	@Override
	public int getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues() {
		// The value for the lookbackPeriod needs to be a sum of the lookback period and the MA for Fast->Slow calculations and MA for K->D calculations.
		return lookbackPeriod + maPeriodCountForCalculatingSlowFromFast + maPeriodCountForCalculatingDFromK;
	}

	@Override
	public Stochastic calculate(List<? extends UnitPrice> unitPrices) {
		StochasticCalculator stochasticCalculator = new StochasticCalculator();
		return stochasticCalculator.calculate(unitPrices, this);
	}

	@Override
	public String getName() {
		return getClass().getSimpleName() + "[" + lookbackPeriod + "," + maPeriodCountForCalculatingSlowFromFast + "," + maPeriodCountForCalculatingDFromK + ","
				+ maTypeForCalculatingSlowFromFast.toString() + "]";
	}
}
