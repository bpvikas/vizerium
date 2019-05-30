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

import com.vizerium.payoffmatrix.criteria.Criteria;
import com.vizerium.payoffmatrix.dao.OptionDataStore;
import com.vizerium.payoffmatrix.io.Output;
import com.vizerium.payoffmatrix.option.Option;
import com.vizerium.payoffmatrix.option.OptionStrategy;

public abstract class PayoffCalculator {

	private static Logger logger = Logger.getLogger(PayoffCalculator.class);

	public static long countOptionWithOppositeActions = 0L;

	public abstract Option[] filterOptionChainForEvaluatingNewPositions(Option[] optionChain, Criteria criteria);

	public abstract Output calculatePayoff(Criteria criteria, OptionDataStore optionDataStore);

	protected <E extends OptionStrategy> boolean containsOppositeActionsForSameStrikeAndSeries(List<E> optionStrategies) {
		List<Option> currentOptions = new ArrayList<Option>();
		for (OptionStrategy optionStrategy : optionStrategies) {
			for (Option option : optionStrategy.getOptions()) {
				currentOptions.add(option);
			}
		}
		for (int i = 0; i < currentOptions.size() - 1; i++) {
			for (int j = i + 1; j < currentOptions.size(); j++) {
				if (currentOptions.get(i).getStrike() == currentOptions.get(j).getStrike() && currentOptions.get(i).getType().equals(currentOptions.get(j).getType())
						&& currentOptions.get(i).getContractSeries().equals(currentOptions.get(j).getContractSeries())
						&& currentOptions.get(i).getNumberOfLots() == currentOptions.get(j).getNumberOfLots()
						&& !(currentOptions.get(i).getTradeAction().equals(currentOptions.get(j).getTradeAction()))) {
					if (logger.isDebugEnabled()) {
						logger.debug("Same option with opposite actions. " + currentOptions.get(i) + " " + currentOptions.get(j));
					}
					++countOptionWithOppositeActions;
					return true;
				}
			}
		}
		return false;
	}
}
