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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.vizerium.payoffmatrix.criteria.Criteria;
import com.vizerium.payoffmatrix.dao.OptionDataStore;
import com.vizerium.payoffmatrix.io.Output;
import com.vizerium.payoffmatrix.option.Option;
import com.vizerium.payoffmatrix.option.OptionChainIterator;
import com.vizerium.payoffmatrix.option.TradeAction;

public class PositionalPayoffCalculator extends PayoffCalculator {

	private static final Logger logger = Logger.getLogger(PositionalPayoffCalculator.class);

	// can return a configurable number of best payoffs to look at.
	@Override
	public Output calculatePayoff(Criteria criteria, OptionDataStore optionDataStore) {

		Option[] optionChain = filterOptionChainForEvaluatingNewPositions(optionDataStore.readOptionChainData(criteria), criteria);
		List<OptionStrategiesWithPayoff> allOptionsWithPayoff = new ArrayList<OptionStrategiesWithPayoff>(optionChain.length * 2);

		float underlyingRangeTop = criteria.getVolatility().getUnderlyingRange().getHigh();
		float underlyingRangeBottom = criteria.getVolatility().getUnderlyingRange().getLow();
		float underlyingRangeStep = criteria.getVolatility().getUnderlyingRange().getStep();

		for (int j = 0; j <= criteria.getMaxOptionOpenPositions() - criteria.getExistingPositions().length; j++) {
			OptionChainIterator<Option> optionChainIterator = new OptionChainIterator<Option>(optionChain, j);
			while (optionChainIterator.hasNext()) {
				List<Option> newPositions = optionChainIterator.next();
				Option[] newAndExistingPositions = ArrayUtils.addAll(criteria.getExistingPositions(), newPositions.toArray(new Option[newPositions.size()]));

				logger.info("Options being evaluated are : ");
				String optionsString = "";
				for (Option newOrExistingPosition : newAndExistingPositions) {
					optionsString += (newOrExistingPosition);
				}
				logger.info(optionsString);

				List<Payoff> payoffs = new ArrayList<Payoff>();
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
					payoffs.add(new Payoff(underlyingPrice, netPayoff));
				}
				PayoffMatrix payoffMatrix = new PayoffMatrix(payoffs.toArray(new Payoff[payoffs.size()]), criteria.getVolatility().getUnderlyingValue());
				logger.info(payoffMatrix);
				if (payoffMatrix.getMinNegativePayoff().getPayoff() > (criteria.getMaxLoss() * -1)) {
					allOptionsWithPayoff.add(new OptionStrategiesWithPayoff(newAndExistingPositions, payoffMatrix));
				}
			}
		}
		return new Output(allOptionsWithPayoff.toArray(new OptionStrategiesWithPayoff[allOptionsWithPayoff.size()]));
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
