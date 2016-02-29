package com.edureka.testcase;

import java.util.ArrayList;

public class Configuration {
	
	ArrayList<String> variables = new ArrayList<>();
	ArrayList<String> values = new ArrayList<>();

	public void setSettings(String cellData) {
		this.variables.add(cellData);
		
	}

	public void setValue(String cellData) {
		this.values.add(cellData);
		
	}

	public String getValueOf(String testCase) {
		int index = this.variables.indexOf(testCase);
		return this.values.get(index);
	}

}
