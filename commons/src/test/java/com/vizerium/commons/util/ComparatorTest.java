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

package com.vizerium.commons.util;

import java.util.Comparator;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class ComparatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		TreeSet<ComparableClass> treeSet = new TreeSet<ComparableClass>(new ComparatorClass2());
		treeSet.add(new ComparableClass(0.1f, 50.0f));
		treeSet.add(new ComparableClass(0.2f, 100.0f));
		treeSet.add(new ComparableClass(0.1f, 150.0f));
		treeSet.add(new ComparableClass(0.2f, 40.0f));
		treeSet.add(new ComparableClass(0.2f, 0.0f));
		treeSet.add(new ComparableClass(0.1f, 25.0f));
		treeSet.add(new ComparableClass(0.1f, 70.0f));

		for (ComparableClass comparableClass : treeSet) {
			System.out.println(comparableClass);
		}
	}

	class ComparableClass {
		float a;
		float b;

		ComparableClass(float a, float b) {
			this.a = a;
			this.b = b;
		}

		float getA() {
			return a;
		}

		float getB() {
			return b;
		}

		@Override
		public String toString() {
			return a + " " + b;
		}
	}

	class ComparatorClass implements Comparator<ComparableClass> {

		@Override
		public int compare(ComparableClass o1, ComparableClass o2) {
			return Comparator.comparing(ComparableClass::getA).thenComparing(ComparableClass::getB).compare(o2, o1);
		}
	}

	class ComparatorClass2 implements Comparator<ComparableClass> {

		@Override
		public int compare(ComparableClass o1, ComparableClass o2) {
			return Comparator.comparing(ComparableClass::getA).reversed().thenComparing(ComparableClass::getB).compare(o2, o1);
		}
	}

	class ComparatorClass3 implements Comparator<ComparableClass> {

		@Override
		public int compare(ComparableClass o1, ComparableClass o2) {
			return Comparator.comparing(ComparableClass::getA).thenComparing(ComparableClass::getB).reversed().compare(o2, o1);
		}
	}
}
