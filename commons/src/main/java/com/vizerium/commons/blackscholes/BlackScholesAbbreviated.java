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
 * References: 
 * 		https://bret-blackford.github.io/black-scholes/
 * 		https://github.com/bret-blackford/black-scholes/tree/master/OptionValuation/src/mBret/options/
 * 		
 * GitHub Clone : https://github.com/bret-blackford/black-scholes.git
 */

package com.vizerium.commons.blackscholes;

import org.apache.commons.math3.distribution.NormalDistribution;

/**
 *  @author mblackford  mBret Michael Bret Blackford
 * Class from Jernej Kovse -- http://www1.relacija.com/?p=57 
 * 
 * To compile this, you will need the Apache Common Math library (due to 
 * the NormalDistributionImpl class).
 * 
 * an example of Java implementation of the Black Scholes formula for 
 * valuing call options. The formula is used to value a European call 
 * option on a non-dividend paying stock.
 */
public class BlackScholesAbbreviated {

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
	public static double[] getCallPriceGreeks(double s, double x, double sigma, double t, double r) {

		double[] c = new double[6];
		double d1 = (Math.log(s / x) + (r + Math.pow(sigma, 2) / 2) * t) / (sigma * Math.sqrt(t));
		double d2 = d1 - sigma * Math.sqrt(t);

		NormalDistribution n = new NormalDistribution();
		c[0] = s * n.cumulativeProbability(d1) - x * Math.exp(-r * t) * n.cumulativeProbability(d2);

		c[1] = n.cumulativeProbability(d1); // delta

		double thetaLeft = -(s * n.density(d1) * sigma) / (2 * Math.sqrt(t));
		double thetaRight = r * x * Math.exp(-r * t) * n.cumulativeProbability(d2);
		c[2] = (thetaLeft - thetaRight) / 365.0; // theta

		c[3] = (x * t * Math.exp(-r * t) * n.cumulativeProbability(d2)) / 100.0; // rho
		c[4] = n.density(d1) / (s * sigma * Math.sqrt(t)); // gamma
		c[5] = (s * n.density(d1) * Math.sqrt(t)) / 100.0; // vega

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
	public static double[] getPutPriceGreeks(double s, double x, double sigma, double t, double r) {

		double[] p = new double[6];
		double d1 = (Math.log(s / x) + (r + Math.pow(sigma, 2) / 2) * t) / (sigma * Math.sqrt(t));
		double d2 = d1 - sigma * Math.sqrt(t);

		NormalDistribution n = new NormalDistribution();
		p[0] = x * Math.exp(-r * t) * n.cumulativeProbability(-d2) - s * n.cumulativeProbability(-d1);

		p[1] = n.cumulativeProbability(d1) - 1; // delta

		double thetaLeft = -(s * n.density(d1) * sigma) / (2 * Math.sqrt(t));
		double thetaRight = r * x * Math.exp(-r * t) * n.cumulativeProbability(-d2);
		p[2] = (thetaLeft + thetaRight) / 365.0; // theta

		p[3] = (-x * t * Math.exp(-r * t) * n.cumulativeProbability(-d2)) / 100.0; // rho
		p[4] = n.density(d1) / (s * sigma * Math.sqrt(t)); // gamma
		p[5] = (s * n.density(d1) * Math.sqrt(t)) / 100.0; // vega

		return p;
	}
}
