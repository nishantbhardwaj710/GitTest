package com.magic.utils;

import java.io.IOException;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.magic.common.TestBase;



public class ResultListener extends TestBase implements ITestListener {

	@Override
	public void onStart(ITestContext context) 
	{
		DOMConfigurator.configure("log4j.xml");			
	}

	@Override
	public void onTestStart(ITestResult result) 
	{
		Log.startTestCase(getTestMethodName(result));	
	}

	@Override
	public void onTestSuccess(ITestResult result) 
	{
		Log.endTestCase(getTestMethodName(result));
		test.log(Status.PASS, MarkupHelper.createLabel(getTestMethodName(result)+": Test Case Passed: ", ExtentColor.GREEN));
		test.pass("Test passed");
	}

	@Override
	public void onTestFailure(ITestResult result) 
	{
		//Log.info(getTestMethodName(result)+ " Test failed");
		Log.error("Test failed due to : ", result.getThrowable());
		test.log(Status.FAIL, MarkupHelper.createLabel(getTestMethodName(result)+": Test Case Failed due to below issues: ", ExtentColor.RED));
		test.fail(result.getThrowable());
		String screenShotPath = "";
		try {
			screenShotPath = TestBase.getScreenshot(driver, getTestMethodName(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			test.addScreenCaptureFromPath(screenShotPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getTestMethodName(ITestResult result) 
	{
		return result.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void onTestSkipped(ITestResult result) 
	{
		Log.info(getTestMethodName(result)+ " test case skipped");
		test.log(Status.SKIP, MarkupHelper.createLabel(getTestMethodName(result)+": Test Case Skipped: ", ExtentColor.ORANGE));
		test.skip(result.getThrowable());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) 
	{
		// TODO Auto-generated method stub		
	}	

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}
