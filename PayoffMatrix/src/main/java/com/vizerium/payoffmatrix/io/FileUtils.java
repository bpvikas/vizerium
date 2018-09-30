/*
 * Copyright 2018 Vizerium, Inc.
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

package com.vizerium.payoffmatrix.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

public class FileUtils {

	public static String directoryPath = "C:/Work/Vizerium/data/";

	public static File getLastModifiedFileInDirectory(String directoryPath, String endsWith) {

		FilenameFilter fileNameFilter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.lastIndexOf(endsWith) >= 0) {
					return true;
				}
				return false;
			}
		};
		File[] files = new File(directoryPath).listFiles(fileNameFilter);

		Arrays.sort(files, new Comparator<File>() {
			public int compare(File f1, File f2) {
				return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
			}
		});

		if (files == null || files.length == 0) {
			throw new RuntimeException("Last modified file not found for " + directoryPath + " for " + endsWith);
		} else {
			return files[0];
		}
	}
}
