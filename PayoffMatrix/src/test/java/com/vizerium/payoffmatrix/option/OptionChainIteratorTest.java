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

package com.vizerium.payoffmatrix.option;

import org.junit.Before;
import org.junit.Test;

public class OptionChainIteratorTest {

	private Option[] optionChain;
	private int selectionSetSize;

	private OptionChainIterator<Option> unit;

	@Before
	public void setup() {
		optionChain = new Option[5];
		optionChain[0] = new CallOption();
		optionChain[1] = new CallOption();
		optionChain[2] = new PutOption();
		optionChain[3] = new CallOption();
		optionChain[4] = new PutOption();

		selectionSetSize = 3;

		unit = new OptionChainIterator<Option>(optionChain, selectionSetSize);
	}

	@Test
	public void testIncrements() {
		while (unit.hasNext()) {
			unit.next();
		}
	}
}
