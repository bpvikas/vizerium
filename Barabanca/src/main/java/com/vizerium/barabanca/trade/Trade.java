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

import java.text.NumberFormat;
import java.time.LocalDateTime;

import org.apache.log4j.Logger;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.trade.TradeAction;
import com.vizerium.commons.util.NumberFormats;

public class Trade {

	private static final Logger logger = Logger.getLogger(Trade.class);

	private static NumberFormat nf = NumberFormats.getForPrice();

	private String scripName;

	private TimeFormat timeFormat;

	private TradeAction action;

	private LocalDateTime entryDateTime;

	private float entryPrice;

	private float entryStopLoss;

	private LocalDateTime exitDateTime;

	private float exitPrice;

	private float exitStopLoss;

	private float payoff = Float.MIN_VALUE;

	private float maxUnrealisedProfit = Float.MIN_VALUE;

	private LocalDateTime maxUnrealisedProfitDateTime;

	private float maxUnrealisedLoss = Float.MAX_VALUE;

	private LocalDateTime maxUnrealisedLossDateTime;

	private float currentUnrealisedPL;

	public Trade() {

	}

	public Trade(String scripName, TimeFormat timeFormat, TradeAction action, LocalDateTime entryDateTime, float entryPrice) {
		this.scripName = scripName;
		this.timeFormat = timeFormat;
		this.action = action;
		this.entryDateTime = entryDateTime;
		this.entryPrice = entryPrice;
	}

	public Trade(String scripName, TimeFormat timeFormat, TradeAction action, LocalDateTime entryDateTime, float entryPrice, float stopLoss) {
		this(scripName, timeFormat, action, entryDateTime, entryPrice);
		this.entryStopLoss = stopLoss;
	}

	public Trade(String scripName, TimeFormat timeFormat, TradeAction action, LocalDateTime entryDateTime, float entryPrice, LocalDateTime exitDateTime, float exitPrice) {
		this(scripName, timeFormat, action, entryDateTime, entryPrice);
		this.exitDateTime = exitDateTime;
		this.exitPrice = exitPrice;
	}

	public Trade(String scripName, TimeFormat timeFormat, TradeAction action, LocalDateTime entryDateTime, float entryPrice, float stopLoss, LocalDateTime exitDateTime,
			float exitPrice) {
		this(scripName, timeFormat, action, entryDateTime, entryPrice, stopLoss);
		this.exitDateTime = exitDateTime;
		this.exitPrice = exitPrice;
	}

	public String getScripName() {
		return scripName;
	}

	public void setScripName(String scripName) {
		this.scripName = scripName;
	}

	public TimeFormat getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(TimeFormat timeFormat) {
		this.timeFormat = timeFormat;
	}

	public TradeAction getAction() {
		return action;
	}

	public void setAction(TradeAction action) {
		this.action = action;
	}

	public LocalDateTime getEntryDateTime() {
		return entryDateTime;
	}

	public void setEntryDateTime(LocalDateTime entryDateTime) {
		this.entryDateTime = entryDateTime;
	}

	public float getEntryPrice() {
		return entryPrice;
	}

	public void setEntryPrice(float entryPrice) {
		this.entryPrice = entryPrice;
	}

	public float getEntryStopLoss() {
		return entryStopLoss;
	}

	public void setEntryStopLoss(float entryStopLoss) {
		this.entryStopLoss = entryStopLoss;
	}

	public LocalDateTime getExitDateTime() {
		return exitDateTime;
	}

	public void setExitDateTime(LocalDateTime exitDateTime) {
		this.exitDateTime = exitDateTime;
	}

	public float getExitPrice() {
		return exitPrice;
	}

	public void setExitPrice(float exitPrice) {
		this.exitPrice = exitPrice;
	}

	public float getExitStopLoss() {
		return exitStopLoss;
	}

	public void setExitStopLoss(float exitStopLoss) {
		this.exitStopLoss = exitStopLoss;
	}

	public boolean isExitStopLossHit(float price) {
		if (exitStopLoss == 0.0f) {
			return false;
		} else if (TradeAction.SHORT.equals(action) && price > exitStopLoss) {
			return true;
		} else if (TradeAction.LONG.equals(action) && price < exitStopLoss) {
			return true;
		} else {
			return false;
		}
	}

	public float getExitStoppedPrice(UnitPriceData unitPriceData) {
		if (exitStopLoss == 0.0f) {
			return unitPriceData.getClose();
		} else {
			if (TradeAction.SHORT.equals(action)) {
				if (unitPriceData.getOpen() >= exitStopLoss) { // This is in case of gap-ups at open while holding a short position.
					return unitPriceData.getOpen();
				} else if (unitPriceData.getHigh() >= exitStopLoss && unitPriceData.getLow() <= exitStopLoss) {
					return exitStopLoss;
				}
			} else {
				if (unitPriceData.getOpen() <= exitStopLoss) { // This is in case of gap-downs at open while holding a long position.
					return unitPriceData.getOpen();
				} else if (unitPriceData.getHigh() >= exitStopLoss && unitPriceData.getLow() <= exitStopLoss) {
					return exitStopLoss;
				}
			}
		}
		throw new RuntimeException("Unable to determine Exit Stopped Price for " + unitPriceData.toString());
	}

