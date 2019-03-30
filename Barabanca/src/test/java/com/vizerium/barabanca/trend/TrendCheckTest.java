package com.vizerium.barabanca.trend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.vizerium.barabanca.historical.HistoricalDataReader;
import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.DirectionalSystem;
import com.vizerium.commons.indicators.MACD;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner.class)
public class TrendCheckTest {

	private static final Logger logger = Logger.getLogger(TrendCheckTest.class);

	@InjectMocks
	private TrendCheck unit;

	@Mock
	private HistoricalDataReader historicalDataReader;

	@Before
	public void setUp() {
		Mockito.when(
				historicalDataReader.getUnitPriceDataForRange(Mockito.isA(String.class), Mockito.isA(LocalDateTime.class), Mockito.isA(LocalDateTime.class),
						Mockito.isA(TimeFormat.class))).thenAnswer(new Answer<List<UnitPriceData>>() {
			@Override
			public List<UnitPriceData> answer(InvocationOnMock invocation) throws Throwable {
				Object[] arguments = invocation.getArguments();
				return getOHLCData((LocalDateTime) arguments[1], (LocalDateTime) arguments[2], (TimeFormat) arguments[3]);
			}
		});
	}

	@Test
	public void test01_BankNiftyTrendFor2018WeeklyBy13EMASlope() {
		List<UnitPriceData> unitPrices = getOHLCData(LocalDateTime.of(2018, 1, 1, 6, 0), LocalDateTime.of(2018, 12, 31, 21, 00), TimeFormat._1DAY);
		List<PeriodTrend> periodTrends = unit.getTrendByEMASlope("BANKNIFTY", TimeFormat._1WEEK, unitPrices, 13);
		for (PeriodTrend periodTrend : periodTrends) {
			logger.info(periodTrend);
		}
	}

	@Test
	public void test11_BankNiftyTrendFor2018WeeklyByMACDHistogramSlope() {
		List<UnitPriceData> unitPrices = getOHLCData(LocalDateTime.of(2018, 1, 1, 6, 0), LocalDateTime.of(2018, 12, 31, 21, 00), TimeFormat._1DAY);
		List<PeriodTrend> periodTrends = unit.getTrendByMACDHistogramSlope("BANKNIFTY", TimeFormat._1WEEK, unitPrices, new MACD());
		for (PeriodTrend periodTrend : periodTrends) {
			logger.info(periodTrend);
		}
	}

	@Test
	public void test21_BankNiftyTrendFor2018WeeklyByDirectionalSystemAndADX() {
		List<UnitPriceData> unitPrices = getOHLCData(LocalDateTime.of(2018, 1, 1, 6, 0), LocalDateTime.of(2018, 12, 31, 21, 00), TimeFormat._1DAY);
		List<PeriodTrend> periodTrends = unit.getTrendByDirectionalSystemAndADX("BANKNIFTY", TimeFormat._1WEEK, unitPrices, new DirectionalSystem());
		for (PeriodTrend periodTrend : periodTrends) {
			logger.info(periodTrend);
		}
	}

	private List<UnitPriceData> getOHLCData(LocalDateTime startDateTime, LocalDateTime endDateTime, TimeFormat timeFormat) {

		List<UnitPriceData> unitPrices = new ArrayList<UnitPriceData>();
		try {
			File testDataFile = new File("src/test/resources/testData_trendcheck_calculation_" + timeFormat.getProperty() + ".csv");

			BufferedReader br = new BufferedReader(new FileReader(testDataFile));
			String dataLine = br.readLine(); // This is to read off the header line.
			while ((dataLine = br.readLine()) != null) {
				String[] dataLineDetails = dataLine.split(",");
				UnitPriceData unitPriceData = new UnitPriceData(dataLineDetails);
				if (!unitPriceData.getDateTime().isBefore(startDateTime) && !unitPriceData.getDateTime().isAfter(endDateTime)) {
					unitPrices.add(unitPriceData);
				}
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Assert.fail(ioe.getMessage());
		}
		return unitPrices;
	}
}