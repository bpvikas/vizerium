package com.vizerium.barabanca.trade;

import java.util.ArrayList;
import java.util.ListIterator;

import com.vizerium.barabanca.dao.UnitPriceData;
import com.vizerium.barabanca.historical.TimeFormat;
import com.vizerium.commons.trade.TradeAction;

public class TradeBook extends ArrayList<Trade> {

	private static final long serialVersionUID = -198397680690476669L;

	private float payoff = Float.MIN_VALUE;

	private int profitTradesCount = 0;

	private int lossTradesCount = 0;

	private float profitPayoff = 0.0f;

	private float lossPayoff = 0.0f;

	private Trade largestLossTrade;

	private Trade largestProfitTrade;

	public void printAllTrades() {
		ListIterator<Trade> i = listIterator();
		while (i.hasNext()) {
			Trade t = i.next();
			if (Math.abs(t.getPayoff() / t.getExitPrice()) > 0.1f) {
				System.out.println(t);
			}
		}
	}

	public float getPayoff() {
		if (payoff == Float.MIN_VALUE) {
			payoff = 0.0f;
			largestLossTrade = get(0);
			largestProfitTrade = get(0);
			ListIterator<Trade> i = listIterator();
			while (i.hasNext()) {
				Trade t = i.next();
				float p = t.getPayoff();
				if (p > 0.0f) {
					profitPayoff += p;
					++profitTradesCount;
					if (p > largestProfitTrade.getPayoff()) {
						largestProfitTrade = t;
					}
				} else {
					lossPayoff += p;
					++lossTradesCount;
					if (p < largestLossTrade.getPayoff()) {
						largestLossTrade = t;
					}
				}
				payoff += p;
			}
		}
		return payoff;
	}

	public boolean isProfitable() {
		return getPayoff() > 0 ? true : false;
	}

	public boolean isLastTradeExited() {
		return (size() > 0) ? (last().getExitDateTime() != null) || (last().getExitPrice() != 0.0f) : true;
	}

	public Trade last() {
		return get(size() - 1);
	}

	public boolean addLongTrade(Trade trade) {
		trade.setAction(TradeAction.LONG);
		return add(trade);
	}

	public boolean addShortTrade(Trade trade) {
		trade.setAction(TradeAction.SHORT);
		return add(trade);
	}

	private void exitLastTrade(UnitPriceData unitPriceData) {
		if (size() != 0) {
			Trade currentTrade = last();
			currentTrade.setExitDateTime(unitPriceData.getDateTime());
			currentTrade.setExitPrice(unitPriceData.getClose());
		}
	}

	public void exitLongTrade(UnitPriceData unitPriceData) {
		if (size() != 0) {
			if (last().getAction().equals(TradeAction.LONG)) {
				exitLastTrade(unitPriceData);
			} else {
				throw new RuntimeException("Last trade is SHORT.");
			}
		}
	}

	public void coverShortTrade(UnitPriceData unitPriceData) {
		if (size() != 0) {
			if (last().getAction().equals(TradeAction.SHORT)) {
				exitLastTrade(unitPriceData);
			} else {
				throw new RuntimeException("Last trade is LONG.");
			}
		}
	}

	public void printStatus(TimeFormat timeFormat) {
		if (size() > 0) {
			System.out.println(get(0).getScripName() + " " + timeFormat.getProperty() + " " + String.valueOf(get(0).getExitDateTime().getYear()) + " " + "\t"
					+ (isProfitable() ? "PROFIT" : "LOSS") + "\t" + getPayoff() + "\t" + size() + " trades.\n" + String.valueOf(profitTradesCount) + " profit trades fetching "
					+ String.valueOf(profitPayoff) + " points " + profitPayoff / profitTradesCount + " per trade.\n" + String.valueOf(lossTradesCount) + " loss trades losing "
					+ String.valueOf(lossPayoff) + " points " + lossPayoff / lossTradesCount + " per trade. \nLargest profit @ " + largestProfitTrade + "\nLargest loss @ "
					+ largestLossTrade + ".\n");
		} else {
			System.out.println("No trades executed.");
		}
	}
}