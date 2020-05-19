package com.magic.utils;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyUtil 
{
	static Properties prop;
	static
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
		System.setProperty("current.date", dateFormat.format(new Date()));
	}
	
	@SuppressWarnings("unused")
	private static Logger log=Logger.getLogger(PropertyUtil.class);

	public static String getproperty(String filename, String propertyname) throws Exception
	{
			prop=new Properties();
			prop.load(new FileInputStream(filename+".properties"));
			String value=prop.getProperty(propertyname);
			if(value==null)
			{
				//log.error("Property "+propertyname+" not found in "+filename+".properties");
				throw new Exception("Property "+propertyname+" not found in "+filename+".properties");
			}
			return value;
		

	}

}
