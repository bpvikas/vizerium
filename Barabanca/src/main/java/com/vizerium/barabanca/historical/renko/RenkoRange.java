package com.vizerium.barabanca.historical.renko;

import java.util.ArrayList;
import java.util.OptionalDouble;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class RenkoRange extends ArrayList<Renko> {

	private static final long serialVersionUID = -270282056461384924L;

	private static final Logger logger = Logger.getLogger(RenkoRange.class);

	private float start;

	private float end;

	public Renko last() {
		return get(size() - 1);
	}

	public boolean isLastRenkoClosed() {
		return (size() > 0) ? ((last().getEndDateTime() != null) ? true : false) : true;
	}

	public float getStart() {
		return start;
	}

	public void setStart(float start) {
		this.start = start;
	}

	public float getEnd() {
		return end;
	}

	public void setEnd(float end) {
		this.end = end;
	}

	public float getPreviousHigh() {
		OptionalDouble max = this.stream().mapToDouble(Renko::getHigherPrice).max();
		if (max != null && max.getAsDouble() == get(size() - 1).getHigherPrice()) {
			return get(size() - 1).getHigherPrice() + get(size() - 1).getBrickSize();
		}

		for (int i = size() - 1; i > 1; i--) {
			Renko lastRenko = get(i);
			Renko lastButOneRenko = get(i - 1);
			Renko lastButTwoRenko = get(i - 2);

			if (lastRenko.getHigherPrice() < lastButOneRenko.getHigherPrice() && lastButOneRenko.getHigherPrice() > lastButTwoRenko.getHigherPrice()) {
				logger.debug("Previous High Renko : " + lastButOneRenko);
				return lastButOneRenko.getHigherPrice() + lastButOneRenko.getBrickSize();
			}
		}
		throw new RuntimeException("Could not determine previous high from RenkoRange.");
	}

	public float getPreviousLow() {
		OptionalDouble min = this.stream().mapToDouble(Renko::getLowerPrice).min();
		if (min != null && min.getAsDouble() == get(size() - 1).getLowerPrice()) {
			return get(size() - 1).getLowerPrice() - get(size() - 1).getBrickSize();
		}

		for (int i = size() - 1; i > 1; i--) {
			Renko lastRenko = get(i);
			Renko lastButOneRenko = get(i - 1);
			Renko lastButTwoRenko = get(i - 2);

			if (lastRenko.getLowerPrice() > lastButOneRenko.getLowerPrice() && lastButOneRenko.getLowerPrice() < lastButTwoRenko.getLowerPrice()) {
				logger.debug("Previous Low Renko : " + lastButOneRenko);
				return lastButOneRenko.getLowerPrice() - lastButOneRenko.getBrickSize();
			}
		}
		throw new RuntimeException("Could not determine previous low from RenkoRange.");
	}

	public TreeMap<Renko, Float> getAllReversals() {
		TreeMap<Renko, Float> reversalsMap = new TreeMap<Renko, Float>();

		// get all top and bottom reversals
		for (int i = 0; i < size() - 2; i++) {
			Renko firstRenko = get(i);
			Renko secondRenko = get(i + 1);
			Renko thirdRenko = get(i + 2);

			if (firstRenko.getHigherPrice() < secondRenko.getHigherPrice() && secondRenko.getHigherPrice() > thirdRenko.getHigherPrice()) {
				reversalsMap.put(secondRenko, secondRenko.getHigherPrice() + secondRenko.getBrickSize());
			}

			if (firstRenko.getLowerPrice() > secondRenko.getLowerPrice() && secondRenko.getLowerPrice() < thirdRenko.getLowerPrice()) {
				reversalsMap.put(secondRenko, secondRenko.getLowerPrice() - secondRenko.getBrickSize());
			}
		}
		return reversalsMap;
	}
}
