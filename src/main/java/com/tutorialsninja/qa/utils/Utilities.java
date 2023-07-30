package com.tutorialsninja.qa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class Utilities {

	public static final int IMPLICIT_WAIT_TIME = 15;  //we made this final so that this value cannot be changed when user try to update it from any other class. also used static so that it can be easily used with the help of this uti;ities class in any other class.
	public static final int PAGE_LOAD_TIME=15;                                          
	
	
	
	public static String generateEmailWithTimeStamp() {  //->Make this method as "Static" to access this method as static method can be accessed by using class name anywhere.
		Date date = new Date();
		String timestamp= date.toString().replace(" ", "_").replace(":", "_");
		return "Valid1"+timestamp+"@gmail.com";
	}
	
	
	public static Object[][] getTestDataFromExcel(String sheetName) {// Here based upon sheet name I am accepting the data from excel file
		File excelFile = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\tutorialsninja\\qa\\testdata\\TutorialsNinjaTestData.xlsx");
		XSSFWorkbook workbook = null;    //here declared XSSFWorkbook workbook globally so that we can use this outside the try catch block hence instead of writing--> XSSFWorkbook workbook= new XSSFWorkbook --> written workbook= new XSSFWorkbook
		try {
		FileInputStream fisExcel = new FileInputStream(excelFile);
		workbook = new XSSFWorkbook(fisExcel); //Here we have created object for XSSFWorkbook but we unable to import it until we add POI dependency in pom.xml so add it then import this
		}catch(Throwable e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = workbook.getSheet(sheetName);  //Here pass the sheetName that you passed on the first line i.e in the method parameter when we hover on the getsheet it returns object of XSSFSheet so write sheet and hover on it and select create local variable
		int rows = sheet.getLastRowNum(); //So in above line we get a sheet name and in this line we have found total number of rows on the particular sheet
		int cols = sheet.getRow(0).getLastCellNum(); //here 1st we selected the row number u can give any number (i.e 0/1/2) the we getting last cell number so means on the particular row we start i.e from 0 to the last cell number of that row will denotes total columns so for login we get total 2 column count as on login sheet we only have 2 columns 
	    Object[][] data = new Object[rows][cols];  //Here we have created 2D array to pass 2 parameters rows and columns (Eg: email and pass)
	
	    for(int i=0; i<rows; i++) {
	    	
	    XSSFRow row = sheet.getRow(i+1); //Here i+1 because we need to reed the data from line 2 in excel sheet because in row 1 we have just provided column name not actual data. Also when we hover on getROw it returns XSSFROW so write XSSFRow row.
	    
	    for(int j=0; j<cols; j++) {
	    	
	    	XSSFCell cell = row.getCell(j); // Here row.getcell because on i+1th row we have to get number of cells to get the column count
	    
	    	CellType cellType = cell.getCellType(); //used this because in our xcel sheet email is in string format and pwd is in numeric format so to handle these cases
	    	
	    	switch(cellType) {
	    	
	    	case STRING:
	    		data[i][j] = cell.getStringCellValue();
	    		break;
	    		
	    	case NUMERIC:
	    		data[i][j] = Integer.toString((int)cell.getNumericCellValue()); //--> here 1st we used -> cell.getNumericCellValue() but this will not give pure numeric value it will give 123.00 so we type casrted it to give integer value (i.e 123 )-->(int)cell.getNumericCellValue();--> Now agian as we have to pass the data in string format so to convert this into string used-> 
	    		break;
	    		
	    	case BOOLEAN:
	    		data[i][j] = cell.getStringCellValue();
	    		break;
	    	}
	    
	    }
	    
	    }
	
	    return data; 
	}
	
	public static String captureScreenshot(WebDriver driver, String testName) {
		
		File srcScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);  /**Here this event fire when one of the test class method fails so we need to get the driver from that failed method into this method into the result parameter so for that we userd result and above code also in each class make WebDriver driver as public other wise they cannot be accesses and we get null pointer exception*/
		String destinationScreenshotPath = System.getProperty("user.dir")+"\\Screenshots\\"+testName+".png"; /**Here first manually create a folder for screenshot then we have provided path to store the file to the given destination and we use testName as name of the scrrnshot and provided pnj extension*/
		
		try {
			FileHandler.copy(srcScreenshot, new File(destinationScreenshotPath));     		/**Here to handle the screenshot file i.e copy from source to destination we used fileHandler class provide by selenium*/
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destinationScreenshotPath;
		
	}
	
	
}
