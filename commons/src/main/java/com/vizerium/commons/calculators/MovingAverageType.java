package com.vizerium.commons.calculators;

public enum MovingAverageType {
	SIMPLE, EXPONENTIAL;

	public String toString() {
		return name().substring(0, 1) + "MA";
	}
}
