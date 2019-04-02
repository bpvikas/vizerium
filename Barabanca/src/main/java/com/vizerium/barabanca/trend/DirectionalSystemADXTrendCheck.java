package com.vizerium.barabanca.trend;

import java.util.ArrayList;
import java.util.List;

import com.vizerium.barabanca.historical.LookbackPeriodCalculator;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.DirectionalSystem;

public class DirectionalSystemADXTrendCheck implements IndicatorTrendCheck<DirectionalSystem> {

	private DirectionalSystem ds;

	public DirectionalSystemADXTrendCheck(DirectionalSystem ds) {
		this.ds = ds;
	}

	@Override
	public List<PeriodTrend> getTrend(String scripName, TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataList) {

		List<UnitPriceData> expandedUnitPriceDataList = getLookbackPeriodCalculator().getUnitPricesIncludingLookbackPeriodWithTimeFormat(scripName, trendTimeFormat,
				unitPriceDataList, ds);

		ds = ds.calculate(expandedUnitPriceDataList);
		List<PeriodTrend> periodTrends = new ArrayList<PeriodTrend>();

		// TODO: Need to read Alexander Elder's book to determine exact rules for trend being, up, down or choppy
		return periodTrends;
	}

	@Override
	public DirectionalSystem getIndicator() {
		return ds;
	}

	@Override
	public LookbackPeriodCalculator<DirectionalSystem> getLookbackPeriodCalculator() {
		return new LookbackPeriodCalculator<DirectionalSystem>();
	}
}
