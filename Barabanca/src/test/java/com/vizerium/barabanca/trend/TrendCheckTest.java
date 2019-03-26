package com.vizerium.barabanca.trend;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.vizerium.barabanca.historical.HistoricalDataReader;
import com.vizerium.barabanca.historical.TimeFormat;

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
		List<PeriodTrend> periodTrends = unit.getTrendByMACDHistogramSlope("BANKNIFTY", LocalDateTime.of(2018, 12, 1, 0, 0), LocalDateTime.of(2018, 12, 31, 23, 59),
				TimeFormat._1HOUR);
		for (PeriodTrend periodTrend : periodTrends) {
			logger.info(periodTrend);
		}
	}

	@Test
	public void testBankNiftyTrendFor2018DecInHourlyByDirectionalSystemAndADX() {
		List<PeriodTrend> periodTrends = unit.getTrendByDirectionalSystemAndADX("BANKNIFTY", LocalDateTime.of(2018, 12, 1, 0, 0), LocalDateTime.of(2018, 12, 31, 23, 59),
				TimeFormat._1HOUR);
		for (PeriodTrend periodTrend : periodTrends) {
			logger.info(periodTrend);
		}
	}
}