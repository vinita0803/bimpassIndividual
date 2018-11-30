package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import driverScript.Generic_Functions;


public class TestUtil extends Generic_Functions
{	// returns current date and time
	public static String now(String dateFormat)
	{
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    return sdf.format(cal.getTime());

	}
	
}
