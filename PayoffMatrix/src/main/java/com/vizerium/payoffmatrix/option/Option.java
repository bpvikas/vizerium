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

package com.vizerium.payoffmatrix.option;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.vizerium.commons.blackscholes.BlackScholes;
import com.vizerium.commons.trade.TradeAction;
import com.vizerium.commons.util.NumberFormats;

public abstract class Option implements Cloneable, Serializable, OptionStrategy {

	private static final long serialVersionUID = 5247339041828137416L;

	protected int openInterest;

	protected int openInterestChange;

	protected float currentPremium;

	protected float tradedPremium;

	protected float strike;

	protected OptionType type;

	protected boolean existing;

	protected float underlyingPrice;

	protected ContractSeries contractSeries;

	protected LocalDate expiryDate;

	protected LocalDate tradedDate;

	protected LocalDate currentPremiumDate;

	protected TradeAction tradeAction;

	protected int numberOfLots = 1;

	protected int lotSize;

	protected float impliedVolatility;

	protected float riskFreeInterestRate;

	protected double[] greeks;

	public float getUnderlyingPrice() {
		return underlyingPrice;
	}

	public void setUnderlyingPrice(float underlyingPrice) {
		this.underlyingPrice = underlyingPrice;
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
		this.expiryDate = expiryDate;
	}

	public LocalDate getTradedDate() {
		return tradedDate;
	}

	public void setTradedDate(LocalDate tradedDate) {
		this.tradedDate = tradedDate;
	}

	public LocalDate getCurrentPremiumDate() {
		return currentPremiumDate;
	}

	public void setCurrentPremiumDate(LocalDate currentPremiumDate) {
		this.currentPremiumDate = currentPremiumDate;
	}

	public float getCurrentPremium() {
		return currentPremium;
	}

	public void setCurrentPremium(float currentPremium) {
		this.currentPremium = currentPremium;
	}

	public float getTradedPremium() {
		return tradedPremium;
	}

	public void setTradedPremium(float tradedPremium) {
		this.tradedPremium = tradedPremium;
	}

	public float getStrike() {
		return strike;
	}

	public void setStrike(float strike) {
		this.strike = strike;
	}

	public void setOpenInterest(int openInterest) {
		this.openInterest = openInterest;
	}

	public int getOpenInterest() {
		return openInterest;
	}

	public void setOpenInterestChange(int openInterestChange) {
		this.openInterestChange = openInterestChange;
	}

	public int getOpenInterestChange() {
		return openInterestChange;
	}

	public boolean isExisting() {
		return existing;
	}

	public void setExisting(boolean existing) {
		this.existing = existing;
	}

	public TradeAction getTradeAction() {
		return tradeAction;
	}

	public void setTradeAction(TradeAction tradeAction) {
		this.tradeAction = tradeAction;
	}

	public int getNumberOfLots() {
		return numberOfLots;
	}

	public void setNumberOfLots(int numberOfLots) {
		this.numberOfLots = numberOfLots;
	}

	public int getLotSize() {
		return lotSize;
	}

	public void setLotSize(int lotSize) {
		this.lotSize = lotSize;
	}

	public float getImpliedVolatility() {
		return impliedVolatility;
	}

	public void setImpliedVolatility(float impliedVolatility) {
		// The impliedVolatility is used for Black-Scholes option greeks calculations for which it needs to be a decimal between 0 and 1.
		if (impliedVolatility < 0.0f) {
			throw new RuntimeException("Implied Volatility cannot be -ve.");
		}
		this.impliedVolatility = impliedVolatility;
	}

	public float getRiskFreeInterestRate() {
		return riskFreeInterestRate;
	}

	public void setRiskFreeInterestRate(float riskFreeInterestRate) {
		this.riskFreeInterestRate = riskFreeInterestRate;
	}

	public double[] getGreeks() {
		return greeks;
	}

