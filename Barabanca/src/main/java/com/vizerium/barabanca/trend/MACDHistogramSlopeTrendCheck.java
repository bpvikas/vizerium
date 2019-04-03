package com.vizerium.barabanca.trend;

import java.util.List;

import com.vizerium.barabanca.historical.LookbackPeriodCalculator;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.MACD;

public class MACDHistogramSlopeTrendCheck implements IndicatorTrendCheck<MACD> {

	private MACD macd;

	public MACDHistogramSlopeTrendCheck(MACD macd) {
		this.macd = macd;
	}

	@Override
	public PeriodTrends getTrend(TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataListCurrentTimeFormat) {

		List<UnitPriceData> expandedUnitPriceDataListTrendTimeFormat = getLookbackPeriodCalculator().getUnitPricesIncludingLookbackPeriodWithTimeFormat(trendTimeFormat,
				unitPriceDataListCurrentTimeFormat, macd);

		macd = macd.calculate(expandedUnitPriceDataListTrendTimeFormat);

		PeriodTrends periodTrends = new PeriodTrends();
		for (int i = 1; i < expandedUnitPriceDataListTrendTimeFormat.size(); i++) {
			if (macd.getHistogramValues()[i] > macd.getHistogramValues()[i - 1]) {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataListTrendTimeFormat.get(i).getDateTime(), trendTimeFormat, Trend.UP));
			} else if (macd.getHistogramValues()[i] < macd.getHistogramValues()[i - 1]) {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataListTrendTimeFormat.get(i).getDateTime(), trendTimeFormat, Trend.DOWN));
			} else {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataListTrendTimeFormat.get(i).getDateTime(), trendTimeFormat, Trend.CHOPPY));
			}
		}
		return periodTrends;
	}

	@Override
	public MACD getIndicator() {
		return macd;
	}

	@Override
	public LookbackPeriodCalculator<MACD> getLookbackPeriodCalculator() {
		return new LookbackPeriodCalculator<MACD>();
	}
}