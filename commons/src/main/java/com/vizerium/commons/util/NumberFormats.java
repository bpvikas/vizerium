package com.vizerium.commons.util;

import java.text.NumberFormat;

public class NumberFormats {

	private static NumberFormat pnf = NumberFormat.getInstance();
	static {
		pnf.setMaximumFractionDigits(2);
		pnf.setMinimumFractionDigits(2);
		pnf.setGroupingUsed(false);
	}

	private static NumberFormat manf = NumberFormat.getInstance();
	static {
		manf.setMinimumFractionDigits(4);
		manf.setMaximumFractionDigits(4);
		manf.setGroupingUsed(false);
	}

	public static NumberFormat getForPrice() {
		return pnf;
	}

	public static NumberFormat getForMovingAverage() {
		return manf;
	}
}
