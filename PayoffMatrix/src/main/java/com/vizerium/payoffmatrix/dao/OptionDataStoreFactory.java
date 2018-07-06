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

public class OptionDataStoreFactory {

	private static OptionDataStore optionDataStore;

	private OptionDataStoreFactory() {

	}

	public static OptionDataStore getOptionDataStore(LocalDataSource property) {
		if (optionDataStore == null) {
			if (LocalDataSource.CSV.equals(property)) {
				optionDataStore = new OptionCsvDataStore();
			} else if (LocalDataSource.DB.equals(property)) {
				optionDataStore = new OptionDBDataStore();
			} else {
				throw new RuntimeException("Unable to identify local data source to lookup option chain. " + property);
			}
		}
		return optionDataStore;
	}
}
