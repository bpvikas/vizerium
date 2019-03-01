package com.vizerium.barabanca.historical;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;

import com.vizerium.barabanca.dao.UnitPriceData;

public class TrendIdentificationTest {

	@Test
	public void testBankNiftyTrendFor2018DecInHourlyBy13EMASlope() {

		HistoricalDataReader unit = new HistoricalDataReader();
		List<UnitPriceData> unitPriceDataList = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2018, 12, 1, 0, 0), LocalDateTime.of(2018, 12, 31, 23, 59),
				TimeFormat._1HOUR);

		for (int i = 3; i < unitPriceDataList.size(); i++) {
			if (unitPriceDataList.get(i - 3).getMovingAverage(13) < unitPriceDataList.get(i - 2).getMovingAverage(13)
					&& unitPriceDataList.get(i - 2).getMovingAverage(13) < unitPriceDataList.get(i - 1).getMovingAverage(13)) {
				System.out.println(unitPriceDataList.get(i).getDateTime().toString() + " : Prior trend is UP.");
			} else if (unitPriceDataList.get(i - 3).getMovingAverage(13) > unitPriceDataList.get(i - 2).getMovingAverage(13)
					&& unitPriceDataList.get(i - 2).getMovingAverage(13) > unitPriceDataList.get(i - 1).getMovingAverage(13)) {
				System.out.println(unitPriceDataList.get(i).getDateTime().toString() + " : Prior trend is DOWN.");
			} else {
				System.out.println(unitPriceDataList.get(i).getDateTime().toString() + " : Prior trend is CHOPPY.");
			}
		}
	}

	@Test
	public void testBankNiftyTrendFor2018DecInHourlyByMACDHistogramSlope() {

		HistoricalDataReader unit = new HistoricalDataReader();
		List<UnitPriceData> unitPriceDataList = unit.getUnitPriceDataForRange("BANKNIFTY", LocalDateTime.of(2018, 12, 1, 0, 0), LocalDateTime.of(2018, 12, 31, 23, 59),
				TimeFormat._1HOUR);

		for (int i = 3; i < unitPriceDataList.size(); i++) {
			if (unitPriceDataList.get(i - 3).getMACDHistogram(13, 26) < unitPriceDataList.get(i - 2).getMACDHistogram(13, 26)
					&& unitPriceDataList.get(i - 2).getMACDHistogram(13, 26) < unitPriceDataList.get(i - 1).getMACDHistogram(13, 26)) {
				System.out.println(unitPriceDataList.get(i).getDateTime().toString() + " : Prior trend is UP.");
			} else if (unitPriceDataList.get(i - 3).getMACDHistogram(13, 26) > unitPriceDataList.get(i - 2).getMACDHistogram(13, 26)
					&& unitPriceDataList.get(i - 2).getMACDHistogram(13, 26) > unitPriceDataList.get(i - 1).getMACDHistogram(13, 26)) {
				System.out.println(unitPriceDataList.get(i).getDateTime().toString() + " : Prior trend is DOWN.");
			} else {
				System.out.println(unitPriceDataList.get(i).getDateTime().toString() + " : Prior trend is CHOPPY.");
			}
		}
	}

}