	public float getPayoff() {
		if (payoff == Float.MIN_VALUE) {
			if (entryDateTime != null && exitDateTime != null) {
				payoff = (action == TradeAction.LONG) ? exitPrice - entryPrice : entryPrice - exitPrice;
			} else {
				payoff = 0.0f;
			}
		}
		return payoff;
	}

	public boolean isProfitable() {
		return getPayoff() > 0 ? true : false;
	}

	public String printUnrealisedStatus() {
		return "Unrealised currentPL " + nf.format(currentUnrealisedPL) + "\tmax_p @ " + maxUnrealisedProfitDateTime + " @ " + nf.format(maxUnrealisedProfit) + " \tmax_l @ "
				+ maxUnrealisedLossDateTime + " @ " + nf.format(maxUnrealisedLoss);
	}

	public void setUnrealisedStatus(UnitPriceData unitPriceData) {
		if (exitDateTime == null || exitPrice == 0.0f) {
			if (TradeAction.LONG.equals(action)) {
				currentUnrealisedPL = unitPriceData.getClose() - entryPrice;
				if (currentUnrealisedPL > maxUnrealisedProfit) {
					maxUnrealisedProfit = currentUnrealisedPL;
					maxUnrealisedProfitDateTime = unitPriceData.getDateTime();
				}
				if (currentUnrealisedPL < maxUnrealisedLoss) {
					maxUnrealisedLoss = currentUnrealisedPL;
					maxUnrealisedLossDateTime = unitPriceData.getDateTime();
				}

			}
			if (TradeAction.SHORT.equals(action)) {
				currentUnrealisedPL = entryPrice - unitPriceData.getClose();
				if (currentUnrealisedPL > maxUnrealisedProfit) {
					maxUnrealisedProfit = currentUnrealisedPL;
					maxUnrealisedProfitDateTime = unitPriceData.getDateTime();
				}
				if (currentUnrealisedPL < maxUnrealisedLoss) {
					maxUnrealisedLoss = currentUnrealisedPL;
					maxUnrealisedLossDateTime = unitPriceData.getDateTime();
				}
			}
			logger.debug(unitPriceData.getDateTime() + " " + printUnrealisedStatus());
		}
	}

	public float getCurrentUnrealisedPL(UnitPriceData unitPriceData) {
		if (exitDateTime == null || exitPrice == 0.0f) {
			if (TradeAction.LONG.equals(action)) {
				currentUnrealisedPL = unitPriceData.getClose() - entryPrice;
			}
			if (TradeAction.SHORT.equals(action)) {
				currentUnrealisedPL = entryPrice - unitPriceData.getClose();
			}
		} else {
			currentUnrealisedPL = 0.0f;
		}
		return currentUnrealisedPL;
	}

	public float getMaxUnrealisedLoss() {
		return maxUnrealisedLoss;
	}

	public LocalDateTime getMaxUnrealisedLossDateTime() {
		return maxUnrealisedLossDateTime;
	}

	public float getMaxUnrealisedProfit() {
		return maxUnrealisedProfit;
	}

	public LocalDateTime getMaxUnrealisedProfitDateTime() {
		return maxUnrealisedProfitDateTime;
	}

	public String toString() {
		return action.name() + "\t" + scripName + " at " + entryDateTime + " @ " + nf.format(entryPrice) + " exited at " + exitDateTime + " @ " + nf.format(exitPrice) + "\t"
				+ (isProfitable() ? "PROFIT" : "LOSS") + "\t" + nf.format(getPayoff()) + System.lineSeparator() + printUnrealisedStatus();
	}

	public String toCsvString() {
		return timeFormat.getProperty() + "," + action.name() + "," + scripName + "," + entryDateTime.toLocalDate() + "," + entryDateTime.toLocalTime() + ","
				+ nf.format(entryPrice) + "," + exitDateTime.toLocalDate() + "," + exitDateTime.toLocalTime() + "," + nf.format(exitPrice) + ","
				+ (isProfitable() ? "PROFIT" : "LOSS") + "," + nf.format(getPayoff()) + ","
				+ ((maxUnrealisedProfitDateTime != null) ? maxUnrealisedProfitDateTime.toLocalDate() : "") + ","
				+ ((maxUnrealisedProfitDateTime != null) ? maxUnrealisedProfitDateTime.toLocalTime() : "") + "," + nf.format(maxUnrealisedProfit) + ","
				+ ((maxUnrealisedLossDateTime != null) ? maxUnrealisedLossDateTime.toLocalDate() : "") + ","
				+ ((maxUnrealisedLossDateTime != null) ? maxUnrealisedLossDateTime.toLocalTime() : "") + "," + nf.format(maxUnrealisedLoss);
	}

	public static String getCsvHeaderString() {
		return "TimeFormat,Action,Scrip,EntryDate,EntryTime,Price,ExitDate,ExitTime,Price,P/L,Amount,UnrProfitDate,UnrProfitTime,UnrProfit,UnrLossDate,UnrLossTime,UnrLoss";
	}
}
