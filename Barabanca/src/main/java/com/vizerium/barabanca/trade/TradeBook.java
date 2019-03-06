package com.vizerium.barabanca.trade;

import java.util.ArrayList;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.vizerium.barabanca.historical.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.trade.TradeAction;

public class TradeBook extends ArrayList<Trade> {

	private static final long serialVersionUID = -198397680690476669L;

	private static final Logger logger = Logger.getLogger(TradeBook.class);

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
				logger.debug(t);
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

	public boolean isLastTradeLong() {
		return (size() > 0 && TradeAction.LONG.equals(last().getAction())) ? true : false;
	}

	public boolean isLastTradeShort() {
		return (size() > 0 && TradeAction.SHORT.equals(last().getAction())) ? true : false;
	}

	public Trade last() {
		return get(size() - 1);
	}

	public boolean addLongTrade(Trade trade) {
		logger.debug("Going long.");
		trade.setAction(TradeAction.LONG);
		return add(trade);
	}

	public boolean addShortTrade(Trade trade) {
		logger.debug("Going short.");
		trade.setAction(TradeAction.SHORT);
		return add(trade);
	}

	public void exitLastTrade(UnitPriceData unitPriceData) {
		if (size() != 0) {
			Trade currentTrade = last();
			if ((currentTrade.getExitDateTime() != null) || (currentTrade.getExitPrice() != 0.0f)) {
				throw new RuntimeException("Trying to close an already closed trade.");
			}
			currentTrade.setExitDateTime(unitPriceData.getDateTime());
			currentTrade.setExitPrice(unitPriceData.getClose());
			logger.debug("Exiting Trade : " + currentTrade);
		}
	}

	public void exitLongTrade(UnitPriceData unitPriceData) {
		if (size() != 0) {
			if (last().getAction().equals(TradeAction.LONG)) {
				logger.debug("Closing long.");
				exitLastTrade(unitPriceData);
			} else {
				throw new RuntimeException("Last trade is SHORT.");
			}
		}
	}

	public void coverShortTrade(UnitPriceData unitPriceData) {
		if (size() != 0) {
			if (last().getAction().equals(TradeAction.SHORT)) {
				logger.debug("Closing short.");
				exitLastTrade(unitPriceData);
			} else {
				throw new RuntimeException("Last trade is LONG.");
			}
		}
	}

	public void printStatus(TimeFormat timeFormat) {
		if (size() > 0) {
			logger.info(get(0).getScripName() + " " + timeFormat.getProperty() + " " + String.valueOf(get(0).getExitDateTime().getYear()) + " " + "\t"
					+ (isProfitable() ? "PROFIT" : "LOSS") + "\t" + getPayoff() + "\t" + size() + " trades.\n" + String.valueOf(profitTradesCount) + " profit trades fetching "
					+ String.valueOf(profitPayoff) + " points " + profitPayoff / profitTradesCount + " per trade.\n" + String.valueOf(lossTradesCount) + " loss trades losing "
					+ String.valueOf(lossPayoff) + " points " + lossPayoff / lossTradesCount + " per trade. \nLargest profit @ " + largestProfitTrade + "\nLargest loss @ "
					+ largestLossTrade + ".\n");
		} else {
			logger.info("No trades executed.");
		}
	}
}