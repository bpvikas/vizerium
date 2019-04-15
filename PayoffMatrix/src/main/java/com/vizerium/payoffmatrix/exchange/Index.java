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

package com.vizerium.payoffmatrix.exchange;

import java.util.List;

public class Index {

	private String name;

	private List<String> stockNames;

	public Index() {
	}

	public Index(String name) {
		this.name = name;
	}

	public Index(String name, List<String> stockNames) {
		this.name = name;
		this.stockNames = stockNames;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getStockNames() {
		return stockNames;
	}

	public void setStockNames(List<String> stockNames) {
		this.stockNames = stockNames;
	}

}
