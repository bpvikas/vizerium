package com.vizerium.commons.indicators;

import java.util.Arrays;

public enum StandardMovingAverages {
	_5, _13, _26, _50, _100, _200;

	public static StandardMovingAverages getMAByNumber(int maInput) {
		for (StandardMovingAverages ma : values()) {
			if (Integer.parseInt(ma.name().substring(1)) == maInput) {
				return ma;
			}
		}
		throw new RuntimeException("Unable to identify standard moving average from " + maInput);
	}

	public int getNumber() {
		return Integer.parseInt(name().substring(1));
	}

	public static int[] getAllStandardMAsSorted() {
		int i = 0;
		int[] allValues = new int[values().length];
		for (StandardMovingAverages ma : values()) {
			allValues[i++] = ma.getNumber();
		}
		Arrays.sort(allValues);
		return allValues;
	}
}