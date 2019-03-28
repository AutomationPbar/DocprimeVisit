package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class TestBase {
	
	public static WebDriver driver;
	
	public static WebDriverWait wait;
	public static Properties prop;
	public ExtentReports extent;
	public ExtentTest extenttest;
	
	
	public TestBase(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream("C:\\Users\\Avani\\workspace\\DocPrimeVisitlog\\src\\main\\java\\com\\fw\\qa\\config\\config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void initialization(){
		String browserName = prop.getProperty("browser");
		
			
		if(browserName.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\main\\java\\com\\fw\\qa\\config\\chromedriver.exe");	
			driver = new ChromeDriver(); 
		}
		else if(browserName.equals("FF")){
			System.setProperty("webdriver.gecko.driver", "");	
			driver = new FirefoxDriver(); 
		}
		
		wait = new WebDriverWait(driver,40);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("baseurl"));	
		
		
	}
		

	
	
	
	public void extentreport(){
		extent = new ExtentReports(System.getProperty("user.dir")+"/test-output/ExentReport.html", true);
		extent.addSystemInfo("Host Name", "Avani PC");
		extent.addSystemInfo("Env", "QA");
		extent.addSystemInfo("Tester", "Avani");
		System.out.println("Extent Report setup done");
	}

public void endreport(){
		
		extent.flush();
		extent.close();
	}
public static String getscreenshot(WebDriver driver,String scrname) throws IOException{
	
	String Datename = new SimpleDateFormat("yyyymmdd").format(new Date());
	File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + scrname + Datename
			+ ".png";
	File finalDestination = new File(destination);
	FileUtils.copyFile(scr, finalDestination);
	return destination;
}

public void extentteardown(ITestResult result ) throws IOException{
	
	if(result.getStatus()==ITestResult.FAILURE){
		
		extenttest.log(LogStatus.FAIL, "Test case failed is -- "+result.getName());
		extenttest.log(LogStatus.FAIL, result.getThrowable());
		
		String scrpath = TestBase.getscreenshot(driver,result.getName());
		extenttest.log(LogStatus.FAIL, extenttest.addScreenCapture(scrpath));
	}else if(result.getStatus()==ITestResult.SKIP){
		
		extenttest.log(LogStatus.SKIP, "Test case skipped is -- "+result.getName());
	}else if (result.getStatus()== ITestResult.SUCCESS){
		
		extenttest.log(LogStatus.PASS, "Test Case Passed is --" + result.getName());
	}
	
	extent.endTest(extenttest);
	
	
}

}
