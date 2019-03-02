package com.vizerium.commons.trade;

import java.text.NumberFormat;
import java.time.LocalDateTime;

public class Trade {

	private static NumberFormat nf = NumberFormat.getInstance();
	static {
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		nf.setGroupingUsed(false);
	}

	private String scripName;

	private TradeAction action;

	private LocalDateTime entryDateTime;

	private LocalDateTime exitDateTime;

	private float entryPrice;

	private float exitPrice;

	public Trade() {

	}

	public Trade(String scripName, TradeAction action, LocalDateTime entryDateTime, float entryPrice) {
		this.scripName = scripName;
		this.action = action;
		this.entryDateTime = entryDateTime;
		this.entryPrice = entryPrice;
	}

	public Trade(String scripName, TradeAction action, LocalDateTime entryDateTime, float entryPrice, LocalDateTime exitDateTime, float exitPrice) {
		this(scripName, action, entryDateTime, entryPrice);
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

	public LocalDateTime getExitDateTime() {
		return exitDateTime;
	}

	public void setExitDateTime(LocalDateTime exitDateTime) {
		this.exitDateTime = exitDateTime;
	}

	public float getEntryPrice() {
		return entryPrice;
	}

	public void setEntryPrice(float entryPrice) {
		this.entryPrice = entryPrice;
	}

	public float getExitPrice() {
		return exitPrice;
	}

	public void setExitPrice(float exitPrice) {
		this.exitPrice = exitPrice;
	}

	public float getPayoff() {
		if (entryDateTime != null && exitDateTime != null) {
			return (action == TradeAction.LONG) ? exitPrice - entryPrice : entryPrice - exitPrice;
		} else {
			return 0.0f;
		}
	}

	public boolean isProfitable() {
		return getPayoff() > 0 ? true : false;
	}

	public String toString() {
		return action.name() + "\t" + scripName + " at " + entryDateTime + " @ " + nf.format(entryPrice) + " exited at " + exitDateTime + " @ " + nf.format(exitPrice) + "\t"
				+ (isProfitable() ? "PROFIT" : "LOSS") + "\t" + nf.format(getPayoff());
	}
}
