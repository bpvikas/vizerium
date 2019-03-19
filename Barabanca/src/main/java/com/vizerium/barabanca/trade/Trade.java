package com.vizerium.barabanca.trade;

import java.text.NumberFormat;
import java.time.LocalDateTime;

import org.apache.log4j.Logger;

import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.trade.TradeAction;
import com.vizerium.commons.util.NumberFormats;

public class Trade {

	private static final Logger logger = Logger.getLogger(Trade.class);

	private static NumberFormat nf = NumberFormats.getForPrice();

	private String scripName;

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

	public Trade(String scripName, TradeAction action, LocalDateTime entryDateTime, float entryPrice) {
		this.scripName = scripName;
		this.action = action;
		this.entryDateTime = entryDateTime;
		this.entryPrice = entryPrice;
	}

	public Trade(String scripName, TradeAction action, LocalDateTime entryDateTime, float entryPrice, float stopLoss) {
		this(scripName, action, entryDateTime, entryPrice);
		this.entryStopLoss = stopLoss;
	}

	public Trade(String scripName, TradeAction action, LocalDateTime entryDateTime, float entryPrice, LocalDateTime exitDateTime, float exitPrice) {
		this(scripName, action, entryDateTime, entryPrice);
		this.exitDateTime = exitDateTime;
		this.exitPrice = exitPrice;
	}

	public Trade(String scripName, TradeAction action, LocalDateTime entryDateTime, float entryPrice, float stopLoss, LocalDateTime exitDateTime, float exitPrice) {
		this(scripName, action, entryDateTime, entryPrice, stopLoss);
		this.exitDateTime = exitDateTime;
		this.exitPrice = exitPrice;
	}

	public String getScripName() {
		return scripName;
	}

	public void setScripName(String scripName) {
		this.scripName = scripName;
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
		return "Unrealised max_p @ " + maxUnrealisedProfitDateTime + " @ " + nf.format(maxUnrealisedProfit) + " \tmax_l @ " + maxUnrealisedLossDateTime + " @ "
				+ nf.format(maxUnrealisedLoss) + "\tcurrentPL " + nf.format(currentUnrealisedPL);
	}

	public void setUnrealisedStatus(UnitPriceData unitPriceData) {
		if (exitDateTime == null || exitPrice == 0.0f) {
			if (TradeAction.LONG.equals(action)) {
				currentUnrealisedPL = unitPriceData.getTradedValue() - entryPrice;
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
				currentUnrealisedPL = entryPrice - unitPriceData.getTradedValue();
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
}
