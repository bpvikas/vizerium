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

public class StochasticMomentum implements Indicator<StochasticMomentum> {

	// The default value for the look back period for the Stochastic Momentum indicator.
	public static final int DEFAULT_STOCHASTIC_MOMENTUM_LOOK_BACK_PERIOD = 10;
	private static final int DEFAULT_MA_PERIOD_COUNT_FOR_SMOOTHING_K = 3;
	private static final MovingAverageType DEFAULT_MA_TYPE_FOR_CALCULATING_D_FROM_K = MovingAverageType.EXPONENTIAL;

	private int percentKLookbackPeriod;

	private int maPeriodCountForFirstSmoothingK;

	private int maPeriodCountForDoubleSmoothingK;

	private MovingAverageType maTypeForCalculatingDFromK;

	private int percentDLookbackPeriod;

	private float[] smiArray;

	private float[] signalArray;

	public static final int UPI_POSN_SMINDEX = 4;

	public StochasticMomentum() {
		this.percentKLookbackPeriod = DEFAULT_STOCHASTIC_MOMENTUM_LOOK_BACK_PERIOD;
		this.maPeriodCountForFirstSmoothingK = DEFAULT_MA_PERIOD_COUNT_FOR_SMOOTHING_K;
		this.maPeriodCountForDoubleSmoothingK = DEFAULT_MA_PERIOD_COUNT_FOR_SMOOTHING_K;
		this.maTypeForCalculatingDFromK = DEFAULT_MA_TYPE_FOR_CALCULATING_D_FROM_K;
		this.percentDLookbackPeriod = DEFAULT_STOCHASTIC_MOMENTUM_LOOK_BACK_PERIOD;
	}

	public StochasticMomentum(int percentKLookbackPeriod, int maPeriodCountForFirstSmoothingK, int maPeriodCountForDoubleSmoothingK, MovingAverageType maTypeForCalculatingDFromK,
			int percentDLookbackPeriod) {
		this.percentKLookbackPeriod = percentKLookbackPeriod;
		this.maPeriodCountForFirstSmoothingK = maPeriodCountForFirstSmoothingK;
		this.maPeriodCountForDoubleSmoothingK = maPeriodCountForDoubleSmoothingK;
		this.maTypeForCalculatingDFromK = maTypeForCalculatingDFromK;
		this.percentDLookbackPeriod = percentDLookbackPeriod;
	}

	public StochasticMomentum(MovingAverageType maTypeForCalculatingDFromK) {
		this();
		this.maTypeForCalculatingDFromK = maTypeForCalculatingDFromK;
	}

	public float[] getSmiArray() {
		return smiArray;
	}

	public void setSmiArray(float[] smiArray) {
		this.smiArray = smiArray;
	}

	public float[] getSignalArray() {
		return signalArray;
	}

	public void setSignalArray(float[] signalArray) {
		this.signalArray = signalArray;
	}

	public int getPercentKLookbackPeriod() {
		return percentKLookbackPeriod;
	}

	public int getMaPeriodCountForFirstSmoothingK() {
		return maPeriodCountForFirstSmoothingK;
	}

	public int getMaPeriodCountForDoubleSmoothingK() {
		return maPeriodCountForDoubleSmoothingK;
	}

	public MovingAverageType getMaTypeForCalculatingDFromK() {
		return maTypeForCalculatingDFromK;
	}

	public void setMaTypeForCalculatingDFromK(MovingAverageType maTypeForCalculatingDFromK) {
		this.maTypeForCalculatingDFromK = maTypeForCalculatingDFromK;
	}

	public int getPercentDLookbackPeriod() {
		return percentDLookbackPeriod;
	}

	@Override
	public float[] getUnitPriceIndicator(int position) {
		return new float[] { percentKLookbackPeriod, maPeriodCountForFirstSmoothingK, maPeriodCountForDoubleSmoothingK, percentDLookbackPeriod, smiArray[position],
				signalArray[position] };
	}

	@Override
	public int getUnitPriceIndicatorValuesLength() {
		return 6;
	}

	@Override
	public int getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues() {
		// The value for the lookbackPeriod needs to be a sum of the %K lookback period and the MA for first smoothing and the MA for the
		// second smoothing and the %D lookbackPeriod.
		return percentKLookbackPeriod + maPeriodCountForFirstSmoothingK + maPeriodCountForDoubleSmoothingK + percentDLookbackPeriod;
	}

	@Override
	public StochasticMomentum calculate(List<? extends UnitPrice> unitPrices) {
		StochasticMomentumCalculator smCalculator = new StochasticMomentumCalculator();
		return smCalculator.calculate(unitPrices, this);
	}

	@Override
	public String getName() {
		return getClass().getSimpleName() + "[" + percentKLookbackPeriod + "," + maPeriodCountForFirstSmoothingK + ", " + maPeriodCountForDoubleSmoothingK + ", "
				+ percentDLookbackPeriod + ", " + maTypeForCalculatingDFromK.toString() + "]";
	}
}
