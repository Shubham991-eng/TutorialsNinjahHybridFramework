/**
 * 
 */
package com.tutorialsninja.qa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * @author Shubham Sonwane
 *
 */
public class ExtentReporter {
	
	
	public static ExtentReports generateExtentReport() {
		
		
		ExtentReports  extentReport = new ExtentReports();
		File extentReportFile = new File(System.getProperty("user.dir")+"\\test-output\\ExtentReports\\extentReport.html");
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportFile);
		
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setReportName("TutorialsNinja Test Automation Results");
		sparkReporter.config().setDocumentTitle("TN Automation Report"); /**here we give our report page title*/
		sparkReporter.config().setTimeStampFormat("dd/MM/yyyy hh:mm:dd");
		
		extentReport.attachReporter(sparkReporter);                           /**here to the object of extentRport class we have to attach a type of reporter so as here we are using sparkReporter so attach it so now all the above configurations added for sparkReporter ae applier to extentReporter.*/
		
		/**So here we have to add some more information into the extent reportes like application URL, browserName, operating system, username, java version, email address, password.
		 *  we can see all this on extent reportLast option denoted by bar chart */
		
		
		Properties configProp = new Properties();
		File  configPropFile = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\tutorialsninja\\qa\\config\\config.properties");
		try{
			FileInputStream fisConfigProp = new FileInputStream(configPropFile);
			configProp.load(fisConfigProp);
		}catch(Throwable e) {
			e.printStackTrace(); 
		}
		
		extentReport.setSystemInfo("Application URL", configProp.getProperty("url"));     /** In this we have provided key value pair so to get URL we need to use properties prop see above:*/
		extentReport.setSystemInfo("BrowserName", configProp.getProperty("browserName"));
		extentReport.setSystemInfo("Email", configProp.getProperty("validEmail"));
		extentReport.setSystemInfo("Password", configProp.getProperty("validPassword"));
		
		/** ===============================================================================================
		 * below to get operating system, java version version in extent report  we have created a package -->experiment -->
		 * in this cerate a demo class with main method and in it we have to use this code -->System.getProperties().list(System.out);
		 * Once we run this demo class, in the console we get all the data once we pasted the required data from the console into this class delete that package and demo class  below is the data that we got in the console once we run demo class and these are properties and their values
		 * I have only took below 3information see below:===============================================================================================>
		 * 
		 * user.name=HP
		 * os.name=Windows 10
		 * java.version=17.0.6
		 * 
		 * ==> Now in the main method remove System.getProperties().list(System.out); and write below code and run
		 *   System.out.println(System.getProperty("os.name"));
			 System.out.println(System.getProperty("user.name")); //gives machine name
		     System.out.println(System.getProperty("java.version"));
		  =>After running this we get:
		                              Windows 10
                                      HP
                                      17.0.6
                                      
          these are nothing but values of the keys we have written in above print statements 
		
		 * below we have use these 3 properties
		 *============================================================================================================= */
		
		 extentReport.setSystemInfo("Operating system", System.getProperty("os.name"));
		 extentReport.setSystemInfo("Username", System.getProperty("user.name"));
		 extentReport.setSystemInfo("Java Version", System.getProperty("java.version"));
	
		 
		 return extentReport;
		 
		 /**Now got to Listeners class and instead of using system.out.println call this extent report on every action i.e onStart, Onsuccess onFailure */
	}

}
