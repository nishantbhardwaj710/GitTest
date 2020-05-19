package com.magic.test;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestNG;
import org.testng.annotations.Test;

public class TestDriver {

	public static void main(String[]args) 
	{
		ArrayList<String> list=new ArrayList<String>();
		TestNG run=new TestNG();
		list.add("testng.xml");
		run.setTestSuites(list);
		run.run();
	}
}
