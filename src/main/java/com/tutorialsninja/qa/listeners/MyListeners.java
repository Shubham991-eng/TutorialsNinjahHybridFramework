/**
 * 
 */
package com.tutorialsninja.qa.listeners;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.tutorialsninja.qa.utils.ExtentReporter;
import com.tutorialsninja.qa.utils.Utilities;

/**
 * @author Shubham Sonwane
 *
 */
public class MyListeners implements ITestListener{
	
	ExtentReports extentReports;
	ExtentTest extentTest;
	//String testName;   Instead of declaring it globally we can directly use its code in place of testName, i.e result.getName
	
	
	@Override
	public void onStart(ITestContext context) {
		/**System.out.println("Execution of Project Tests started"); 		 Now instead of using system.out.println call this extent report on every action by using its class name i.e onStart, Onsuccess onFailure */
		 extentReports = ExtentReporter.generateExtentReport();
		
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		
		//testName = result.getName();                    /**Here to optimize the code we can globally declare String testName and only just in this method we need to use testName = result.getName();  and from other methods we can delete this line */
		extentTest = extentReports.createTest(result.getName());  //instead of using above line we can directly write the code in place of testName, i.e result.getName 
		extentTest.log(Status.INFO, result.getName()+" started executing"); /** Here in the 2nd parameter we have pasted the same line we user in sytem.out.println()  to print the message in extent report log*/
		
		/**System.out.println(testName+" started executing"); Used above line code as this statement I don't want to print in the console rather I want to print it in the extent report*/
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		//extentTest = extentReports.createTest(testName); don't need the as in above listener we used it and declared its object globally 
		
		extentTest.log(Status.PASS, result.getName()+" got successfully executed"); // here also we pasted same line in 2nd parameter that we used in syso.out.printLn()
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
	
		
		WebDriver driver = null;   /**Here we have give null so that in this we store the driver of tes classes*/
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance()); /**Here getInstance method gives thedriver in object format so we need to typecast it see on left side*/
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {          /**Here as this method is giving some checked exceptions so we surronded it wit the try catch block.*/  
			
			e.printStackTrace();
		} 
		
		String destinationScreenshotPath = Utilities.captureScreenshot(driver, result.getName()); /** Here in 2nd parameter instead of writing testName we used result.getName() and here this method returns the path so give the destination*/
		
/**=======================Here below is code for the screenshot so instead of using it here place it in some other class method and call that method by class name see above code ===============================*/
//		File srcScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);  /**Here this event fire when one of the test class method fails so we need to get the driver from that failed method into this method into the result parameter so for that we userd result and above code also in each class make WebDriver driver as public other wise they cannot be accesses and we get null pointer exception*/
//		String destinationScreenshotPath = System.getProperty("user.dir")+"\\Screenshots\\"+result.getName()+".png"; /**Here first manually create a folder for screenshot then we have provided path to store the file to the given destination and we use testName as name of the scrrnshot and provided pnj extension*/
//		
//		try {
//			FileHandler.copy(srcScreenshot, new File(destinationScreenshotPath));     		/**Here to handle the screenshot file i.e copy from source to destination we used fileHandler class provide by selenium*/
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
		
/**========================Paste the above code in the utility class and just call it here by class name===============================================*/		                                                                   /**Once the test got fail to take screenshot we written above code but here we don't have driver so for that we done some extra code so that we can access the driver in this class */
	
         extentTest.addScreenCaptureFromPath(destinationScreenshotPath);    /**Here we written a code to add this screenshot with extent reports*/              
         extentTest.log(Status.INFO, result.getThrowable());        /** here in 2nd parameter we have pasted this print satement=>  System.out.println(result.getThrowable());*/
         extentTest.log(Status.FAIL, result.getName()+" got failed");
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		extentTest.log(Status.INFO, result.getThrowable());
		extentTest.log(Status.SKIP, result.getName()+" got skipped");
	
	}

	

	@Override
	public void onFinish(ITestContext context) {
		extentReports.flush();  /**Here if we don't use this line at end, nothing will be added to the test report so its very important*/
		String pathOfExtentReport = System.getProperty("user.dir")+"\\test-output\\ExtentReports\\extentReport.html";
		File extentReport = new File(pathOfExtentReport);	 /** Here we have written the code for automatically launching the extent reports once all test execution is finished here its ask for URi not URL so 1st get path of the extentreports see above code*/
	    try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

