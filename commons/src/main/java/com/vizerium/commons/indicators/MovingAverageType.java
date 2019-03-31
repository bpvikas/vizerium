package com.vizerium.commons.indicators;

public enum MovingAverageType {
	SIMPLE, EXPONENTIAL, WELLESWILDER;

	@Override
	public String toString() {
		return name().substring(0, 1) + "MA";
	}

	public static MovingAverageType getByName(String name) {
		for (MovingAverageType movingAverageType : values()) {
			if (movingAverageType.name().equalsIgnoreCase(name) || movingAverageType.toString().equalsIgnoreCase(name)
					|| movingAverageType.name().substring(0, 1).equalsIgnoreCase(name)) {
				return movingAverageType;
			}
		}
		throw new RuntimeException("Unable to determine MovingAverageType from " + name);
	}
}