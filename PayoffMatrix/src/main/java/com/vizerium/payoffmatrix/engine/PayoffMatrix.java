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

package com.vizerium.payoffmatrix.engine;

import java.text.NumberFormat;

import com.vizerium.commons.util.NumberFormats;

public class PayoffMatrix {

	/*
	 * The payoffs is a array of float arrays. This had to be converted into a float array instead of a regular object, as we needed to create approximately 54 million instances of
	 * the object and that was causing memory issues. Converting from its own class to a double float array reduced the memory footprint from 3.4kb to 2.2kb per instance. The 2
	 * elements in the array are [underlyingPrice, payoff].
	 */
	private float[][] payoffs;

	private int positivePayoffsCount = 0;

	private int negativePayoffsCount = 0;

	private float positivePayoffSum = 0.0f;

	private float negativePayoffSum = 0.0f;

	private float[] maxPositivePayoff;

	private float[] minNegativePayoff;

	private float profitProbability = 0.0f;

	private float payoffAverage = 0.0f;

	private float riskRewardRatio = 0.0f;

	public PayoffMatrix() {

	}

	public PayoffMatrix(float[][] payoffs) {
		this.payoffs = payoffs;
		performPayoffAnalysis();
	}

	public float[][] getPayoffs() {
		return payoffs;
	}

	public void setPayoffs(float[][] payoffs) {
		this.payoffs = payoffs;
		performPayoffAnalysis();
	}

	public float getPositivePayoffsCount() {
		return positivePayoffsCount;
	}

	public float getNegativePayoffsCount() {
		return negativePayoffsCount;
	}

	public float getPositivePayoffSum() {
		return positivePayoffSum;
	}

	public float getNegativePayoffSum() {
		return negativePayoffSum;
	}

	public float getProfitProbability() {
		return profitProbability;
	}

	public float getPayoffAverage() {
		return payoffAverage;
	}

	public float getRiskRewardRatio() {
		return riskRewardRatio;
	}

	public float[] getMaxPositivePayoff() {
		return maxPositivePayoff;
	}

	public float[] getMinNegativePayoff() {
		return minNegativePayoff;
	}

	public String getRiskRewardRatioAsString() {
		NumberFormat nf = NumberFormats.getForPrice();
		return "1:" + nf.format(1 / riskRewardRatio);
	}

	public String getProfitProbabilityAsString() {
		NumberFormat nf = NumberFormats.getForPrice();
		return nf.format(profitProbability * 100) + "%";
	}

	public void performPayoffAnalysis() {
		positivePayoffsCount = 0;
		negativePayoffsCount = 0;

		float payoffSum = 0.0f;
		positivePayoffSum = 0.0f;
		negativePayoffSum = 0.0f;

		maxPositivePayoff = payoffs[0];
		minNegativePayoff = payoffs[0];

		float underlyingPriceRange = payoffs[payoffs.length - 1][0] - payoffs[0][0];
		// The weightage is calculated by deciding how far the price is from the midpoint, which would approximately be the current underlying Price.
		float rangeMidPoint = payoffs[0][0] + underlyingPriceRange / 2.0f;

		for (float[] payoff : payoffs) {
			if (payoff[1] > 0.0f) {
				++positivePayoffsCount;
				positivePayoffSum += payoff[1] * Math.abs(1 - (Math.abs(rangeMidPoint - payoff[0]) / (underlyingPriceRange / 2.0f)));
			} else {
				++negativePayoffsCount;
				negativePayoffSum += payoff[1] * Math.abs(1 - (Math.abs(rangeMidPoint - payoff[0]) / (underlyingPriceRange / 2.0f)));
			}

			payoffSum += payoff[1] * Math.abs(1 - (Math.abs(rangeMidPoint - payoff[0]) / (underlyingPriceRange / 2.0f)));

			if (payoff[1] > maxPositivePayoff[1]) {
				maxPositivePayoff = payoff;
			} else if (payoff[1] <= minNegativePayoff[1]) {
				minNegativePayoff = payoff;
			}
		}
		payoffAverage = payoffSum / payoffs.length;
		profitProbability = (float) (positivePayoffsCount) / (float) (positivePayoffsCount + negativePayoffsCount);

		// For calculating the riskRewardRatio, with a view that we need to eventually COMPARE them, then we have multiple scenarios
		// Scenario 1: loss 0, profit 5 v/s loss 0 profit 10, in this case, simply doing loss/profit for both cases will return riskRewardRatio = 0, and we will lose the part where
		// one had higher profit than the other
		// Scenario 2: loss 0 profit 0; In this case as well, the riskRewardRatio = loss/profit = 0, indicating profitable trade
		// So, to take care of such scenarios, we will set the loss to -1, where loss = 0, and then calculate it.

		// Now consider the opposite scenario, where profit 0, loss 5 OR profit 0, loss 10, in this case, we need to use the riskRewardRatio to flag maximum risk.
		// So, here we can set the profit to a very small number e.g 0.0001f so, that the riskRewardRatio gets converted to a very large number, and thus that
		// strategy does not figure in our top 7 best riskRewardRatio list.

		if (positivePayoffSum == 0.0f) {
			positivePayoffSum = 0.0001f;
		}
		if (negativePayoffSum == 0.0f) {
			negativePayoffSum = -1;
		}
		riskRewardRatio = Math.abs(negativePayoffSum / positivePayoffSum);
	}

	public String getPayoffAtEachUnderlyingPrice() {
		String payoffAtEachUnderlyingPrice = "";
		for (float[] payoff : payoffs) {
			payoffAtEachUnderlyingPrice += ("Payoff " + payoff[1] + " at " + payoff[0] + System.lineSeparator());
		}
		return payoffAtEachUnderlyingPrice;
	}

	@Override
	public String toString() {
		return positivePayoffsCount + "+ " + negativePayoffsCount + "- " + ", profit% " + getProfitProbabilityAsString() + " RRR : " + getRiskRewardRatioAsString()
				+ ", payoffAverage : " + payoffAverage + ", maxPositive : " + maxPositivePayoff[1] + " at " + maxPositivePayoff[0] + ", minNegative : " + minNegativePayoff[1]
				+ " at " + minNegativePayoff[0];
	}
}
