package com.vizerium.barabanca.trend;

import java.util.List;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;

public interface TrendCheck {

	public List<PeriodTrend> getTrend(String scripName, TimeFormat trendTimeFormat, List<UnitPriceData> unitPriceDataListCurrentTimeFormat);

}
