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

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.vizerium.commons.io.FileUtils;

public class LogUtils {

	public static void initializeLogging(String strategyName) {
		String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		System.setProperty("application.run.datetime", dateString);
		System.setProperty("application.strategy.name", strategyName);

		String commandLineParameterDirectoryPath = System.getProperty("directoryPath");
		if (commandLineParameterDirectoryPath != null && !"".equals(commandLineParameterDirectoryPath)) {
			FileUtils.directoryPath = commandLineParameterDirectoryPath;
		} else {
			System.setProperty("directoryPath", FileUtils.directoryPath);
		}
	}

	public static void main(String[] args) {
		File logFile = new File(FileUtils.directoryPath + "output-log-v2/testrun.log");
		if (logFile.exists()) {
			String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			boolean isRenamed = logFile.renameTo(new File(FileUtils.directoryPath + "output-log-v2/testrun_" + dateString + ".log"));
			System.out.println("TestLogFile renamed : " + isRenamed);
		}
	}
}
