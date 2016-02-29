package com.edureka.testcase;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ExecutionTest {

	@Test
	public void executeTestScriptReturnTrueIfCompleted() {
		
		String filePath = "D:\\Project\\TestScript.xlsx";
		
		Excel excel = new Excel( filePath );
		
		Outcome configurationDetails = excel.getconfiguration();
		
		Settings settings = new Settings();
		Configuration configuration = settings.setSeleniumSettings(configurationDetails);
		
		String testCases = configuration.getValueOf("TestCase");
		String url = configuration.getValueOf("URL");
		String browser = configuration.getValueOf("Browser");

		Outcome outcome = excel.getScript(testCases);
		
		Execution execution = new Execution( url, browser, filePath );
		execution.startBrowser();
		Assert.assertTrue(execution.run( outcome ));
		
		execution.driver.quit();
	}
}
