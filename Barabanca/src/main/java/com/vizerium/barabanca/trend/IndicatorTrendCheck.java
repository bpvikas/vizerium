package com.vizerium.barabanca.trend;

import com.vizerium.barabanca.historical.LookbackPeriodCalculator;
import com.vizerium.commons.indicators.Indicator;

public interface IndicatorTrendCheck<I extends Indicator<I>> extends TrendCheck {

	I getIndicator();

	LookbackPeriodCalculator<I> getLookbackPeriodCalculator();

}
