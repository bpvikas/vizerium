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

package com.vizerium.payoffmatrix.option;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class OptionChainIterator<E extends OptionStrategy> implements Iterator<List<E>> {

	private int selectionSetSize;

	private int[] cpos; // currentPosition

	private List<E> optionStrategyChain;

	private boolean returnTrueOnlyOnceIfSelectionSetSizeIs0;

	public OptionChainIterator() {

	}

	public OptionChainIterator(E[] optionStrategyChain, int selectionSetSize) {
		this(Arrays.asList(optionStrategyChain), selectionSetSize);
	}

	public OptionChainIterator(List<E> optionStrategyChain, int selectionSetSize) {
		this.optionStrategyChain = optionStrategyChain;
		this.selectionSetSize = selectionSetSize;
		this.cpos = new int[selectionSetSize];
		initializeCurrentPosition();
		if (selectionSetSize == 0) {
			returnTrueOnlyOnceIfSelectionSetSizeIs0 = true;
		}
	}

	@Override
	public boolean hasNext() {
		for (int i = 0; i < cpos.length; i++) {
			if (cpos[i] < optionStrategyChain.size() - 1) {
				return true;
			}
		}
		if (selectionSetSize == 0 && returnTrueOnlyOnceIfSelectionSetSizeIs0) {
			returnTrueOnlyOnceIfSelectionSetSizeIs0 = false;
			return true;
		}
		return false;
	}

	@Override
	public List<E> next() {
		List<E> nextOptionTradingList = new ArrayList<E>(selectionSetSize);
		for (int i = 0; i < cpos.length; i++) {
			nextOptionTradingList.add(optionStrategyChain.get(cpos[i]));
		}
		incrementCurrentPosition();

		return nextOptionTradingList;
	}

	public int getSelectionSetSize() {
		return selectionSetSize;
	}

	public List<E> getOptionChain() {
		return optionStrategyChain;
	}

	private void initializeCurrentPosition() {
		for (int i = 0; i < cpos.length; i++) {
			cpos[i] = 0;
		}
		printCurrentPosition();
	}

	private void incrementCurrentPosition() {
		if (cpos.length > 0) {
			if (cpos[cpos.length - 1] >= optionStrategyChain.size() - 1) {
				incrementParent(cpos.length - 1);
			} else {
				cpos[cpos.length - 1] = ++cpos[cpos.length - 1];
			}
		}
		printCurrentPosition();
	}

	private void incrementParent(int location) {
		if (location > 0) {
			if (cpos[location - 1] >= optionStrategyChain.size() - 1) {
				incrementParent(location - 1);
			} else {
				cpos[location - 1] = cpos[location - 1] + 1;
			}
		}
		cpos[location] = cpos[location - 1];
	}

	private void printCurrentPosition() {
		for (int i = 0; i < cpos.length; i++) {
			System.out.print(cpos[i] + " ");
		}
		System.out.println();
	}
}
