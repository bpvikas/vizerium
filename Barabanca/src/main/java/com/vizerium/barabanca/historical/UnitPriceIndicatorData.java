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

package com.vizerium.barabanca.historical;

import java.util.List;

import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.Indicator;

public class UnitPriceIndicatorData<I extends Indicator<I>> {

	public List<UnitPriceData> updateIndicatorDataInUnitPriceDataList(List<UnitPriceData> unitPriceDataList, I indicator) {

		LookbackPeriodCalculator<I> lookbackPeriodCalculator = new LookbackPeriodCalculator<I>();
		if (!unitPriceDataList.isEmpty()) {
			List<UnitPriceData> unitPriceDataListWithLookbackPeriod = lookbackPeriodCalculator
					.getUnitPricesIncludingLookbackPeriodWithTimeFormat(unitPriceDataList.get(0).getTimeFormat(), unitPriceDataList, indicator);

			indicator = indicator.calculate(unitPriceDataListWithLookbackPeriod);

			int lookbackPeriod = 0;
			for (UnitPriceData unitPriceDataInLookbackPeriod : unitPriceDataListWithLookbackPeriod) {
				if (unitPriceDataInLookbackPeriod.equals(unitPriceDataList.get(0))) {
					break;
				} else {
					++lookbackPeriod;
				}
			}

			for (int i = 0; i < unitPriceDataList.size(); i++) {
				unitPriceDataList.get(i).addIndicator(indicator.getName(), indicator.getUnitPriceIndicator(lookbackPeriod + i));
			}
		}
		return unitPriceDataList;
	}
}
