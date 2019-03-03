package com.vizerium.commons.calculators;

import java.util.List;

import com.vizerium.barabanca.dao.UnitPrice;

public interface StochasticCalculatorBase {

	/*
	 * ll - lowest low. This method returns the lowest low among the elements between the start and end position.
	 */
	public default float ll(List<UnitPrice> p, int start, int end) {
		float ll = Float.MAX_VALUE;
		for (int i = start; i <= end; i++) {
			if (p.get(i).getLow() < ll) {
				ll = p.get(i).getLow();
			}
		}
		return ll;
	}

	/*
	 * hh - highest high. This method returns the highest high among the elements between the start and end position.
	 */
	public default float hh(List<UnitPrice> p, int start, int end) {
		float hh = Float.MIN_VALUE;
		for (int i = start; i <= end; i++) {
			if (p.get(i).getHigh() > hh) {
				hh = p.get(i).getHigh();
			}
		}
		return hh;
	}
}
