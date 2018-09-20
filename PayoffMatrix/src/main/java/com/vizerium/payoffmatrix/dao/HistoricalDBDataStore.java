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

package com.vizerium.payoffmatrix.dao;

import java.time.LocalDate;

import com.vizerium.payoffmatrix.volatility.DateRange;

public class HistoricalDBDataStore implements HistoricalDataStore {

	private String underlyingName;

	public HistoricalDBDataStore() {

	}

	public HistoricalDBDataStore(String underlyingName) {
		this.underlyingName = underlyingName;
	}

	@Override
	public float[] readHistoricalData(DateRange dateRange) {
		throw new UnsupportedOperationException("Historical data cannot be read from the DB as yet.");
	}

	@Override
	public void writeHistoricalData(LocalDate date, String open, String high, String low, String close, String volume) {
		throw new UnsupportedOperationException("Historical data cannot be updated into the DB as yet.");
	}

	@Override
	public String getUnderlyingName() {
		return underlyingName;
	}
}
