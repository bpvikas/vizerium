/*
 * Copyright 2019 Vizerium, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vizerium.barabanca.trade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.trade.TradeAction;

public class TradeBook extends ArrayList<Trade> {

	private static final long serialVersionUID = -198397680690476669L;

	private static final Logger logger = Logger.getLogger(TradeBook.class);

	private String strategyName;

	private String scripName;

	private TimeFormat timeFormat;

	private float payoff = Float.MIN_VALUE;

	private int profitTradesCount = 0;

	private int lossTradesCount = 0;

	private float profitPayoff = 0.0f;

	private float lossPayoff = 0.0f;

	private float drawdown = 0.0f;

	private float maxDrawdown = Float.MAX_VALUE;

	private LocalDateTime maxDrawdownDateTime = null;

	private Trade largestLossTrade;

	private Trade largestProfitTrade;

	public String getStrategyName() {
		return strategyName;
	}

	public void setIdentifiers(String strategyName, String scripName, TimeFormat timeFormat) {
		this.strategyName = strategyName;
		this.scripName = scripName;
		this.timeFormat = timeFormat;
	}

	public String getScripName() {
		return scripName;
	}

	public TimeFormat getTimeFormat() {
		return timeFormat;
	}

	public float getPayoff() {
		if (payoff == Float.MIN_VALUE) {
			payoff = 0.0f;
			if (!isEmpty()) {
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
						drawdown = 0.0f;
					} else {
						lossPayoff += p;
						++lossTradesCount;
						if (p < largestLossTrade.getPayoff()) {
							largestLossTrade = t;
						}
						drawdown += p;
						if (drawdown < maxDrawdown) {
							maxDrawdown = drawdown;
							maxDrawdownDateTime = t.getExitDateTime();
						}
					}
					payoff += p;
				}
			}
		}
		return payoff;
	}

	public float getProfitPayoff() {
		getPayoff();
		return profitPayoff;
	}

	public float getLossPayoff() {
		getPayoff();
		return lossPayoff;
	}

	public boolean isProfitable() {
		return getPayoff() > 0;
	}

	public boolean isLastTradeExited() {
		return (!isEmpty()) ? (last().getExitDateTime() != null) || (last().getExitPrice() != 0.0f) : true;
	}

	public boolean isLastTradeLong() {
		return (!isEmpty() && TradeAction.LONG.equals(last().getAction())) ? true : false;
	}

	public boolean isLastTradeShort() {
		return (!isEmpty() && TradeAction.SHORT.equals(last().getAction())) ? true : false;
	}

	public Trade last() {
		return get(size() - 1);
	}

	public boolean addLongTrade(UnitPriceData unitPriceData) {
		if (!isLastTradeExited()) {
			throw new RuntimeException("Adding a trade from " + unitPriceData + " while the last trade is not exited.");
		}
		Trade trade = new Trade(unitPriceData.getScripName(), unitPriceData.getTimeFormat(), TradeAction.LONG, unitPriceData.getDateTime(), unitPriceData.getTradedValue());
		logger.debug("Going long @ " + trade.getEntryPrice() + " @ " + trade.getEntryDateTime());
		return add(trade);
	}

	public boolean addShortTrade(UnitPriceData unitPriceData) {
		if (!isLastTradeExited()) {
			throw new RuntimeException("Adding a trade from " + unitPriceData + " while the last trade is not exited.");
		}
		Trade trade = new Trade(unitPriceData.getScripName(), unitPriceData.getTimeFormat(), TradeAction.SHORT, unitPriceData.getDateTime(), unitPriceData.getTradedValue());
		logger.debug("Going short @ " + trade.getEntryPrice() + " @ " + trade.getEntryDateTime());
		return add(trade);
	}

	public void exitLastTrade(UnitPriceData unitPriceData) {
		if (!isEmpty()) {
			Trade currentTrade = last();
			if ((currentTrade.getExitDateTime() != null) || (currentTrade.getExitPrice() != 0.0f)) {
				throw new RuntimeException("Trying to close an already closed trade.");
			}
			currentTrade.exit(unitPriceData);
			logger.debug("Exiting Trade : " + currentTrade);
			// resetting the traded information here, so that it is available fresh for other trades.
			unitPriceData.setTradedValue(0.0f);
		}
	}

	public void exitLongTrade(UnitPriceData unitPriceData) {
		if (!isEmpty()) {
			if (last().getAction().equals(TradeAction.LONG)) {
				exitLastTrade(unitPriceData);
				logger.debug("Closing long @ " + last().getExitPrice() + " @ " + last().getExitDateTime());
			} else {
				throw new RuntimeException("Last trade is SHORT.");
			}
		}
	}

	public void coverShortTrade(UnitPriceData unitPriceData) {
		if (!isEmpty()) {
			if (last().getAction().equals(TradeAction.SHORT)) {
				exitLastTrade(unitPriceData);
				logger.debug("Closing short @ " + last().getExitPrice() + " @ " + last().getExitDateTime());
			} else {
				throw new RuntimeException("Last trade is LONG.");
			}
		}
	}

	public float getPayoffPerLotAfterBrokerageAndTaxes() {

		if ("NIFTY".equalsIgnoreCase(scripName.trim())) {
			return (getPayoff() * 75) - (size() * 250); // Nifty lot size 75, Approx brokerage and exchange charges per lot 250.
		} else if ("BANKNIFTY".equalsIgnoreCase(scripName.trim())) {
			return (getPayoff() * 20) - (size() * 250); // BankNifty lot size 20, Approx brokerage and exchange charges per lot 250.
		} else {
			throw new RuntimeException("Unable to determine scrip from " + scripName);
		}
	}

	public void printStatus() {
		if (logger.isDebugEnabled()) {
			if (!isEmpty()) {
				logger.debug(strategyName + " " + scripName + " " + timeFormat.getProperty() + " " + String.valueOf(get(0).getExitDateTime().getYear()) + " "
						+ String.valueOf(get(0).getExitDateTime().getMonth().name().substring(0, 3)) + " " + "\t" + (isProfitable() ? "PROFIT" : "LOSS") + "\t" + getPayoff() + "\t"
						+ size() + " trades.\n" + String.valueOf(profitTradesCount) + " profit trades fetching " + String.valueOf(profitPayoff) + " points "
						+ profitPayoff / profitTradesCount + " per trade.\n" + String.valueOf(lossTradesCount) + " loss trades losing " + String.valueOf(lossPayoff) + " points "
						+ lossPayoff / lossTradesCount + " per trade. \nLargest profit @ " + largestProfitTrade + "\nLargest loss @ " + largestLossTrade + ".\nMaximum drawdown "
						+ maxDrawdown + " @ " + maxDrawdownDateTime + ", Payoff After Brokerage/Taxes " + getPayoffPerLotAfterBrokerageAndTaxes() + ".\n");
			} else {
				logger.debug("No trades executed.");
			}
		}
	}

	public String toCsvString() {
		if (!isEmpty()) {
			return strategyName + "," + scripName + "," + timeFormat.getProperty() + "," + (isProfitable() ? "PROFIT" : "LOSS") + ","
					+ String.valueOf(get(0).getEntryDateTime().toLocalDate()) + "," + String.valueOf(last().getExitDateTime().toLocalDate()) + "," + profitTradesCount + ","
					+ profitPayoff + "," + (profitPayoff / profitTradesCount) + "," + lossTradesCount + "," + lossPayoff + "," + (lossPayoff / lossTradesCount) + "," + size() + ","
					+ getPayoff() + "," + (getPayoff() / size()) + "," + getPayoffPerLotAfterBrokerageAndTaxes() + "," + maxDrawdown + "," + String.valueOf(maxDrawdownDateTime)
					+ "," + largestProfitTrade.getPayoff() + "," + largestProfitTrade.getExitDateTime() + "," + largestLossTrade.getPayoff() + ","
					+ largestLossTrade.getExitDateTime();
		} else {
			return strategyName + "," + get(0).getScripName() + "," + timeFormat.getProperty() + ",,,,0,0.0,0.0,0,0.0,0.0," + size() + ",0.0,0.0,0.0,0.0,,0.0,,0.0,";
		}
	}

	public static String getCsvHeaderString() {
		return "Strategy,Scrip,TimeFormat,P/L,Start Date,End Date,Profit Trades,Profit Points,Avg Profit,Loss Trades,Loss Points,Avg Loss,Total Trades,Total Points,Avg Total,"
				+ "Payoff After Tax,Max Drawdown,Max Drawdown Time,Largest Profit,Largest Profit Time,Largest Loss,Largest Loss Time";
	}
}