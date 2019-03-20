package com.vizerium.barabanca.historical.renko;

import java.util.ArrayList;
import java.util.List;

import com.vizerium.barabanca.historical.PriceRange;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.AverageTrueRangeCalculator;

public class RenkoCalculator {

	public Renko[] calculate(List<UnitPriceData> unitPriceDataList, Renko renko) {

		Renko[] renkoRange = getRenkoRange(unitPriceDataList, renko);
		return renkoRange;
	}

	private Renko[] getRenkoRange(List<UnitPriceData> unitPriceDataList, Renko renko) {

		if (renko.getBrickSize() <= 0) {
			renko.setBrickSize(AverageTrueRangeCalculator.calculate(unitPriceDataList));
			System.out.println("Auto calculated brick size " + renko.getScripName() + " " + renko.getTimeFormat() + " " + renko.getBrickSize());
		}

		PriceRange[] priceRanges = getPriceRanges(unitPriceDataList, renko.getBrickSize());

		RenkoRange renkoRange = new RenkoRange();
		renkoRange.setStart(priceRanges[0].getLow());
		renkoRange.setEnd(priceRanges[priceRanges.length - 1].getHigh());
		for (UnitPriceData unitPriceData : unitPriceDataList) {
			float close = unitPriceData.getClose();
			PriceRange priceRange = getPriceRange(priceRanges, close);
			if (renkoRange.isEmpty()) {
				Renko renkoToAdd = renko.clone();
				renko.setStartDate(unitPriceData.getDateTime());

				// This is under the assumption that the first trade is positive.
				renko.setStartPrice(priceRange.getLow());
				renko.setEndPrice(priceRange.getHigh());
				renkoRange.add(renkoToAdd);
			} else {
				Renko lastRenko = renkoRange.last();
				if (!lastRenko.isPriceWithinRange(close)) {

				}

			}

		}
		return renkoRange.toArray(new Renko[renkoRange.size()]);
	}

	private PriceRange getPriceRange(PriceRange[] priceRanges, float close) {
		for (PriceRange priceRange : priceRanges) {
			if (priceRange.isPriceWithinRange(close)) {
				return priceRange;
			}
		}
		throw new RuntimeException("Unable to recogize price range for " + close);
	}

	private PriceRange[] getPriceRanges(List<UnitPriceData> unitPriceDataList, float brickSize) {

		float highestClose = Float.MIN_VALUE;
		float lowestClose = Float.MAX_VALUE;

		for (UnitPriceData unitPriceData : unitPriceDataList) {
			float close = unitPriceData.getClose();
			if (close > highestClose) {
				highestClose = close;
			}
			if (close < lowestClose) {
				lowestClose = close;
			}
		}
		List<PriceRange> priceRanges = new ArrayList<PriceRange>();
		float currentPrice = lowestClose;
		while (currentPrice <= highestClose + brickSize) { // highestClose + brickSize added so that the highest close is also captured in a Renko brick.
			priceRanges.add(new PriceRange(currentPrice, currentPrice + brickSize));
			currentPrice = currentPrice + brickSize;
		}

		if (priceRanges.isEmpty()) {
			throw new RuntimeException("Unable to determine price Ranges for Renko calculations.");
		}

		return priceRanges.toArray(new PriceRange[priceRanges.size()]);
	}
}