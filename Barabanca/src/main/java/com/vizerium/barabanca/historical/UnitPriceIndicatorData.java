package com.vizerium.barabanca.historical;

import java.util.List;

import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.Indicator;

public class UnitPriceIndicatorData {

	private LookbackPeriodCalculator lookbackPeriodCalculator;

	public void updateIndicatorDataInUnitPriceDataList(List<UnitPriceData> unitPriceDataList, Indicator indicator) {

		if (!unitPriceDataList.isEmpty()) {
			List<UnitPriceData> unitPriceDataListWithLookbackPeriod = lookbackPeriodCalculator.getUnitPricesIncludingLookbackPeriodWithTimeFormat(unitPriceDataList.get(0)
					.getScripName(), unitPriceDataList.get(0).getTimeFormat(), unitPriceDataList, indicator);
			
			
			
			
		}
	}
}
