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

import com.vizerium.payoffmatrix.criteria.Criteria;
import com.vizerium.payoffmatrix.option.Option;

public class OptionDBDataStore implements OptionDataStore {

	@Override
	public Option[] readOptionChainData(Criteria criteria) {
		throw new UnsupportedOperationException("Option Chain data cannot be read from the DB as yet.");
	}

	@Override
	public void saveOptionChainData(Criteria criteria, Option[] optionChain) {
		throw new UnsupportedOperationException("Option Chain data cannot be written to the DB as yet.");
	}
}
