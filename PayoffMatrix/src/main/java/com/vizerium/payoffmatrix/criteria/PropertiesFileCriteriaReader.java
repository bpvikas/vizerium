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

package com.vizerium.payoffmatrix.criteria;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.vizerium.commons.io.FileUtils;
import com.vizerium.commons.trade.TradeAction;
import com.vizerium.payoffmatrix.dao.HistoricalData;
import com.vizerium.payoffmatrix.dao.HistoricalDataStore;
import com.vizerium.payoffmatrix.dao.HistoricalDataStoreFactory;
import com.vizerium.payoffmatrix.dao.LocalDataSource;
import com.vizerium.payoffmatrix.dao.RemoteDataSource;
import com.vizerium.payoffmatrix.engine.ExpiryDateCalculator;
import com.vizerium.payoffmatrix.exchange.Exchanges;
import com.vizerium.payoffmatrix.option.CallOption;
import com.vizerium.payoffmatrix.option.ContractDuration;
import com.vizerium.payoffmatrix.option.ContractSeries;
import com.vizerium.payoffmatrix.option.Option;
import com.vizerium.payoffmatrix.option.PutOption;
import com.vizerium.payoffmatrix.option.TradingBias;
import com.vizerium.payoffmatrix.volatility.CsvHistoricalDataVolatilityCalculator;
import com.vizerium.payoffmatrix.volatility.Range;
import com.vizerium.payoffmatrix.volatility.Volatility;
import com.vizerium.payoffmatrix.volatility.VolatilityCalculator;

public class PropertiesFileCriteriaReader implements CriteriaReader {

	private static final Logger logger = Logger.getLogger(PropertiesFileCriteriaReader.class);

	private DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyy");

	private static final String defaultCriteriaFileName = "defaultcriteria.txt";
	private File defaultCriteriaPropertyFile;

	private static final String inputCriteriaFileExtension = "_inputcriteria.txt";
	private File criteriaPropertyFile;

	public PropertiesFileCriteriaReader() {
		defaultCriteriaPropertyFile = FileUtils.getLastModifiedFileInDirectory(FileUtils.directoryPath + "input-criteria/", defaultCriteriaFileName);
		criteriaPropertyFile = FileUtils.getLastModifiedFileInDirectory(FileUtils.directoryPath + "input-criteria/", inputCriteriaFileExtension);
	}

	@Override
	public Criteria readCriteria() {

		Criteria criteria = new Criteria();

		try {
			Properties criteriaProperties = new Properties();
			criteriaProperties.load(new BufferedReader(new FileReader(defaultCriteriaPropertyFile)));
			criteriaProperties.load(new BufferedReader(new FileReader(criteriaPropertyFile)));

			criteria.setUnderlyingName(criteriaPropertyFile.getName().substring(0, criteriaPropertyFile.getName().indexOf(inputCriteriaFileExtension)));
			CriteriaFactory.addCriteria(criteria.getUnderlyingName(), criteria);

			criteria.setContractSeries(ContractSeries.getByProperty(criteriaProperties.getProperty("contract.series")));
			criteria.setContractDuration(ContractDuration.getByProperty(criteriaProperties.getProperty("contract.duration")));

			if (StringUtils.isNotBlank(criteriaProperties.getProperty("expiryDate"))) {
				criteria.setExpiryDate(LocalDate.parse(criteriaProperties.getProperty("expiryDate"), df));
			} else {
				criteria.setExpiryDate(ExpiryDateCalculator.convertToExpiryDate(criteria.getContractDuration(), criteria.getContractSeries()));
			}

			criteria.setHistoricalDataLocalDatasource(LocalDataSource.getByProperty(criteriaProperties.getProperty("historical.data.local.datasource")));
			HistoricalDataStore historicaldataStore = HistoricalDataStoreFactory.getHistoricalDataStore(criteria.getHistoricalDataLocalDatasource(), criteria.getUnderlyingName());

			HistoricalData historicalData = historicaldataStore.readHistoricalData(null);
			criteria.setUnderlyingValue(historicalData.getLastClosingPrice());
			criteria.setHistoricalDataDateRange(historicalData.getStartAndEndDates());
			criteria.setVolatility(getVolatility(criteriaProperties, historicalData, criteria.getExpiryDate()));

			criteria.setMaxLoss(Float.parseFloat(criteriaProperties.getProperty("maxLoss")));

			criteria.setTradingBias(TradingBias.getByProperty(criteriaProperties.getProperty("tradingBias")));

			criteria.setExistingPositions(getAllExistingPositions(criteriaProperties));

			criteria.setOptionChainRemoteDatasource(RemoteDataSource.getByProperty(criteriaProperties.getProperty("option.chain.remote.datasource")));
			criteria.setOptionChainLocalDatasource(LocalDataSource.getByProperty(criteriaProperties.getProperty("option.chain.local.datasource")));

			if (StringUtils.isNotBlank(criteriaProperties.getProperty("minimum.openinterest"))) {
				criteria.setMinOpenInterest(Integer.parseInt(criteriaProperties.getProperty("minimum.openinterest")));
			}
			criteria.setMaxOptionPremium(Float.parseFloat(criteriaProperties.getProperty("maximum.option.premium")));
			criteria.setSellOrderMargin(Float.parseFloat(criteriaProperties.getProperty("sell.order.margin")));

			criteria.setInvestibleAmount(Float.parseFloat(criteriaProperties.getProperty("investing.amount")));

			criteria.setMaxOptionOpenPositions(Integer.parseInt(criteriaProperties.getProperty("max.option.positions")));
			criteria.setMaxOptionSpreadOpenPositions(Integer.parseInt(criteriaProperties.getProperty("max.option.spread.positions")));
			criteria.setMaxOptionNumberOfLots(Integer.parseInt(criteriaProperties.getProperty("max.option.lots")));

			criteria.setLotSize(Integer.parseInt(criteriaProperties.getProperty("lotsize")));

			String criteriaRiskFreeInterestRate = criteriaProperties.getProperty("riskfree.interest.rate");
			if (StringUtils.isBlank(criteriaRiskFreeInterestRate)) {
				criteriaRiskFreeInterestRate = criteriaProperties.getProperty("riskfree.interest.rate.default");
			}
			criteria.setRiskFreeInterestRate(Float.parseFloat(criteriaRiskFreeInterestRate.trim()) / 100.0f);

		} catch (IOException e) {
			logger.error("An error occurred while reading input criteria properties file.", e);
			throw new RuntimeException(e);
		}
		if (logger.isInfoEnabled()) {
			logger.info("The input values are : " + criteria);
		}
		return criteria;
	}

