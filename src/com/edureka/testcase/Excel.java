package com.edureka.testcase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

	String filePath = "";

	public Excel( String filePath ) {
		this.filePath = filePath;
	}
	
	

	public Outcome getScript(String testCases) {

		try {
			FileInputStream fs = new FileInputStream( this.filePath );
			XSSFWorkbook workbook = new XSSFWorkbook(fs);
			
			StringTokenizer token = new StringTokenizer(testCases, ",");
			
			Outcome outcome = new Outcome();
			while(token.hasMoreTokens()){
				
				String testCase = token.nextToken().trim();
				
				XSSFSheet sheet = workbook.getSheet(testCase);
				
				int totalRow = sheet.getPhysicalNumberOfRows();
				int totalColumn = sheet.getRow(0).getLastCellNum();
				
				TestScript script = new TestScript();
				DataFormatter df = new DataFormatter();
				
				for(int r = 1; r < totalRow; r++) {
					XSSFRow row = sheet.getRow(r);
					
					Row rowData = new Row();
					for(int col = 0; col < totalColumn; col++) {
						
						String cellData = df.formatCellValue(row.getCell(col));
						rowData.addCellData(cellData);
					}
					script.addRow(rowData);
				}
				outcome.setStatus(true);
				outcome.setScript(script);
			}			
			return outcome;
		} catch (FileNotFoundException e) {
			return new Outcome();
		} catch (IOException e) {
			return new Outcome();
		}
	}

	public Outcome getconfiguration() {

		try {
			FileInputStream fs = new FileInputStream( this.filePath );
			XSSFWorkbook workbook = new XSSFWorkbook(fs);
			XSSFSheet sheet = workbook.getSheet("Configuration");
			
			
			int totalRow = sheet.getPhysicalNumberOfRows();
			int totalColumn = sheet.getRow(0).getLastCellNum();
			
			TestScript script = new TestScript();
			DataFormatter df = new DataFormatter();
			
			for(int r = 1; r < totalRow; r++) {
				Row rowData = new Row();
				
				XSSFRow row = sheet.getRow(r);
				
				for(int col = 0; col < totalColumn; col++) {
					
					String cellData = df.formatCellValue(row.getCell(col));
					rowData.addCellData(cellData);
					
				}
				script.addRow(rowData);
			}
			
			Outcome outcome = new Outcome();
			outcome.setStatus(true);
			outcome.setScript(script);
			return outcome;
		} catch (FileNotFoundException e) {
			return new Outcome();
		} catch (IOException e) {
			return new Outcome();
		}
	}
}
