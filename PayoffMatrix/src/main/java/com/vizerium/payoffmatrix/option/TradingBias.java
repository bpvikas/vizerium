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

public enum TradingBias {

	MINLOSS, MAXPROFIT;

	public static TradingBias getByProperty(String property) {
		if ("minLoss".equalsIgnoreCase(property)) {
			return MINLOSS;
		} else if ("maxProfit".equalsIgnoreCase(property)) {
			return MAXPROFIT;
		} else {
			return MINLOSS;
		}
	}
}