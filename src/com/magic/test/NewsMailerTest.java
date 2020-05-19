/**
 * 
 */
package com.magic.test;


import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.magic.common.TestBase;
import com.magic.pages.NewsMailer;
import com.magic.utils.ResultListener;

@Listeners(ResultListener.class)
public class NewsMailerTest extends TestBase 
{

	@Test(priority=1)
	public void runtest() throws Exception 
	{
		
		test = extent.createTest("News Mailer","This test is verify Mailer News and Date Test");
		test.assignCategory("USChamberofCommerce");
		NewsMailer tb=new NewsMailer(driver);
		tb.datareading(test);
	}
}
