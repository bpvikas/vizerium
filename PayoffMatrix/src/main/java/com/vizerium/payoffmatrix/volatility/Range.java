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

package com.vizerium.payoffmatrix.volatility;

public class Range {

	private float low;

	private float high;

	private float step;

	public Range() {

	}

	public Range(float low, float high) {
		this.low = low;
		this.high = high;
		if (low > high) {
			throw new RuntimeException("The range low cannot be greater than the range high.");
		}
	}

	public Range(float low, float high, float step) {
		this(low, high);
		this.step = step;
	}

	public Range(String low, String high, String step) {
		this(Float.parseFloat(low), Float.parseFloat(high), Float.parseFloat(step));
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

	public float getStep() {
		return step;
	}

	public void setStep(float step) {
		this.step = step;
	}

	@Override
	public String toString() {
		if (Float.isNaN(step) || step == 0.0f) {
			return "Range [" + low + " -> " + high + "]";
		} else {
			return "Range [" + low + " -> " + step + " -> " + high + "]";
		}
	}
}
