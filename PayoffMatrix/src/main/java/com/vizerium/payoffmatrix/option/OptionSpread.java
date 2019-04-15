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

package com.vizerium.payoffmatrix.option;

public class OptionSpread implements OptionStrategy {

	private Option optionOne;

	private Option optionTwo;

	public OptionSpread() {

	}

	public OptionSpread(Option optionOne, Option optionTwo) {

		if (optionOne.type != optionTwo.type) {
			throw new RuntimeException("Both the options need to be of the same type (either Calls or Puts) to create a Spread.");
		}

		if (optionOne.tradeAction == optionTwo.tradeAction) {
			throw new RuntimeException("The actions for both options need to be opposite (a Buy and a Sell) to create a Spread.");
		}

		if (optionOne.strike == optionTwo.strike && optionOne.contractSeries.equals(optionTwo.contractSeries)) {
			throw new RuntimeException("The strikes Or series for both options need to be different to create a Spread.");
		}

		this.optionOne = optionOne;
		this.optionTwo = optionTwo;
	}

	public Option getOptionOne() {
		return optionOne;
	}

	public Option getOptionTwo() {
		return optionTwo;
	}

	/*
	 * This will be true only if one and only one of the options in the spread is existing. If neither are existing, it will return false, or if both are existing it will return
	 * false since the isExisting() method will return true.
	 */
	public boolean isPartiallyExisting() {
		if (!isExisting()) {
			return optionOne.existing || optionTwo.existing;
		} else {
			return false;
		}

	}

	@Override
	public Option[] getOptions() {
		return new Option[] { optionOne, optionTwo };
	}

	public boolean isExisting() {
		return optionOne.existing && optionTwo.existing;
	}

	@Override
	public String toString() {
		return "OptionSpread [optionOne=" + optionOne + ", optionTwo=" + optionTwo + "]";
	}
}