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

public class SuperTrend implements Indicator<SuperTrend> {

	private int period;

	private float multiplier;

	private MovingAverageType atrMAType;

	private static final int DEFAULT_PERIOD = 10;

	private static final float DEFAULT_MULTIPLIER = 3.0f;

	private float[] atrValues;

	private float[] basicUpperBandValues;

	private float[] basicLowerBandValues;

	private float[] finalUpperBandValues;

	private float[] finalLowerBandValues;

	private float[] superTrendValues;

	private float[] trendValues;

	public static final int UPI_POSN_SUPERTREND_VALUE = 2;

	public static final int UPI_POSN_TREND = 3;

	public SuperTrend() {
		this(DEFAULT_PERIOD, DEFAULT_MULTIPLIER);
	}

	public SuperTrend(int period, float multiplier) {
		this(period, multiplier, AverageTrueRange.DEFAULT_SMOOTHING_MA_TYPE);
	}

	public SuperTrend(int period, float multiplier, MovingAverageType atrMAType) {
		this.period = period;
		this.multiplier = multiplier;
		this.atrMAType = atrMAType;
	}

	public int getPeriod() {
		return period;
	}

	public float getMultiplier() {
		return multiplier;
	}

	public MovingAverageType getAtrMAType() {
		return atrMAType;
	}

	public float[] getAtrValues() {
		return atrValues;
	}

	public void setAtrValues(float[] atrValues) {
		this.atrValues = atrValues;
	}

	public float[] getBasicUpperBandValues() {
		return basicUpperBandValues;
	}

	public void setBasicUpperBandValues(float[] basicUpperBandValues) {
		this.basicUpperBandValues = basicUpperBandValues;
	}

	public float[] getBasicLowerBandValues() {
		return basicLowerBandValues;
	}

	public void setBasicLowerBandValues(float[] basicLowerBandValues) {
		this.basicLowerBandValues = basicLowerBandValues;
	}

	public float[] getFinalUpperBandValues() {
		return finalUpperBandValues;
	}

	public void setFinalUpperBandValues(float[] finalUpperBandValues) {
		this.finalUpperBandValues = finalUpperBandValues;
	}

	public float[] getFinalLowerBandValues() {
		return finalLowerBandValues;
	}

	public void setFinalLowerBandValues(float[] finalLowerBandValues) {
		this.finalLowerBandValues = finalLowerBandValues;
	}

	public float[] getSuperTrendValues() {
		return superTrendValues;
	}

	public void setSuperTrendValues(float[] superTrendValues) {
		this.superTrendValues = superTrendValues;
	}

	public float[] getTrendValues() {
		return trendValues;
	}

	public void setTrendValues(float[] trendValues) {
		this.trendValues = trendValues;
	}

	@Override
	public float[] getUnitPriceIndicator(int position) {
		return new float[] { period, multiplier, superTrendValues[position], trendValues[position] };
	}

	@Override
	public int getUnitPriceIndicatorValuesLength() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public int getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues() {
		// The value for the lookbackPeriod needs to be a sum of the start point of the gain loss calculation and the smoothing period
		return AverageTrueRangeCalculator.TRUE_RANGE_CALCULATION_START + period;
	}

	@Override
	public SuperTrend calculate(List<? extends UnitPrice> unitPrices) {
		SuperTrendCalculator stCalculator = new SuperTrendCalculator();
		return stCalculator.calculate(unitPrices, this);
	}

	@Override
	public String getName() {
		return getClass().getSimpleName() + "[" + period + atrMAType.toString() + "," + multiplier + "]";
	}
}
