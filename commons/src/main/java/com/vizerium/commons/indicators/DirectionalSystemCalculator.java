package com.vizerium.commons.indicators;

import java.util.Arrays;
import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public class DirectionalSystemCalculator implements IndicatorCalculator<DirectionalSystem> {

	static final int DIRECTIONAL_MOVEMENT_CALCULATION_START = 1;

	public DirectionalSystem calculate(List<? extends UnitPrice> unitPrices) {
		return calculate(unitPrices, new DirectionalSystem());
	}

	@Override
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
		return getDM(unitPrices, true);
	}

	private float[] getMinusDM(List<? extends UnitPrice> unitPrices) {
		return getDM(unitPrices, false);
	}

	private float[] getDM(List<? extends UnitPrice> unitPrices, boolean high) {
		float[] dm = new float[unitPrices.size()];
		// This starts from i=1 as minusDM[0] defaults to 0.0f
		for (int i = DIRECTIONAL_MOVEMENT_CALCULATION_START; i < unitPrices.size(); i++) {
			float highDiff = unitPrices.get(i).getHigh() - unitPrices.get(i - 1).getHigh();
			float lowDiff = unitPrices.get(i - 1).getLow() - unitPrices.get(i).getLow();
			float a, b;
			if (high) {
				a = highDiff;
				b = lowDiff;
			} else {
				a = lowDiff;
				b = highDiff;
			}
			if (a > b && a > 0) {
				dm[i] = a;
			} else {
				dm[i] = 0.0f;
			}
		}
		return dm;
	}

	private float[] getAverageTrueRange(List<? extends UnitPrice> unitPrices) {
		AverageTrueRangeCalculator atrCalculator = new AverageTrueRangeCalculator();
		return atrCalculator.calculate(unitPrices).getValues();
	}

	private float[] getSmoothedPlusDM(float[] plusDM, DirectionalSystem ds) {
		return getSmoothedDM(plusDM, ds);
	}

	private float[] getSmoothedMinusDM(float[] minusDM, DirectionalSystem ds) {
		return getSmoothedDM(minusDM, ds);
	}

	private float[] getSmoothedDM(float[] dm, DirectionalSystem ds) {
		float[] calculableDM = Arrays.copyOfRange(dm, DIRECTIONAL_MOVEMENT_CALCULATION_START, dm.length);
		float[] smoothedDM = MovingAverageCalculator.calculateArrayMA(ds.getMovingAverageType(), calculableDM, ds.getSmoothingPeriod());

		float[] smoothedDMShifted = new float[dm.length];
		System.arraycopy(smoothedDM, 0, smoothedDMShifted, DIRECTIONAL_MOVEMENT_CALCULATION_START, smoothedDM.length);
		return smoothedDMShifted;
	}

	private float[] getSmoothedPlusDI(float[] smoothedPlusDM, float[] atr) {
		return getSmoothedDI(smoothedPlusDM, atr);
	}

	private float[] getSmoothedMinusDI(float[] smoothedMinusDM, float[] atr) {
		return getSmoothedDI(smoothedMinusDM, atr);
	}

	private float[] getSmoothedDI(float[] smoothedDM, float[] atr) {
		float[] smoothedDI = new float[smoothedDM.length];
		for (int i = 0; i < smoothedDM.length; i++) {
			if (atr[i] == 0.0f) {
				smoothedDI[i] = 0.0f;
			} else {
				smoothedDI[i] = 100 * smoothedDM[i] / atr[i];
			}
		}
		return smoothedDI;
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
