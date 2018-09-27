package com.vizerium.payoffmatrix.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {
	static {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hhmmss");
		System.setProperty("current.datetime", dateFormat.format(new Date()));
	}
}
