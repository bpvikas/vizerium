package com.vizerium.barabanca.trend;

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
	public PeriodTrends getTrend(TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataList) {

		List<UnitPriceData> expandedUnitPriceDataList = getLookbackPeriodCalculator().getUnitPricesIncludingLookbackPeriodWithTimeFormat(trendTimeFormat, unitPriceDataList, ds);

		ds = ds.calculate(expandedUnitPriceDataList);
		PeriodTrends periodTrends = new PeriodTrends();

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
