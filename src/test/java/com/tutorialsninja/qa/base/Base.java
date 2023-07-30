package com.tutorialsninja.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.tutorialsninja.qa.utils.Utilities;

public class Base { // to make this as parent class use --> extends Base--> In the required class or create constructor to that class and use super keyword so that that class constructor will point to this base (parent) class constructor written on line no.22

    WebDriver driver; //-->declared this globally 
    public Properties prop; //-->declared this globally and make prop public so that this can be accessed anywhere in the child class or other classes
    public Properties dataProp; //->//-->declared this globally and make prop public so that this can be accessed anywhere in the child class or other classes
 
    public Base() { //--> before initializeBrowserAndOpenApplication() method, this constructor should be executed (as its name we used same as class name and removed the return type void-->so it becomes constuctor now-->previosly we created this as method named as ->loadPropertiesFile(); which is also correct but now we make this to constructor and we created one class in login and used super keyword (denotes constructor of parent class (base class) ) so that login class can access the this constructor
    	
    	prop = new Properties();  //(1st step)create object of properties file
    	File propFile = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\tutorialsninja\\qa\\config\\config.properties");//--> (4th step)System.getProperty("user.dir") -->Gives the path of the project->now to go till congig.prop file -->added further path by concatination i.e +"\\src\\main\\java\\com\\tutorialsninja\\qa\\config\\config.properties"
    	
        dataProp = new Properties();
    	File dataPropFile = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\tutorialsninja\\qa\\testdata\\testdata.properties");
    	try {
	    	FileInputStream dataFis= new FileInputStream(dataPropFile);
	    	dataProp.load(dataFis);
    	}catch(Throwable e) {
    		e.printStackTrace();
    	}
 
    	//******Below code is for loading cofig.prop file// and above is to load testdata.prop file*****************
    	try {
	    	FileInputStream fis = new FileInputStream(propFile); //-->(3rd step)here FileInputStream() constuctor expect a file so above code is for that
	    	prop.load(fis); //-> (2st step)But the load method of this properties class is expecting Inputstream so above code is for that
    	}catch(Throwable e) {             //(3rd and 2nd steps gives some checked exceptions so surround them with try catch block
    	      e.printStackTrace();        //We can also use parent "Exception" but we used "Throwable" as its grand parent of checked exception so if the properties file path is not accessible we get an exception which is handled by try and catch block
    	}
    }
	
   
	public WebDriver initializeBrowserAndOpenApplication(String browserName) { // Here I received browserName as parameter. also instead of void return type should be Webdriver to make this method reusable
	
		//String browserName = "edge";   //--> commented this line becoz we are getting error as we already used browserName as parameter  we can also remove this line
		
		
		if(browserName.equalsIgnoreCase("chrome")) {//previously we have used if(browserName.equals("chrome") but sometime if we make any mistake while providing the borwserName in config file then the browser will not load so to avoid capital small case sensitivity use equalsIgnore instead of equals 
			driver = new ChromeDriver();
		
		}else if(browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
			
		}else if(browserName.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
			
		}else if(browserName.equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Utilities.IMPLICIT_WAIT_TIME));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Utilities.PAGE_LOAD_TIME));//the page has to load in the 5 sec only otherwise test will fail.
		//driver.get("https://tutorialsninja.com/demo/");  // To open the Website -->Instead of using this hard coded url use below 
		driver.get(prop.getProperty("url")); //--> in double quotes provide the name of property we want from config.prop file
			
		return driver;
	}
}
