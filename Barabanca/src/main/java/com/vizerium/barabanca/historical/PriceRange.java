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

package com.vizerium.barabanca.historical;

import java.util.Objects;

public class PriceRange {

	private float low;

	private float high;

	public PriceRange(float low, float high) {
		if (high < low) {
			throw new RuntimeException("high " + high + " cannot be less than low " + low);
		}
		this.low = low;
		this.high = high;
	}

	public float getLow() {
		return low;
	}

	public float getHigh() {
		return high;
	}

	public boolean isPriceWithinRange(float price) {
		return price >= low && price < high;
	}

	@Override
	public int hashCode() {
		return Objects.hash(low, high);
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
		PriceRange other = (PriceRange) obj;
		return Objects.equals(low, other.low) && Objects.equals(high, other.high);
	}

	@Override
	public String toString() {
		return low + " -> " + high;
	}
}
