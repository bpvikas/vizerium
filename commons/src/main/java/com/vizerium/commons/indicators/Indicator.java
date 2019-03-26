package com.vizerium.commons.indicators;

/*
 * This is just a marker annotation to indicate that this is an indicator class.
 * I tried using an annotation @interface but when I was trying to put it in a List
 * or Map, I could not add elements of a certain annotation.	
 */
public interface Indicator {
	public float[] getUnitPriceIndicator(int position);

}