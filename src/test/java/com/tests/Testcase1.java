package com.tests;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.utils.ReadExcel;
import com.utils.TestBase;

public class Testcase1 extends TestBase{
	
public Testcase1(){
		
		super();
	}
	
	@BeforeMethod
	
	public void setup(){
		
		initialization();
		
		
		
		SimpleDateFormat formDate = new SimpleDateFormat("yyyyMMdd");

	       
	       
	}
	
	@Test
	
	public void readfile(){
		
		ReadExcel.read();
	}

}
