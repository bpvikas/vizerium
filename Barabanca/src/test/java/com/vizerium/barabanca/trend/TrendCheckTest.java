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

package com.vizerium.barabanca.trend;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.vizerium.barabanca.historical.HistoricalDataReader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner.class)
public class TrendCheckTest {

	@InjectMocks
	private TrendCheck unit;

	@Mock
	private HistoricalDataReader historicalDataReader;

	@Before
	public void setUp() {
		/*
		 * Mockito.when(historicalDataReader.getUnitPriceDataForRange(Mockito.isA(String.class), Mockito.isA(LocalDateTime.class), Mockito.isA(LocalDateTime.class),
		 * Mockito.isA(TimeFormat.class))).thenAnswer(new Answer<List<UnitPriceData>>() {
		 * 
		 * @Override public List<UnitPriceData> answer(InvocationOnMock invocation) throws Throwable { Object[] arguments = invocation.getArguments(); return
		 * getOHLCData((LocalDateTime) arguments[1], (LocalDateTime) arguments[2], (TimeFormat) arguments[3]); } });
		 */ }

}