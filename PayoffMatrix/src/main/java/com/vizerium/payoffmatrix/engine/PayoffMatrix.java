/*
 * Copyright 2018 Vizerium, Inc.
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

public class PayoffMatrix {

	private Payoff[] payoffs;

	private int positivePayoffsCount = 0;

	private int negativePayoffsCount = 0;

	private Payoff maxPositivePayoff;

	private Payoff minNegativePayoff;

	private float profitProbability = 0.0f;

	private float payoffAverage = 0.0f;

	private float underlyingCurrentPrice = 0.0f;

	public PayoffMatrix() {

	}

	public PayoffMatrix(Payoff[] payoffs, float underlyingCurrentPrice) {
		this.payoffs = payoffs;
		this.underlyingCurrentPrice = underlyingCurrentPrice;
		performPayoffAnalysis();
	}

	public Payoff[] getPayoffs() {
		return payoffs;
	}

	public void setPayoffs(Payoff[] payoffs) {
		this.payoffs = payoffs;
		performPayoffAnalysis();
	}

	public int getPositivePayoffsCount() {
		return positivePayoffsCount;
	}

	public int getNegativePayoffsCount() {
		return negativePayoffsCount;
	}

	public Payoff getMaxPositivePayoff() {
		return maxPositivePayoff;
	}

	public Payoff getMinNegativePayoff() {
		return minNegativePayoff;
	}

	public float getProfitProbability() {
		return profitProbability;
	}

	public float getPayoffAverage() {
		return payoffAverage;
	}

	public void performPayoffAnalysis() {
		positivePayoffsCount = 0;
		negativePayoffsCount = 0;

		float payoffSum = 0.0f;

		maxPositivePayoff = payoffs[0];
		minNegativePayoff = payoffs[0];

		for (Payoff payoff : payoffs) {
			if (payoff.getPayoff() > 0.0f) {
				++positivePayoffsCount;
			} else {
				++negativePayoffsCount;
			}

			payoffSum += payoff.getPayoff();

			if (payoff.getPayoff() > maxPositivePayoff.getPayoff()) {
				maxPositivePayoff = payoff;
			} else if (payoff.getPayoff() <= minNegativePayoff.getPayoff()) {
				minNegativePayoff = payoff;
			}
		}
		payoffAverage = payoffSum / payoffs.length;
		profitProbability = (float) (positivePayoffsCount) / (float) (positivePayoffsCount + negativePayoffsCount);
	}

	public String getPayoffAtEachUnderlyingPrice() {
		String payoffAtEachUnderlyingPrice = "";
		for (Payoff payoff : payoffs) {
			payoffAtEachUnderlyingPrice += (payoff + System.lineSeparator());
		}
		return payoffAtEachUnderlyingPrice;
	}

	@Override
	public String toString() {
		return "positive : " + positivePayoffsCount + ", negative : " + negativePayoffsCount + ", maxPositive : " + maxPositivePayoff.getPayoff() + " at "
				+ maxPositivePayoff.getUnderlyingPrice() + ", minNegative : " + minNegativePayoff.getPayoff() + " at " + minNegativePayoff.getUnderlyingPrice()
				+ ", profitProbability : " + profitProbability + ", payoffAverage : " + payoffAverage + ", underlyingCurrentPrice : " + underlyingCurrentPrice;
	}
}
