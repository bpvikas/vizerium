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

package com.vizerium.payoffmatrix.dao;

public class HistoricalDataStoreFactory {

	private static HistoricalDataStore historicalDataStore;

	private HistoricalDataStoreFactory() {

	}

	public static HistoricalDataStore getHistoricalDataStore(LocalDataSource property, String underlyingName) {
		if (historicalDataStore == null) {
			if (LocalDataSource.CSV.equals(property)) {
				historicalDataStore = new HistoricalCsvDataStore(underlyingName);
			} else if (LocalDataSource.DB.equals(property)) {
				historicalDataStore = new HistoricalDBDataStore(underlyingName);
			} else {
				throw new RuntimeException("Unable to identify local data source to lookup historical data. " + property);
			}
		}
		return historicalDataStore;
	}
}
