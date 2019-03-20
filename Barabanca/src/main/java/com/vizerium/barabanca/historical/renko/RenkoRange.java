package com.vizerium.barabanca.historical.renko;

import java.util.ArrayList;

public class RenkoRange extends ArrayList<Renko> {

	private static final long serialVersionUID = -270282056461384924L;

	private float start;

	private float end;

	public Renko last() {
		return get(size() - 1);
	}

	public boolean isLastRenkoClosed() {
		return (size() > 0) ? ((last().getEndDate() != null) ? true : false) : true;
	}

	public float getStart() {
		return start;
	}

	public void setStart(float start) {
		this.start = start;
	}

	public float getEnd() {
		return end;
	}

	public void setEnd(float end) {
		this.end = end;
	}

}
