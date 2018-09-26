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

import com.vizerium.payoffmatrix.option.Option;

public class OptionsWithPayoff {

	private PayoffMatrix payoffMatrix;

	private Option[] options;

	private boolean existingOpenPosition;

	public OptionsWithPayoff(Option[] newAndExistingPositions, PayoffMatrix payoffMatrix) {
		this.options = newAndExistingPositions;
		this.payoffMatrix = payoffMatrix;
		setExistingOpenPosition();
	}

	public Option[] getOptions() {
		return options;
	}

	public void setOptions(Option[] options) {
		this.options = options;
		setExistingOpenPosition();
	}

	public PayoffMatrix getPayoffMatrix() {
		return payoffMatrix;
	}

	public void setPayoffMatrix(PayoffMatrix payoffMatrix) {
		this.payoffMatrix = payoffMatrix;
	}

	public float getMaxPositivePayoff() {
		return payoffMatrix.getMaxPositivePayoff().getPayoff();
	}

	public float getMinNegativePayoff() {
		return payoffMatrix.getMinNegativePayoff().getPayoff();
	}

	public float getProfitProbability() {
		return payoffMatrix.getProfitProbability();
	}

	public float getPayoffAverage() {
		return payoffMatrix.getPayoffAverage();
	}

	public float getRiskRewardRatio() {
		// This is best used only for Spread positions.
		return (payoffMatrix.getMinNegativePayoff().getPayoff() / payoffMatrix.getMaxPositivePayoff().getPayoff());
	}

	public float getTotalPremium() {
		float totalPremium = 0;
		for (Option option : options) {
			totalPremium += option.getPremiumPaidReceived();
		}
		return totalPremium;
	}

	private void setExistingOpenPosition() {
		existingOpenPosition = true;
		for (Option option : options) {
			if (!option.isExisting()) {
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
		if (options.length > 0) {
			String optionsString = "";
			for (Option option : options) {
				optionsString += (option + " ");
			}
			return "OptionsWithPayoff with options [" + optionsString + "]" + System.lineSeparator() + "for premium paid/received " + getTotalPremium() + " with payoff at "
					+ System.lineSeparator() + payoffMatrix + System.lineSeparator() + "Payoff at each underlying " + System.lineSeparator()
					+ payoffMatrix.getPayoffAtEachUnderlyingPrice();
		} else {
			return "No options present.";
		}
	}
}