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

import java.time.LocalDate;

import org.apache.commons.lang3.SerializationUtils;

public class PutOption extends Option {

	private static final long serialVersionUID = 3997232431317895590L;

	@Override
	public OptionType getType() {
		return type;
	}

	public PutOption() {
		this.type = OptionType.PUT;
	}

	public PutOption(String strike, String openInterest, String currentPremium, String underlyingPrice, LocalDate currentPremiumDate, int lotSize) {
		this.strike = Float.parseFloat(strike);
		this.openInterest = Integer.parseInt(openInterest);
		this.currentPremium = Float.parseFloat(currentPremium);
		this.underlyingPrice = Float.parseFloat(underlyingPrice);
		this.currentPremiumDate = currentPremiumDate;
		this.lotSize = lotSize;
		this.type = OptionType.PUT;
	}

	@Override
	public float getShortPayoffAtExpiryForCurrentPremium(float underlyingSpotPrice) {
		// premium - max (0, strike - spot)
		return (currentPremium - Math.max(0.0f, strike - underlyingSpotPrice)) * numberOfLots * lotSize;
	}

	@Override
	public float getLongPayoffAtExpiryForCurrentPremium(float underlyingSpotPrice) {
		// max (0, strike - spot) - premium
		return (Math.max(0.0f, strike - underlyingSpotPrice) - currentPremium) * numberOfLots * lotSize;
	}

	@Override
	public float getShortPayoffAtExpiryForTradedPremium(float underlyingSpotPrice) {
		// premium - max (0, strike - spot)
		return (tradedPremium - Math.max(0.0f, strike - underlyingSpotPrice)) * numberOfLots * lotSize;
	}

	@Override
	public float getLongPayoffAtExpiryForTradedPremium(float underlyingSpotPrice) {
		// max (0, strike - spot) - premium
		return (Math.max(0, strike - underlyingSpotPrice) - tradedPremium) * numberOfLots * lotSize;
	}

	@Override
	public PutOption clone() {
		return SerializationUtils.clone(this);
	}
}
