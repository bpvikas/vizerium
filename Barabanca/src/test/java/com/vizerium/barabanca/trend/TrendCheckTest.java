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