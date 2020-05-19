package com.magic.utils;



import java.io.File;
import java.io.FilenameFilter;

public class GetAllFilesInDir 
{
	
	public static File[] getAllFiles(String dir, final String [] extension) 
	{
	    File fl = new File(dir);
	    File[] files = fl.listFiles(new FilenameFilter() 
	    {          
	        public boolean accept(File file, String name) 
	        {
	        	for (String ext : extension) 
	        	{
	        		if(name.toLowerCase().endsWith(ext.trim()))
	        		{
	        			return true;
	        		}
	        		
	        		
				}
				return false;
	        	
	        }
	    });
	    return files;
	}
	
	public static boolean fileExists(String dir, final String [] extension, String fileName) 
	{
	    File fl = new File(dir);
	    
	    File[] files = fl.listFiles(new FilenameFilter() 
	    {          
	        public boolean accept(File file, String name) 
	        {
	        	for (String ext : extension) 
	        	{
	        		if(name.toLowerCase().endsWith(ext.trim()))
	        		{
	        			return true;
	        		}
	        		
	        		
				}
				return false;
	        	
	        }
	    });
	    
	    for (File file : files) 
	    {
	    	if(file.getName().equals(fileName));
	    	return true;
			
		}
		return false;
	   
	}
	
	

}
