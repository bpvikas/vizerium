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
