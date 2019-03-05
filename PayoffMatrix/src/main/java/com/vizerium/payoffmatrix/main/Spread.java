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

package com.vizerium.payoffmatrix.main;

import org.apache.log4j.Logger;

import com.vizerium.commons.util.LogUtils;
import com.vizerium.payoffmatrix.criteria.Criteria;
import com.vizerium.payoffmatrix.criteria.CriteriaReader;
import com.vizerium.payoffmatrix.criteria.PropertiesFileCriteriaReader;
import com.vizerium.payoffmatrix.dao.OptionDataStore;
import com.vizerium.payoffmatrix.dao.OptionDataStoreFactory;
import com.vizerium.payoffmatrix.engine.SpreadPayoffCalculator;
import com.vizerium.payoffmatrix.io.OptionChainReader;
import com.vizerium.payoffmatrix.io.OptionChainReaderFactory;
import com.vizerium.payoffmatrix.io.Output;
import com.vizerium.payoffmatrix.option.Option;

public class Spread {
	static {
		LogUtils.initializeLogging(Spread.class.getSimpleName());
	}

	private static final Logger logger = Logger.getLogger(Spread.class);

	public static void main(String[] args) {

		CriteriaReader criteriaReader = new PropertiesFileCriteriaReader();
		Criteria criteria = criteriaReader.readCriteria();

		OptionChainReader optionChainReader = OptionChainReaderFactory.getOptionChainReader(criteria.getRemoteDatasource());
		Option[] optionChain = optionChainReader.readOptionChain(criteria);
		OptionDataStore optionDataStore = OptionDataStoreFactory.getOptionDataStore(criteria.getLocalDatasource());
		optionDataStore.saveOptionChainData(criteria, optionChain);

		SpreadPayoffCalculator payoffCalculator = new SpreadPayoffCalculator();
		Output output = payoffCalculator.calculatePayoff(criteria, optionDataStore);
		logger.info(criteria);
		logger.info(output);
	}
}
