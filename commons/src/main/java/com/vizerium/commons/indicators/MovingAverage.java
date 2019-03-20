package com.vizerium.commons.indicators;

import java.util.Arrays;

public enum MovingAverage {
	_5, _13, _26, _50, _100, _200;

	public static MovingAverage getMAByNumber(int maInput) {
		for (MovingAverage ma : values()) {
			if (Integer.parseInt(ma.name().substring(1)) == maInput) {
				return ma;
			}
		}
		throw new RuntimeException("Unable to identify moving average from " + maInput);
	}

	public int getNumber() {
		return Integer.parseInt(name().substring(1));
	}

	public static int[] getAllStandardMAValuesSorted() {
		int i = 0;
		int[] allValues = new int[values().length];
		for (MovingAverage ma : values()) {
			allValues[i++] = ma.getNumber();
		}
		Arrays.sort(allValues);
		return allValues;
	}
}
