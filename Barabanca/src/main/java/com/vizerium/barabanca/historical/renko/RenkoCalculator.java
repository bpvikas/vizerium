package com.vizerium.barabanca.historical.renko;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vizerium.barabanca.historical.PriceRange;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.AverageTrueRangeCalculator;

public class RenkoCalculator {

	private static final Logger logger = Logger.getLogger(RenkoCalculator.class);

	public Renko[] calculate(List<UnitPriceData> unitPriceDataList, Renko renko) {

		Renko[] renkoRange = getRenkoRange(unitPriceDataList, renko);
		return renkoRange;
	}

	private Renko[] getRenkoRange(List<UnitPriceData> unitPriceDataList, Renko renko) {
		if (renko.getBrickSize() <= 0.0f) {
			renko.setBrickSize(AverageTrueRangeCalculator.calculate(unitPriceDataList));
			logger.info("Auto calculated Renko brick size " + renko.getScripName() + " " + renko.getTimeFormat() + " " + renko.getBrickSize());
		}
		PriceRange[] priceRanges = getPriceRanges(unitPriceDataList, renko.getBrickSize(), renko.isSmoothPriceRange());

		RenkoRange renkoRange = new RenkoRange();
		renkoRange.setStart(priceRanges[0].getLow());
		renkoRange.setEnd(priceRanges[priceRanges.length - 1].getHigh());
		for (UnitPriceData unitPriceData : unitPriceDataList) {
			float close = unitPriceData.getClose();
			logger.debug("Close is " + close + "\t" + unitPriceData.getDateTime());
			if (renkoRange.isEmpty()) {
				renkoRange.add(createNewRenko(renko, null, unitPriceData, priceRanges));
			} else {
				Renko lastRenko = renkoRange.last();
				if (renkoRange.size() >= 2) {
					// This is the case where there is a half created Renko (no endDateTime) but the prices do not continue in the same direction
					// so that we can close the Renko. It has reversed direction, and moved into one of the previous Renkos.
					// In that case, the last half created Renko has to be deleted, while the one prior to the last will be the only existing completed Renko.
					if (!lastRenko.isPriceWithinRange(close) && !renkoRange.isLastRenkoClosed()) {
						if ((lastRenko.isDown() && close > lastRenko.getStartPrice() && close > lastRenko.getEndPrice())
								|| (lastRenko.isUp() && close < lastRenko.getStartPrice() && close < lastRenko.getEndPrice())) {
							deleteLastOpenRenko(renkoRange);
							lastRenko = renkoRange.last(); // This is for the next calculation
						}
					}
				}
				if (!lastRenko.isPriceWithinRange(close)) {
					if (!renkoRange.isLastRenkoClosed()) {
						closeLastRenko(lastRenko, unitPriceData);
					}

					int noOfRenkosToBeAdded = getPriceRangeGapBetweenLastRenkoAndCurrentClose(lastRenko, close, priceRanges);
					logger.debug("Gap between last renko and current close : " + noOfRenkosToBeAdded);

					for (int i = 0; i < Math.abs(noOfRenkosToBeAdded); i++) {
						lastRenko = createNewRenko(renko, lastRenko, unitPriceData, priceRanges);
						renkoRange.add(lastRenko);
						if (Math.abs(noOfRenkosToBeAdded) > 1 && i < Math.abs(noOfRenkosToBeAdded) - 1) {
							closeLastRenko(lastRenko, unitPriceData);
						}
					}
				}
			}
		}
		logger.debug("RenkoRange Size : " + renkoRange.size());
		renkoRange.forEach(logger::debug);
		return renkoRange.toArray(new Renko[renkoRange.size()]);
	}

	private void deleteLastOpenRenko(RenkoRange renkoRange) {
		logger.debug("deleteLastOpenRenko called.");
		if (!renkoRange.isLastRenkoClosed()) {
			Renko deletedRenko = renkoRange.remove(renkoRange.size() - 1);
			logger.debug("Deleting last Renko : " + deletedRenko);
		}
	}

	private void swapStartAndEndPrices(Renko renko) {
		logger.debug("Swapping start and end prices : " + renko.getStartPrice() + "," + renko.getEndPrice());
		float start = renko.getStartPrice();
		renko.setStartPrice(renko.getEndPrice());
		renko.setEndPrice(start);
		logger.debug("Swapped start and end prices : " + renko.getStartPrice() + "," + renko.getEndPrice());
	}

