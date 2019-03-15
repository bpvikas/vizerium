package com.vizerium.commons.indicators;

import java.util.List;

import com.vizerium.commons.dao.UnitPriceData;

public class MACDCalculator {

	public MACD calculate(List<UnitPriceData> unitPrices) {
		return calculate(unitPrices, new MACD());
	}

	public MACD calculate(List<UnitPriceData> unitPrices, MACD macd) {

		int size = unitPrices.size();
		int smooth = macd.getSmoothingPeriod();

		if (size < smooth) {
			return calculateMACDValue(unitPrices.get(unitPrices.size() - 1), macd);
		} else if (size >= smooth) {
			float[] macdValues = new float[size - smooth + 1];
			for (int i = smooth - 1; i < size; i++) {
				macdValues[i - (smooth - 1)] = calculateMACDValue(unitPrices.get(i), macd).getValue();
			}
			float signal = MovingAverageCalculator.calculateMA(macd.getSmoothingMAType(), macdValues, smooth);
			macd.setSignalValue(signal);

		} else {
			throw new RuntimeException("Error while calculating MACD for unitPrices size : " + size + " and " + macd);
		}
		return macd;
	}

	private MACD calculateMACDValue(UnitPriceData unitPriceData, MACD macd) {
		for (MovingAverageAndValue ma : unitPriceData.getMovingAverages()) {
			if (ma.getMA() == macd.getFastMA().getMA()) {
				macd.setFastMA(ma);
			}
			if (ma.getMA() == macd.getSlowMA().getMA()) {
				macd.setSlowMA(ma);
			}
		}
		if (macd.getFastMA().getValue() <= 0.0f || macd.getSlowMA().getValue() <= 0.0f) {
			throw new RuntimeException("Error while calculating MACD fast [" + macd.getFastMA().getValue() + ", slow " + macd.getSlowMA().getValue() + "]");
		}
		return macd;
	}
}
