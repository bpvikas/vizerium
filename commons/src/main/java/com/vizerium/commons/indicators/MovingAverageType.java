package com.vizerium.commons.indicators;

public enum MovingAverageType {
	SIMPLE, EXPONENTIAL;

	public String toString() {
		return name().substring(0, 1) + "MA";
	}
}