	private void closeLastRenko(Renko lastRenko, UnitPriceData unitPriceData) {
		float close = unitPriceData.getClose();
		lastRenko.setEndDateTime(unitPriceData.getDateTime());
		if (close < lastRenko.getStartPrice() && close < lastRenko.getEndPrice()) {
			if (lastRenko.isUp()) {
				swapStartAndEndPrices(lastRenko);
			}
		}

		if (close > lastRenko.getStartPrice() && close > lastRenko.getEndPrice()) {
			if (lastRenko.isDown()) {
				swapStartAndEndPrices(lastRenko);
			}
		}
		logger.debug("Closed last Renko : " + lastRenko);
	}

	private Renko createNewRenko(Renko renko, Renko lastRenko, UnitPriceData unitPriceData, PriceRange[] priceRanges) {
		float close = unitPriceData.getClose();
		Renko renkoToAdd = renko.clone();
		renkoToAdd.setStartDateTime(unitPriceData.getDateTime());

		if (lastRenko != null) {
			int lastRenkoPriceRangeLocation = getLastRenkoPriceRangeLocation(lastRenko, priceRanges);

			if (close < lastRenko.getStartPrice() && close < lastRenko.getEndPrice()) {
				renkoToAdd.setStartPrice(priceRanges[lastRenkoPriceRangeLocation - 1].getHigh());
				renkoToAdd.setEndPrice(priceRanges[lastRenkoPriceRangeLocation - 1].getLow());
			}

			if (close > lastRenko.getStartPrice() && close > lastRenko.getEndPrice()) {
				renkoToAdd.setStartPrice(priceRanges[lastRenkoPriceRangeLocation + 1].getLow());
				renkoToAdd.setEndPrice(priceRanges[lastRenkoPriceRangeLocation + 1].getHigh());
			}
		} else { // This is the first Renko being added.
			// This is under the assumption that the first Renko is a green Renko.
			// This gets auto-updated while closing the Renko brick.
			int currentClosePriceRangeLocation = getCurrentClosePriceRangeLocation(close, priceRanges);

			renkoToAdd.setStartPrice(priceRanges[currentClosePriceRangeLocation].getLow());
			renkoToAdd.setEndPrice(priceRanges[currentClosePriceRangeLocation].getHigh());
		}
		logger.debug("Creating new Renko: " + renkoToAdd);
		return renkoToAdd;
	}

	private int getPriceRangeGapBetweenLastRenkoAndCurrentClose(Renko lastRenko, float close, PriceRange[] priceRanges) {
		return getCurrentClosePriceRangeLocation(close, priceRanges) - getLastRenkoPriceRangeLocation(lastRenko, priceRanges);
	}

	private int getLastRenkoPriceRangeLocation(Renko lastRenko, PriceRange[] priceRanges) {
		int lastRenkoPriceRangeLocation = -1;
		float previousStart = lastRenko.getStartPrice();
		float previousEnd = lastRenko.getEndPrice();

		for (int i = 0; i < priceRanges.length; i++) {
			float prHigh = priceRanges[i].getHigh();
			float prLow = priceRanges[i].getLow();
			if ((prHigh == previousStart || prLow == previousStart) && (prHigh == previousEnd || prLow == previousEnd)) {
				lastRenkoPriceRangeLocation = i;
				break;
			}
		}
		if (lastRenkoPriceRangeLocation < 0) {
			throw new RuntimeException("Unable to get priceRange for the last Renko " + lastRenko);
		}
		return lastRenkoPriceRangeLocation;
	}

	private int getCurrentClosePriceRangeLocation(float close, PriceRange[] priceRanges) {
		int currentClosePriceRangeLocation = -1;
		for (int i = 0; i < priceRanges.length; i++) {
			if (priceRanges[i].isPriceWithinRange(close)) {
				currentClosePriceRangeLocation = i;
				break;
			}
		}
		if (currentClosePriceRangeLocation < 0) {
			throw new RuntimeException("Unable to get priceRange for the current close " + close);
		}
		return currentClosePriceRangeLocation;
	}

	private PriceRange[] getPriceRanges(List<UnitPriceData> unitPriceDataList, float brickSize, boolean smoothPriceRange) {

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

		if (smoothPriceRange) {
			highestClose = ((int) (highestClose / brickSize)) * brickSize + brickSize;
			lowestClose = ((int) (lowestClose / brickSize)) * brickSize;
		}

		List<PriceRange> priceRanges = new ArrayList<PriceRange>();
		float currentPrice = lowestClose;
		while (currentPrice <= highestClose + brickSize) { // highestClose + brickSize added so that the highest close is also captured in a Renko brick.
			priceRanges.add(new PriceRange(currentPrice, currentPrice + brickSize));
			currentPrice = currentPrice + brickSize;
		}

		if (priceRanges.isEmpty()) {
			throw new RuntimeException("Unable to determine price ranges for Renko calculations.");
		}

		return priceRanges.toArray(new PriceRange[priceRanges.size()]);
	}
}