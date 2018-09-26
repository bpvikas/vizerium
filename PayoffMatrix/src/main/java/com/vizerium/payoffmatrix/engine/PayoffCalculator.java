package com.vizerium.payoffmatrix.engine;

import com.vizerium.payoffmatrix.criteria.Criteria;
import com.vizerium.payoffmatrix.dao.OptionDataStore;
import com.vizerium.payoffmatrix.io.Output;
import com.vizerium.payoffmatrix.option.Option;

public abstract class PayoffCalculator {

	public abstract Option[] filterOptionChainForEvaluatingNewPositions(Option[] optionChain, Criteria criteria);

	public abstract Output calculatePayoff(Criteria criteria, OptionDataStore optionDataStore);

}
