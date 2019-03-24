package com.vizerium.commons.indicators;

public enum MovingAverageType {
	SIMPLE, EXPONENTIAL, WELLESWILDER;

	@Override
	public String toString() {
		return name().substring(0, 1) + "MA";
	}
}
