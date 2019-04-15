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

package com.vizerium.commons.dao;

import java.text.NumberFormat;
import java.util.Objects;

import com.vizerium.commons.util.NumberFormats;

public class UnitPrice {

	protected static final NumberFormat nf = NumberFormats.getForPrice();

	protected float open;

	protected float high;

	protected float low;

	protected float close;

	public UnitPrice() {

	}

	public UnitPrice(float open, float high, float low, float close) {
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
	}

	public float getOpen() {
		return open;
	}

	public void setOpen(float open) {
		this.open = open;
	}

	public float getHigh() {
		return high;
	}

	public void setHigh(float high) {
		this.high = high;
	}

	public float getLow() {
		return low;
	}

	public void setLow(float low) {
		this.low = low;
	}

	public float getClose() {
		return close;
	}

	public void setClose(float close) {
		this.close = close;
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
		UnitPrice other = (UnitPrice) obj;
		return Objects.equals(open, other.open) && Objects.equals(high, other.high) && Objects.equals(low, other.low) && Objects.equals(close, other.close);

	};

	@Override
	public int hashCode() {
		return Objects.hash(open, high, low, close);
	};

	@Override
	public String toString() {
		return nf.format(open) + "," + nf.format(high) + "," + nf.format(low) + "," + nf.format(close);
	}
}
