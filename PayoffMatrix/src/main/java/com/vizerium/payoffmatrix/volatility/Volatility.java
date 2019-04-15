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

package com.vizerium.payoffmatrix.volatility;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.vizerium.payoffmatrix.exchange.Exchange;

public class Volatility {

	private float mean;

	private float standardDeviation;

	private float standardDeviationMultiple;

	private float underlyingValue;

	private Range underlyingRange;

	public Volatility() {

	}

	public Volatility(float mean, float standardDeviation) {
		this.mean = mean;
		this.standardDeviation = standardDeviation;
	}

	public float getMean() {
		return mean;
	}

	public void setMean(float mean) {
		this.mean = mean;
	}

	public float getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(float standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public void setStandardDeviationFromImpliedVolatility(float impliedVolatility) {
		// This can be set to the implied volatility calculated over a year (252 working days)
		this.standardDeviation = impliedVolatility / 100 / (float) Math.sqrt(252);
	}

	public void setStandardDeviationFromImpliedVolatility(String impliedVolatility) {
		setStandardDeviationFromImpliedVolatility(Float.parseFloat(impliedVolatility));
	}

	public float getStandardDeviationMultiple() {
		return standardDeviationMultiple;
	}

	public void setStandardDeviationMultiple(float standardDeviationMultiple) {
		this.standardDeviationMultiple = standardDeviationMultiple;
	}

	public float getUnderlyingValue() {
		return underlyingValue;
	}

	public void setUnderlyingValue(float underlyingValue) {
		this.underlyingValue = underlyingValue;
	}

	public Range getUnderlyingRange() {
		return underlyingRange;
	}

	public void setUnderlyingRange(Range underlyingRange) {
		this.underlyingRange = underlyingRange;
	}

	public void calculateUnderlyingRange(LocalDate fromDate, LocalDate expiryDate, Exchange exchange, float underlyingRangeStep) {
		long daysToExpiry = ChronoUnit.DAYS.between(fromDate, expiryDate) + 1;

		for (LocalDate date = fromDate; date.compareTo(expiryDate) < 0; date = date.plusDays(1)) {
			if (exchange.isHoliday(date)) {
				--daysToExpiry;
			}
		}

		float meanForDaystoExpiry = mean * daysToExpiry;
		// logger.info("Mean : " + underlyingValue * Math.exp(mean));
		// logger.info("meanForDaystoExpiry : " + underlyingValue * Math.exp(meanForDaystoExpiry));
		float standardDeviationForDaystoExpiry = standardDeviation * (float) Math.sqrt(daysToExpiry);

		float rangeLow = meanForDaystoExpiry - (standardDeviationMultiple * standardDeviationForDaystoExpiry);
		float rangeHigh = meanForDaystoExpiry + (standardDeviationMultiple * standardDeviationForDaystoExpiry);

		float underlyingRangeLow = underlyingValue * (float) Math.exp(rangeLow);
		underlyingRangeLow = (int) (underlyingRangeLow / underlyingRangeStep) * underlyingRangeStep;

		float underlyingRangeHigh = underlyingValue * (float) Math.exp(rangeHigh);
		underlyingRangeHigh = ((int) (underlyingRangeHigh / underlyingRangeStep) + 1) * underlyingRangeStep;

		underlyingRange = new Range(underlyingRangeLow, underlyingRangeHigh, underlyingRangeStep);
	}

	@Override
	public String toString() {
		return "Volatility [mean=" + mean + ", standardDeviation=" + standardDeviation + ", standardDeviationMultiple=" + standardDeviationMultiple + ", underlyingValue="
				+ underlyingValue + ", underlyingRange=" + underlyingRange + "]";
	}
}
