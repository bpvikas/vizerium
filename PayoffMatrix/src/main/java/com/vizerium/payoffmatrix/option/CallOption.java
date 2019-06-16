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

import org.apache.commons.lang3.SerializationUtils;

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

	public CallOption(String strike, String openInterest, String currentPremium, float impliedVolatility, float underlyingPrice, LocalDate currentPremiumDate, int lotSize) {
		this.strike = Float.parseFloat(strike);
		this.openInterest = Integer.parseInt(openInterest);
		this.currentPremium = Float.parseFloat(currentPremium);
		setImpliedVolatility(impliedVolatility);
		this.underlyingPrice = underlyingPrice;
		this.currentPremiumDate = currentPremiumDate;
		this.lotSize = lotSize;
		this.type = OptionType.CALL;
	}

	@Override
	public float getPayoffAtExpiry(float underlyingSpotPrice) {
		float premium = isExisting() ? tradedPremium : currentPremium;
		return (Math.max(0.0f, underlyingSpotPrice - strike) - premium) * numberOfLots * lotSize * (tradeAction.equals(TradeAction.LONG) ? 1 : -1);
	}

	@Override
	public float getBreakevenAtExpiry() {
		return strike + (isExisting() ? tradedPremium : currentPremium);
	}

	@Override
	public CallOption clone() {
		return SerializationUtils.clone(this);
	}
}
