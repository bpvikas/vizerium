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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.vizerium.payoffmatrix.comparator.MaximumOpenInterestChangeComparator;
import com.vizerium.payoffmatrix.comparator.MaximumOpenInterestComparator;
import com.vizerium.payoffmatrix.option.Option;
import com.vizerium.payoffmatrix.option.OptionType;
import com.vizerium.payoffmatrix.volatility.Range;

public class OpenInterestAnalytics {

	private static Logger logger = Logger.getLogger(OpenInterestAnalytics.class);

	private List<Option> optionChain;

	public OpenInterestAnalytics(List<Option> optionChain) {
		this.optionChain = optionChain;
	}

	public Range getOIBasedRange(int rangeStep) {

		Collections.sort(optionChain, new MaximumOpenInterestComparator());
		if (logger.isInfoEnabled()) {
			logger.info("");
			logger.info("The options with maximum OI are ");
			for (int i = 0; i <= 7; i++) {
				logger.info(optionChain.get(i).toOptionChainDetailsString());
			}
			logger.info("");
		}

		Option highestOICallOption = null;
		Option secondHighestOICallOption = null;
		Option highestOIPutOption = null;
		Option secondHighestOIPutOption = null;

		for (Option o : optionChain) {
			if (highestOICallOption == null && o.getType().equals(OptionType.CALL)) {
				highestOICallOption = o;
			} else if (highestOICallOption != null && secondHighestOICallOption == null && o.getType().equals(OptionType.CALL)) {
				secondHighestOICallOption = o;
			} else if (highestOIPutOption == null && o.getType().equals(OptionType.PUT)) {
				highestOIPutOption = o;
			} else if (highestOIPutOption != null && secondHighestOIPutOption == null && o.getType().equals(OptionType.PUT)) {
				secondHighestOIPutOption = o;
			}

			if (highestOICallOption != null && secondHighestOICallOption != null && highestOIPutOption != null && secondHighestOIPutOption != null) {
				break;
			}
		}
		float[] ranges = new float[] { highestOIPutOption.getStrike() - highestOIPutOption.getCurrentPremium(),
				highestOICallOption.getStrike() + highestOICallOption.getCurrentPremium(), secondHighestOIPutOption.getStrike() - secondHighestOIPutOption.getCurrentPremium(),
				secondHighestOICallOption.getStrike() + secondHighestOICallOption.getCurrentPremium() };
		Arrays.sort(ranges);

		Range oiBasedRange = new Range(((int) (ranges[0] / rangeStep)) * rangeStep, ((int) (ranges[ranges.length - 1] / rangeStep) + 1) * rangeStep);
		if (logger.isInfoEnabled()) {
			logger.info("Estimated Market Range based on current OI calculation : " + oiBasedRange);
		}
		return oiBasedRange;
	}

	public float calculatePCR() {
		return calculatePCR(-1);
	}

	public float calculatePCR(int topN) {
		// Calculating PCR on all values in case of -ve or zero topN
		if (topN <= 0) {
			topN = 10000;
		}
		Collections.sort(optionChain, new MaximumOpenInterestComparator());

		int putOItotal = 0;
		int callOItotal = 0;

		for (int i = 0; i < Math.min(topN, optionChain.size()); i++) {
			if (optionChain.get(i).getType().equals(OptionType.CALL)) {
				callOItotal += optionChain.get(i).getOpenInterest();
			} else if (optionChain.get(i).getType().equals(OptionType.PUT)) {
				putOItotal += optionChain.get(i).getOpenInterest();
			} else {
				throw new RuntimeException("Unable to determine type of option for : " + optionChain.get(i).toOptionChainDetailsString());
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("PCR for " + (topN == 10000 ? "All" : String.valueOf(topN)) + " options is " + (float) putOItotal / callOItotal);
		}

		return (float) putOItotal / callOItotal;
	}

	public void getMaxOpenInterestAdditions() {
		Collections.sort(optionChain, new MaximumOpenInterestChangeComparator());
		if (logger.isInfoEnabled()) {
			logger.info("");
			logger.info("The options with maximum OI additions are ");
			for (int i = 0; i <= 4; i++) {
				if (optionChain.get(i).getOpenInterestChange() < 0) {
					break;
				}
				logger.info(optionChain.get(i).toOptionChainDetailsString());
			}
			logger.info("");
		}
	}

	public void getMaxOpenInterestExits() {
		Collections.sort(optionChain, new MaximumOpenInterestChangeComparator().reversed());
		if (logger.isInfoEnabled()) {
			logger.info("");
			logger.info("The options with maximum OI exits are ");
			for (int i = 0; i <= 4; i++) {
				if (optionChain.get(i).getOpenInterestChange() > 0) {
					break;
				}
				logger.info(optionChain.get(i).toOptionChainDetailsString());
			}
			logger.info("");
		}
	}

	public int calculateMinimumOpenInterest() {

		float openInterestLogNaturalSum = 0.0f;
		for (Option option : optionChain) {
			openInterestLogNaturalSum += Math.log(option.getOpenInterest());
		}
		float openInterestLogNaturalMean = openInterestLogNaturalSum / optionChain.size();

		int minimumOpenInterest = (int) Math.exp(openInterestLogNaturalMean);
		if (logger.isInfoEnabled()) {
			logger.info("Auto-calculated minimum OI: " + minimumOpenInterest);
		}
		return minimumOpenInterest;
	}

}
