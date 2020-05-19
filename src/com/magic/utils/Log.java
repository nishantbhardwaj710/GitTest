package com.magic.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class Log 
{

	// Initialize Log4j logs
	static
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
		System.setProperty("current.date", dateFormat.format(new Date()));
	}

	private static Logger Log = Logger.getLogger(ResultListener.class);

	// This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite

	public static void startTestCase(String sTestCaseName)
	{

		Log.info("****************************************************************************************");

		Log.info("$$$$$$$$$$$$$$$$$$$$$         "+sTestCaseName+ "       		 $$$$$$$$$$$$$$$$$$$$$$$$$");

		Log.info("****************************************************************************************");

	}

	//This is to print log for the ending of the test case

	public static void endTestCase(String sTestCaseName)
	{

		Log.info("XXXXXXXXXXXXXXXXXXXXX         "+"-E---N---D-"+"                XXXXXXXXXXXXXXXXXXXXXX");

	}

	// Need to create these methods, so that they can be called  

	public static void info(String message) 
	{

		Log.info(message);

	}

	public static void warn(String message) {

		Log.warn(message);

	}

	public static void error(String message,Throwable t) 
	{

		Log.error(message,t);

	}

	public static void fatal(String message) 
	{

		Log.fatal(message);

	}

	public static void debug(String message) 
	{

		Log.debug(message);

	}


}