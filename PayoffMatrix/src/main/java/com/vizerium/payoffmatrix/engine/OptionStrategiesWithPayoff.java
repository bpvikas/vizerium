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

	public OptionStrategiesWithPayoff(OptionStrategy[] newAndExistingPositions, PayoffMatrix payoffMatrix) {
		this.optionStrategies = newAndExistingPositions;
		this.payoffMatrix = payoffMatrix;
		setExistingOpenPosition();
	}

	public OptionStrategy[] getOptions() {
		return optionStrategies;
	}

	public void setOptions(OptionStrategy[] optionStrategies) {
		this.optionStrategies = optionStrategies;
		setExistingOpenPosition();
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

	public float getPayoffAverage() {
		return payoffMatrix.getPayoffAverage();
	}

	public float getRiskRewardRatio() {
		return payoffMatrix.getRiskRewardRatio();
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