	public void calculateGreeks() {
		if (type == null || currentPremiumDate == null || expiryDate == null || Float.isNaN(underlyingPrice) || underlyingPrice == 0.0f || Float.isNaN(strike) || strike == 0.0f
				|| Float.isNaN(riskFreeInterestRate) || riskFreeInterestRate == 0.0f || Float.isNaN(impliedVolatility) || impliedVolatility == 0.0f || numberOfLots == 0
				|| tradeAction == null) {
			throw new RuntimeException("Unable to calculate option greeks as one of the input values is null. ");
		}

		double timeToExpiryInYears = (ChronoUnit.DAYS.between(currentPremiumDate, expiryDate) + 1) / 365.0;
		greeks = BlackScholes.getPriceGreeks(type.equals(OptionType.CALL), underlyingPrice, strike, riskFreeInterestRate, impliedVolatility, timeToExpiryInYears);

		for (int i = 0; i < greeks.length; i++) {
			greeks[i] = greeks[i] * numberOfLots * ((tradeAction.equals(TradeAction.LONG) ? 1 : -1));
		}
	}

	@Override
	public double getDelta() {
		if (greeks == null || greeks.length != 6) {
			calculateGreeks();
		}
		return greeks[1];
	}

	public double getTheta() {
		if (greeks == null || greeks.length != 6) {
			calculateGreeks();
		}
		return greeks[2];
	}

	public double getRho() {
		if (greeks == null || greeks.length != 6) {
			calculateGreeks();
		}
		return greeks[3];
	}

	public double getGamma() {
		if (greeks == null || greeks.length != 6) {
			calculateGreeks();
		}
		return greeks[4];
	}

	public double getVega() {
		if (greeks == null || greeks.length != 6) {
			calculateGreeks();
		}
		return greeks[5];
	}

	public float getPremiumPaidReceived() {
		return tradeAction == TradeAction.SHORT ? ((existing ? tradedPremium : currentPremium) * numberOfLots * lotSize)
				: ((existing ? tradedPremium : currentPremium) * numberOfLots * lotSize * -1);
	}

	public void setType(OptionType type) {
		this.type = type;
	}

	public abstract OptionType getType();

	public abstract float getPayoffAtExpiry(float underlyingSpotPrice);

	public abstract float getBreakevenAtExpiry();

	@Override
	public Option[] getOptions() {
		return new Option[] { this };
	}

	@Override
	public abstract Option clone();

	public String toOptionChainDetailsString() {
		return (int) strike + type.getShortName() + "," + currentPremium + "," + currentPremiumDate.toString() + "," + lotSize + ",Iv"
				+ NumberFormats.getForMovingAverage().format(impliedVolatility) + "," + openInterest + "," + openInterestChange + " ";
	}

	public String toExistingPositionDetailsString() {
		return (int) strike + type.getShortName() + "," + "existing," + tradedPremium + "," + tradedDate.toString() + "," + tradeAction.name() + "," + numberOfLots + "lot(s),"
				+ lotSize + " ";
	}

	@Override
	public String toString() {
		return (int) strike + type.getShortName() + "," + (existing ? "existing," : "new,") + (existing ? tradedPremium : currentPremium) + ","
				+ (existing ? tradedDate.toString() : currentPremiumDate.toString()) + "," + tradeAction.name() + "," + numberOfLots + "lot(s)," + lotSize + ",Iv"
				+ NumberFormats.getForMovingAverage().format(impliedVolatility) + ",del" + NumberFormats.getForMovingAverage().format(getDelta()) + " ";
	}

	public String toFullString() {
		return "Option [openInterest=" + openInterest + ", currentPremium=" + currentPremium + ", tradedPremium=" + tradedPremium + ", strike=" + strike + ", type=" + type
				+ ", existing=" + existing + ", underlyingPrice=" + underlyingPrice + ", contractSeries=" + contractSeries + ", expiryDate=" + expiryDate + ", tradedDate="
				+ tradedDate + ", currentPremiumDate=" + currentPremiumDate + ", tradeAction=" + tradeAction + ", numberOfLots=" + numberOfLots + ", lotSize=" + lotSize
				+ ", impliedVolatility=" + impliedVolatility + ", riskFreeInterestRate=" + riskFreeInterestRate + "]";
	}
}
