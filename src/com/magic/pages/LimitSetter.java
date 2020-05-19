package com.magic.pages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class LimitSetter 
{
	public boolean checkValidityOfTool() throws IOException, ParseException 
	{
		boolean statusTool=false;
		List<String> lines=null;
		String userName = System.getProperty("user.name");
		try 
		{
			File f = new  File("C:\\Users\\"+userName+"\\AppData\\Local\\CacheM\\TL\\limiEx2\\limi.txt");
			if(f.exists()) 
			{ 
				lines = FileUtils.readLines(new  File("C:\\Users\\"+userName+"\\AppData\\Local\\CacheM\\TL\\limiEx2\\limi.txt"));
				//System.out.println("File already there");
				
			}
			else
			{
				File file=new File("C:\\Users\\"+userName+"\\AppData\\Local\\CacheM\\TL\\limiEx2");

				boolean status = file.mkdirs();
				File file2=new File("C:\\Users\\"+userName+"\\AppData\\Local\\CacheM\\TL\\limiEx2\\limi.txt");

				try 
				{
					FileWriter fos=new FileWriter("C:\\Users\\"+userName+"\\AppData\\Local\\CacheM\\TL\\limiEx2\\limi.txt");
					fos.write("01-03-2020");
					fos.close();
				}
				catch (Exception e) {
				}
				lines = FileUtils.readLines(new  File("C:\\Users\\"+userName+"\\AppData\\Local\\CacheM\\TL\\limiEx2\\limi.txt"));
			} 
		}
		catch (Exception e) 
		{
			e.printStackTrace();  
		}

		for (int i = 0; i < lines.size(); i++) 
		{

			String data = lines.get(i);
			//System.out.println("file data-"+data);
			String limiDate1 = data.substring(data.indexOf("=")+1, data.length());
			//System.out.println("Extracted-"+date1);
			SimpleDateFormat ds = new SimpleDateFormat("dd/MM/yyyy");

			Date dt=new Date();
			//System.out.println(ds.format(dt));
			String date2 = ds.format(dt);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			limiDate1 =limiDate1.replaceAll("-", "/");
			Date limiDate = sdf.parse(limiDate1);
			Date currentDate = sdf.parse(date2);
			if(limiDate.after(currentDate))
			{
				statusTool=true;
			}
			else if(limiDate.before(currentDate))
			{
				statusTool=false;
			}
			else if(limiDate.equals(currentDate))
			{
				statusTool=true;
			}
			else
			{
				System.out.println("Error during date and time given to the script");
			}
		}
		return statusTool;
	}
}




