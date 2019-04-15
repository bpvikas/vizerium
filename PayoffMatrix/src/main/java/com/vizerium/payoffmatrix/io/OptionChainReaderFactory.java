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

package com.vizerium.payoffmatrix.io;

import com.vizerium.payoffmatrix.dao.RemoteDataSource;

public class OptionChainReaderFactory {

	private static OptionChainReader optionChainReader;

	private OptionChainReaderFactory() {

	}

	public static OptionChainReader getOptionChainReader(RemoteDataSource property) {
		if (optionChainReader == null) {
			if (RemoteDataSource.WEB.equals(property)) {
				optionChainReader = new HttpOptionChainReader();
			} else if (RemoteDataSource.LOCALHTML.equals(property)) {
				optionChainReader = new LocalHtmlOptionChainReader();
			} else {
				throw new RuntimeException("Unable to identify Remote data source to lookup option chain. " + property);
			}
		}
		return optionChainReader;
	}
}
