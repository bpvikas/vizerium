package com.vizerium.barabanca.historical;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.barabanca.trend.PeriodTrend;
import com.vizerium.barabanca.trend.Trend;
import com.vizerium.barabanca.trend.TrendCheck;
import com.vizerium.commons.dao.UnitPriceData;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrendCheckTest {

	private static final Logger logger = Logger.getLogger(TrendCheckTest.class);

	private TrendCheck unit;

	@Before
	public void setUp() {
		unit = new TrendCheck(new HistoricalDataReader());
	}

	@Test
	public void testBankNiftyTrendFor2018WeeklyBy13EMASlope() {
		List<PeriodTrend> periodTrends = unit.getTrendByEMASlope("BANKNIFTY", LocalDateTime.of(2018, 1, 1, 0, 0), LocalDateTime.of(2018, 12, 31, 23, 59), TimeFormat._1WEEK, 13);
		for (PeriodTrend periodTrend : periodTrends) {
			logger.info(periodTrend);
		}
	}

	@Test
	public void testBankNiftyTrendFor2018DecInHourlyByMACDHistogramSlope() {

		// TODO: This needs to be fixed.
		HistoricalDataReader unit = new HistoricalDataReader();
		List<UnitPriceData> unitPriceDataList = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2018, 12, 1, 0, 0), LocalDateTime.of(2018, 12, 31, 23, 59),
				TimeFormat._1HOUR);

		for (int i = 3; i < unitPriceDataList.size(); i++) {
			if (unitPriceDataList.get(i - 3).getMACD(13, 26) < unitPriceDataList.get(i - 2).getMACD(13, 26)
					&& unitPriceDataList.get(i - 2).getMACD(13, 26) < unitPriceDataList.get(i - 1).getMACD(13, 26)) {
				logger.info(unitPriceDataList.get(i).getDateTime().toString() + " : Prior trend is " + Trend.UP.name());
			} else if (unitPriceDataList.get(i - 3).getMACD(13, 26) > unitPriceDataList.get(i - 2).getMACD(13, 26)
					&& unitPriceDataList.get(i - 2).getMACD(13, 26) > unitPriceDataList.get(i - 1).getMACD(13, 26)) {
				logger.info(unitPriceDataList.get(i).getDateTime().toString() + " : Prior trend is " + Trend.DOWN.name());
			} else {
				logger.info(unitPriceDataList.get(i).getDateTime().toString() + " : Prior trend is " + Trend.CHOPPY.name());
			}
		}
	}
}