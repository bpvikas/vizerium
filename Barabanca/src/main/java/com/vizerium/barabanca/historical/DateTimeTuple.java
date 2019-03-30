package com.vizerium.barabanca.historical;

import java.time.LocalDateTime;

public class DateTimeTuple {

	private LocalDateTime startDateTime;

	private LocalDateTime endDateTime;

	DateTimeTuple(LocalDateTime startDateTime, LocalDateTime endDateTime) {
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
