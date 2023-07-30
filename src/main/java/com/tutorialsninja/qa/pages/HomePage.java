package com.tutorialsninja.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	WebDriver driver;
	/* Objects/ elements : */
	
	@FindBy(xpath="//span[text()='My Account']")
	private WebElement myAccountDropMenu;       //make this private so that it cannot be accessed outside this class
	
	@FindBy(linkText="Login")
	private WebElement loginOption;
	
	@FindBy(linkText="Register")
	private WebElement registerOption;
	
	@FindBy(name="search")
	private WebElement searchBoxField;
	
	@FindBy(xpath ="//div[@id='search']/descendant::button")
	private WebElement SearchButton;
	
	
	public HomePage(WebDriver driver) {    // Created constructor of this class so that when we use this in any test class, so when we create object of this class in any test class , this constructor will be called and it will load the object and we can use this class webElement in any test class.
		
		this.driver = driver;              //its meaning is to this class driver i.e written in line no.10 we have to assign line no.20 driver
		PageFactory.initElements(driver, this);//In this driver --> it driver of this class line no.10 and this --> indicates this class you can also use Hoempage.class instead of this keyword.to auto initialize the element of this class to avoid stale element reference exception here it will attach the element to the locator 
											 // So the moment when the test method call this constructor, it will auto initialize all web elements of this class
	}
	
	/* Actions: */   //Here we performing some action like clicking on something and entering text.
	
	public void clickOnMyAccount() {     //reason for creating these public methods is to access the private webElements outside this class with these public methods
		
		myAccountDropMenu.click();
		
	}
	
	public LoginPage selectLoginOption() {
		
		loginOption.click();
		return new LoginPage(driver);
	}                                //Below method is combine method of these two commented methos just to reduce optimize the code.
	
	public LoginPage navigateToLoginPage() {
		
		myAccountDropMenu.click();
		loginOption.click();
		return new LoginPage(driver);
		
	}
	
	public RegisterPage navigateToRegisterPage() {
		
		myAccountDropMenu.click();
		registerOption.click();
		return new RegisterPage(driver);
	}
	
	public RegisterPage selectRegisterOption() {
		
		registerOption.click();
		return new RegisterPage(driver);
	}
	
	public void enterProdcutNameIntoSearchBoxField(String productText) {
		searchBoxField.sendKeys(productText);
	}
	
	public SearchPage clickOnSearchButton() {
		SearchButton.click();
		return new SearchPage(driver);
	}
	
	public SearchPage searchForAProduct(String productText) {
		searchBoxField.sendKeys(productText);
		SearchButton.click();
		return new SearchPage(driver);
	}
}
