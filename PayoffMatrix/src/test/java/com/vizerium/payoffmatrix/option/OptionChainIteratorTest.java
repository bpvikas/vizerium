package com.vizerium.payoffmatrix.option;

import org.junit.Before;
import org.junit.Test;

public class OptionChainIteratorTest {

	private Option[] optionChain;
	private int selectionSetSize;

	private OptionChainIterator unit;

	@Before
	public void setup() {
		optionChain = new Option[5];
		optionChain[0] = new CallOption();
		optionChain[1] = new CallOption();
		optionChain[2] = new PutOption();
		optionChain[3] = new CallOption();
		optionChain[4] = new PutOption();

		selectionSetSize = 3;

		unit = new OptionChainIterator(optionChain, selectionSetSize);
	}

	@Test
	public void testIncrements() {
		while (unit.hasNext()) {
			unit.next();
		}
	}
}
