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

/*
 * Reference: https://introcs.cs.princeton.edu/java/22library/BlackScholes.java
 * 
 * Greeks Calculations:
 *  https://bret-blackford.github.io/black-scholes/
 * 	https://github.com/bret-blackford/black-scholes/tree/master/OptionValuation/src/mBret/options/
 */

package com.vizerium.commons.blackscholes;

/******************************************************************************
 * Compilation: javac BlackScholes.java MyMath.java Execution: java BlackScholes s x r sigma t
 * 
 * Reads in five command line inputs and calculates the option price according to the Black-Scholes formula.
 *
 * % java BlackScholes 23.75 15.00 0.01 0.35 0.5 8.879159279691955 (actual = 9.10)
 * 
 * % java BlackScholes 30.14 15.0 0.01 0.332 0.25 15.177462481562186 (actual = 14.50)
 *
 *
 * Information calculated based on closing data on Monday, June 9th 2003.
 *
 * Microsoft: share price: 23.75 strike price: 15.00 risk-free interest rate: 1% volatility: 35% (historical estimate) time until expiration: 0.5 years
 *
 * GE: share price: 30.14 strike price: 15.00 risk-free interest rate 1% volatility: 33.2% (historical estimate) time until expiration 0.25 years
 *
 *
 * Reference: http://www.hoadley.net/options/develtoolsvolcalc.htm
 *
 ******************************************************************************/

public class BlackScholes {

	/**
	 *	@param s	: current stock price, i.e., spot price 
	 *	@param x	: strike (exercise) price
	 *	@param sigma	: standard deviation of continuously compounded annual returns, i.e., volatility 
	 *	@param t	: remaining lifetime of the option in years 
	 *	@param r	: continuously compounded risk-free rate
	 * 
	 *  @return double[] c
	 *  	c[0]	: price
	 *  	c[1]	: delta
	 *  	c[2]	: theta
	 *  	c[3]	: rho
	 *  	c[4]	: gamma
	 *  	c[5]	: vega
	 *  
	 */
	public static double[] getCallPriceGreeks(double s, double x, double r, double sigma, double t) {
		double c[] = new double[6];
		double d1 = (Math.log(s / x) + (r + sigma * sigma / 2) * t) / (sigma * Math.sqrt(t));
		double d2 = d1 - sigma * Math.sqrt(t);
		c[0] = s * Gaussian.cdf(d1) - x * Math.exp(-r * t) * Gaussian.cdf(d2); // price

		c[1] = Gaussian.cdf(d1); // delta

		double thetaLeft = -(s * Gaussian.pdf(d1) * sigma) / (2 * Math.sqrt(t));
		double thetaRight = r * x * Math.exp(-r * t) * Gaussian.cdf(d2);
		c[2] = (thetaLeft - thetaRight) / 365.0; // theta

		c[3] = (x * t * Math.exp(-r * t) * Gaussian.cdf(d2)) / 100.0; // rho
		c[4] = Gaussian.pdf(d1) / (s * sigma * Math.sqrt(t)); // gamma
		c[5] = (s * Gaussian.pdf(d1) * Math.sqrt(t)) / 100.0; // vega

		return c;
	}

	/**
	 *	@param s	: current stock price, i.e., spot price 
	 *	@param x	: strike (exercise) price
	 *	@param sigma	: standard deviation of continuously compounded annual returns, i.e., volatility 
	 *	@param t	: remaining lifetime of the option in years 
	 *	@param r	: continuously compounded risk-free rate
	 * 
	 *  @return double[] p
	 *  	p[0]	: price
	 *  	p[1]	: delta
	 *  	p[2]	: theta
	 *  	p[3]	: rho
	 *  	p[4]	: gamma
	 *  	p[5]	: vega
	 *  
	 */
	public static double[] getPutPriceGreeks(double s, double x, double r, double sigma, double t) {
		double p[] = new double[6];
		double d1 = (Math.log(s / x) + (r + sigma * sigma / 2) * t) / (sigma * Math.sqrt(t));
		double d2 = d1 - sigma * Math.sqrt(t);
		p[0] = x * Math.exp(-r * t) * Gaussian.cdf(-d2) - s * Gaussian.cdf(-d1); // price

		p[1] = Gaussian.cdf(d1) - 1; // delta

		double thetaLeft = -(s * Gaussian.pdf(d1) * sigma) / (2 * Math.sqrt(t));
		double thetaRight = r * x * Math.exp(-r * t) * Gaussian.cdf(-d2);
		p[2] = (thetaLeft + thetaRight) / 365.0; // theta

		p[3] = (-x * t * Math.exp(-r * t) * Gaussian.cdf(-d2)) / 100.0; // rho
		p[4] = Gaussian.pdf(d1) / (s * sigma * Math.sqrt(t)); // gamma
		p[5] = (s * Gaussian.pdf(d1) * Math.sqrt(t)) / 100.0; // vega

		return p;
	}
}