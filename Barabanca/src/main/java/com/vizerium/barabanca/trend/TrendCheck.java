package com.vizerium.barabanca.trend;

import java.util.ArrayList;
import java.util.List;

import com.vizerium.barabanca.historical.LookbackPeriodCalculator;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.DirectionalSystem;
import com.vizerium.commons.indicators.DirectionalSystemCalculator;
import com.vizerium.commons.indicators.MACD;
import com.vizerium.commons.indicators.MACDCalculator;

public class TrendCheck {

	private LookbackPeriodCalculator lookbackPeriodCalculator;

	public TrendCheck() {
	}

	public List<PeriodTrend> getTrendByEMASlope(String scripName, TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataListCurrentTimeFormat, int ma) {

		int lookbackPeriodForEMASlopeCalculations = lookbackPeriodCalculator.getLookbackPeriodForEMASlopeCalculations();
		List<UnitPriceData> expandedUnitPriceDataList = lookbackPeriodCalculator.getUnitPricesIncludingLookbackPeriodWithTimeFormat(scripName, trendTimeFormat,
				unitPriceDataListCurrentTimeFormat, lookbackPeriodForEMASlopeCalculations);
		List<PeriodTrend> periodTrends = new ArrayList<PeriodTrend>();

		for (int i = lookbackPeriodForEMASlopeCalculations - 1; i < expandedUnitPriceDataList.size(); i++) {
			if (expandedUnitPriceDataList.get(i - 2).getMovingAverage(ma) < expandedUnitPriceDataList.get(i - 1).getMovingAverage(ma)) {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataList.get(i).getDateTime(), trendTimeFormat, Trend.UP));
			} else if (expandedUnitPriceDataList.get(i - 2).getMovingAverage(ma) > expandedUnitPriceDataList.get(i - 1).getMovingAverage(ma)) {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataList.get(i).getDateTime(), trendTimeFormat, Trend.DOWN));
			} else {
				periodTrends.add(new PeriodTrend(expandedUnitPriceDataList.get(i).getDateTime(), trendTimeFormat, Trend.CHOPPY));
			}
		}
		return periodTrends;
	}

	public List<PeriodTrend> getTrendByMACDHistogramSlope(String scripName, TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataList, MACD macdInput) {

		int lookbackPeriodForMACDHistogramCalculations = lookbackPeriodCalculator.getLookbackPeriodForIndicator(macdInput);
		List<UnitPriceData> expandedUnitPriceDataListTrendTimeFormat = lookbackPeriodCalculator.getUnitPricesIncludingLookbackPeriodWithTimeFormat(scripName, trendTimeFormat,
				unitPriceDataList, lookbackPeriodForMACDHistogramCalculations);

		List<PeriodTrend> periodTrends = new ArrayList<PeriodTrend>();

		MACDCalculator macdCalculator = new MACDCalculator();
		MACD macd = macdCalculator.calculate(expandedUnitPriceDataListTrendTimeFormat, macdInput);
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

	public List<PeriodTrend> getTrendByDirectionalSystemAndADX(String scripName, TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataList, DirectionalSystem dsInput) {

		int lookbackPeriodForDirectionalSystemCalculations = lookbackPeriodCalculator.getLookbackPeriodForIndicator(dsInput);
		List<UnitPriceData> expandedUnitPriceDataList = lookbackPeriodCalculator.getUnitPricesIncludingLookbackPeriodWithTimeFormat(scripName, trendTimeFormat, unitPriceDataList,
				lookbackPeriodForDirectionalSystemCalculations);

		List<PeriodTrend> periodTrends = new ArrayList<PeriodTrend>();

		DirectionalSystemCalculator dsCalculator = new DirectionalSystemCalculator();
		DirectionalSystem ds = dsCalculator.calculate(expandedUnitPriceDataList, dsInput);

		// TODO: Need to read Alexander Elder's book to determine exact rules for trend being, up, down or choppy

		return periodTrends;
	}

}
