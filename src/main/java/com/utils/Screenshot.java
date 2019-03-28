package com.utils;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Screenshot extends TestBase {

	public static void takescreenshot(String visitid){
	try{
		
		String Datename = new SimpleDateFormat("yyyymmdd").format(new Date());
		File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" +visitid+ ".jpg";
		File finalDestination = new File(destination);
		FileUtils.copyFile(scr, finalDestination);
		
	}catch(Exception e){
		
	}

	
}
}