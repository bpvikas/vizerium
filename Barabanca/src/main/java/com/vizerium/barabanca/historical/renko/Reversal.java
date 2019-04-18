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

import java.util.Objects;

public class Reversal {

	static enum ReversalType {
		TOP, BOTTOM;
	}

	private Renko renko;

	private float extremePrice;

	private ReversalType type;

	public Reversal() {

	}

	public Reversal(Renko renko, float extreme, ReversalType type) {
		this.renko = renko;
		this.extremePrice = extreme;
		this.type = type;
	}

	public Renko getRenko() {
		return renko;
	}

	public float getExtremePrice() {
		return extremePrice;
	}

	public ReversalType getType() {
		return type;
	}

	public boolean isTop() {
		return type.equals(ReversalType.TOP);
	}

	public boolean isBottom() {
		return type.equals(ReversalType.BOTTOM);
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
		Reversal other = (Reversal) obj;
		return Objects.equals(renko, other.renko) && Objects.equals(extremePrice, other.extremePrice) && Objects.equals(type, other.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(renko, extremePrice, type);
	}

	@Override
	public String toString() {
		return type.name() + " @ " + extremePrice + " @ " + renko.getEndDateTime();
	}
}
