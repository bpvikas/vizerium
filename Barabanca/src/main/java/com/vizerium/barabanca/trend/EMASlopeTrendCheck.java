package com.vizerium.barabanca.trend;

import java.util.ArrayList;
import java.util.List;

import com.vizerium.barabanca.historical.LookbackPeriodCalculator;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MovingAverage;

public class EMASlopeTrendCheck implements IndicatorTrendCheck<MovingAverage> {

	private MovingAverage ma;

	public EMASlopeTrendCheck(MovingAverage ma) {
		this.ma = ma;
	}

	@Override
	public List<PeriodTrend> getTrend(String scripName, TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataListCurrentTimeFormat) {

		List<UnitPriceData> expandedUnitPriceDataList = getLookbackPeriodCalculator().getUnitPricesIncludingLookbackPeriodWithTimeFormat(scripName, trendTimeFormat,
				unitPriceDataListCurrentTimeFormat, ma);

		ma = ma.calculate(expandedUnitPriceDataList);

		List<PeriodTrend> periodTrends = new ArrayList<PeriodTrend>();
		for (int i = ma.getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues(); i < expandedUnitPriceDataList.size(); i++) {
			if (ma.getValues()[i - 2] < ma.getValues()[i - 1]) {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataList.get(i).getDateTime(), trendTimeFormat, Trend.UP));
			} else if (ma.getValues()[i - 2] > ma.getValues()[i - 1]) {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataList.get(i).getDateTime(), trendTimeFormat, Trend.DOWN));
			} else {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataList.get(i).getDateTime(), trendTimeFormat, Trend.CHOPPY));
			}
		}
		return periodTrends;
	}

	@Override
	public MovingAverage getIndicator() {
		return ma;
	}

	@Override
	public LookbackPeriodCalculator<MovingAverage> getLookbackPeriodCalculator() {
		return new LookbackPeriodCalculator<MovingAverage>();
	}
}
