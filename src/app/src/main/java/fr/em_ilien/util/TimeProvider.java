package fr.em_ilien.util;

import java.util.Calendar;

public class TimeProvider {
	public static int currentYearValue() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
}
