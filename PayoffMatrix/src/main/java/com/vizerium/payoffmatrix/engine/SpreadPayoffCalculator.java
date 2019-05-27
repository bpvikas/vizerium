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

import org.apache.log4j.Logger;

import com.vizerium.commons.trade.TradeAction;
import com.vizerium.payoffmatrix.criteria.Criteria;
import com.vizerium.payoffmatrix.dao.OptionDataStore;
import com.vizerium.payoffmatrix.io.Output;
import com.vizerium.payoffmatrix.option.Option;
import com.vizerium.payoffmatrix.option.OptionChainIterator;
import com.vizerium.payoffmatrix.option.OptionSpread;

public class SpreadPayoffCalculator extends PayoffCalculator {

	private static final Logger logger = Logger.getLogger(SpreadPayoffCalculator.class);

	// can return a configurable number of best spread payoffs to look at.
	@Override
	public Output calculatePayoff(Criteria criteria, OptionDataStore optionDataStore) {

		Option[] optionChain = filterOptionChainForEvaluatingNewPositions(optionDataStore.readOptionChainData(criteria), criteria);
		OptionSpread[] optionSpreadChain = createOptionSpreadsFromSingleOptions(optionChain, criteria.getExistingPositions());

		List<OptionStrategiesWithPayoff> allOptionsWithPayoff = new ArrayList<OptionStrategiesWithPayoff>(optionSpreadChain.length * 2);

		float underlyingRangeTop = criteria.getVolatility().getUnderlyingRange().getHigh();
		float underlyingRangeBottom = criteria.getVolatility().getUnderlyingRange().getLow();
		float underlyingRangeStep = criteria.getVolatility().getUnderlyingRange().getStep();

		for (int j = 0; j <= criteria.getMaxOptionSpreadOpenPositions(); j++) {
			OptionChainIterator<OptionSpread> optionChainIterator = new OptionChainIterator<OptionSpread>(optionSpreadChain, j);
			while (optionChainIterator.hasNext()) {
				List<OptionSpread> optionSpreads = optionChainIterator.next();

				if (logger.isDebugEnabled()) {
					logger.debug("Option Spreads being evaluated are : ");
					String optionsSpreadString = "";
					for (OptionSpread optionSpread : optionSpreads) {
						optionsSpreadString += (optionSpread);
					}
					logger.debug(optionsSpreadString);
				}
				if (containsOppositeActionsForSameStrikeAndSeries(optionSpreads)) {
					continue;
				}
				List<Payoff> payoffs = new ArrayList<Payoff>();
				for (float underlyingPrice = underlyingRangeBottom; underlyingPrice <= underlyingRangeTop; underlyingPrice += underlyingRangeStep) {
					float netPayoff = 0.0f;
					for (OptionSpread optionSpread : optionSpreads) {
						for (Option option : optionSpread.getOptions()) {
							if (optionSpread.isExisting()) {
								if (TradeAction.LONG.equals(option.getTradeAction())) {
									netPayoff += option.getLongPayoffAtExpiryForTradedPremium(underlyingPrice);
								} else if (TradeAction.SHORT.equals(option.getTradeAction())) {
									netPayoff += option.getShortPayoffAtExpiryForTradedPremium(underlyingPrice);
								} else {
									throw new RuntimeException("Could not determine whether the existing position is long/short, " + optionSpread);
								}

							} else {
								if (TradeAction.LONG.equals(option.getTradeAction())) {
									netPayoff += option.getLongPayoffAtExpiryForCurrentPremium(underlyingPrice);
								} else if (TradeAction.SHORT.equals(option.getTradeAction())) {
									netPayoff += option.getShortPayoffAtExpiryForCurrentPremium(underlyingPrice);
								} else {
									throw new RuntimeException("Could not determine whether the existing position is long/short, " + optionSpread);
								}
							}
						}
					}
					payoffs.add(new Payoff(underlyingPrice, netPayoff));
				}
				PayoffMatrix payoffMatrix = new PayoffMatrix(payoffs.toArray(new Payoff[payoffs.size()]), criteria.getVolatility().getUnderlyingValue());
				if (logger.isDebugEnabled()) {
					logger.debug(payoffMatrix);
				}
				if (payoffMatrix.getMinNegativePayoff().getPayoff() > (criteria.getMaxLoss() * -1)) {
					allOptionsWithPayoff.add(new OptionStrategiesWithPayoff(optionSpreads.toArray(new OptionSpread[optionSpreads.size()]), payoffMatrix));
				}
			}
		}
		Output output = new Output(allOptionsWithPayoff.toArray(new OptionStrategiesWithPayoff[allOptionsWithPayoff.size()]));
		output.setUnderlyingRange(criteria.getVolatility().getUnderlyingRange());
		output.setOptionStrategiesCount(criteria.getMaxOptionSpreadOpenPositions());
		return output;
	}

	@Override
	public Option[] filterOptionChainForEvaluatingNewPositions(Option[] optionChain, Criteria criteria) {
		List<Option> filteredOptionChain = new ArrayList<Option>();

		for (Option optionChainEntry : optionChain) {
			if (optionChainEntry.getOpenInterest() >= criteria.getMinOpenInterest() && optionChainEntry.getCurrentPremium() <= criteria.getMaxOptionPremium()) {
				Option longOption = optionChainEntry.clone();
				longOption.setTradeAction(TradeAction.LONG);
				longOption.setContractSeries(criteria.getContractSeries());
				filteredOptionChain.add(longOption);

				Option shortOption = optionChainEntry.clone();
				shortOption.setTradeAction(TradeAction.SHORT);
				shortOption.setContractSeries(criteria.getContractSeries());
				filteredOptionChain.add(shortOption);
			}
		}
		if (filteredOptionChain.size() == 0) {
			throw new RuntimeException("Could not find any options matching the filter criteria provided.");
		}

		return filteredOptionChain.toArray(new Option[filteredOptionChain.size()]);
	}

	private OptionSpread[] createOptionSpreadsFromSingleOptions(Option[] optionChain, Option[] existingPositions) {

		for (Option existingOption : existingPositions) {
			for (Option optionChainOption : optionChain) {
				if ((optionChainOption.getStrike() == existingOption.getStrike()) && (optionChainOption.getType().equals(existingOption.getType()))
						&& (optionChainOption.getExpiryDate().equals(existingOption.getExpiryDate()))
						&& optionChainOption.getTradeAction().equals(existingOption.getTradeAction())) {
					optionChainOption.setExisting(true);
				}
			}
		}

		List<OptionSpread> optionSpreadChain = new ArrayList<OptionSpread>();

		for (int i = 0; i < optionChain.length - 1; i++) {
			for (int j = i + 1; j < optionChain.length; j++) {
				try {
					optionSpreadChain.add(new OptionSpread(optionChain[i], optionChain[j]));
				} catch (RuntimeException e) {
					// All invalid spread combinations will be eliminated here, due to the validations in the OptionSpread constructor;
					// Not duplicating any of those validations here.
				}
			}
		}

		if (optionSpreadChain.size() == 0) {
			throw new RuntimeException("Could not find any option Spreads among the filtered options.");
		}

		return optionSpreadChain.toArray(new OptionSpread[optionSpreadChain.size()]);
	}
}
