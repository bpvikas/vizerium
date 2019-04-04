package com.vizerium.barabanca.trade;

import com.vizerium.commons.indicators.DirectionalSystem;

public class ClosingPricesWithTrendCheckByDirectionalSystem_ADX_14Test extends ClosingPricesWithTrendCheckByDirectionalSystemTest {

	@Override
	protected DirectionalSystem getDirectionalSystem() {
		// The default DirectionalSystem constructor has a smoothingPeriod of 14.
		return new DirectionalSystem();
	}

	@Override
	protected String getPreviousResultFileName() {
		return null;
	}
}
