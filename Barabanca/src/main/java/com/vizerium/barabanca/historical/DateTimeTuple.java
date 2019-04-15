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

package com.vizerium.barabanca.historical;

import java.time.LocalDateTime;

public class DateTimeTuple {

	private LocalDateTime startDateTime;

	private LocalDateTime endDateTime;

	public DateTimeTuple(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		if (endDateTime.isBefore(startDateTime)) {
			throw new RuntimeException("endDateTime " + endDateTime + " cannot be before startDateTime " + startDateTime);
		}
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	@Override
	public String toString() {
		return startDateTime + " -> " + endDateTime;
	}
}
