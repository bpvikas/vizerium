/*
 * Copyright 2018 Vizerium, Inc.
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

package com.vizerium.payoffmatrix.volatility;

public class BollingerBand {

	private float low;

	private float mid;

	private float high;

	public BollingerBand() {

	}

	public BollingerBand(float low, float mid, float high) {
		if (low > high || low > mid || mid > high) {
			throw new RuntimeException("The band details should be low < mid < high.");
		}
		this.low = low;
		this.mid = mid;
		this.high = high;
	}

	public float getLow() {
		return low;
	}

	public void setLow(float low) {
		this.low = low;
	}

	public float getHigh() {
		return high;
	}

	public void setHigh(float high) {
		this.high = high;
	}

	public float getMid() {
		return mid;
	}

	public void setMid(float mid) {
		this.mid = mid;
	}

	@Override
	public String toString() {
		return "Bollinger Band Range: " + low + " -> " + mid + " -> " + high;
	}
}