	private Option[] getAllExistingPositions(Properties criteriaProperties) {

		List<Option> existingOptionOpenPositions = new ArrayList<Option>();
		try {
			Set<String> propertyNames = criteriaProperties.stringPropertyNames();
			for (String propertyName : propertyNames) {
				if (propertyName.toLowerCase().startsWith("existingposition")) {
					String propertyValue = criteriaProperties.getProperty(propertyName);

					if (StringUtils.isBlank(propertyValue)) {
						continue;
					}

					String[] existingPositionDetails = propertyValue.split(",");
					if (existingPositionDetails.length != 7) {
						throw new RuntimeException("Unable to get all details for an existing option. " + propertyValue);
					}

					Option existingOption;
					if (existingPositionDetails[0].trim().equalsIgnoreCase("CE")) {
						existingOption = new CallOption();
					} else if (existingPositionDetails[0].trim().equalsIgnoreCase("PE")) {
						existingOption = new PutOption();
					} else {
						throw new RuntimeException("Unable to identify whether the existing Option is a Call or Put. ");
					}
					existingOption.setStrike(Float.parseFloat(existingPositionDetails[1]));
					existingOption.setTradeAction(TradeAction.getByProperty(existingPositionDetails[2]));
					existingOption.setNumberOfLots(Integer.parseInt(existingPositionDetails[3]));
					existingOption.setTradedPremium(Float.parseFloat(existingPositionDetails[4]));

					existingOption.setTradedDate(LocalDate.parse(existingPositionDetails[5], df));
					existingOption.setContractSeries(ContractSeries.getByProperty(existingPositionDetails[6]));

					existingOption.setExpiryDate(ExpiryDateCalculator.convertToExpiryDate(criteriaProperties.getProperty("contract.duration"), existingPositionDetails[6]));
					existingOption.setExisting(true);
					existingOption.setLotSize(Integer.parseInt(criteriaProperties.getProperty("lotsize")));

					existingOptionOpenPositions.add(existingOption);
				}
			}
		} catch (Exception e) {
			logger.error("An error occurred while reading existing positions from input criteria.", e);
			throw new RuntimeException(e);
		}
		return existingOptionOpenPositions.toArray(new Option[existingOptionOpenPositions.size()]);
	}

	private Volatility getVolatility(Properties criteriaProperties, HistoricalData historicalData, LocalDate expiryDate) {

		Volatility volatility = new Volatility();

		String underlyingRangeBottom = criteriaProperties.getProperty("underlying.range.bottom");
		String underlyingRangeTop = criteriaProperties.getProperty("underlying.range.top");
		String underlyingRangeStep = criteriaProperties.getProperty("underlying.range.step");

		if (StringUtils.isNoneBlank(underlyingRangeBottom, underlyingRangeTop)) {
			volatility.setUnderlyingRange(new Range(underlyingRangeBottom, underlyingRangeTop, underlyingRangeStep));
		} else {
			String underlyingImpliedVolatility = criteriaProperties.getProperty("underlying.impliedVolatility");

			VolatilityCalculator volatilityCalculator = new CsvHistoricalDataVolatilityCalculator();
			volatility = volatilityCalculator.calculateVolatility(historicalData);

			if (StringUtils.isNotBlank(underlyingImpliedVolatility)) {
				volatility.setStandardDeviationFromImpliedVolatility(underlyingImpliedVolatility);
			}
			volatility.setUnderlyingValue(historicalData.getLastClosingPrice());

			String underlyingRangeStdDevMultiple = criteriaProperties.getProperty("underlying.volatility.standardDeviationMultiple");
			volatility.setStandardDeviationMultiple(Float.parseFloat(underlyingRangeStdDevMultiple));

			volatility.calculateUnderlyingRange(historicalData.getLast().getDate(), expiryDate, Exchanges.get(criteriaProperties.getProperty("exchange")),
					Float.parseFloat(underlyingRangeStep));
		}

		return volatility;

	}
}
