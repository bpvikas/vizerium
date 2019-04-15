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

public class MACD implements Indicator<MACD> {

	// The default value for the fast MA for the MACD.
	private static final int DEFAULT_FAST_MA = 12;

	// The default value for the slow MA for the MACD.
	private static final int DEFAULT_SLOW_MA = 26;

	private static final MovingAverageType DEFAULT_FAST_SLOW_MA_TYPE = MovingAverageType.EXPONENTIAL;

	private static final MovingAverageType DEFAULT_SMOOTHING_MA_TYPE = MovingAverageType.EXPONENTIAL;

	private static final int DEFAULT_SMOOTHING_PERIOD_COUNT = 9;

	private int fastMA;

	private int slowMA;

	private MovingAverageType fastSlowMAType;

	private MovingAverageType smoothingMAType;

	private int smoothingPeriod;

	private float[] fastMAValues;

	private float[] slowMAValues;

	private float[] differenceMAValues;

	private float[] signalValues;

	private float[] histogramValues;

	public static final int UPI_POSN_HISTOGRAM = 7;

	public MACD() {
		this.fastMA = DEFAULT_FAST_MA;
		this.slowMA = DEFAULT_SLOW_MA;
		this.fastSlowMAType = DEFAULT_FAST_SLOW_MA_TYPE;
		this.smoothingMAType = DEFAULT_SMOOTHING_MA_TYPE;
		this.smoothingPeriod = DEFAULT_SMOOTHING_PERIOD_COUNT;
	}

	public MACD(int fastMA, int slowMA, MovingAverageType fastSlowMAType, MovingAverageType smoothingMAType, int smoothingPeriod) {
		if (fastMA >= slowMA) {
			throw new RuntimeException("Fast MA (" + fastMA + ") has to be less than Slow MA (" + slowMA + ")");
		}
		this.fastMA = fastMA;
		this.slowMA = slowMA;
		this.fastSlowMAType = fastSlowMAType;
		this.smoothingMAType = smoothingMAType;
		this.smoothingPeriod = smoothingPeriod;
	}

	public MACD(MovingAverage fastMA, MovingAverage slowMA) {
		this(fastMA, slowMA, DEFAULT_SMOOTHING_MA_TYPE, DEFAULT_SMOOTHING_PERIOD_COUNT);
	}

	public MACD(MovingAverage fastMA, MovingAverage slowMA, MovingAverageType smoothingMAType, int smoothingPeriod) {
		if (!(fastMA.getType().equals(slowMA.getType()))) {
			throw new RuntimeException("Fast MA type (" + fastMA.getType().toString() + ") has to be same as Slow MA type (" + slowMA.getType().toString() + ")");
		}
		if (fastMA.getMA() >= slowMA.getMA()) {
			throw new RuntimeException("Fast MA (" + fastMA.getMA() + ") has to be less than Slow MA (" + slowMA.getMA() + ")");
		}
		this.fastMA = fastMA.getMA();
		this.slowMA = slowMA.getMA();
		this.fastSlowMAType = fastMA.getType();
		this.smoothingMAType = smoothingMAType;
		this.smoothingPeriod = smoothingPeriod;
	}

	public int getFastMA() {
		return fastMA;
	}

	public void setFastMA(int fastMA) {
		this.fastMA = fastMA;
	}

	public int getSlowMA() {
		return slowMA;
	}

	public void setSlowMA(int slowMA) {
		this.slowMA = slowMA;
	}

	public float[] getFastMAValues() {
		return fastMAValues;
	}

	public void setFastMAValues(float[] fastMAValues) {
		this.fastMAValues = fastMAValues;
	}

	public float[] getSlowMAValues() {
		return slowMAValues;
	}

	public void setSlowMAValues(float[] slowMAValues) {
		this.slowMAValues = slowMAValues;
	}

	public float[] getDifferenceMAValues() {
		return differenceMAValues;
	}

	public void setDifferenceMAValues(float[] differenceMAValues) {
		this.differenceMAValues = differenceMAValues;
	}

	public float[] getSignalValues() {
		return signalValues;
	}

	public void setSignalValues(float[] signalValues) {
		this.signalValues = signalValues;
	}

	public float[] getHistogramValues() {
		return histogramValues;
	}

	public void setHistogramValues(float[] histogramValues) {
		this.histogramValues = histogramValues;
	}

	public MovingAverageType getFastSlowMAType() {
		return fastSlowMAType;
	}

	public MovingAverageType getSmoothingMAType() {
		return smoothingMAType;
	}

	public int getSmoothingPeriod() {
		return smoothingPeriod;
	}

	@Override
	public float[] getUnitPriceIndicator(int position) {
		return new float[] { fastMA, fastMAValues[position], slowMA, slowMAValues[position], smoothingPeriod, differenceMAValues[position], signalValues[position],
				histogramValues[position] };
	}

	@Override
	public int getUnitPriceIndicatorValuesLength() {
		return 8;
	}

	@Override
	public int getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues() {
		// The value for the lookbackPeriod needs to be a sum of the slower MA and the smoothing period so that we can get the calculations correct.
		return getSlowMA() + getSmoothingPeriod();
	}

	@Override
	public MACD calculate(List<? extends UnitPrice> unitPrices) {
		MACDCalculator macdCalculator = new MACDCalculator();
		return macdCalculator.calculate(unitPrices, this);
	}

	@Override
	public String getName() {
		return getClass().getSimpleName() + "[" + fastMA + fastSlowMAType.toString() + "," + slowMA + fastSlowMAType.toString() + "," + smoothingPeriod + smoothingMAType.toString()
				+ "]";
	}
}
