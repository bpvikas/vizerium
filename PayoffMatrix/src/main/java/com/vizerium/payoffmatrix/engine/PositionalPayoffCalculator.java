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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.vizerium.commons.trade.TradeAction;
import com.vizerium.payoffmatrix.criteria.Criteria;
import com.vizerium.payoffmatrix.dao.OptionDataStore;
import com.vizerium.payoffmatrix.io.Output;
import com.vizerium.payoffmatrix.option.Option;
import com.vizerium.payoffmatrix.option.OptionChainIterator;

public class PositionalPayoffCalculator extends PayoffCalculator {

	private static final Logger logger = Logger.getLogger(PositionalPayoffCalculator.class);

	// can return a configurable number of best payoffs to look at.
	@Override
	public Output calculatePayoff(Criteria criteria, OptionDataStore optionDataStore) {

		Option[] optionChain = filterOptionChainForEvaluatingNewPositions(optionDataStore.readOptionChainData(criteria), criteria);
		updateCurrentDetailsInExistingPositions(optionChain, criteria.getExistingPositions());

		float underlyingRangeTop = criteria.getVolatility().getUnderlyingRange().getHigh();
		float underlyingRangeBottom = criteria.getVolatility().getUnderlyingRange().getLow();
		float underlyingRangeStep = criteria.getVolatility().getUnderlyingRange().getStep();

		Output output = new Output();
		output.setUnderlyingName(criteria.getUnderlyingName());
		output.setUnderlyingRange(criteria.getVolatility().getUnderlyingRange());
		output.setOptionStrategiesCount(criteria.getMaxOptionOpenPositions());

		for (int j = 0; j <= criteria.getMaxOptionOpenPositions() - criteria.getExistingPositions().length; j++) {
			OptionChainIterator<Option> optionChainIterator = new OptionChainIterator<Option>(optionChain, j);
			while (optionChainIterator.hasNext()) {
				List<Option> newPositions = optionChainIterator.next();
				Option[] newAndExistingPositions = ArrayUtils.addAll(criteria.getExistingPositions(), newPositions.toArray(new Option[newPositions.size()]));

				if (logger.isDebugEnabled()) {
					logger.debug("Options being evaluated are : ");
					String optionsString = "";
					for (Option newOrExistingPosition : newAndExistingPositions) {
						optionsString += (newOrExistingPosition);
					}
					logger.debug(optionsString);
				}
				if (containsOppositeActionsForSameStrikeAndSeries(newPositions)) {
					continue;
				}

				int payoffsSize = 0;
				// This for loop is a bit lame to get to the size of payoffs, but wanted to get the accurate count without having to write too complicated code.
				for (float underlyingPrice = underlyingRangeBottom; underlyingPrice <= underlyingRangeTop; underlyingPrice += underlyingRangeStep) {
					++payoffsSize;
				}
				float[][] payoffs = new float[payoffsSize][];

				int payoffCounter = 0;
				for (float underlyingPrice = underlyingRangeBottom; underlyingPrice <= underlyingRangeTop; underlyingPrice += underlyingRangeStep) {
					float netPayoff = 0.0f;
					for (Option newOrExistingPosition : newAndExistingPositions) {
						if (newOrExistingPosition.isExisting()) {
							if (TradeAction.LONG.equals(newOrExistingPosition.getTradeAction())) {
								netPayoff += newOrExistingPosition.getLongPayoffAtExpiryForTradedPremium(underlyingPrice);
							} else if (TradeAction.SHORT.equals(newOrExistingPosition.getTradeAction())) {
								netPayoff += newOrExistingPosition.getShortPayoffAtExpiryForTradedPremium(underlyingPrice);
							} else {
								throw new RuntimeException("Could not determine whether the existing position is long/short, " + newOrExistingPosition);
							}

						} else {
							if (TradeAction.LONG.equals(newOrExistingPosition.getTradeAction())) {
								netPayoff += newOrExistingPosition.getLongPayoffAtExpiryForCurrentPremium(underlyingPrice);
							} else if (TradeAction.SHORT.equals(newOrExistingPosition.getTradeAction())) {
								netPayoff += newOrExistingPosition.getShortPayoffAtExpiryForCurrentPremium(underlyingPrice);
							} else {
								throw new RuntimeException("Could not determine whether the existing position is long/short, " + newOrExistingPosition);
							}
						}
					}
					payoffs[payoffCounter++] = new float[] { underlyingPrice, netPayoff };
				}
				PayoffMatrix payoffMatrix = new PayoffMatrix(payoffs, criteria.getVolatility().getUnderlyingValue());
				if (logger.isDebugEnabled()) {
					logger.debug(payoffMatrix);
				}
				output.performPayoffAnalysis(new OptionStrategiesWithPayoff(newAndExistingPositions, payoffMatrix));
			}
		}
		output.finale();
		return output;
	}

	@Override
	public Option[] filterOptionChainForEvaluatingNewPositions(Option[] optionChain, Criteria criteria) {
		List<Option> filteredOptionChain = new ArrayList<Option>();

		for (Option optionChainEntry : optionChain) {
			if (optionChainEntry.getOpenInterest() >= criteria.getMinOpenInterest() && optionChainEntry.getCurrentPremium() <= criteria.getMaxOptionPremium()) {
				for (int numberOfLots = 1; numberOfLots <= criteria.getMaxOptionNumberOfLots(); numberOfLots++) {
					Option longOption = optionChainEntry.clone();
					longOption.setTradeAction(TradeAction.LONG);
					longOption.setNumberOfLots(numberOfLots);
					longOption.setContractSeries(criteria.getContractSeries());
					filteredOptionChain.add(longOption);

					Option shortOption = optionChainEntry.clone();
					shortOption.setTradeAction(TradeAction.SHORT);
					shortOption.setNumberOfLots(numberOfLots);
					shortOption.setContractSeries(criteria.getContractSeries());
					filteredOptionChain.add(shortOption);
				}
			}
		}
		if (filteredOptionChain.size() == 0) {
			throw new RuntimeException("Could not find any options matching the filter criteria provided.");
		}

		return filteredOptionChain.toArray(new Option[filteredOptionChain.size()]);
	}
}
