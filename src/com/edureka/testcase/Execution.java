package com.edureka.testcase;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class Execution {


	Configuration configuration; 
	String url;
	String browser;
	WebDriver driver;
	String filePath;
	private DatasetDetail details;
	private int datasetRowIndex;

	public Execution(String url, String browser, String filePath) {
		this.url = url;
		this.browser = browser;
		this.filePath = filePath;
	}

	public boolean run(Outcome outcome) {

		ArrayList<TestScript> testScripts = outcome.getScript();

		for(int index = 0; index < testScripts.size(); index++){

			TestScript script = testScripts.get(index);
			for(int i = 0; i < script.size(); i++){
				Row row = testScripts.get(index).getRow(i);
				String object = row.getCellData(0);
				String value = row.getCellData(1);
				if(value.startsWith("{") && value.endsWith("}")){
					value = getValue(value);
				}
				String property = row.getCellData(2);
				String action = row.getCellData(3);

				System.out.println( object + "\t" + value + "\t" + property + "\t" + action );

				if( object.equalsIgnoreCase( "Execute" ) ) {
					StringTokenizer token = new StringTokenizer( value, ":" );
					String testScript = token.nextToken();
					String datasetName = token.nextToken();
					Dataset dataset = new Dataset( this.filePath );
					details = dataset.getData( datasetName );
					Excel excel = new Excel( this.filePath );
					Outcome datasetOutcome = excel.getScript(testScript);
					for( int j = 0; j < details.getValues().size(); j++ ){
						datasetRowIndex = j;
						this.driver.get(url);
						run(datasetOutcome);
					}
				} else if( object.equals("REFRESH") ) {
					this.driver.navigate().refresh();

				} else if( object.contains("VERIFY") ){
					StringTokenizer token = new StringTokenizer(object, ":");
					token.nextToken();
					String validator = token.nextToken().trim();
					if( validator.equals("CONTAINS") || validator.equals("TEXT")){

						String pageText = this.driver.findElement(By.tagName("body")).getText();
						if( pageText.contains( property ) )
							System.out.println("Pass");
						else
							System.out.println("Fail");

					} else if( validator.equals("VALUE") ){
						SeleniumAction seleniumAction = new SeleniumAction(this.driver, object, value, property, action);
						WebElement element = seleniumAction.findObject();
						String fetchedValue = element.getAttribute("value");
						if( fetchedValue.contains( value ) )
							System.out.println("Pass");
						else
							System.out.println("Fail");

					} else if( validator.equals("OBJECTEXIST") ){
						
						SeleniumAction seleniumAction = new SeleniumAction(this.driver, object, value, property, action);
						WebElement element = seleniumAction.findObject();
						String fetchedValue = "FALSE";
						if( element != null )
							fetchedValue = "TRUE";
						if( fetchedValue.equals( value ) )
							System.out.println("Pass");
						else
							System.out.println("Fail");

					} else if( validator.equals("TITLE") ) {

						String title = this.driver.getTitle();
						if( title.equals( value ) )
							System.out.println("Pass");
						else
							System.out.println("Fail");
					}


				} else if( object.equals("CAPTURESCREENSHOT") ) {
					Screenshot screen = new Screenshot(driver, this.filePath);
					screen.takeScreenshot();
				}
				else {
					SeleniumAction seleniumAction = new SeleniumAction(this.driver, object, value, property, action);
					seleniumAction.perform();
				}
			}

		}
		return true;

	}

	private String getValue(String value) {
		value = value.replace("{", "");
		value = value.replace("}", "");
		int index = details.getHeader().indexOf(value);
		return details.getValues().get(datasetRowIndex).get(index);	
	}

	public void startBrowser() {
		if( this.browser.equalsIgnoreCase("IE") ){
			IE ie = new IE();
			this.driver = ie.getDriver();
		} else if( this.browser.equalsIgnoreCase("Chrome") ) {
			Chrome chrome = new Chrome();
			this.driver = chrome.getDriver();
		} else {
			this.driver = new FirefoxDriver();
		}
		this.driver.get(this.url);
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		this.driver.manage().window().maximize();
	}

}
