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

package com.vizerium.payoffmatrix.option;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Option implements Cloneable, Serializable, OptionStrategy {

	private static final long serialVersionUID = 5247339041828137416L;

	protected int openInterest;

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

	public void setType(OptionType type) {
		this.type = type;
	}

	public float getPremiumPaidReceived() {
		return tradeAction == TradeAction.SHORT ? ((existing ? tradedPremium : currentPremium) * numberOfLots * lotSize) : ((existing ? tradedPremium : currentPremium)
				* numberOfLots * lotSize * -1);
	}

	public abstract OptionType getType();

	public abstract float getShortPayoffAtExpiryForCurrentPremium(float underlyingSpotPrice);

	public abstract float getLongPayoffAtExpiryForCurrentPremium(float underlyingSpotPrice);

	public abstract float getShortPayoffAtExpiryForTradedPremium(float underlyingSpotPrice);

	public abstract float getLongPayoffAtExpiryForTradedPremium(float underlyingSpotPrice);

	@Override
	public Option[] getOptions() {
		return new Option[] { this };
	}

	@Override
	public abstract Option clone();

	@Override
	public String toString() {
		return (int) strike + type.getShortName() + "," + (existing ? "existing," : "new,") + (existing ? tradedPremium : currentPremium) + ","
				+ (existing ? tradedDate.toString() : currentPremiumDate.toString()) + "," + tradeAction.name() + "," + numberOfLots + "lot(s)," + lotSize + " ";
	}

	public String toFullString() {
		return "Option [openInterest=" + openInterest + ", currentPremium=" + currentPremium + ", tradedPremium=" + tradedPremium + ", strike=" + strike + ", type=" + type
				+ ", existing=" + existing + ", underlyingPrice=" + underlyingPrice + ", contractSeries=" + contractSeries + ", expiryDate=" + expiryDate + ", tradedDate="
				+ tradedDate + ", currentPremiumDate=" + currentPremiumDate + ", tradeAction=" + tradeAction + ", numberOfLots=" + numberOfLots + ", lotSize=" + lotSize + "]";
	}
}
