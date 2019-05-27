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

import java.util.List;

import org.apache.log4j.Logger;

import com.vizerium.payoffmatrix.option.Option;

public class MinimumOpenInterestCalculator {

	private static Logger logger = Logger.getLogger(MinimumOpenInterestCalculator.class);

	public static int calculateMinimumOpenInterest(List<Option> optionChain) {

		float openInterestLogNaturalSum = 0.0f;
		for (Option option : optionChain) {
			openInterestLogNaturalSum += Math.log(option.getOpenInterest());
		}
		float openInterestLogNaturalMean = openInterestLogNaturalSum / optionChain.size();

		int minimumOpenInterest = (int) Math.exp(openInterestLogNaturalMean);
		logger.info("Auto-calculated minimum OI: " + minimumOpenInterest);
		return minimumOpenInterest;
	}
}
