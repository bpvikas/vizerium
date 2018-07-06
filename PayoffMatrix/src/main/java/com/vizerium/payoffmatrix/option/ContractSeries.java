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

public enum ContractSeries {
	NEAR, MID, FAR;

	public static ContractSeries getByProperty(String property) {
		if ("near".equalsIgnoreCase(property.trim())) {
			return NEAR;
		} else if ("mid".equalsIgnoreCase(property.trim())) {
			return MID;
		} else if ("far".equalsIgnoreCase(property.trim())) {
			return FAR;
		} else {
			throw new RuntimeException("Unable to determine contract series." + property);
		}
	}
}
