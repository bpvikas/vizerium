package com.vizerium.commons.indicators;

import java.util.Arrays;
import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public class AverageTrueRangeCalculator {

	private int smoothingPeriod;

	private MovingAverageType smoothingMAType;

	private static final int DEFAULT_SMOOTHING_PERIOD = 14;
	private static final MovingAverageType DEFAULT_SMOOTHING_MA_TYPE = MovingAverageType.WELLESWILDER;
	private static final int TRUE_RANGE_CALCULATION_START = 1;

	public AverageTrueRangeCalculator() {
		this.smoothingPeriod = DEFAULT_SMOOTHING_PERIOD;
		this.smoothingMAType = DEFAULT_SMOOTHING_MA_TYPE;
	}

	public AverageTrueRangeCalculator(int smoothingPeriod, MovingAverageType smoothingMAType) {
		this.smoothingPeriod = smoothingPeriod;
		this.smoothingMAType = smoothingMAType;
	}

	public float[] calculateTrueRange(List<? extends UnitPrice> unitPrices) {
		float[] trueRange = new float[unitPrices.size()];
		// This starts from i=1 as trueRange[0] defaults to 0.0f
		for (int i = TRUE_RANGE_CALCULATION_START; i < unitPrices.size(); i++) {
			trueRange[i] = getTrueRange(unitPrices.get(i).getHigh(), unitPrices.get(i).getLow(), unitPrices.get(i - 1).getClose());
		}
		return trueRange;
	}

	public float[] calculateAverageTrueRange(List<? extends UnitPrice> unitPrices) {
		int size = unitPrices.size();
		if (size < smoothingPeriod) {
			return new float[size];
		} else {
			float[] trueRangeArr = calculateTrueRange(unitPrices);
			float[] calculableTrueRangeArr = Arrays.copyOfRange(trueRangeArr, TRUE_RANGE_CALCULATION_START, size);
			float[] smoothAtrArr = MovingAverageCalculator.calculateArrayMA(smoothingMAType, calculableTrueRangeArr, smoothingPeriod);
			float[] smoothAtrArrShifted = new float[size];
			System.arraycopy(smoothAtrArr, 0, smoothAtrArrShifted, TRUE_RANGE_CALCULATION_START, smoothAtrArr.length);
			return smoothAtrArrShifted;
		}
	}

	private float getTrueRange(float todayHigh, float todayLow, float yesterdayClose) {
		float th_tl = Math.abs(todayHigh - todayLow);
		float th_yc = Math.abs(todayHigh - yesterdayClose);
		float tl_yc = Math.abs(todayLow - yesterdayClose);

		return th_tl > th_yc ? (th_tl > tl_yc ? th_tl : tl_yc) : (th_yc > tl_yc ? th_yc : tl_yc);
	}
}
