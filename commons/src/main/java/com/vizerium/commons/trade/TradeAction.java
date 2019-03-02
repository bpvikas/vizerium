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

package com.vizerium.commons.trade;

public enum TradeAction {

	LONG, SHORT;

	public static TradeAction getByProperty(String property) {
		if ("long".equalsIgnoreCase(property.trim()) || "buy".equalsIgnoreCase(property.trim())) {
			return LONG;
		} else if ("short".equalsIgnoreCase(property.trim()) || "sell".equalsIgnoreCase(property.trim())) {
			return SHORT;
		} else {
			throw new RuntimeException("Unable to determine trade action." + property);
		}
	}
}
