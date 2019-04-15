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

package com.vizerium.commons.indicators;

import java.util.List;

import com.vizerium.commons.dao.UnitPrice;

public interface StochasticCalculatorBase {

	/*
	 * ll - lowest low. This method returns the lowest low among the elements between the start and end position.
	 */
	public default float ll(List<? extends UnitPrice> p, int start, int end) {
		float ll = Float.MAX_VALUE;
		for (int i = start; i <= end; i++) {
			if (p.get(i).getLow() < ll) {
				ll = p.get(i).getLow();
			}
		}
		return ll;
	}

	/*
	 * hh - highest high. This method returns the highest high among the elements between the start and end position.
	 */
	public default float hh(List<? extends UnitPrice> p, int start, int end) {
		float hh = Float.MIN_VALUE;
		for (int i = start; i <= end; i++) {
			if (p.get(i).getHigh() > hh) {
				hh = p.get(i).getHigh();
			}
		}
		return hh;
	}
}
