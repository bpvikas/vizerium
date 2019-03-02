package com.vizerium.commons.trade;

import java.util.ArrayList;
import java.util.ListIterator;

public class TradeList extends ArrayList<Trade> {

	private static final long serialVersionUID = -198397680690476669L;

	public void printAllTrades() {
		ListIterator<Trade> i = listIterator();
		while (i.hasNext()) {
			System.out.println(i.next());
		}
	}

	public float getPayoff() {
		float totalPayoff = 0.0f;
		ListIterator<Trade> i = listIterator();
		while (i.hasNext()) {
			totalPayoff += i.next().getPayoff();
		}
		return totalPayoff;
	}

	public boolean isProfitable() {
		return getPayoff() > 0 ? true : false;
	}
}
