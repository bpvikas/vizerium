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

package com.vizerium.barabanca.historical.renko;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Objects;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.util.NumberFormats;

public class Renko implements Cloneable, Comparable<Renko> {

	private float brickSize;

	private float startPrice;

	private float endPrice;

	private LocalDateTime startDateTime;

	private LocalDateTime endDateTime;

	private TimeFormat timeFormat;

	private String scripName;

	private boolean smoothPriceRange;

	public Renko() {

	}

	public float getBrickSize() {
		return brickSize;
	}

	public void setBrickSize(float brickSize) {
		this.brickSize = brickSize;
	}

	public float getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(float startPrice) {
		this.startPrice = startPrice;
	}

	public float getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(float endPrice) {
		this.endPrice = endPrice;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDate) {
		this.startDateTime = startDate;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDate) {
		this.endDateTime = endDate;
	}

	public TimeFormat getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(TimeFormat timeFormat) {
		this.timeFormat = timeFormat;
	}

	public String getScripName() {
		return scripName;
	}

	public void setScripName(String scripName) {
		this.scripName = scripName;
	}

	public boolean isSmoothPriceRange() {
		return smoothPriceRange;
	}

	public void setSmoothPriceRange(boolean smoothPriceRange) {
		this.smoothPriceRange = smoothPriceRange;
	}

	public boolean isUp() {
		return startPrice < endPrice;
	}

	public boolean isDown() {
		return !isUp();
	}

	public boolean isPriceWithinRange(float price) {
		return (isUp()) ? ((startPrice < price && price < endPrice) ? true : false) : ((startPrice >= price && price >= endPrice) ? true : false);
	}

	public float getHigherPrice() {
		return Math.max(startPrice, endPrice);
	}

	public float getLowerPrice() {
		return Math.min(startPrice, endPrice);
	}

	@Override
	public Renko clone() {
		Renko other = new Renko();
		other.brickSize = this.brickSize;
		other.startPrice = this.startPrice;
		other.endPrice = this.endPrice;
		other.startDateTime = this.startDateTime;
		other.endDateTime = this.endDateTime;
		other.timeFormat = this.timeFormat;
		other.scripName = this.scripName;
		return other;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Renko other = (Renko) obj;
		return Objects.equals(brickSize, other.brickSize) && Objects.equals(startPrice, other.startPrice) && Objects.equals(endPrice, other.endPrice)
				&& Objects.equals(startDateTime, other.startDateTime) && Objects.equals(endDateTime, other.endDateTime) && Objects.equals(timeFormat, other.timeFormat)
				&& Objects.equals(scripName, other.scripName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(brickSize, startPrice, endPrice, startDateTime, endDateTime, timeFormat, scripName);
	}

	@Override
	public String toString() {
		NumberFormat pnf = NumberFormats.getForPrice();
		return "Renko[" + scripName + "," + pnf.format(brickSize) + "," + (isUp() ? "Up" : "Down") + "\tstart " + startDateTime + "@" + pnf.format(startPrice) + "\t\tend "
				+ endDateTime + "@" + pnf.format(endPrice) + "]";
	}

	@Override
	public int compareTo(Renko o) {
		return startDateTime.compareTo(o.startDateTime);
	}
}
