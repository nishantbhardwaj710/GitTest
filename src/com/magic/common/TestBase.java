package com.magic.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.magic.utils.Driver;
import com.magic.utils.PropertyUtil;



public class TestBase {

	protected static long startTime ;
	protected static long endTime ;	
	protected static  WebDriver driver;
	public static ExtentReports extent;
	public static ExtentTest test;
	static String configfilename="config/config";

	static String reportPath = System.getProperty("user.dir")+"/extent_reports/ExtentReport.html";

	private static Logger log=Logger.getLogger(TestBase.class.getName());

	static
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
		System.setProperty("current.date", dateFormat.format(new Date()));
	}


	@BeforeSuite
	public void beforesuite() throws Exception
	{
		extent = createInstance(reportPath);
	}

	@BeforeTest
	public void beforetest() throws Exception
	{

	}

	@BeforeClass
	public void beforeclass() throws Exception
	{
		startTime = System.currentTimeMillis();
		this.driver = Driver.getDriver();
	}

	@AfterClass
	public void afterclass() throws Exception
	{	
		//driver.quit();
	}

	@AfterTest
	public void aftertest() throws Exception
	{

	}

	@AfterSuite
	public void aftersuite() throws Exception
	{
		endTime =  System.currentTimeMillis();
	}

	public static ExtentReports createInstance(String fileName) throws Exception 
	{
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle("AUTOMATION REPORT");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("US Chamber of Commerce AUTOMATION REPORT");
		String browser = PropertyUtil.getproperty(configfilename, "browser");


		extent = new ExtentReports();
		extent.setSystemInfo("OS Name:", System.getProperty("os.name"));
		extent.setSystemInfo("OS Version:", System.getProperty("os.version"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Browser", browser);
		extent.attachReporter(htmlReporter);

		return extent;
	}

	/*Action class methods*/

	public static void mouseHover(String elementXpath, WebDriver driver)
	{
		Actions act=new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath(elementXpath))).build().perform();
	}

	public static void moveToelementAndClick(String elementXpath, WebDriver driver)
	{
		Actions act=new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath(elementXpath))).click().build().perform();
	}


	/*Select class methods*/

	public static void selectbyText(String elementXpath, String text, WebDriver driver)
	{
		Select sel=new Select(driver.findElement(By.xpath(elementXpath)));
		sel.selectByVisibleText(text);
	}

	public static void selectbyIndex(String elementXpath, int index, WebDriver driver)
	{
		Select sel=new Select(driver.findElement(By.xpath(elementXpath)));
		sel.selectByIndex(index);
	}

	public static void selectbyvalue(String elementXpath, String value, WebDriver driver)
	{
		Select sel=new Select(driver.findElement(By.xpath(elementXpath)));
		sel.selectByValue(value);
	}


	/*driver wait methods*/

	public static void waitforElementVisible(String elementXpath, WebDriver driver, long time)
	{
		WebDriverWait wd=new WebDriverWait(driver, time);
		wd.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(elementXpath)));
	}

	public static void waitforElementInvisible(String elementXpath, WebDriver driver, long time)
	{
		WebDriverWait wd=new WebDriverWait(driver, time);
		wd.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(elementXpath)));
	}

	public static void waitForElementPresence(WebDriver driver, String xpath, int waitInSeconds) 
	{
		waitIgnoringStale(driver, waitInSeconds).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

	}

	@SuppressWarnings("deprecation")
	public static FluentWait<WebDriver> waitIgnoringStale(WebDriver driver,int waitInSeconds) 
	{
		return new WebDriverWait(driver, waitInSeconds).ignoring(StaleElementReferenceException.class).pollingEvery(500, TimeUnit.MILLISECONDS);
	}

	public static void waitforElementClickable(WebDriver driver, long time, String element)
	{
		WebDriverWait wait=new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(element))));
	}
	public static boolean checkIfElementIsClickable(String element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 7);
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(element))));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void changeImplicitWaits(WebDriver driver,int waitInSeconds) 
	{
		driver.manage().timeouts().implicitlyWait(waitInSeconds, TimeUnit.SECONDS);
	}

	/*Javascript methods*/

	public static void scrollDownViewElement(WebDriver driver, String xpath)
	{
		WebElement element = driver.findElement(By.xpath(xpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static void scrollUpViewElement(WebDriver driver, String xpath)
	{
		WebElement element = driver.findElement(By.xpath(xpath));
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("scroll(0,-2000);",element);
	}

	public static void clickonElement(WebDriver driver, WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	/*Alert Handling*/

	public static void handleAlert(WebDriver driver)
	{
		Alert alr=driver.switchTo().alert();
		alr.accept();
	}

	public static void waitforAlert(WebDriver driver, long time)
	{
		WebDriverWait wait=new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.alertIsPresent());
	}

	/*Window Handling*/

	public static int countWindow(WebDriver driver)
	{
		Set<String> windowHandle = driver.getWindowHandles();
		int count = windowHandle.size();		
		return count;		
	}

	public static void switchwindow(WebDriver driver,int windowno)
	{		
		Set<String> window=driver.getWindowHandles();
		driver.switchTo().window(window.toArray()[windowno].toString());		
	}

	/*File Handling*/

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList getfileNamesFromFolder(String path) 
	{
		ArrayList listOfFilesArray = new ArrayList<>();

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if(listOfFiles[i].isFile()) 
			{
				System.out.println("File " + listOfFiles[i].getName());
				listOfFilesArray.add(listOfFiles[i].getName());
			}
			else if(listOfFiles[i].isDirectory()) 
			{
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
		System.out.println(listOfFilesArray);
		return listOfFilesArray;
	}

	/*Screenshot*/

	public static void captureScreenShot(WebDriver driver, String filename) throws IOException
	{
		File file=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("./screenshots/"+filename+"_"+System.getProperty("current.date")+".png"));

	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception 
	{
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir")+"/failedtestsScreenshots/"+screenshotName+"_"+System.getProperty("current.date")+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}
	public void sendkeys(String value,WebElement element)
	{
		((JavascriptExecutor)driver).executeScript("arguments[0].setAttribute('value','"+value+"')",element);
	}
	public static final WebElement waitForElementPresence(WebDriver driver, By findByCondition, int waitInSeconds) {
		waitIgnoringStale(driver, waitInSeconds).until(ExpectedConditions.presenceOfElementLocated(findByCondition));
		return driver.findElement(findByCondition);
	}

	public static WebElement waitForClickable(WebDriver driver,By findByCondition,int waitInSeconds){
		waitIgnoringStale(driver, waitInSeconds).until(ExpectedConditions.elementToBeClickable(findByCondition));
		return driver.findElement(findByCondition);		
	}

	public static WebElement waitForVisibility(WebDriver driver,By findByCondition,int waitInSeconds){
		waitIgnoringStale(driver, waitInSeconds).until(ExpectedConditions.visibilityOfElementLocated(findByCondition));
		return driver.findElement(findByCondition);
	}
	public static void opennewTab(WebDriver driver) throws IOException
	{
		//To open a new tab         
		((JavascriptExecutor)driver).executeScript("window.open('about:blank', '-blank')");		
	}
	
}