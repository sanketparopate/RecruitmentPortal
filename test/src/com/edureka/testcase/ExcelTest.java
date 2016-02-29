package com.edureka.testcase;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ExcelTest {

	@Test
	public void getTestCaseDataFromExcel() {
		String filePath = "D:\\Project\\TestScript.xlsx";
		Excel excel = new Excel( filePath );
		Outcome outcome = excel.getScript("Facebook");
		Assert.assertTrue(outcome.isSuccessful());
	}

	@Test
	public void checkSheetIsNotBlank(){
		String filePath = "D:\\Project\\TestScript.xlsx";
		Excel excel = new Excel( filePath );
		Outcome outcome = excel.getScript("Facebook");
		System.out.println(outcome.getTotalNumberOfRows());
		Assert.assertEquals(25, outcome.getTotalNumberOfRows());
	}

	@Test
	public void getConfiguration(){
		String filePath = "D:\\Project\\TestScript.xlsx";
		Excel excel = new Excel( filePath );
		Outcome outcome = excel.getconfiguration();
		Assert.assertTrue(outcome.isSuccessful());
	}
}
