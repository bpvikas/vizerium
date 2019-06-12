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

import com.vizerium.payoffmatrix.option.Option;
import com.vizerium.payoffmatrix.option.OptionStrategy;

public class OptionStrategiesWithPayoff {

	private PayoffMatrix payoffMatrix;

	private OptionStrategy[] optionStrategies;

	private boolean existingOpenPosition;

	private double positionDelta;

	public OptionStrategiesWithPayoff(OptionStrategy[] newAndExistingPositions, PayoffMatrix payoffMatrix) {
		this.optionStrategies = newAndExistingPositions;
		this.payoffMatrix = payoffMatrix;
		setExistingOpenPosition();
		calculatePositionDelta();
	}

	public OptionStrategy[] getOptions() {
		return optionStrategies;
	}

	public void setOptions(OptionStrategy[] optionStrategies) {
		this.optionStrategies = optionStrategies;
		setExistingOpenPosition();
		calculatePositionDelta();
	}

	public PayoffMatrix getPayoffMatrix() {
		return payoffMatrix;
	}

	public void setPayoffMatrix(PayoffMatrix payoffMatrix) {
		this.payoffMatrix = payoffMatrix;
	}

	public float getPositivePayoffSum() {
		return payoffMatrix.getPositivePayoffSum();
	}

	public float getNegativePayoffSum() {
		return payoffMatrix.getNegativePayoffSum();
	}

	public float getProfitProbability() {
		return payoffMatrix.getProfitProbability();
	}

	/*
	 * This method was introduced for comparison in the {@link com.vizerium.payoffmatrix.comparator.HighestProfitProbabilityPayoffMatrixComparator}.
	 * In the firstRound, only those combinations which had 100% profit were topping the charts. (positivePayoffs = totalPayoffs as negativePayoffs would be 0).
	 * But the problem, with these was that the average payoffs were very poor.
	 * So, the idea is to round off the profitProbability to the nearest multiples of 0.1. That way, we can check all combinations from 0.9 to 1.0, and then do a 
	 * second comparison with the highest total positive payoffs. 
	 * After running UTs, the results have top 7 entries within the 0.9 to 1 profit probability, with an average of say 27/29 recording the highest payoffs.
	 * i.e. 27 +ve payoffs / (27 +ve and 2 -ve = 29 total payoffs)
	 */
	public float getProfitProbabilityForComparison() {
		return roundTo1Decimal(getProfitProbability());
	}

	public float getPayoffAverage() {
		return payoffMatrix.getPayoffAverage();
	}

	public float getRiskRewardRatio() {
		return payoffMatrix.getRiskRewardRatio();
	}

	/*
	 * This method was introduced for comparison in the {@link com.vizerium.payoffmatrix.comparator.BestRiskRewardRatioPayoffMatrixComparator}. 
	 * The next problem which occurred here was that there were many RR Ratios like 1:5000, 1:10000, 1:8500, and such weird fractions. So, these would come up tops in the 
	 * actual comparison but but but, they would have very poor average payoffs. So, the idea is to round off the riskRewardRatio up to the nearest multiples of 0.1. 
	 * And then do a second comparison with higher total positive payoffs.
	 * After running UTs, the results have top 7 entries within the 0.0 to 0.1 riskRewardRatio, i.e. 1:10.xx and higher riskReward.
	 * 
	 */
	public float getRiskRewardRatioForComparison() {
		return roundTo1Decimal(getRiskRewardRatio());
	}

	public float getTotalPremium() {
		float totalPremium = 0;
		for (OptionStrategy optionStrategy : optionStrategies) {
			for (Option option : optionStrategy.getOptions()) {
				totalPremium += option.getPremiumPaidReceived();
			}
		}
		return totalPremium;
	}

	private void setExistingOpenPosition() {
		existingOpenPosition = true;
		for (OptionStrategy optionStrategy : optionStrategies) {
			if (!optionStrategy.isExisting()) {
				existingOpenPosition = false;
				break;
			}
		}
	}

	public boolean isExistingOpenPosition() {
		return existingOpenPosition;
	}

	public double getPositionDeltaForComparison() {
		return roundTo2Decimals(Math.abs(positionDelta));
	}

	public double getPositionDelta() {
		return positionDelta;
	}

	public void calculatePositionDelta() {
		positionDelta = 0.0;
		for (OptionStrategy optionStrategy : optionStrategies) {
			positionDelta += optionStrategy.getDelta();
		}
	}

	private float roundTo1Decimal(float d) {
		return Math.round(d * 10.0f) / 10.0f;
	}

	private double roundTo2Decimals(double d) {
		return Math.round(d * 100.0) / 100.0;
	}

	@Override
	public String toString() {
		if (optionStrategies.length > 0) {
			String optionsString = "";
			for (OptionStrategy optionStrategy : optionStrategies) {
				optionsString += (optionStrategy + " ");
			}
			return "OptionsWithPayoff with optionStrategies [" + optionsString + "]" + System.lineSeparator() + "for premium paid/received " + getTotalPremium()
					+ " with payoff at " + System.lineSeparator() + payoffMatrix;
			// + System.lineSeparator() + "Payoff at each underlying " + System.lineSeparator() + payoffMatrix.getPayoffAtEachUnderlyingPrice();
		} else {
			return "No option strategies present.";
		}
	}
}