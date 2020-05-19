package com.magic.utils;

import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;



public class Driver {
    public static WebDriver dvr;

    public static WebDriver getDriver() throws Exception {

	String browser = PropertyUtil.getproperty("./config/config", "browser");

	switch (browser) 
	{
	case "firefox":
	    System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
	    dvr = new FirefoxDriver();
	    break;

	case "chrome":

	    ChromeOptions options = new ChromeOptions();
		options.addArguments("--allow-running-insecure-content");
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		dvr = new ChromeDriver(options);
		break;

	case "ie":

	    System.setProperty("webdriver.ie.driver", "./drivers/internetexplorerdriver.exe");
	    dvr = new InternetExplorerDriver();
	    break;

	default:
	    break;
	}

	dvr.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	dvr.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
	dvr.manage().window().maximize();
	return dvr;

    }

}
