package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.AccountPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.LoginPage;
import com.tutorialsninja.qa.utils.Utilities;


public class LoginTest extends Base {
	public WebDriver driver;
	LoginPage loginPage;
	
	public LoginTest(){
		super();
	}
	
	/*
	 * public Login() { // Above I have created one more constructor (as its name is
	 * same as class name also it dont's have return type super(); // this super
	 * keyword points to the parent class (base class) constructor. }
	 */

	@BeforeMethod
	public void setUp() {
		
		//loadPropertiesFile(); --> so either we can call this method of base class to execute this before initializeBrowser() method or we can just change the name of  loadPropertiesFile()--> method to same as class name like Base and we can all that construction here and we use "super" keyword by creating a method public login() which denotes constructor from parent class i.e it denotes the prop file constructor from base class see above 
		
		//initializeBrowserAndOpenApplication(String browserName) --> //If we use this line we get error so in argument type instead of
		                                                            //passing String browser name as argument just pass "chrome" also we get error becoz this method is not a part f this class its part of base class so use extends keyword and 
		                                                            //write Base so now all the properties from base class is inherited into this class also when we
		                                                            //Hover on initializeBrowserAndOpenApplication("chrome") will return us object of webdriver so write 
		                                                            // driver = initializeBrowserAndOpenApplication("chrome")
		
		//driver = initializeBrowserAndOpenApplication("chrome"); --> Instead of this use property name from config.prop file see below:
		driver = initializeBrowserAndOpenApplication(prop.getProperty("browserName"));
		HomePage homePage = new HomePage(driver);  //In the parameter of the homepage class constructor object we have to pass driver of this class so that it can be assigned to homepage constructor and then to the constructor of Homepage class.
		loginPage = homePage.navigateToLoginPage();       //Here we know that clicking on login option take us to login page hence in below @test we have created the object of login page so to reduce that line just go to homePage where seleect login method is written then add its return type to login page and now hover on this line (line no.41) and u will see it return the object of login page so select it and  create a local variable then declare this globally and now u can remove LoginPage logiPage = new LoginPage(driver) line from every @text method do this optimization same for other test as well 
	
	}
	
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority=1, dataProvider="validCredentialsSupplier")// Here Either we can provide dataProvider method name(I.e=> supplyTestData)  or data provider name (I.e=>validCredentialsSupplier)
		public void verifyLoginWithValidCredentials(String email, String password) {//Here we have provided the email with the help of data provider so now this test will run 3 times as we have provided 3 email and passwords
			
			//LoginPage loginPage = new LoginPage(driver); -->I have removed this line becoze homePage returns object of logiPage so this line is not required read line no.40 
			
			AccountPage accountPage = loginPage.login(email, password);
			Assert.assertTrue(accountPage.getDisplayStatusOfEditYourAccountInformationOption(),"Edit your account informat option is not displayed");//now to check whether this user is on login page verify any of the text on login page
					
		}
	
	@DataProvider(name = "validCredentialsSupplier")            //this will supply data to @Test(priority=1) method and currently it is hard coded																				
	public Object[][] supplyTestData() {
	      // Object [][] data = {{"Valid@gmail.com","Valid@123"},{"Rahul@gmail.com","Valid@123"},{"Ajinkya@gmail.com","Valid@123"}};
         Object [][] data = Utilities.getTestDataFromExcel("Login"); //intsead of using above line data we just simply provided the excelreader method name from test utilities class and as its static so called it by the class name utilities.
	 return data;
	}
	
	
	@Test(priority=2)
	public void verifyLoginWithInvalidCredentials() {
	
		//driver.findElement(By.id("input-email")).sendKeys("abc142"+generateTimeStamp()+"@gmail.com");//--> added timestamp method 
		//OR use Below code is more easy i.e. call the timestamp method in sendkeys see below:
		loginPage.login(Utilities.generateEmailWithTimeStamp(), dataProp.getProperty("invalidPassword"));//just write Utilities and import the package by hovering on it Then just call the method
		//Now for the invalid creds we need to verify the error message i.e actual and expected.
	 
		//String expectedWarningMessage= dataProp.getProperty("emailPasswordNoMatchWarning");--> Instead of using "expectedWarningMessage" in .contains directly use "dataProp.getProperty("emailPasswordNoMatchWarning")"
		Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText().contains(dataProp.getProperty("emailPasswordNoMatchWarning")),"Expected error message not found");
		
		/*
		 * When the user try to attempt the invalid login attempt, the test case gets failed because of exceeding login attempt 
		 * the account gets blocked so to avoid this just use current date and time and add it to the invalid email, it will not 
		 * fail the test case as every time the time added in the email will be changed
		 * So we need to append (Add) a time stamp with the invalid email address 
		 */
			
	}
		
			
		@Test(priority=3)
		public void verifyLoginWithInvalidEmailAndValidPassword() {
			loginPage.login(Utilities.generateEmailWithTimeStamp(), prop.getProperty("validPassword"));//--> added timestamp method 
			//Now for the invalid creds we need to verify the error message i.e actual and expected.

			Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText().contains(dataProp.getProperty("emailPasswordNoMatchWarning")),"Expected error message not found");
		}
		
		@Test(priority=4)
		public void verifyLoginWithValidEmailAndInvalidPassword() {
			loginPage.login(prop.getProperty("validEmail"), dataProp.getProperty("invalidPassword"));
			//Now for the invalid creds we need to verify the error message i.e actual and expected.

			Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText().contains(dataProp.getProperty("emailPasswordNoMatchWarning")),"Expected error message not found");
		}
	
		
		@Test(priority=5)
		public void verifyLoginWithoutProvidingCredentials() {
//			driver.findElement(By.id("input-email")).sendKeys("");    //-> commented 112 and 113 as instead of passing blank values we can just click on login btn to get error
//			driver.findElement(By.id("input-password")).sendKeys("");
			loginPage.clickOnLoginButton();
			//Now for the invalid creds we need to verify the error message i.e actual and expected.
			
			Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText().contains(dataProp.getProperty("emailPasswordNoMatchWarning")),"Expected error message not found");
			
		}
		
}
