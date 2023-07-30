 package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.SearchPage;

public class SearchTest extends Base{
	
	public WebDriver driver;
	SearchPage searchPage;
	HomePage homePage;
	
	public SearchTest() {
		super();
	}
	
	@BeforeMethod
	public void setUp() {
		driver = initializeBrowserAndOpenApplication(prop.getProperty("browserName"));	
		homePage = new HomePage(driver);
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	
		
	
	@Test(priority=1)
	public void verifySearchingWithValidProduct() {
			
		  searchPage = homePage.searchForAProduct(dataProp.getProperty("validProduct"));

	      Assert.assertTrue(searchPage.displayStatusOfHPValidProduct(),"Valid product HP is not displayed in the search results");
		
	}
	
	@Test(priority=2)
	public void verifySearchWithInvalidProduct() {
		
		  searchPage = homePage.searchForAProduct(dataProp.getProperty("invalidProduct"));
		 
	      Assert.assertEquals( searchPage.retrieveNoProductMessage(), "abcd","No product message is not displayed in search result");
	//dataProp.getProperty("noProductTextInSearchResults")
	}
	
	@Test(priority=3,dependsOnMethods= {"verifySearchWithInvalidProduct","verifySearchingWithValidProduct"})
	public void verifySearchWithoutAnyProduct() {

		
		  //driver.findElement(By.name("search")).sendKeys("");-->we can also remove this line as just by clicking on search without entering any text we get the warning message
		 searchPage = homePage.clickOnSearchButton();// Here we need to locate grand child so used xpath by axes with the word descendant::button
	      //Below we have to assert whether the product not match message is displayed or not
	      Assert.assertEquals(searchPage.retrieveNoProductMessage(), dataProp.getProperty("noProductTextInSearchResults"),"No product message is not displayed in search result");
	
	}

}
