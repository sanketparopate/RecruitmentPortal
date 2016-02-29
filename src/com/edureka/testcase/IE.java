package com.edureka.testcase;

import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class IE {

	public WebDriver getDriver() {

		File file = new File("driver/IEDriverServer.exe");		
		System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		WebDriver driver = new InternetExplorerDriver();
		return driver;
	}

}
