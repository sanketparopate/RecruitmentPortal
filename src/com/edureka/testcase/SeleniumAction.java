package com.edureka.testcase;

import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SeleniumAction {

	private String object;
	private String value;
	private String property;
	private String action;
	WebDriver driver;
	
	public SeleniumAction(WebDriver driver, String object, String value, String property, String action) {
		this.object = object; 
		this.value = value; 
		this.property = property; 
		this.action = action;
		this.driver = driver;
	}

	public void perform() {
		WebElement webElement;
		switch(this.action){
		case "EnterText" :
			System.out.println("EnterText");
			webElement = findObject();
			webElement.sendKeys(this.value);
			break;
		case "Click" :
			System.out.println("Click");
			webElement = findObject();
			webElement.click();
			break;
			
		case "Select":
			System.out.println("Select");
			webElement = findObject();
			Select select = new Select(webElement);
			select.selectByVisibleText(this.value);
			break;
		
		default :
			System.out.println("No Action Found;");
		}

	}

	public WebElement findObject() {
		String[] properties = this.property.split(" = ");
		String attribute = properties[0].trim();
		String attributeValue = properties[1].trim();
		StringTokenizer token = new StringTokenizer(attributeValue, ":");
		attributeValue = token.nextToken().trim();
		while(token.hasMoreTokens()){			
			this.driver.switchTo().frame(token.nextToken().trim());
		}
		WebElement webElement = null;
		switch (attribute) {
		case "name" :
			webElement = this.driver.findElement(By.name(attributeValue));
			break;
		case "id" :
			webElement = this.driver.findElement(By.id(attributeValue));
			break;
		case "text" :
			webElement = this.driver.findElement(By.linkText(attributeValue));
			break;
		case "xpath" :
			webElement = this.driver.findElement(By.xpath(attributeValue));
			break;
		}
		return webElement;
	}

}
