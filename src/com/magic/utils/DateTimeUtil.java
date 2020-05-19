package com.magic.utils;



import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeUtil 
{

	public static long startTime()
	{
		long startTime =System.currentTimeMillis(); //start time
		return startTime;
	}

	public static long endTime()
	{
		long endTime =System.currentTimeMillis(); //start time
		return endTime;
	}

	public static double totaltimeInmin(long starttime, long endtime)
	{
		double time =(endtime - starttime) / 1000.0;
		double timeInMin=time/60.0;
		return timeInMin;

	}

	public static boolean validateDateFormat(String format, String date) throws ParseException
	{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		try
		{
			sdf.parse(date);
			return true;
		}
		catch(Exception e)
		{

		}
		return false;

	}
	



}
