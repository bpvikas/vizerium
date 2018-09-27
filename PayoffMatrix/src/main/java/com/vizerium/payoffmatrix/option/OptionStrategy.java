package com.vizerium.payoffmatrix.option;

public interface OptionStrategy {

	boolean isExisting();

	Option[] getOptions();

}
