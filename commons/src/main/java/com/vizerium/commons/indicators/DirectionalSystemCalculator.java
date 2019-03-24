package com.vizerium.commons.indicators;

import java.util.Arrays;
import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public class DirectionalSystemCalculator {

	private static final int DIRECTIONAL_MOVEMENT_CALCULATION_START = 1;

	public DirectionalSystem calculate(List<? extends UnitPrice> unitPrices) {
		return calculate(unitPrices, new DirectionalSystem());
	}

	public DirectionalSystem calculate(List<? extends UnitPrice> unitPrices, DirectionalSystem ds) {

		float[] plusDM = getPlusDM(unitPrices);
		float[] minusDM = getMinusDM(unitPrices);
		float[] atr = getAverageTrueRange(unitPrices);
		float[] smoothedPlusDM = getSmoothedPlusDM(plusDM, ds);
		float[] smoothedMinusDM = getSmoothedMinusDM(minusDM, ds);
		float[] smoothedPlusDI = getSmoothedPlusDI(smoothedPlusDM, atr);
		ds.setSmoothedPlusDI(smoothedPlusDI);
		float[] smoothedMinusDI = getSmoothedMinusDI(smoothedMinusDM, atr);
		ds.setSmoothedMinusDI(smoothedMinusDI);
		float[] dx = getDx(smoothedPlusDI, smoothedMinusDI);
		float[] adx = getAdx(dx, ds);
		ds.setAdx(adx);

		return ds;
	}

	private float[] getPlusDM(List<? extends UnitPrice> unitPrices) {
		float[] plusDM = new float[unitPrices.size()];
		// This starts from i=1 as plusDM[0] defaults to 0.0f
		for (int i = DIRECTIONAL_MOVEMENT_CALCULATION_START; i < unitPrices.size(); i++) {
			float highDiff = unitPrices.get(i).getHigh() - unitPrices.get(i - 1).getHigh();
			float lowDiff = unitPrices.get(i - 1).getLow() - unitPrices.get(i).getLow();
			if (highDiff > lowDiff && highDiff > 0) {
				plusDM[i] = highDiff;
			} else {
				plusDM[i] = 0.0f;
			}
		}
		return plusDM;
	}

	private float[] getMinusDM(List<? extends UnitPrice> unitPrices) {
		float[] minusDM = new float[unitPrices.size()];
		// This starts from i=1 as minusDM[0] defaults to 0.0f
		for (int i = DIRECTIONAL_MOVEMENT_CALCULATION_START; i < unitPrices.size(); i++) {
			float highDiff = unitPrices.get(i).getHigh() - unitPrices.get(i - 1).getHigh();
			float lowDiff = unitPrices.get(i - 1).getLow() - unitPrices.get(i).getLow();
			if (lowDiff > highDiff && lowDiff > 0) {
				minusDM[i] = lowDiff;
			} else {
				minusDM[i] = 0.0f;
			}
		}
		return minusDM;
	}

	private float[] getAverageTrueRange(List<? extends UnitPrice> unitPrices) {
		AverageTrueRangeCalculator atrCalculator = new AverageTrueRangeCalculator();
		return atrCalculator.calculateAverageTrueRange(unitPrices);
	}

	private float[] getSmoothedPlusDM(float[] plusDM, DirectionalSystem ds) {
		float[] calculablePlusDM = Arrays.copyOfRange(plusDM, DIRECTIONAL_MOVEMENT_CALCULATION_START, plusDM.length);
		float[] smoothedPlusDM = MovingAverageCalculator.calculateArrayMA(ds.getMovingAverageType(), calculablePlusDM, ds.getSmoothingPeriod());

		float[] smoothedPlusDMShifted = new float[plusDM.length];
		System.arraycopy(smoothedPlusDM, 0, smoothedPlusDMShifted, DIRECTIONAL_MOVEMENT_CALCULATION_START, smoothedPlusDM.length);
		return smoothedPlusDMShifted;
	}

	private float[] getSmoothedMinusDM(float[] minusDM, DirectionalSystem ds) {
		float[] calculableMinusDM = Arrays.copyOfRange(minusDM, DIRECTIONAL_MOVEMENT_CALCULATION_START, minusDM.length);
		float[] smoothedMinusDM = MovingAverageCalculator.calculateArrayMA(ds.getMovingAverageType(), calculableMinusDM, ds.getSmoothingPeriod());

		float[] smoothedMinusDMShifted = new float[minusDM.length];
		System.arraycopy(smoothedMinusDM, 0, smoothedMinusDMShifted, DIRECTIONAL_MOVEMENT_CALCULATION_START, smoothedMinusDM.length);
		return smoothedMinusDMShifted;
	}

	private float[] getSmoothedPlusDI(float[] smoothedPlusDM, float[] atr) {
		float[] smoothedPlusDI = new float[smoothedPlusDM.length];
		for (int i = 0; i < smoothedPlusDM.length; i++) {
			if (atr[i] == 0.0f) {
				smoothedPlusDI[i] = 0.0f;
			} else {
				smoothedPlusDI[i] = 100 * smoothedPlusDM[i] / atr[i];
			}
		}
		return smoothedPlusDI;
	}

	private float[] getSmoothedMinusDI(float[] smoothedMinusDM, float[] atr) {
		float[] smoothedMinusDI = new float[smoothedMinusDM.length];
		for (int i = 0; i < smoothedMinusDM.length; i++) {
			if (atr[i] == 0.0f) {
				smoothedMinusDI[i] = 0.0f;
			} else {
				smoothedMinusDI[i] = 100 * smoothedMinusDM[i] / atr[i];
			}
		}
		return smoothedMinusDI;
	}

	private float[] getDx(float[] smoothedPlusDI, float[] smoothedMinusDI) {
		float[] dx = new float[smoothedPlusDI.length];
		for (int i = 0; i < smoothedPlusDI.length; i++) {
			if (smoothedPlusDI[i] == 0.0f && smoothedMinusDI[i] == 0.0f) {
				dx[i] = 0;
			} else {
				dx[i] = 100 * Math.abs(smoothedPlusDI[i] - smoothedMinusDI[i]) / (smoothedPlusDI[i] + smoothedMinusDI[i]);
			}
		}
		return dx;
	}

	private float[] getAdx(float[] dx, DirectionalSystem ds) {
		if (dx.length <= (DIRECTIONAL_MOVEMENT_CALCULATION_START + ds.getSmoothingPeriod() - 1 + ds.getSmoothingPeriod() - 1)) {
			return new float[dx.length];
		} else {
			float[] calculableDx = Arrays.copyOfRange(dx, DIRECTIONAL_MOVEMENT_CALCULATION_START + ds.getSmoothingPeriod() - 1, dx.length);
			float[] adx = MovingAverageCalculator.calculateArrayMA(ds.getMovingAverageType(), calculableDx, ds.getSmoothingPeriod());
			float[] adxShifted = new float[dx.length];
			System.arraycopy(adx, 0, adxShifted, DIRECTIONAL_MOVEMENT_CALCULATION_START + ds.getSmoothingPeriod() - 1, adx.length);
			return adxShifted;
		}
	}
}
