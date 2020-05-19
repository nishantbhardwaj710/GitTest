package com.magic.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.magic.common.TestBase;
import com.magic.utils.ExcelUtil;
import com.magic.utils.Mailer;
import com.magic.utils.PropertyUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class NewsMailer
{
	WebDriver driver;
	String configFilename = "./config/config";
	String objectFilename = "./object/NewsMailer";
	String ResultFolder="./result/";
	int row=1;
	int map_count=0;
	Map<Integer, List<String>> map=new LinkedHashMap<Integer, List<String>>();

	public NewsMailer(WebDriver driver)
	{
		this.driver = driver;
	}
	static
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
		System.setProperty("current.date", dateFormat.format(new Date()));
	}
	private static Logger Log = Logger.getLogger(NewsMailer.class.getName());
	public void datareading(ExtentTest test) throws Exception 
	{

		String URL = PropertyUtil.getproperty(configFilename, "url");
		String FileName=PropertyUtil.getproperty(configFilename, "filename");
		String SheetName=PropertyUtil.getproperty(configFilename, "sheetname"); 
		String heading_xpath=PropertyUtil.getproperty(objectFilename, "heading_xpath");
		String date_xpath=PropertyUtil.getproperty(objectFilename, "date_xpath");
		String viewIndetail_xpath=PropertyUtil.getproperty(objectFilename, "viewIndetail_xpath");
		String newwindowdate_xpath=PropertyUtil.getproperty(objectFilename, "newwindowdate_xpath");
		String newwindowpeninsuladate=PropertyUtil.getproperty(objectFilename, "newwindowpeninsuladate");
		
		ArrayList<String> list=new ArrayList<String>();
		
		try
		{
			driver.get(URL);
			Thread.sleep(5000);
			
			List<WebElement> heading_list = driver.findElements(By.xpath(heading_xpath));
			for(int i=1;i<=heading_list.size();i++) 
			{
				 list=new ArrayList<String>();
				String newwindowdate="";
				String headingname = TestBase.waitForVisibility(driver, By.xpath("("+heading_xpath+")["+i+"]"), 10).getText();
				list.add(Integer.toString(i));
				list.add(headingname);
				//ExcelUtil.writeData(ResultFolder+FileName, SheetName, i, 0, Integer.toString(i));
				//ExcelUtil.writeData(ResultFolder+FileName, SheetName, i, 1, headingname);

				String date = TestBase.waitForVisibility(driver, By.xpath("("+heading_xpath+")["+i+"]"+date_xpath), 10).getText();
				String Date=splitarray(date);
				list.add(Date);
				Log.info("Heading Number "+i);
				Log.info("HeadingName="+headingname+" Date="+splitarray(date));
				//ExcelUtil.writeData(ResultFolder+FileName, SheetName, i, 2, Date);

				TestBase.waitForVisibility(driver, By.xpath("("+heading_xpath+")["+i+"]"+date_xpath+viewIndetail_xpath), 10).click();
				TestBase.switchwindow(driver, 1);
				Thread.sleep(2000);
				String titlename = driver.getTitle();
				list.add(titlename);
				//ExcelUtil.writeData(ResultFolder+FileName, SheetName, i, 3, titlename);
				if(driver.findElements(By.xpath(newwindowdate_xpath)).size()>0) {
					newwindowdate = TestBase.waitForVisibility(driver, By.xpath(newwindowdate_xpath), 5).getText();

				}
				else if(driver.findElements(By.xpath(newwindowpeninsuladate)).size()>0) {
					newwindowdate = TestBase.waitForVisibility(driver, By.xpath(newwindowpeninsuladate), 5).getText();	
				}
				String NewwindowDate=splitarray(newwindowdate);
				//ExcelUtil.writeData(ResultFolder+FileName, SheetName, i, 4, NewwindowDate);
				list.add(NewwindowDate);
				Log.info("NewWindowHeadingName="+titlename+" NewWindowDate="+splitarray(newwindowdate));
				if(headingname.equals(titlename) && Date.equals(NewwindowDate)) 
				{
					//ExcelUtil.writeData(ResultFolder+FileName, SheetName, i, 5, "Match");
					list.add("Match");
				}
				else {
					//ExcelUtil.writeData(ResultFolder+FileName, SheetName, i, 5, "Not Match");
					list.add("Not Match");
				}
				driver.close();
				TestBase.switchwindow(driver, 0);
				map.put(map_count, list);
				map_count++;
				System.out.println("list="+list);
				System.out.println("map="+map);
			}

		} catch (Exception e){
			map.put(map_count, list);
			map_count++;
			e.printStackTrace();
			
		}

		//		  String from="v-nishant.sharma@magicedtech.com";
		//		  
		//		  Mailer mail=new Mailer(); String to="v-nishant.sharma@magicedtech.com";
		//		  String emailPassword="Nishant#123"; String subject="Report Result"; String
		//		  messageText="This is report"; String attachment =
		//		  "./result/MyLab_report.xlsx"; mail.executionReportMail(from, to,
		//		  emailPassword, subject, messageText, attachment);
		writedata(FileName, SheetName);
		validatecell(FileName, SheetName);
	}
	public void writedata(String FileName, String SheetName) throws IOException {
		
		List<String> header=new ArrayList<String>();
		header.add("S.No");
		header.add("Heading Name");
		header.add("Date");
		header.add("New Window Heading Name");
		header.add("New Window Date");
		header.add("Status");
		ExcelUtil.writeAllData(map, header, ResultFolder+FileName, SheetName);
	}
	
	public static String splitarray(String s) {
		String value = null;
		if(s.contains("|"))
		{
			String []array=s.split("\\|");
			value=array[0].replace(",", "").trim();
			System.out.println();
		}
		else if(!s.contains("|")) 
		{
			String []array=s.split("2020");
			value=array[0]+"2020";
		}
		return value;
	}
	public void validatecell(String Filename,String SheetName) throws Exception
	{
		Map<Integer, String> status_columndata = ExcelUtil.readColumnData(ResultFolder+Filename, SheetName, "Status");
		for (int key1 : status_columndata.keySet())
		{
			String status_data = status_columndata.get((key1));
			if(status_data.equals("Not Match")) 
			{
				ExcelUtil.makecellRed(ResultFolder+Filename, SheetName, (key1+1), 5);
			}
		}
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now1 = LocalDateTime.now();
		String date_time1 = dtf1.format(now1);
		Log.info("**************"+date_time1+"**************");
	}
}

