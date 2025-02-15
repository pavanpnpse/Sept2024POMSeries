package com.Qa.openCart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.Qa.openCart.constants.AppConstants;
import com.Qa.openCart.util.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//1 By locator: page objects
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	
	//2 public page actions - methods (features)
	//below step annotation is from allure report
	@Step("getLoginPageTitle")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_TIME_OUT);
		//String title = driver.getTitle();
		System.out.println("login page title===>" + title);
		return title;
	}
	
	//below step annotation is from allure report
	@Step("getLoginPageURL")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION, AppConstants.DEFAULT_TIME_OUT);
		//String url = driver.getCurrentUrl();
		System.out.println("login page url===>" + url);
		return url;
	}
	
	public boolean isForgotPwdLinkExist() {
		//return driver.findElement(forgotPwdLink).isDisplayed();
		return eleUtil.doIsElementDisplayed(forgotPwdLink);
	}
	
	//below step annotation is from allure report
	@Step("Login with username: {0} and password {1} ")
	public String doLogin(String username, String pwd) {
		System.out.println("App cread are:===>" + username + pwd);
		eleUtil.waitForElementVisible(emailId, AppConstants.SHORT_TIME_OUT).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		
		//driver.findElement(emailId).sendKeys(username);
		//driver.findElement(password).sendKeys(pwd);
		//driver.findElement(loginBtn).click();
		return driver.getTitle();
	}

}
