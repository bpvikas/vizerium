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

import java.time.LocalDate;

import com.vizerium.payoffmatrix.dao.LocalDataSource;
import com.vizerium.payoffmatrix.dao.RemoteDataSource;
import com.vizerium.payoffmatrix.option.ContractDuration;
import com.vizerium.payoffmatrix.option.ContractSeries;
import com.vizerium.payoffmatrix.option.Option;
import com.vizerium.payoffmatrix.option.TradingBias;
import com.vizerium.payoffmatrix.volatility.DateRange;
import com.vizerium.payoffmatrix.volatility.Volatility;

public class Criteria {

	private String underlyingName;

	private float underlyingValue;

	private Volatility volatility;

	private float maxLoss;

	private TradingBias tradingBias;

	private ContractDuration contractDuration;

	private ContractSeries contractSeries;

	private LocalDate expiryDate;

	private Option[] existingPositions;

	private RemoteDataSource optionChainRemoteDatasource;

	private LocalDataSource optionChainLocalDatasource;

	private LocalDataSource historicalDataLocalDatasource;

	private DateRange historicalDataDateRange;

	private int minOpenInterest;

	private float maxOptionPremium;

	private float sellOrderMargin;

	private float investibleAmount;

	private int maxOptionOpenPositions;

	private int maxOptionSpreadOpenPositions;

	private int maxOptionNumberOfLots;

	private int lotSize;

	private float riskFreeInterestRate;

	public String getUnderlyingName() {
		return underlyingName;
	}

	public void setUnderlyingName(String underlyingName) {
		this.underlyingName = underlyingName;
	}

	public float getUnderlyingValue() {
		return underlyingValue;
	}

	public void setUnderlyingValue(float underlyingValue) {
		this.underlyingValue = underlyingValue;
	}

	public Volatility getVolatility() {
		return volatility;
	}

	public void setVolatility(Volatility volatility) {
		this.volatility = volatility;
	}

	public float getMaxLoss() {
		return maxLoss;
	}

	public void setMaxLoss(float maxLoss) {
		this.maxLoss = maxLoss;
	}

	public TradingBias getTradingBias() {
		return tradingBias;
	}

	public void setTradingBias(TradingBias tradingBias) {
		this.tradingBias = tradingBias;
	}

	public ContractDuration getContractDuration() {
		return contractDuration;
	}

	public void setContractDuration(ContractDuration contractDuration) {
		this.contractDuration = contractDuration;
	}

	public ContractSeries getContractSeries() {
		return contractSeries;
	}

	public void setContractSeries(ContractSeries contractSeries) {
		this.contractSeries = contractSeries;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		if (expiryDate.isBefore(LocalDate.now())) {
			throw new RuntimeException("Expiry Date : " + expiryDate + " cannot be in the past.");
		}
		this.expiryDate = expiryDate;
	}

	public Option[] getExistingPositions() {
		return existingPositions;
	}

	public void setExistingPositions(Option[] existingPositions) {
		this.existingPositions = existingPositions;
	}

	public RemoteDataSource getOptionChainRemoteDatasource() {
		return optionChainRemoteDatasource;
	}

	public void setOptionChainRemoteDatasource(RemoteDataSource optionChainRemoteDatasource) {
		this.optionChainRemoteDatasource = optionChainRemoteDatasource;
	}

	public LocalDataSource getOptionChainLocalDatasource() {
		return optionChainLocalDatasource;
	}

	public void setOptionChainLocalDatasource(LocalDataSource optionChainLocalDatasource) {
		this.optionChainLocalDatasource = optionChainLocalDatasource;
	}

	public LocalDataSource getHistoricalDataLocalDatasource() {
		return historicalDataLocalDatasource;
	}

	public void setHistoricalDataLocalDatasource(LocalDataSource historicalDataLocalDatasource) {
		this.historicalDataLocalDatasource = historicalDataLocalDatasource;
	}

