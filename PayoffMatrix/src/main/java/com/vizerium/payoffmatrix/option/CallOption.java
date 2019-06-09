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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.SerializationUtils;

import com.vizerium.commons.blackscholes.BlackScholes;
import com.vizerium.commons.trade.TradeAction;

public class CallOption extends Option {

	private static final long serialVersionUID = 7201418994594279277L;

	@Override
	public OptionType getType() {
		return type;
	}

	public CallOption() {
		this.type = OptionType.CALL;
	}

	public CallOption(String strike, String openInterest, String currentPremium, float impliedVolatility, String underlyingPrice, LocalDate currentPremiumDate, int lotSize) {
		this.strike = Float.parseFloat(strike);
		this.openInterest = Integer.parseInt(openInterest);
		this.currentPremium = Float.parseFloat(currentPremium);
		setImpliedVolatility(impliedVolatility);
		this.underlyingPrice = Float.parseFloat(underlyingPrice);
		this.currentPremiumDate = currentPremiumDate;
		this.lotSize = lotSize;
		this.type = OptionType.CALL;
	}

	@Override
	public float getShortPayoffAtExpiryForCurrentPremium(float underlyingSpotPrice) {
		// premium - max (0, spot - strike)
		return (currentPremium - Math.max(0.0f, underlyingSpotPrice - strike)) * numberOfLots * lotSize;
	}

	@Override
	public float getLongPayoffAtExpiryForCurrentPremium(float underlyingSpotPrice) {
		// max (0, spot - strike) - premium
		return (Math.max(0.0f, underlyingSpotPrice - strike) - currentPremium) * numberOfLots * lotSize;
	}

	@Override
	public float getShortPayoffAtExpiryForTradedPremium(float underlyingSpotPrice) {
		// premium - max (0, spot - strike)
		return (tradedPremium - Math.max(0.0f, underlyingSpotPrice - strike)) * numberOfLots * lotSize;
	}

	@Override
	public float getLongPayoffAtExpiryForTradedPremium(float underlyingSpotPrice) {
		// max (0, spot - strike) - premium
		return (Math.max(0.0f, underlyingSpotPrice - strike) - tradedPremium) * numberOfLots * lotSize;
	}

	@Override
	public double getDelta() {
		double timeToExpiryInYears = (ChronoUnit.DAYS.between(currentPremiumDate, expiryDate) + 1) / 365.0;
		return BlackScholes.getCallPriceGreeks(underlyingPrice, strike, riskFreeInterestRate, impliedVolatility, timeToExpiryInYears)[1] * numberOfLots
				* ((tradeAction.equals(TradeAction.LONG) ? 1 : -1));
	}

	@Override
	public CallOption clone() {
		return SerializationUtils.clone(this);
	}

}
