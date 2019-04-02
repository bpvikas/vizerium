package com.vizerium.barabanca.historical;

import java.util.List;

import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.Indicator;

public class UnitPriceIndicatorData {

	public <I extends Indicator<I>> List<UnitPriceData> updateIndicatorDataInUnitPriceDataList(List<UnitPriceData> unitPriceDataList, I indicator) {

		LookbackPeriodCalculator<I> lookbackPeriodCalculator = new LookbackPeriodCalculator<I>();
		if (!unitPriceDataList.isEmpty()) {
			List<UnitPriceData> unitPriceDataListWithLookbackPeriod = lookbackPeriodCalculator.getUnitPricesIncludingLookbackPeriodWithTimeFormat(
					unitPriceDataList.get(0).getScripName(), unitPriceDataList.get(0).getTimeFormat(), unitPriceDataList, indicator);

			indicator = indicator.calculate(unitPriceDataListWithLookbackPeriod);

			int lookbackPeriod = indicator.getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues();
			for (int i = lookbackPeriod; i < unitPriceDataListWithLookbackPeriod.size() - 1; i++) {
				unitPriceDataList.get(i - lookbackPeriod).addIndicator(indicator.getName(), indicator.getUnitPriceIndicator(i));
			}
		}
		return unitPriceDataList;
	}
}
