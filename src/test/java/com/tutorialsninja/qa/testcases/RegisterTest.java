package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.AccountSuccessPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.RegisterPage;
import com.tutorialsninja.qa.utils.Utilities;

public class RegisterTest extends Base{   //-->to remove un-used import statements just use--> control+shift+o
    
	public WebDriver driver;
	RegisterPage registerPage;
	AccountSuccessPage accountSuccessPage;
	
    public RegisterTest(){  // here I have created one more constructor (as its name is same as class name also it dont's have return type
		super();       // this super keyword points to the parent class (base class) constructor.
	}
	
	@BeforeMethod
	public void setUp() {
		
		driver = initializeBrowserAndOpenApplication(prop.getProperty("browserName")); //Instead of creating object of the base class we have used extends Base to inherit all the methods from base class
		HomePage homePage = new HomePage(driver);
		registerPage = homePage.navigateToRegisterPage();

	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	
	@Test(priority =1)
	public void verifyRegisteringAnAccountWithMandatoryFields() {
		
		
//		registerPage.enterFirstName(dataProp.getProperty("firstName"));
//		registerPage.enterLastName(dataProp.getProperty("lastName"));
//		registerPage.enterEmailAddress(Utilities.generateEmailWithTimeStamp());//-->Here everytime I need to provide a fresh email Id So use append timestamp here to avoid test fail so we used common time stamp method from Utilities class i.e added it with the help of class name fot method name
//		registerPage.enterTelephone(dataProp.getProperty("telephoneNumber"));
//		registerPage.enterPassword(prop.getProperty("validPassword"));
//		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
//		registerPage.selectPrivacyPolicy();
//		AccountSuccessPage accountSuccessPage = registerPage.clickOnContinueButton();   ***Written below one line instead of these much lines
		
		accountSuccessPage = registerPage.registerWithMandatoryFields(dataProp.getProperty("firstName"), dataProp.getProperty("lastName"), Utilities.generateEmailWithTimeStamp(), dataProp.getProperty("telephoneNumber"), prop.getProperty("validPassword"));
		//Below code is written to verify if the use getting successful account creation message or not.

		Assert.assertEquals(accountSuccessPage.retrieveAccountSuccessPageHeading(), dataProp.getProperty("accountSuccessfullyCreatedHeading"),"Account success page is not displyed");
	
	}
	
	
	@Test(priority=2)
	public void verifyRegisteringAccountByProvidingAllFields() { //I.e considering non mandatory field as well
		
		accountSuccessPage = registerPage.registerWithAllFields(dataProp.getProperty("firstName"), dataProp.getProperty("lastName"), Utilities.generateEmailWithTimeStamp(), dataProp.getProperty("telephoneNumber"), prop.getProperty("validPassword"));
		
		//Below line code is written to verify if the user getting successful account creation message or not.
		Assert.assertEquals(accountSuccessPage.retrieveAccountSuccessPageHeading(), dataProp.getProperty("accountSuccessfullyCreatedHeading"));
	
	}
	
	@Test(priority=3)
	
	
	public void verifyRegisteringAccountWithExistingEmailAddress() {
		registerPage.registerWithAllFields(dataProp.getProperty("firstName"), dataProp.getProperty("lastName"), prop.getProperty("validEmail"), dataProp.getProperty("telephoneNumber"), prop.getProperty("validPassword"));
		//Below line code is written to verify if the use getting error message for already email address or not.
	    Assert.assertTrue(registerPage.retrieveDuplicateEmailAddressWarning().contains(dataProp.getProperty("duplicateEmailWarning")),"Warning message regarding duplicate email address is not displayed");
	
	}
	
	@Test(priority =4)
	public void verifyRegisteringAccountWithoutFillingAnyDetails() {//For this test case we need to verify couple of error messages by using assertions
		
		registerPage.clickOnContinueButton();
		
		Assert.assertTrue(registerPage.displayStatusOfWarningMessages(dataProp.getProperty("privacyPolicyWarning"), dataProp.getProperty("firstNameWarning"), dataProp.getProperty("lastNameWarning"), dataProp.getProperty("emailWarning"), dataProp.getProperty("telephoneWarning"), dataProp.getProperty("passwordWarning")));
		
		
		//we have written above single line for all below assertions for code optimization
		//Below we have used xpath by axes we can also use--> //input[@id='input-firstname']/..//div  xpath for first name
//		Assert.assertEquals(registerPage.retrieveFirstNameWarning(), dataProp.getProperty("firstNameWarning"),"First Name Warning Message is not displayed");
//		
//		Assert.assertEquals(registerPage.retrievelastNameWarning(), dataProp.getProperty("lastNameWarning"),"Last Name Warning Message is not displayed");
//		
//		Assert.assertEquals(registerPage.retrieveEmailWarning(), dataProp.getProperty("emailWarning"),"Email Warning Message is not displayed");
//		
//		Assert.assertEquals(registerPage.retrieveTelephoneWarning(), dataProp.getProperty("telephoneWarning"));
//		
//		Assert.assertEquals(registerPage.retrievePasswordWaring(), dataProp.getProperty("passwordWarning"),"Password Warning Message is not displayed");
//		
		 
	}
	
}
