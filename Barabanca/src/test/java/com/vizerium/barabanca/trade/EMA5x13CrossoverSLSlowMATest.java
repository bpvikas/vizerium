package com.vizerium.barabanca.trade;

public class EMA5x13CrossoverSLSlowMATest extends EMA5x13CrossoverTest {

	@Override
	protected int getStopLossMA() {
		return getSlowMA();
	}
}
