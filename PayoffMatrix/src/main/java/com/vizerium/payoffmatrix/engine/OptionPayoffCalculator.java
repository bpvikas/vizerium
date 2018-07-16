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

import com.vizerium.payoffmatrix.criteria.Criteria;
import com.vizerium.payoffmatrix.dao.OptionDataStore;
import com.vizerium.payoffmatrix.io.Output;
import com.vizerium.payoffmatrix.option.Option;
import com.vizerium.payoffmatrix.option.OptionChainIterator;
import com.vizerium.payoffmatrix.option.TradeAction;

public class OptionPayoffCalculator {

	// can return a configurable number of best payoffs to look at.
	public Output calculatePayoff(Criteria criteria, OptionDataStore optionDataStore) {

		Option[] optionChain = filterOptionChainForEvaluatingNewPositions(optionDataStore.readOptionChainData(criteria), criteria);
		List<OptionsWithPayoff> allOptionsWithPayoff = new ArrayList<OptionsWithPayoff>(optionChain.length * 2);

		float underlyingRangeTop = criteria.getVolatility().getUnderlyingRange().getHigh();
		float underlyingRangeBottom = criteria.getVolatility().getUnderlyingRange().getLow();
		float underlyingRangeStep = criteria.getVolatility().getUnderlyingRange().getStep();

		for (int j = 0; j <= criteria.getMaxOptionOpenPositions() - criteria.getExistingPositions().length; j++) {
			OptionChainIterator optionChainIterator = new OptionChainIterator(optionChain, j);
			while (optionChainIterator.hasNext()) {
				Option[] newPositions = optionChainIterator.next();
				Option[] newAndExistingPositions = ArrayUtils.addAll(criteria.getExistingPositions(), newPositions);

				System.out.println("Options being evaluated are : ");
				for (Option newOrExistingPosition : newAndExistingPositions) {
					System.out.print(newOrExistingPosition);
				}

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
				System.out.println(payoffMatrix);
				if (payoffMatrix.getMinNegativePayoff().getPayoff() > (criteria.getMaxLoss() * -1)) {
					allOptionsWithPayoff.add(new OptionsWithPayoff(newAndExistingPositions, payoffMatrix));
				}
			}
		}
		return new Output(allOptionsWithPayoff.toArray(new OptionsWithPayoff[allOptionsWithPayoff.size()]));
	}

	private Option[] filterOptionChainForEvaluatingNewPositions(Option[] optionChain, Criteria criteria) {
		List<Option> filteredOptionChain = new ArrayList<Option>();

		for (Option optionChainEntry : optionChain) {
			if (optionChainEntry.getOpenInterest() >= criteria.getMinOpenInterest() && optionChainEntry.getCurrentPremium() <= criteria.getMaxOptionPremium()) {
				for (int numberOfLots = 1; numberOfLots <= criteria.getMaxOptionNumberOfLots(); numberOfLots++) {
					Option longOption = optionChainEntry.clone();
					longOption.setTradeAction(TradeAction.LONG);
					longOption.setNumberOfLots(numberOfLots);
					filteredOptionChain.add(longOption);

					Option shortOption = optionChainEntry.clone();
					shortOption.setTradeAction(TradeAction.SHORT);
					shortOption.setNumberOfLots(numberOfLots);
					filteredOptionChain.add(shortOption);
				}
			}
		}

		return filteredOptionChain.toArray(new Option[filteredOptionChain.size()]);
	}
}
