package com.vizerium.commons.indicators;

import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public class AverageTrueRangeCalculator {

	private static final int DEFAULT_SMOOTHING_PERIOD = 14;

	private static final MovingAverageType DEFAULT_SMOOTHING_MA_TYPE = MovingAverageType.EXPONENTIAL;

	public static float calculate(List<? extends UnitPrice> unitPrices) {
		return calculate(unitPrices, DEFAULT_SMOOTHING_PERIOD, DEFAULT_SMOOTHING_MA_TYPE)[unitPrices.size() - 1];
	}

	public static float[] calculateArray(List<? extends UnitPrice> unitPrices) {
		return calculate(unitPrices, DEFAULT_SMOOTHING_PERIOD, DEFAULT_SMOOTHING_MA_TYPE);
	}

	public static float[] calculate(List<? extends UnitPrice> unitPrices, int smoothingPeriod, MovingAverageType smoothingMAType) {
		int size = unitPrices.size();
		if (size < smoothingPeriod) {
			return new float[size];
		} else {
			float[] trueRangeArr = new float[size - 1];
			for (int i = 1; i < size; i++) {
				trueRangeArr[i - 1] = getTrueRange(unitPrices.get(i).getHigh(), unitPrices.get(i).getLow(), unitPrices.get(i - 1).getClose());
			}
			return MovingAverageCalculator.calculateArrayMA(smoothingMAType, trueRangeArr, smoothingPeriod);
		}
	}

	private static float getTrueRange(float todayHigh, float todayLow, float yesterdayClose) {
		float th_tl = Math.abs(todayHigh - todayLow);
		float th_yc = Math.abs(todayHigh - yesterdayClose);
		float tl_yc = Math.abs(todayLow - yesterdayClose);

		return th_tl > th_yc ? (th_tl > tl_yc ? th_tl : tl_yc) : (th_yc > tl_yc ? th_yc : tl_yc);
	}
}