	public DateRange getHistoricalDataDateRange() {
		return historicalDataDateRange;
	}

	public void setHistoricalDataDateRange(DateRange historicalDataDateRange) {
		this.historicalDataDateRange = historicalDataDateRange;
	}

	public int getMinOpenInterest() {
		return minOpenInterest;
	}

	public void setMinOpenInterest(int minOpenInterest) {
		this.minOpenInterest = minOpenInterest;
	}

	public float getMaxOptionPremium() {
		return maxOptionPremium;
	}

	public void setMaxOptionPremium(float maxOptionPremium) {
		this.maxOptionPremium = maxOptionPremium;
	}

	public float getSellOrderMargin() {
		return sellOrderMargin;
	}

	public void setSellOrderMargin(float sellOrderMargin) {
		this.sellOrderMargin = sellOrderMargin;
	}

	public float getInvestibleAmount() {
		return investibleAmount;
	}

	public void setInvestibleAmount(float investibleAmount) {
		this.investibleAmount = investibleAmount;
	}

	public int getMaxOptionOpenPositions() {
		return maxOptionOpenPositions;
	}

	public void setMaxOptionOpenPositions(int maxOptionOpenPositions) {
		this.maxOptionOpenPositions = maxOptionOpenPositions;
	}

	public int getMaxOptionSpreadOpenPositions() {
		return maxOptionSpreadOpenPositions;
	}

	public void setMaxOptionSpreadOpenPositions(int maxOptionSpreadOpenPositions) {
		this.maxOptionSpreadOpenPositions = maxOptionSpreadOpenPositions;
	}

	public int getMaxOptionNumberOfLots() {
		return maxOptionNumberOfLots;
	}

	public void setMaxOptionNumberOfLots(int maxOptionNumberOfLots) {
		this.maxOptionNumberOfLots = maxOptionNumberOfLots;
	}

	public int getLotSize() {
		return lotSize;
	}

	public void setLotSize(int lotSize) {
		this.lotSize = lotSize;
	}

	public float getRiskFreeInterestRate() {
		return riskFreeInterestRate;
	}

	public void setRiskFreeInterestRate(float riskFreeInterestRate) {
		this.riskFreeInterestRate = riskFreeInterestRate;
	}

	private String getExistingPostionDetailsString() {
		String existingPositionDetails = "";
		if (existingPositions != null && existingPositions.length > 0) {
			for (Option existingPostion : existingPositions) {
				existingPositionDetails += existingPostion.toExistingPositionDetailsString();
			}
		}
		return existingPositionDetails;
	}

	@Override
	public String toString() {
		return "Criteria [underlyingName=" + underlyingName + ", underlyingValue=" + underlyingValue + ", volatility=" + volatility + ", maxLoss=" + maxLoss + ", tradingBias="
				+ tradingBias + ", contractDuration=" + contractDuration + ", contractSeries=" + contractSeries + ", expiryDate=" + expiryDate + ", existingPositions="
				+ getExistingPostionDetailsString() + ", optionChainRemoteDatasource=" + optionChainRemoteDatasource + ", optionChainLocalDatasource=" + optionChainLocalDatasource
				+ ", historicalDataLocalDatasource=" + historicalDataLocalDatasource + ", historicalDataDateRange=" + historicalDataDateRange + ", minOpenInterest="
				+ minOpenInterest + ", maxOptionPremium=" + maxOptionPremium + ", sellOrderMargin=" + sellOrderMargin + ", investibleAmount=" + investibleAmount
				+ ", maxOptionOpenPositions=" + maxOptionOpenPositions + ", maxOptionSpreadOpenPositions=" + maxOptionSpreadOpenPositions + ", maxOptionNumberOfLots="
				+ maxOptionNumberOfLots + ", lotSize=" + lotSize + ", riskFreeInterestRate=" + riskFreeInterestRate + "]";
	}
}
